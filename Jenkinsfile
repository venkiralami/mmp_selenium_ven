pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JAVA_HOME'
    }
    
	parameters {
        string(name: 'Branch_Name', defaultValue: 'working_v1.0', description: 'Git Branch to be built')
    }
    
    environment {
           SONARQUBE_ENV = 'LocalSonar'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${params.Branch_Name}",
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
post {
        always {
            script {
                // Paths to reports
                def surefireReport = "target/surefire-reports/index.html"
                def extentReport = "target/ExtentReport_*.html"

                // Archive reports so they can be accessed via Jenkins
                archiveArtifacts artifacts: "${surefireReport}, ${extentReport}", fingerprint: true

                // Read Extent report content to extract counts
                def passedCount = 0
                def failedCount = 0
                if (fileExists(extentReport)) {
                    def extentHtml = readFile(extentReport)
                    def passMatch = (extentHtml =~ /class="pass">(\d+)</)
                    def failMatch = (extentHtml =~ /class="fail">(\d+)</)
                    passedCount = passMatch ? passMatch[0][1] : "N/A"
                    failedCount = failMatch ? failMatch[0][1] : "N/A"
                }

                // Build HTML body for email
                def emailBody = """
                    <h2>Automation Test Execution Report</h2>
                    <p><b>Job:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> ${env.BUILD_NUMBER}</p>
                    <p><b>Passed:</b> ${passedCount}</p>
                    <p><b>Failed:</b> ${failedCount}</p>
                    <p><a href="${env.BUILD_URL}artifact/${surefireReport}">Surefire HTML Report</a></p>
                    <p><a href="${env.BUILD_URL}artifact/${extentReport}">Extent HTML Report</a></p>
                """

                // Send email
                emailext(
                    subject: "Test Execution Report - ${currentBuild.currentResult} - Build #${BUILD_NUMBER}",
                    body: emailBody,
                    mimeType: 'text/html',
                    to: 'venki.ralami@gmail.com',
                    attachmentsPattern: extentReport
                )
            }
        }
    }
    }
