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
        EXTENT_REPORT_PATTERN = "target/ExtentReport_*.html"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out branch: ${params.Branch_Name}"
    }
}
}
   
    post {
        always {
            script {
				 // Read the XML file as plain text
                def xmlContent = readFile 'target/surefire-reports/testng-results.xml'

                // Extract numbers using regex — no XmlSlurper, no sandbox approval
                def total = (xmlContent =~ /total="(\d+)"/)[0][1]
                def passed = (xmlContent =~ /passed="(\d+)"/)[0][1]
                def failed = (xmlContent =~ /failed="(\d+)"/)[0][1]
                def skipped = (xmlContent =~ /skipped="(\d+)"/)[0][1]
				def ignored = (xmlContent =~ /ignored="(\d+)"/)[0][1]
                				
                				// Find all extent report HTML files matching pattern recursively
                    def reportFiles = findFiles(glob: 'target/ExtentReport_*.html')

                    if (reportFiles.length == 0) {
                        echo "No extent report files found."
                    } else {
                        echo "Found extent report files:"
                        for (file in reportFiles) {
                            // file.path is relative to the workspace root
                            echo " - ${file.path}"
                              env.EXTENT_REPORT_PATTERN =  ${file.path}
                              
                            // To get absolute path on the agent:
                            echo "Absolute path: ${env.WORKSPACE}/${file.path}"
                        }
                    }
               
               def extentReportHtml = readFile file: "${env.EXTENT_REPORT_PATTERN}"

                // Compose summary table HTML for email
                def summaryTable = """
                    <h4>TestNG Execution Summary</h4>
                    <table border="1" cellpadding="5" cellspacing="0">
                        <tr><th>Total</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Ignored</th></tr>
                        <tr>
                        	<td style='color:blue'>${total}</td>
                            <td style='color:green'>${passed}</td>
                            <td style='color:red'>${failed}</td>
                            <td style='color:orange'>${skipped}</td>
                            <td style='color:yellow'>${ignored}</td>
                        </tr>
                    </table>
                """

                // Combine summary and full report for email body
                def emailBody = """
                   <br><hr><br>  
                    ${summaryTable}
                    <br><br>                    
                    <h4> Jenkins Build URL: </h4> <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a>
                    <br><br>
    				<h4>Extent Report: </h4> 📊<a href="${env.BUILD_URL}artifact/${env.EXTENT_REPORT_PATTERN}">View Full Extent Report</a>
    				<br><br>
                    <h4>Extent Report Content: </h4>
                     <br><br>
                    ${extentReportHtml}
                    <br><hr><br>
                    
                """

                // Send Email
                emailext(
                    subject: "Test Execution Report - - Build #${env.BUILD_NUMBER} :: ${currentBuild.currentResult}",
                    body: emailBody,
                    to: "venki.ralami@gmail.com",
                    mimeType: 'text/html',
                    attachmentsPattern: '${extentReportHtml} ${extentReportPath}'
                )
                
                  // Archive the ExtentReport artifact
                  archiveArtifacts artifacts: "${SUREFIRE_REPORT_PATTERN}, ${env.EXTENT_REPORT_PATTERN}", fingerprint: true
            
            }
        }
    }
}
