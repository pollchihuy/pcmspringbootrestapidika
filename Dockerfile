FROM khipu/openjdk21-alpine

COPY target/rest-api.jar /docker-build/rest-api.jar

CMD ["java","-jar","/docker-build/rest-api.jar"]