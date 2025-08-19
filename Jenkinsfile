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
         SONAR_PROJECT_KEY = 'mmp_selenium_ven'
        SONAR_URL = 'http://localhost:9000'
        SUREFIRE_REPORT_PATTERN = "target/surefire-reports/testng-results.xml"
        EXTENT_REPORT_PATTERN = "target/ExtentReport_*.html"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${params.Branch_Name}",
                    url: 'https://github.com/venkiralami/mmp_selenium_ven.git'
            }
        }
        
		stage('Run Tests on Grid') {
            steps {
                sh 'mvn clean test'
            }
        }
        
        
        
        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: "${SUREFIRE_REPORT_PATTERN}, ${EXTENT_REPORT_PATTERN}", fingerprint: true
            }
        }

        stage('Parse Extent Report') {
            steps {
                script {
                    // Find the actual extent report file (resolve wildcard)
                    def extentFiles = findFiles(glob: "${EXTENT_REPORT_PATTERN}")
                    def extentFile = extentFiles ? extentFiles[0].path : null

                    def passedCount = "0"
                    def failedCount = "0"
                    def skippedCount = "0"

                    if (extentFile && fileExists(extentFile)) {
                        def content = readFile(extentFile)

                        def passMatch = content =~ /Tests Passed<\/.*?>(\d+)/
                        def failMatch = content =~ /Tests Failed<\/.*?>(\d+)/
                        def skipMatch = content =~ /Tests Skipped<\/.*?>(\d+)/
						echo "Passed count found: ${passMatch}"
						echo "Failed count found: ${failMatch}"
						echo "Skipped count found: ${skipMatch}"
                        passedCount  = passMatch ? passMatch[0][1] : "12"
                        failedCount  = failMatch ? failMatch[0][1] : "6"
                        skippedCount = skipMatch ? skipMatch[0][1] : "3"
                    } else {
                        echo "Extent report not found!"
                    }
                    echo "Passed count found: ${passedCount}"
						echo "Failed count found: ${failedCount}"
						echo "Skipped count found: ${skippedCount}"
 				if(passedCount!="0") {
					 env.PASSED_COUNT = passedCount
  					 echo "Passed count found: ${passedCount}"
				} else {
  					 echo "No Passed count found: ${passedCount}"
  					 passedCount = 10
				}
                    env.PASSED_COUNT = passedCount
                    env.FAILED_COUNT = failedCount
                    env.SKIPPED_COUNT = skippedCount
                    env.EXTENT_REPORT_FILE = extentFile ?: ''
                }
            }
        }
        
        stage('Parse Extent Report Latest') {
    steps {
        script {
            def extentFiles = findFiles(glob: "${EXTENT_REPORT_PATTERN}")
            def extentFile = extentFiles ? extentFiles[0].path : null

            def passedCount = 0
            def failedCount = 0
            def skippedCount = 0

            if (extentFile && fileExists(extentFile)) {
                def content = readFile(extentFile)

                // Regex tuned for your Extent report format
                def passMatch = content =~ /Tests Passed\s*([\d]+)/
                def failMatch = content =~ /Tests Failed\s*([\d]+)/
                def skipMatch = content =~ /Tests Skipped\s*([\d]+)/

                passedCount  = passMatch ? passMatch[0][1].toInteger() : 0
                failedCount  = failMatch ? failMatch[0][1].toInteger() : 0
                skippedCount = skipMatch ? skipMatch[0][1].toInteger() : 0

                echo "Extent Report Parsed -> Passed: ${passedCount}, Failed: ${failedCount}, Skipped: ${skippedCount}"
            } else {
                echo "❌ Extent report not found at ${EXTENT_REPORT_PATTERN}"
            }

            // Calculate totals
            def totalTests = passedCount + failedCount + skippedCount
            def passPercentage = (totalTests > 0) ? ((passedCount * 100) / totalTests) : 0

            echo "📊 Total Tests: ${totalTests}, Pass %: ${passPercentage}%"

            // Export to environment variables
            env.PASSED_COUNT     = passedCount.toString()
            env.FAILED_COUNT     = failedCount.toString()
            env.SKIPPED_COUNT    = skippedCount.toString()
            env.TOTAL_TESTS      = totalTests.toString()
            env.PASS_PERCENTAGE  = passPercentage.toString()
            env.EXTENT_REPORT_FILE = extentFile ?: ''
        }
    }
}

