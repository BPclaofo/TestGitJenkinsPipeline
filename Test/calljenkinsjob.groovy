node ('master') {
    def response = bat 'C:\\Users\\claofo\\Documents\\Curl\\curl.exe -sIX -w "\%{http_code}" POST http://127.0.0.1:8080/job/PluralsightHelloWorld/build?token=authenticated'
}
