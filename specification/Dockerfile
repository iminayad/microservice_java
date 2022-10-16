FROM openjdk:11
ADD target/specification-0.0.1-SNAPSHOT.jar specification.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","specification.jar"]