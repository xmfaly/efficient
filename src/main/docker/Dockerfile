FROM java:8
VOLUME /log
ADD *.jar /app.jar
COPY apk/* /res/apk/
COPY img/* /res/img/
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar >>/log/log" ]
