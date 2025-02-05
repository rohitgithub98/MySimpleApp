pipeline {
    agent any

    environment {
        SONAR_SCANNER = tool name: 'SonarQube Scanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat '''
                    "%SONAR_SCANNER%/bin/sonar-scanner" ^
                    -D"sonar.projectKey=MySimpleApp" ^
                    -D"sonar.sources=app/src" ^
                    -D"sonar.host.url=http://localhost:9000" ^
                    -D"sonar.login=${SONAR_TOKEN}"
                    '''
                }
            }
        }

        // ✅ Archive APK for other jobs
        stage('Archive APK') {
             steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/app-debug.apk', fingerprint: true
             }
        }
    }

    post {
        success {
            script {
                echo "✅ Build Successful! Sending email via PowerShell..."
                powershell '''
                $SMTPServer = "smtp.gmail.com"
                $SMTPPort = "587"
                $Username = "tarakarohit@gmail.com"
                $Password = "ohsr qmyt wmdx ewhr"
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")
                $Message.To.Add("tarakarohit@gmail.com")
                $Message.Subject = "✅ Jenkins Build Successful"
                $Message.Body = "All tests passed. Build Successful!"
                $SMTPClient = New-Object System.Net.Mail.SmtpClient($SMTPServer, $SMTPPort)
                $SMTPClient.EnableSsl = $true
                $SMTPClient.Credentials = New-Object System.Net.NetworkCredential($Username, $Password)
                $SMTPClient.Send($Message)
                '''
            }
            echo "📧 Email Sent!"
        }

        failure {
            script {
                echo "❌ Build Failed! Sending email via PowerShell..."
                powershell '''
                $SMTPServer = "smtp.gmail.com"
                $SMTPPort = "587"
                $Username = "tarakarohit@gmail.com"
                $Password = "ohsr qmyt wmdx ewhr"
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")
                $Message.To.Add("tarakarohit@gmail.com")
                $Message.Subject = "❌ Jenkins Build Failed"
                $Message.Body = "Tests failed. Build Unsuccessful!"
                $SMTPClient = New-Object System.Net.Mail.SmtpClient($SMTPServer, $SMTPPort)
                $SMTPClient.EnableSsl = $true
                $SMTPClient.Credentials = New-Object System.Net.NetworkCredential($Username, $Password)
                $SMTPClient.Send($Message)
                '''
            }
            echo "📧 Email Sent!"
        }
    }
}
