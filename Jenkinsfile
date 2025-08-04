pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JAVA_HOME'
    }

    environment {
        REPORT_DIR = "test-output"
        
       SONARQUBE_ENV = 'LocalSonar'
    
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'working_v1.0',
                    url: 'https://github.com/venkiralami/mmp_selenium_ven.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([reportDir: "${env.REPORT_DIR}",
                             reportFiles: 'ExtentReport.html',
                             reportName: 'Extent Report'])
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
					
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=PlaywrightProject \
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.login=sqa_e9f060a5bab7ae0dae124a41ee67171135152253
                    '''
                    
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            junit 'test-output/testng-results.xml'
        }
        failure {
            mail to: 'venki.ralami@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME}",
                 body: "Check Jenkins for details: ${env.BUILD_URL}"
        }
    }
}
