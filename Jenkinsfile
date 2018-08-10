pipeline {
    agent any 
    tools { 
        jdk 'Oracle JDK 8 (latest)'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh "./mvnw -version"
                sh "gpg --version"
            }
        }
        stage('Build') { 
            steps {
                withCredentials( [ 
                        string(credentialsId: 'sonar_login', variable: 'SONAR_LOGIN') 
                                 ] ) {
                    sh "./mvnw clean deploy jacoco:report sonar:sonar -U -B -P sonatype-oss-release -s /private/jenkins/settings.xml -Dsonar.organization=fuinorg -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${SONAR_LOGIN}"
                } 
            }
        }
    }
}
