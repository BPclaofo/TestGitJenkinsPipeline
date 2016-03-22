echo 'hello from Pipeline'
echo TestParam
node('master') {
    input 'Run on master?'
    git url: 'https://github.com/BPclaofo/simple-maven-project-with-tests.git'
    def v = version(readFile('pom.xml'))
    if (v) {
        echo "Building version ${v}"
    }
    withEnv(["PATH+MAVEN=${tool 'DefaultMaven'}/bin"]){
        bat 'mvn -B verify -Dmaven.test.failure.ignore verify'
    }
    // def mvnHome = tool 'DefaultMaven'
    // env.PATH = "${mvnHome}/bin:${env.PATH}"
    // bat 'mvn -B verify'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
}

@NonCPS
def version(text) {
    def matcher = text =~ '<version>(.+)</version>'
    matcher ? matcher[0][1 as String] : null
}

node('slave') {
    input 'Run on slave?'
    git url: 'https://github.com/BPclaofo/simple-maven-project-with-tests.git'
    withEnv(["PATH+MAVEN=${tool 'DefaultMaven'}/bin"]){
        bat 'mvn -B verify -Dmaven.test.failure.ignore verify'
    }
    // def mvnHome = tool 'DefaultMaven'
    // env.PATH = "${mvnHome}/bin:${env.PATH}"
    // bat 'mvn -B verify'
    step([$class: 'ArtifactArchiver', artifacts: '**/target/*.jar', fingerprint: true])
    step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
}
