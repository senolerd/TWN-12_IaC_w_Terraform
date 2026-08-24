def buildMvn() {
    echo '[ build_mvn ] Building'
    sh("mvn clean package")
}

def buildContainerImage(){
    sh("$CONTAINER_RUNTIME -v|| true")
    sh("printenv")
    echo("===============")
    
    String appFile = sh( script: "ls target/*.jar", returnStdout: true ).trim().split("/")[-1]
    String commitIdShort =  env.GIT_COMMIT.take(7)

    createContainerFile(appFile)

    String imageName = "java-maven-app:${commitIdShort}-${env.BUILD_ID}"
    sh("podman build -t ${imageName} .")
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
