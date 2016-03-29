node ('master') {
    stage 'Rest API Call'
    response = bash 'if [ `curl -s -w %{http_code} -X POST http://reuxgbls535.bp.com:8110/jenkins/job/FirstTest/buildWithParameters?Parameter=test` = "201" ]; then echo "Success"; fi'
    echo "${response}"
}
