pipeline{
    agent any
    environment {
      AWS_ACCESS_KEY_ID=credentials('aws-key')
      AWS_SECRET_ACCESS_KEY= credentials('aws-secret')
    }
    stages {
        stage('Prepare') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Build Gradle') {
            agent{
                docker {
                    image 'openjdk:8-jdk-alpine'
                    args '-v $HOME/.gradle:/root/.gradle'
                    reuseNode true
                }
            }

            steps {
                sh './gradlew clean build'
            }
            post {
                failure {
                    echo 'build failed'
                    slackSend color: 'good',
                              message: "Build Failed Check it 빌드 실패!! ${BUILD_TAG}  ${BUILD_URL}"
                }
            }
        }

        stage('Dockering') {
            environment{
                DOCKER_HOST="3.37.2.79:2375"
                APP_NAME='copang'
            }
            steps {
                echo 'docker build start'
                sh 'docker-compose -v'
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -f docker-compose.yml rm -f"
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -f docker-compose.yml -p ${APP_NAME} -f docker-compose.yml pull"
                sh "DOCKER_HOST=${DOCKER_HOST} docker-compose -f docker-compose.yml -p ${APP_NAME} -f docker-compose.yml up -d --build"
            }

            post {
                success {
                    echo 'deploy success'
                    slackSend color: 'good',
                              message: "배포 성공했습니다!!  ${BUILD_TAG}    ${BUILD_URL}"
                }

                failure {
                    echo 'deploy failed'
                    slackSend color: 'bad',
                              message: "배포에 실패 했습니다!!  ${BUILD_TAG}    ${BUILD_URL}"
                }
            }
        }
    }
}

