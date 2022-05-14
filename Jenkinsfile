pipeline {
    agent any
    stages{
        stage('Maven clean '){
            steps 'sh mvn clean'
        }
        stage('Run the API tests'){
            steps 'sh mvn test'
        }
        post{
            junit 'target/surefire-reports/junitreports/*.xml'
        }
    }
}