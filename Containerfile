FROM docker.io/library/busybox:latest
# FROM cgr.dev/chainguard/jre:latest
WORKDIR /app
COPY target/*.jar .
CMD sleep 10000