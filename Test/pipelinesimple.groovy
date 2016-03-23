node('master') {
    echo "The job is starting."
    git url: 'https://github.com/BPclaofo/parallel-test-executor-plugin-sample.git'
    stash includes: 'pom.xml, src/', name: 'GitFiles'
}
def splits = splitTests([$class: 'CountDrivenParallelism', size: 4])
def branches = [:]
for (int i = 0; i < splits.size(); i++) {
    def exclusions = splits.get(i);
    branches["split${i}"] = {
        node('slave') {
            deleteDir()
            unstash 'GitFiles'
            writeFile file: 'exclusions.txt', text: exclusions.join("\n")
            bat "${tool 'DefaultMaven'}/bin/mvn -B -Dmaven.test.failure.ignore test"
            step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/*.xml'])
        }
    }
}
parallel branches