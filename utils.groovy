def buildMvn() {
    echo '[buildMvn]: Building'
    sh("mvn clean package")
}

def buildContainerImage(){
    echo "[buildMvn]: Runtime check"
    sh("$CONTAINER_RUNTIME -v")

    sh("printenv")
    
    String jarFile = sh( script: "ls target/*.jar", returnStdout: true ).trim().split("/")[-1]
    createContainerFile(jarFile)
    
    withCredentials([usernamePassword(credentialsId: 'dockerhub-pat-rw', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        echo "[buildContainerImage]: Building image"
        sh('podman build -t ${OCI_REG_ADDR}/${USER}/${IMAGE_NAME} .')

        echo "[buildContainerImage]: logging in to ${env.OCI_REG_ADDR}"
        sh('podman login $OCI_REG_ADDR -u ${USER} -p ${PASS}')

        echo "[buildContainerImage]: pushing image "
        sh('podman push ${OCI_REG_ADDR}/${USER}/${IMAGE_NAME}')
    }
}


def createContainerFile(jarFile){
    echo "[createContainerFile]: Creating Containerfile"
    sh("""
cat <<EOF > Containerfile
FROM ${env.BASE_IMAGE}
WORKDIR /app
COPY target/${jarFile} .
CMD ["-jar", "${jarFile}"]
    """)
}



def iacDeploy(){
    echo "[createContainerFile]: Creating Containerfile"
    withCredentials([usernamePassword(credentialsId: 'aws_iam_user_access_key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        dir("extras/01_IaC_Automate_AWS_infra"){
            sh("""
                terraform init
                terraform validate 
                terraform apply -auto-approve -var="keypair_name=${env.EC2_KEY_PAIR_NAME}" -var="region=${env.REGION}"
            """)
            env.EC2_SERVER_IP = sh(script:"terraform output server_addr", returnStdout: true).trim()
        }
    }
}


def sshWork() {
    echo "SERVER ADDRESS TO SSH: ${env.EC2_SERVER_IP}"

    sshagent(credentials: ['ec2_pem_for_ubuntu']) {
        sh("ssh -o StrictHostKeyChecking=no ubuntu@${env.EC2_SERVER_IP} podman image ls")
    }
    
}




return this
