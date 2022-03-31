pipeline {
    agent any
    tools {
        jdk 'jdk'
        maven 'maven'

    }
    stages {
        stage("build project") {
            steps {
                echo "Java VERSION"
                sh 'java -version'
                echo "Maven VERSION"
                sh 'mvn -version'
                echo 'building project...'
                sh "mvn compile"
                sh "mvn package"
                sh "mvn test"
                sh "mvn clean install"
            }
        }
        stage("run project") {
            steps {
                sh "mvn spring-boot:run"
            }
        }
    }
}