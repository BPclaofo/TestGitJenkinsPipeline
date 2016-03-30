stage 'Arbitrary Echo'
echo "Hello from pipeline"

stage 'Parameter Echo'
node ('master') {
    sh "echo ${Parameter1}"
    sh "echo ${Parameter2}"
    sh "echo ${Parameter3}"
}

stage 'Run Initial Job'
node ('master') {
    sh "HTTPrep=`curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/FirstTest/buildWithParameters?Parameter=${Parameter1}`; if [ \$HTTPrep = 201 ]; then exit 0; else exit \$HTTPrep; fi"
    echo "Sleeping for 1 seconds"
    sleep 1
}

stage 'Run Jobs in Parallel'
node ('master') {
    try {
        sh "HTTPrep=`curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkin/job/FirstTest/buildWithParameters?Parameter=${Parameter2}`; if [ \$HTTPrep = 201 ]; then exit 0; else exit \$HTTPrep; fi"
    } catch (err) {
        echo "Failed: ${err}"
    }
    sh "HTTPrep=`curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/SecondTest/buildWithParameters?Parameter=${Parameter3}`; if [ \$HTTPrep = 201 ]; then exit 0; else exit \$HTTPrep; fi"
}
