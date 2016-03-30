node ('master') {
    stage 'Rest API Call'
    response = sh 'curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/FirstTest/buildWithParameters?Parameter=test'
    stage 'Echo Rest API HTTP response'
    echo "${response}"
}
