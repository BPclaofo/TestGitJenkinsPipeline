node ('master') {
    def response = bat 'C:\\Users\\claofo\\Documents\\Curl\\curl.exe -sX -o /dev/null -w "%%{http_code}" POST http://127.0.0.1:8080/job/PluralsightHelloWorld/build?token=authenticated'
    bat "IF NOT ${response}==000000201 echo Remote Jenkins job failed to start. Exit code ${reponse} & exit 1"
}
