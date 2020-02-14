@Library ('folio_jenkins_shared_libs') _

pipeline {

  options {
    timeout(30)
    buildDiscarder(logRotator(numToKeepStr: '3', artifactNumToKeepStr: '3'))
  }

  agent {
    node {
      label 'jenkins-slave-all'
    }
  }

  stages {
    stage('Setup') {
      steps {
        script {
          currentBuild.displayName = "#${env.BUILD_NUMBER}-${env.JOB_BASE_NAME}"
        }
      }
    }
    stage('Maven Build') {
      when {
        not {
          branch 'master'
        }
      }
      steps {
        echo "Building Maven artifacts"
        withMaven(
          jdk: 'openjdk-8-jenkins-slave-all',
          maven: 'maven3-jenkins-slave-all',
          mavenSettingsConfig: 'folioci-maven-settings') {
          dir ('core/trunk') {
            sh 'mvn clean install'
          }
        }
      }
    }
    stage('Maven Deploy'){
      when {
        not {
          branch 'master'
        }
      }
      steps {
        echo "Deploying artifacts to Maven repository"
        withMaven(
          jdk: 'openjdk-8-jenkins-slave-all',
          maven: 'maven3-jenkins-slave-all',
          mavenSettingsConfig: 'folioci-maven-settings') {
          dir ('core/trunk') {
            sh 'mvn -DskipTests deploy'
          }
        }
      }
    }
  }
}

