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
                echo "✅ Build Successful! Preparing to send email..."
                emailext(
                    subject: "✅ Build Successful: ${currentBuild.fullDisplayName}",
                    body: """
                    Hello,

                    The Jenkins build for your project **${env.JOB_NAME}** has **passed** successfully! 🎉

                    - **Build Number**: ${env.BUILD_NUMBER}
                    - **Job Name**: ${env.JOB_NAME}
                    - **Branch**: ${env.GIT_BRANCH}
                    - **Build URL**: ${env.BUILD_URL}

                    ✅ All tests passed, and the build is ready for deployment.

                    Regards,
                    Jenkins
                    """,
                    mimeType: 'text/plain',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                    to: "tarakarohit@gmail.com"
                )
                echo "📧 Email sent successfully!"
            }
        }
        failure {
            script {
                echo "❌ Build Failed! Preparing to send email..."
                emailext(
                    subject: "❌ Build Failed: ${currentBuild.fullDisplayName}",
                    body: """
                    Hello,

                    The Jenkins build for your project **${env.JOB_NAME}** has **failed** ❌.

                    - **Build Number**: ${env.BUILD_NUMBER}
                    - **Job Name**: ${env.JOB_NAME}
                    - **Branch**: ${env.GIT_BRANCH}
                    - **Build URL**: ${env.BUILD_URL}

                    ❗ Please check the Jenkins logs for details.

                    Regards,
                    Jenkins
                    """,
                    mimeType: 'text/plain',
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                    to: "tarakarohit@gmail.com"
                )
                echo "📧 Failure email sent successfully!"
            }
        }
    }
}
