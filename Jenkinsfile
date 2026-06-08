pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t orchestration-api:latest .'
            }
        }

        stage('Run Container') {
            steps {
                bat 'docker run -d -p 8081:8080 orchestration-api:latest'
            }
        }
    }
}