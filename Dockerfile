FROM adoptopenjdk:16-jre-hotspot
RUN mkdir /opt/app
COPY target/speedrenthu-0.0.1-SNAPSHOT.jar /opt/app/speedrenthu.jar
CMD ["java", "-jar", "/opt/app/speedrenthu.jar"]