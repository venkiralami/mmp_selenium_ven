@Grab('org.jsoup:jsoup:1.17.2')
import org.jsoup.Jsoup

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
           surefireReport = "target/surefire-reports/*"
           extentReport = "target/ExtentReport_*.html"
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
               sh 'mvn clean test'
            }
        }
         stage('Archive Reports') {
            steps {
                // Archive reports so they can be accessed via Jenkins
                archiveArtifacts artifacts: "${surefireReport}, ${extentReport}", fingerprint: true

            }
        }
        
         stage('Email Report') {
            steps {
                script {
                    def passedCount = 0
                    def failedCount = 0
                    def skippedCount = 0

                    if (fileExists(extentReport)) {
                        def html = readFile(extentReport)
                        def doc = Jsoup.parse(html)

                        // Try to locate summary section (update selectors based on your Extent template)
                        // Example: If Extent summary table has a row with these labels
                        passedCount  = doc.select("td:matchesOwn(^Pass\$)").first()?.nextElementSibling()?.text()?.toInteger() ?: 0
                        failedCount  = doc.select("td:matchesOwn(^Fail\$)").first()?.nextElementSibling()?.text()?.toInteger() ?: 0
                        skippedCount = doc.select("td:matchesOwn(^Skip\$)").first()?.nextElementSibling()?.text()?.toInteger() ?: 0
                    } else {
                        echo "Extent report not found at: ${extentReport}"
                    }

                    emailext(
                        to: 'venki.ralami@gmail.com',
                        subject: "Test Report - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                        body: """
                        <html>
                        <body>
                        <h2>Test Execution Summary</h2>
                        <table border="1" cellpadding="5" cellspacing="0">
                          <tr>
                            <th>Passed</th><th>Failed</th><th>Skipped</th>
                          </tr>
                          <tr>
                            <td>${passedCount}</td><td>${failedCount}</td><td>${skippedCount}</td>
                          </tr>
                        </table>
                        <p>Full Extent Report: <a href="${env.BUILD_URL}artifact/${extentReport}">View Report</a></p>
                        </body>
                        </html>
                        """,
                        mimeType: 'text/html'
                    )
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
                    def failMatch = (extentHtml =~ /status="pass">(\d+)</)
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
