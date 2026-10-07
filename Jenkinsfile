pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t employee-management:latest .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop employee-management || true
                    docker rm employee-management || true

                    docker run -d \
                      --name employee-management \
                      --restart unless-stopped \
                      -p 9090:9090 \
                      employee-management:latest
                '''
            }
        }
    }
}