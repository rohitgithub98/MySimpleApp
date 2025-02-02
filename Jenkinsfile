pipeline {
    agent any

    environment {
        ANDROID_HOME = "C:\\Users\\tarak\\AppData\\Local\\Android\\Sdk"
        GRADLE_OPTS = "-Dorg.gradle.daemon=false"
        SMTP_SERVER = "smtp.gmail.com"
        SMTP_PORT = "587"
        SMTP_USER = "tarakarohit@gmail.com"
        SMTP_PASSWORD = credentials('SMTP_APP_PASSWORD')  // 🔥 Store App Password securely in Jenkins credentials
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
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                    to: "tarakarohit@gmail.com",
                    replyTo: "${env.SMTP_USER}",
                    from: "${env.SMTP_USER}",
                    smtpServer: "${env.SMTP_SERVER}",
                    smtpPort: "${env.SMTP_PORT}",
                    useTLS: true
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
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                    to: "tarakarohit@gmail.com",
                    replyTo: "${env.SMTP_USER}",
                    from: "${env.SMTP_USER}",
                    smtpServer: "${env.SMTP_SERVER}",
                    smtpPort: "${env.SMTP_PORT}",
                    useTLS: true
                )
            }
            echo "❌ Build Failed! Email Sent."
        }
    }
}
