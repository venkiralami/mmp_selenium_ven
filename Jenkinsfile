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
        SUREFIRE_REPORT_PATTERN = "target/surefire-reports/testng-results.xml"
        EXTENT_REPORT_PATTERN = "target/ExtentReport_*.html"  // default pattern
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out branch: ${params.Branch_Name}"
                // Add actual Git checkout here
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
