pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JAVA_HOME'
    }

    environment {
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
        
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_ENV}") {
					
                    sh '''
                       mvn jacoco:report sonar:sonar \
   						 -Dsonar.projectKey=mmp_selenium_ven \
   						 -Dsonar.host.url=http://localhost:9000 \
   						 -Dsonar.login=sqa_e9f060a5bab7ae0dae124a41ee67171135152253 \
  					 	 -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                    
                }
            }
        }

        stage('Publish HTML Report') {
    steps {
        script {
            publishHTML([
                reportDir: 'target/surefire-reports',
                reportFiles: 'index.html',
                reportName: 'Surefire Report',
                keepAll: true,
                allowMissing: false,
                alwaysLinkToLastBuild: true
            ])
        }
    }
}

        
    }
/*
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
    */
    }
