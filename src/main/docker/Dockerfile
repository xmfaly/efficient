FROM java:8
VOLUME /app
COPY *.jar /app
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar >>/app/log" ]