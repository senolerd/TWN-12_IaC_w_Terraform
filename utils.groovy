def buildMvn() {
    echo '[ build_mvn ] Building'
    sh("mvn clean package")
}

def buildContainerImage(){
    echo "Runtime check"
    sh("$CONTAINER_RUNTIME -v")

    sh("printenv")
    
    String appFile = sh( script: "ls target/*.jar", returnStdout: true ).trim().split("/")[-1]
    createContainerFile(appFile)

    // env.IMAGE_NAME = "java-maven-app:${env.GIT_COMMIT.take(7)}-b${env.BUILD_ID}"

    sh("podman build -t ${env.IMAGE_NAME} .")
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



return this
