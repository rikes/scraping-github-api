FROM openjdk:11

WORKDIR app

COPY target/scraping-0.0.1-SNAPSHOT.jar /app/scraping-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "scraping-0.0.1-SNAPSHOT.jar"]