def utils

pipeline {   
    agent any
    // tools {
    //     maven 'Maven'
    // }
    stages {
        stage("init") {
            steps {
                script {
                    utils = load "utils.groovy"
                }
            }
        }          
    }
} 
