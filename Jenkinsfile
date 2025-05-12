pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the Git repository
                git url: 'https://github.com/jafararcher/Java', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Build the application using Gradle
                sh './gradlew assemble'
            }
        }

        stage('Test') {
            steps {
                // Run the tests using Gradle
                sh './gradlew test'
            }
        }
    }

    post {
        always {
            // Archive test results and build artifacts for review
            junit '**/build/test-results/test/*.xml'
            archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Build and tests succeeded!'
        }
        failure {
            echo 'Build or tests failed!'
        }
    }
}
