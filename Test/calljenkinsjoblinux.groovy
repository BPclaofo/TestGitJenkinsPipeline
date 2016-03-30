node ('master') {
    stage 'Rest API Call'
    response = sh 'HTTPrep=`curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/FirstTest/buildWithParameters?Parameter=test`; if [HTTPrep = \"201\" ]; then exit 0; else exit HTTPrep; fi'
    stage 'Echo Rest API HTTP response'
    echo "${response}"
}
