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
                bat './gradlew testDebugUnitTest'
                bat 'dir /s "app\\build\\test-results"' // ✅ Debugging: List all report files
            }
            post {
                always {
                    junit 'app/build/test-results/testDebugUnitTest/*.xml' // ✅ Corrected path
                }
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest --rerun-tasks --info --debug'
                sleep time: 5, unit: 'SECONDS'
                bat 'dir /s "app\\build\\outputs\\androidTest-results"' // ✅ Debugging: List all report files
            }
            post {
                always {
                    junit 'app/build/outputs/androidTest-results/connected/*.xml' // ✅ Corrected path
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
                    if (testResults && testResults.totalCount > 0) {  // ✅ Ensure results exist
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
