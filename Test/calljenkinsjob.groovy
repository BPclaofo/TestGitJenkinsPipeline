node ('master') {
    def response = bat 'C:\\Users\\claofo\\Documents\\Curl\\curl.exe -i -X POST http://127.0.0.1:8080/job/PluralsightHelloWorld/build?token=authenticated'
}
