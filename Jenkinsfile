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
            }
            post {
                always {
                    junit 'app/build/test-results/testDebugUnitTest/TEST-*.xml'  // ✅ Fixed path
                }
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest'
            }
            post {
                always {
                    junit 'app/build/outputs/androidTest-results/connected/*.xml' // ✅ Ensure Android test results
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
                    def total = testResults?.totalCount ?: 0
                    def failed = testResults?.failCount ?: 0
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
                        to: "your-email@gmail.com",
                        attachLog: true
                    )
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
