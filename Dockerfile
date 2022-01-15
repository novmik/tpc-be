FROM openjdk:17-oracle
EXPOSE 8080
ADD target/tpc-images.jar tpc-images.jar
ENTRYPOINT ["java","-jar","/tpc-images.jar"]