pipeline {
    agent any
    stages{
        stage('Build jar files'){
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Build the docker image'){
            steps{
                sh 'docker build -t asuhail8/docker-rest .'
            }
        }
        stage('Push the docker image to docker hub'){
            steps{
                sh 'docker push asuhail8/docker-rest'
            }
        }
        stage('Run the automated tests on docker container'){
            steps{
                sh "docker run -v /Users/abdullasuhail/Desktop/Work/Java' 'Projects/Rest/test-output/outputOnDocker:/restapi/test-output asuhail8/docker-rest"
            }
             post{
                always {
                         junit 'test-output/outputOnDocker/junitreports/*.xml'
                         emailext attachmentsPattern: 'test-output/outputOnDocker/emailable-report.html', body: '$DEFAULT_CONTENT', subject: '$DEFAULT_SUBJECT', to: '$DEFAULT_RECIPIENTS'
                }
        }
        }
       
        
    }
}