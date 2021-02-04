FROM xuqm/8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE
COPY $JAR_FILE /home/app.jar
ENTRYPOINT ["sh", "-c", "java -jar /home/app.jar ${BOOT_OPTIONS}"]