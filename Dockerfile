

#RUN echo 'we will build tiny url image'

# Use the official openjdk image as parent image.
FROM openjdk:8

#执行mvn编译
#RUN mvn clean package -U

VOLUME /data
ARG JAR_FILE
ADD  ./target/${JAR_FILE} tinyurl.jar

CMD java -jar -Xms128M -Xmx256M tinyurl.jar

EXPOSE 8080

