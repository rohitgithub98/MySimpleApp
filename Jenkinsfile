pipeline {
    agent any

    environment {
        APP_CENTER_API_TOKEN = credentials('APP_CENTER_API_TOKEN') // Secure API Token
        APP_NAME = "TarakaRohit/MySimpleApp"  // Replace with your App Center app name
        APK_PATH = "app/build/outputs/apk/debug/app-debug.apk" // Path to the generated APK
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

        stage('Upload to Microsoft App Center') {
            steps {
                script {
                    echo "🚀 Uploading APK to Microsoft App Center..."
                    bat """
                    curl -X POST "https://api.appcenter.ms/v0.1/apps/${APP_NAME}/release_uploads" ^
                         -H "X-API-Token: ${APP_CENTER_API_TOKEN}" ^
                         -H "Content-Type: application/json" ^
                         -d "{}" > upload_url.json

                    for /f "tokens=2 delims=: " %%a in ('findstr id upload_url.json') do set UPLOAD_ID=%%a
                    curl -F "ipa=@${APK_PATH}" ^
                         -H "X-API-Token: ${APP_CENTER_API_TOKEN}" ^
                         "https://file.appcenter.ms/upload/%UPLOAD_ID%"
                    """
                }
            }
        }
    }

    post {
        success {
            script {
                echo "✅ Build & Upload Successful! Sending email via PowerShell..."
                powershell '''
                $SMTPServer = "smtp.gmail.com"
                $SMTPPort = "587"
                $Username = "tarakarohit@gmail.com"
                $Password = "ohsr qmyt wmdx ewhr"  # Use your App Password here
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")  # ✅ Professional-looking "From" name
                $Message.To.Add("tarakarohit@gmail.com")
                $Message.Subject = "✅ Jenkins Build & Upload Successful"
                $Message.Body = "All tests passed. APK has been uploaded to Microsoft App Center!"
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
                echo "❌ Build or Upload Failed! Sending email via PowerShell..."
                powershell '''
                $SMTPServer = "smtp.gmail.com"
                $SMTPPort = "587"
                $Username = "tarakarohit@gmail.com"
                $Password = "ohsr qmyt wmdx ewhr"  # Use your App Password here
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")  # ✅ Professional-looking "From" name
                $Message.To.Add("tarakarohit@gmail.com")
                $Message.Subject = "❌ Jenkins Build or Upload Failed"
                $Message.Body = "Something went wrong. Check Jenkins logs!"
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
