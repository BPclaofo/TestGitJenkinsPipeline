node ('master') {
    stage 'Rest API Call'
    def response = bat 'C:\\Users\\claofo\\Documents\\Curl\\curl.exe -s -w %%{http_code} -X POST http://127.0.0.1:8080/job/PluralsightHelloWorld/build?token=authenticated%>tmp.txt & set /p Response=\<tmp.txt & IF NOT %%Response%%==201 exit 1'
    stage 'Condition Check'
    bat "IF NOT ${response}==0 echo Remote Jenkins job failed to start. Exit code (placeholder) & exit 1"
}
