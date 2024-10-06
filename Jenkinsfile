pipeline {
  agent any

  parameters {
    string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Branch to test')
    choice(name: 'ENV', choices: ['dev', 'stage', 'prod'], description: 'Select environment')
    choice(name: 'BROWSER', choices: ['CHROME', 'CHROME_MOBILE'], description: 'Select Browser')
  }

  stages {
    stage('Pre-Build Cleanup') {
      steps {
        cleanWs() // Clean workspace before starting
      }
    }

    stage('Checkout Code') {
      steps {
        git branch: "${params.BRANCH_NAME}", url: 'https://github.com/VINOD-GOSWAMI/TestNg_Selenium_Docker.git'
      }
    }

    stage('Build Docker Image') {
      steps {
        script {
          runCommand('docker-compose build --no-cache', 'Docker build failed. Please check Docker setup and configuration.')
        }
      }
    }

    stage('Run Tests on Docker') {
      steps {
        script {
          runCommand(
            "docker-compose run --rm test-runner -DisRemote=true -Denv=${params.ENV} -Dbrowser=${params.BROWSER}",
            'Test run failed. Ensure Docker containers are running properly.'
          )
        }
      }
    }

    stage('Publish Test Reports') {
      steps {
        script {
          publishHTML([
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: false,
            reportDir: 'docker-report',
            reportFiles: 'ExtentReport*.html',
            reportName: 'Extent Report'
          ])
        }
      }
    }

    stage('Archive Logs and Reports') {
      steps {
        archiveArtifacts artifacts: 'docker-logs/*.log, docker-report/*.html, videos/', allowEmptyArchive: true
      }
    }
  }

  post {
    success {
      notifyByEmail('Job succeeded', 'Good news! The build was successful. <br> Please find the reports and logs attached.')
    }
    failure {
      notifyByEmail('Job failed', 'Unfortunately, the build has failed. Please check the logs.')
    }
    cleanup {
      script {
        runCommand('docker-compose down --remove-orphans') // Stop and remove Docker containers
      }
      cleanWs() // Clean workspace after build
    }
  }
}

// Helper function to run shell or PowerShell commands
def runCommand(command, errorMessage = '') {
  try {
    if (isUnix()) {
      sh command
    } else {
      powershell command
    }
  } catch (Exception e) {
    if (errorMessage) {
      error errorMessage
    } else {
      throw e
    }
  }
}

// Email notification helper function
def notifyByEmail(subjectPrefix, bodyMessage) {
  emailext(
    to: 'vinodpurigoswami735@gmail.com',
    subject: "${subjectPrefix}: Job ${env.JOB_NAME} build #${env.BUILD_NUMBER}",
    body: "${bodyMessage}",
    attachLog: true, // Attach the console output
    attachmentsPattern: '${env.WORKSPACE}/docker-report/*.html, ${env.WORKSPACE}/docker-logs/*.log, docker-report/*.html, docker-logs/*.log', // Attach your report and logs
    mimeType: 'text/html' // Send as HTML email
  )
}
