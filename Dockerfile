FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/*.jar /opt/app/quickurl.jar
CMD ["java", "-jar", "/opt/app/quickurl.jar"]