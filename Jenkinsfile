pipeline {
    agent any

    environment {
        ANDROID_HOME = "C:\\Users\\tarak\\AppData\\Local\\Android\\Sdk"
        GRADLE_OPTS = "-Dorg.gradle.daemon=false"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'dev', url: 'https://github.com/rohitgithub98/MySimpleApp.git'
            }
        }

        stage('Run Unit Tests') {
            steps {
                bat './gradlew testDebugUnitTest --no-daemon'
                bat 'dir /s "app\\build\\test-results"' // ✅ Debugging: Show test files
            }
            post {
                always {
                    junit 'app/build/test-results/testDebugUnitTest/TEST-*.xml' // ✅ Fixed test report path
                }
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest --no-window-animation --rerun-tasks --no-daemon --info --debug'
                sleep time: 5, unit: 'SECONDS'
                bat 'dir /s "app\\build\\outputs\\androidTest-results"' // ✅ Debugging
            }
            post {
                always {
                    script {
                        def testReportPath = 'app/build/outputs/androidTest-results'
                        if (fileExists(testReportPath)) {
                            junit "${testReportPath}/**/TEST-*.xml" // ✅ Scan all subdirectories
                        } else {
                            echo "⚠ No test results found in ${testReportPath}. Skipping."
                        }
                    }
                }
            }
        }

        stage('Build APK') {
            steps {
                bat './gradlew assembleDebug --no-daemon'
            }
        }

        stage('Send Test Report Email') {
            steps {
                script {
                    def testResults = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction)
                    if (testResults && testResults.totalCount > 0) {  // ✅ Ensure results exist before sending email
                        emailext(
                            subject: "Test Results: ${currentBuild.fullDisplayName}",
                            body: """
                            <h3>Jenkins Test Report</h3>
                            <p><b>Total Tests:</b> ${testResults.totalCount}</p>
                            <p><b>Passed:</b> ${testResults.totalCount - testResults.failCount}</p>
                            <p><b>Failed:</b> ${testResults.failCount}</p>
                            <p><a href='${env.BUILD_URL}testReport'>Click here</a> for full details.</p>
                            """,
                            mimeType: 'text/html',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                            to: "tarakarohit@gmail.com"
                        )
                    } else {
                        echo "⚠ No test results found. Skipping email."
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build, Tests Passed, and Email Sent!"
        }
        failure {
            echo "❌ Build or Tests Failed!"
        }
    }
}
