pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'dev', url: 'https://github.com/rohitgithub98/MySimpleApp.git'
            }
        }

        stage('Run Unit Tests') {
            steps {
                bat './gradlew testDebugUnitTest --no-daemon'
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest --no-daemon --rerun-tasks'
            }
        }

        stage('Build APK') {
            steps {
                bat './gradlew assembleDebug --no-daemon'
            }
        }
    }

    post {
        success {
            script {
                emailext(
                    subject: "✅ Build Successful: ${currentBuild.fullDisplayName}",
                    body: "All tests passed. Build Successful!",
                    mimeType: 'text/plain',
                    to: "tarakarohit@gmail.com"
                )
            }
            echo "✅ Build Successful! Email Sent."
        }
        failure {
            script {
                emailext(
                    subject: "❌ Build Failed: ${currentBuild.fullDisplayName}",
                    body: "Tests failed. Build Unsuccessful!",
                    mimeType: 'text/plain',
                    to: "tarakarohit@gmail.com"
                )
            }
            echo "❌ Build Failed! Email Sent."
        }
    }
}
