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
                bat './gradlew testDebugUnitTest --rerun-tasks'  // 🔥 Force test re-run
                sleep time: 5, unit: 'SECONDS'  // 🔥 Wait for test reports to generate
            }
            post {
                always {
                    // ✅ Corrected test report path
                    junit 'app/build/test-results/testDebugUnitTest/TEST-*.xml'
                }
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest --rerun-tasks'  // 🔥 Force test re-run
                sleep time: 5, unit: 'SECONDS'  // 🔥 Wait for test reports to generate
            }
            post {
                always {
                    // ✅ Corrected test report path
                    junit 'app/build/outputs/androidTest-results/connected/TEST-*.xml'
                }
            }
        }

        stage('Build APK') {
            steps {
                bat './gradlew assembleDebug'
            }
        }

        stage('Send Test Report Email') {
            steps {
                script {
                    def testResults = currentBuild.rawBuild.getAction(hudson.tasks.junit.TestResultAction)

                    if (testResults) {
                        def total = testResults.totalCount
                        def failed = testResults.failCount
                        def passed = total - failed
                        def reportUrl = "${env.BUILD_URL}testReport"

                        emailext(
                            subject: "Test Results: ${currentBuild.fullDisplayName}",
                            body: """
                            <h3>Jenkins Test Report</h3>
                            <p><b>Total Tests:</b> ${total}</p>
                            <p><b>Passed:</b> ${passed}</p>
                            <p><b>Failed:</b> ${failed}</p>
                            <p><a href='${reportUrl}'>Click here</a> for full details.</p>
                            """,
                            mimeType: 'text/html',
                            recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                            to: "tarakarohit@gmail.com",
                            attachLog: false // 🔥 Prevent large email size
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
