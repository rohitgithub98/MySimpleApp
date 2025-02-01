pipeline {
    agent any

    environment {
        ANDROID_HOME = "$HOME/Android/Sdk"
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
                bat './gradlew test'
            }
        }

        stage('Run Instrumentation Tests') {
            steps {
                bat './gradlew connectedAndroidTest'
            }
        }

        stage('Build APK') {
            steps {
                bat './gradlew assembleDebug'
            }
        }
    }

    post {
        success {
            echo "✅ Build and Tests Passed!"
        }
        failure {
            echo "❌ Build Failed!"
        }
    }
}
