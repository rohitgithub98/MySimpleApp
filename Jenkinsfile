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

        // ✅ Archive APK so it can be used by other jobs
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
                $Password = "ohsr qmyt wmdx ewhr"  # Use your App Password here
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")  # ✅ Professional-looking "From" name
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
                $Password = "ohsr qmyt wmdx ewhr"  # Use your App Password here
                $Message = New-Object System.Net.Mail.MailMessage
                $Message.From = New-Object System.Net.Mail.MailAddress("tarakarohit@gmail.com", "Jenkins CI Server")  # ✅ Professional-looking "From" name
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