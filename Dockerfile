FROM openjdk:8
VOLUME /data
ARG JAR_FILE
ADD  ./target/${JAR_FILE} tinyurl.jar

CMD java -jar -Xms128M -Xmx256M tinyurl.jar

EXPOSE 8080

