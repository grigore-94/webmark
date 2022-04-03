pipeline {
  agent { label 'linux' }
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  environment {
    HEROKU_API_KEY = credentials('heroku-api-key')
  }
  parameters {
    string(name: 'APP_NAME', defaultValue: 'webmark-1123', description: 'app name ?')
  }
  stages {
    stage('Build') {
      steps {
        print "build"
        bat 'docker build -t grigore-94/webmark-1123:latest .'
      }
    }
    stage('Login') {
      steps {
        bat 'echo $HEROKU_API_KEY | docker login --username=_ --password-stdin registry.heroku.com'
      }
    }
    stage('Push to Heroku registry') {
      steps {
        bat '''
          docker tag grigore-94/webmark-1123:latest registry.heroku.com/$APP_NAME/web
          docker push registry.heroku.com/$APP_NAME/web
        '''
      }
    }
    stage('Release the image') {
      steps {
        bat '''
          heroku container:release web --app=$APP_NAME
        '''
      }
    }
  }
  post {
    always {
      bat 'docker logout'
    }
  }
}