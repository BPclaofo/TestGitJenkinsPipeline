node ('master') {
    stage 'Rest API Call'
    response = sh 'if [ `curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/FirstTest/buildWithParameters?Parameter=test` != \"201\" ]; then return 13; fi'
    stage 'Echo Rest API HTTP response'
    echo "${response}"
}
