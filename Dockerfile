FROM openjdk:17-oracle
VOLUME /tmp
COPY target/*.jar tpc-images.jar
ENTRYPOINT ["java","-jar","/tpc-images.jar"]