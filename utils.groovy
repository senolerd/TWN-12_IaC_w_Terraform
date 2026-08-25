def buildMvn() {
    echo '[ buildMvn ] Building'
    sh("mvn clean package")
}

def buildContainerImage(){
    echo "[ buildMvn ] Runtime check"
    sh("$CONTAINER_RUNTIME -v")

    sh("printenv")
    
    String appFile = sh( script: "ls target/*.jar", returnStdout: true ).trim().split("/")[-1]
    createContainerFile(appFile)
    
    withCredentials([usernamePassword(credentialsId: 'dockerhub-pat-rw', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        echo "[ buildMvn ] Building image "
        sh('podman build -t ${OCI_REG_ADDR}/${USER}/${IMAGE_NAME} .')

        echo "[ buildMvn ] logging in to ${env.OCI_REG_ADDR}"
        sh('podman login $OCI_REG_ADDR -u ${USER} -p ${PASS}')

        echo "[ buildMvn ] pushing"
        sh('podman push ${OCI_REG_ADDR}/${USER}/${IMAGE_NAME}')
    }
}


def createContainerFile(appFile){
    sh("""
cat <<EOF > Containerfile
FROM ${env.BASE_IMAGE}
WORKDIR /app
COPY target/${appFile} .
CMD ["-jar", "${appFile}"]
    """)
}



def iacDeploy(){

    withCredentials([usernamePassword(credentialsId: 'aws_iam_user_access_key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        dir("extras/01_IaC_Automate_AWS_infra"){
            sh("""
                terraform init
                terraform apply -auto-approve
            """)
            env.EC2_SERVER_IP = sh(script:"terraform output server_addr", returnStdout: true).trim()
        }
    }
}


return this
