FROM maven:3.8.1-openjdk-11 AS build
COPY . /vacationcalculator
WORKDIR /vacationcalculator
RUN mvn clean install

FROM openjdk:11-jre-slim
COPY --from=build /vacationcalculator/target/*.jar /vacationcalculator.jar
CMD ["java", "-jar", "/vacationcalculator.jar"]