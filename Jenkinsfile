def utils

pipeline {   
    agent any
    tools {
        maven 'Maven'
    }
    environment{
        // Container Settings
        CONTAINER_RUNTIME = "podman"
        BASE_IMAGE = "cgr.dev/chainguard/jre:latest"
        IMAGE_NAME = "java-maven-app:${env.GIT_COMMIT.take(7)}-b${env.BUILD_ID}"
        OCI_REG_ADDR = "docker.io"
        DOCKER_CRED_ID= "dockerhub-pat-rw" // Jenkins Username/Password Kind of credential's id

        // AWS
        AWS_CRED_ID = "aws_iam_user_access_key"
    }
    stages {
        stage("init") {
            steps {
                script {
                    utils = load "utils.groovy"
                }
            }
        }          

        stage("Java Maven code build") {
            steps {
                script {
                    utils.buildMvn()
                }
            }
        }

        stage("Container Image Build") {
            steps {
                script {
                    utils.buildContainerImage()
                }
            }
        }

        stage("iacDeploy") {
            steps{
                script {
                    utils.iacDeploy()    
                }
            }
        }

        stage("SSH"){
            steps{
                script{
                    utils.sshWork()
                }
            }
        }
    }
} 


// JENKINS_HOME=/var/jenkins_home
// BRANCH_IS_PRIMARY=true
// GIT_PREVIOUS_SUCCESSFUL_COMMIT=8a3f7d3a07aaabb9c5024380183f6049ca6fa0a4
// JENKINS_UC_EXPERIMENTAL=https://updates.jenkins.io/experimental
// CI=true
// HOSTNAME=057bc6c5b73c
// RUN_CHANGES_DISPLAY_URL=http://192.168.1.90:8080/job/TWN-12/job/main/24/display/redirect?page=changes
// NODE_LABELS=built-in
// HUDSON_URL=http://192.168.1.90:8080/
// SHLVL=0
// GIT_COMMIT=8a3f7d3a07aaabb9c5024380183f6049ca6fa0a4
// HOME=/var/jenkins_home
// BUILD_URL=http://192.168.1.90:8080/job/TWN-12/job/main/24/
// HUDSON_COOKIE=d98ac48c-d186-4640-a5c6-7da09f8fcd83
// JENKINS_SERVER_COOKIE=durable-2c0ed71de624d269ba897eda1297b95610e59f39204b0ce95c3a5beb9c09d72a
// MAVEN_HOME=/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/Maven
// CONTAINER_RUNTIME=podman
// JENKINS_UC=https://updates.jenkins.io
// REF=/usr/share/jenkins/ref
// container=podman
// WORKSPACE=/var/jenkins_home/workspace/TWN-12_main
// CONTAINER_HOST=unix:///run/podman/podman.sock
// NODE_NAME=built-in
// RUN_ARTIFACTS_DISPLAY_URL=http://192.168.1.90:8080/job/TWN-12/job/main/24/display/redirect?page=artifacts
// STAGE_NAME=Container Image Build
// GIT_BRANCH=main
// EXECUTOR_NUMBER=1
// BUILD_DISPLAY_NAME=#24
// JENKINS_INCREMENTALS_REPO_MIRROR=https://repo.jenkins-ci.org/incrementals
// JENKINS_VERSION=2.568.2
// RUN_TESTS_DISPLAY_URL=http://192.168.1.90:8080/job/TWN-12/job/main/24/display/redirect?page=tests
// HUDSON_HOME=/var/jenkins_home
// JOB_BASE_NAME=main
// PATH=/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/Maven/bin:/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/Maven/bin:/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
// BUILD_ID=24
// BUILD_TAG=jenkins-TWN-12-main-24
// LANG=C.UTF-8
// JENKINS_URL=http://192.168.1.90:8080/
// JOB_URL=http://192.168.1.90:8080/job/TWN-12/job/main/
// GIT_URL=git@github.com:senolerd/TWN-12_IaC_w_Terraform.git
// BUILD_NUMBER=24
// JENKINS_NODE_COOKIE=73493e4c-1a68-4a44-95a9-274d5bba814d
// JENKINS_SLAVE_AGENT_PORT=50000
// RUN_DISPLAY_URL=http://192.168.1.90:8080/job/TWN-12/job/main/24/display/redirect
// HUDSON_SERVER_COOKIE=dc97c56ecc77c416
// JOB_DISPLAY_URL=http://192.168.1.90:8080/job/TWN-12/job/main/display/redirect
// JOB_NAME=TWN-12/main
// COPY_REFERENCE_FILE_LOG=/var/jenkins_home/copy_reference_file.log
// JAVA_HOME=/opt/java/openjdk
// PWD=/var/jenkins_home/workspace/TWN-12_main
// GIT_PREVIOUS_COMMIT=8a3f7d3a07aaabb9c5024380183f6049ca6fa0a4
// M2_HOME=/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/Maven
// WORKSPACE_TMP=/var/jenkins_home/workspace/TWN-12_main@tmp
// TZ=America/Chicago
// BRANCH_NAME=main