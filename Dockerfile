FROM eclipse-temurin:17-jdk

WORKDIR /backttm

COPY ./ ./

CMD ["./gradlew", "clean", "bootJar"]

FROM eclipse-temurin:17-jre

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

