FROM openjdk:8
VOLUME /data
ARG JAR_FILE
ADD  ./target/${JAR_FILE} tinyurl.jar

FROM mysql:5.7
ADD  ./init.sql /docker-entrypoint-initdb.d

CMD java -jar -Xms128M -Xmx256M tinyurl.jar

EXPOSE 8080