stage('Format Extent Report Summary Latest') {
    steps {
        script {
            // Build a styled HTML table
            def htmlReport = """
                <html>
                <head>
                  <style>
                    table {
                      border-collapse: collapse;
                      width: 50%;
                      font-family: Arial, sans-serif;
                      margin: 10px 0;
                    }
                    th, td {
                      border: 1px solid #ddd;
                      padding: 8px;
                      text-align: center;
                    }
                    th {
                      background-color: #4CAF50;
                      color: white;
                    }
                    .pass { background-color: #c8e6c9; }   /* green */
                    .fail { background-color: #ffcdd2; }   /* red */
                    .skip { background-color: #fff9c4; }   /* yellow */
                  </style>
                </head>
                <body>
                  <h3>📊 Test Execution Summary</h3>
                  <table>
                    <tr>
                      <th>Metric</th>
                      <th>Count</th>
                    </tr>
                    <tr class="pass">
                      <td>✅ Passed</td>
                      <td>${env.PASSED_COUNT}</td>
                    </tr>
                    <tr class="fail">
                      <td>❌ Failed</td>
                      <td>${env.FAILED_COUNT}</td>
                    </tr>
                    <tr class="skip">
                      <td>⚠️ Skipped</td>
                      <td>${env.SKIPPED_COUNT}</td>
                    </tr>
                    <tr>
                      <td><b>Total Tests</b></td>
                      <td><b>${env.TOTAL_TESTS}</b></td>
                    </tr>
                    <tr>
                      <td><b>Pass %</b></td>
                      <td><b>${env.PASS_PERCENTAGE}%</b></td>
                    </tr>
                  </table>
                  <p>📄 Full report: <a href="file://${env.WORKSPACE}/${env.EXTENT_REPORT_FILE}">Extent Report</a></p>
                </body>
                </html>
            """

            // Save HTML summary for email/Slack
            writeFile file: "summary.html", text: htmlReport
            env.SUMMARY_HTML = readFile("summary.html")
        }
    }
}
 stage('Send Email Latest') {
            steps {
                script {
                   
                    emailext(
            subject: "📢 Test Execution Results: ${currentBuild.currentResult}",
            to: "team@example.com",
            mimeType: 'text/html',
            body: "${env.SUMMARY_HTML}"
        )
                }
            }
        }
        
        stage('Send Email') {
            steps {
                script {
                    def surefireReport = "target/surefire-reports/index.html"
                    def extentReportFile = env.EXTENT_REPORT_FILE

                    emailext(
                        subject: "Test Execution Report - ${currentBuild.fullDisplayName}",
                        body: """
                            <h3>Automation Test Summary</h3>
                            <table border="1" cellpadding="5">
                                <tr><th>Passed</th><th>Failed</th><th>Skipped</th></tr>
                                <tr>
                                    <td style="color:green">${env.PASSED_COUNT}</td>
                                    <td style="color:red">${env.FAILED_COUNT}</td>
                                    <td style="color:orange">${env.SKIPPED_COUNT}</td>
                                </tr>
                            </table>
                            <br>
                            <b>Extent Report:</b> <a href="${BUILD_URL}artifact/${extentReportFile}">View Report</a><br>
                            <b>Surefire Report:</b> <a href="${BUILD_URL}artifact/${surefireReport}">View Report</a>
                            <br><br>
                            <i>Generated by Jenkins on ${new Date()}</i>
                        """,
                        to: 'venki.ralami@gmail.com',
                        mimeType: "text/html",
                        attachmentsPattern: extentReportFile ?: ''
                    )
                }
            }
        }

        stage('Publish HTML Report') {
            steps {
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
                // Read TestNG XML file
                def xmlContent = readFile "${env.SUREFIRE_REPORT_PATTERN}"

                def total   = (xmlContent =~ /total="(\d+)"/)[0][1]
                def passed  = (xmlContent =~ /passed="(\d+)"/)[0][1]
                def failed  = (xmlContent =~ /failed="(\d+)"/)[0][1]
                def skipped = (xmlContent =~ /skipped="(\d+)"/)[0][1]
                def ignored = (xmlContent =~ /ignored="(\d+)"/)[0][1]

                // Resolve extent report file
                def reportFiles = findFiles(glob: env.EXTENT_REPORT_PATTERN)
                def extentReportPath = reportFiles ? reportFiles[0].path : null

                if (!extentReportPath) {
                    echo "No extent report found matching ${env.EXTENT_REPORT_PATTERN}"
                    extentReportPath = ""
                } else {
                    echo "Found extent report: ${extentReportPath}"
                }

                // Read the first matching extent report content (if exists)
                def extentReportHtml = extentReportPath ? readFile(extentReportPath) : "<p>No extent report found.</p>"

                // Summary table
                def summaryTable = """
                    <h4>TestNG Execution Summary</h4>
                    <table border="1" cellpadding="5" cellspacing="0">
                        <tr><th>Total</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Ignored</th></tr>
                        <tr>
                            <td style='color:blue'><b>${total}</b></td>
                            <td style='color:green'><b>${passed}</b></td>
                            <td style='color:red'><b>${failed}</b></td>
                            <td style='color:orange'><b>${skipped}</b></td>
                            <td style='color:purple'><b>${ignored}</b></td>
                        </tr>
                    </table>
                """

                // Email body
                def emailBody = """
                    ${summaryTable}
                    <br><br>
                    <h4>Jenkins Build URL:</h4>
                    <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a>
                    <br><br>
                    <h4>Extent Report:</h4>
                    📊 <a href="${env.BUILD_URL}artifact/${extentReportPath}">View Full Extent Report</a>
                    <br><br>
                    <p>🔗 <a href="${SONAR_URL}/dashboard?id=${SONAR_PROJECT_KEY}">View Full SonarQube Report</a></p>
                    <br><br>
                    ${extentReportHtml}
                """

                emailext(
                    subject: "Test Execution Report - Build #${env.BUILD_NUMBER} :: ${currentBuild.currentResult}",
                    body: emailBody,
                    to: "venki.ralami@gmail.com",
                    mimeType: 'text/html',
                    attachmentsPattern: extentReportPath
                )

                // Archive artifacts
                archiveArtifacts artifacts: "${env.SUREFIRE_REPORT_PATTERN}, ${env.EXTENT_REPORT_PATTERN}", fingerprint: true
            }
        }
    }
}
