pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JAVA_HOME'
    }

    environment {
        REPORT_DIR = "test-output"
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
