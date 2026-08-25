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
            env.EC2_SERVER_IP = sh(script:"terraform output -raw server_addr", returnStdout: true).trim()
        }
    }
}

def sshWork() {
    echo "[sshWork]: SERVER ADDRESS TO SSH: ${EC2_SERVER_IP}"

    echo "[sshWork]: 90 sec sleep before asking podman to do something"
    sleep(time: 90, unit: 'SECONDS')
    
    sshagent(credentials: ['ec2_pem_for_ubuntu']) {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-pat-rw', passwordVariable: 'DOCKER_PW', usernameVariable: 'DOCKER_USER')]) { 
            
            echo "[sshWork]: Checking podman whether it answers"
            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_SERVER_IP} 'podman image ls' " 

            echo "[sshWork]: Trying to login docker.io"
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@${EC2_SERVER_IP} "podman login ${OCI_REG_ADDR} -u $DOCKER_USER -p $DOCKER_PW" '

            echo "[sshWork]: Pulling new image to host"
            sh """ ssh -o StrictHostKeyChecking=no ubuntu@${EC2_SERVER_IP} "podman pull ${OCI_REG_ADDR}/${DOCKER_USER}/${IMAGE_NAME}" """

            echo "[sshWork]: Checking podman whether it answers"
            sh "ssh -o StrictHostKeyChecking=no ubuntu@${EC2_SERVER_IP} 'podman rm -f java-maven-app  || true' "
            sh 'ssh -o StrictHostKeyChecking=no ubuntu@${EC2_SERVER_IP} "podman run -d -p 8080:8080 --name java-maven-app ${OCI_REG_ADDR}/${DOCKER_USER}/${IMAGE_NAME}" '
            """
        }
    }
}




return this
