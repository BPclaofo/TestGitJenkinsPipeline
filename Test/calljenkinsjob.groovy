node ('master') {
    def response = bat 'C:\\Users\\claofo\\Documents\\Curl\\curl.exe -sX -o /dev/null -w "%%{http_code}" POST http://127.0.0.1:8080/job/PluralsightHelloWorlds/build?token=authenticated'
    echo response.toString()
}
