echo 'hello from Pipeline'
node('master') {
    stage 'Checkout'
    // input 'Run job?'
    git url: 'https://github.com/BPclaofo/parallel-test-executor-plugin-sample.git'
    stash includes: 'pom.xml, src/', name: 'GitFiles'
}
stage 'Split Tests'
def splits = splitTests([$class: 'CountDrivenParallelism', size: 2])
def branches = [:]
for (int i = 0; i < splits.size(); i++) {
    stage 'Run Build and Tests'
    def exclusions = splits.get(i);
    branches["split${i}"] = {
        node('slave') {
            deleteDir()
            unstash 'GitFiles'
            writeFile file: 'exclusions.txt', text: exclusions.join("\n")
            def v = version(readFile('pom.xml'))
            if (v) {
                echo "Building version ${v}"
            }
            withEnv(["PATH+MAVEN=${tool 'DefaultMaven'}/bin"]){
                bat 'mvn -B verify -Dmaven.test.failure.ignore verify'
            }
            step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
        }
    }
}
parallel branches

node {
    stage 'Load a Separate Pipeline'
    git 'https://github.com/BPclaofo/TestSideGitJenkinsPlugin.git'
    load 'Main/HelloWorld.groovy'
}()

@NonCPS
def version(text) {
    def matcher = text =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}