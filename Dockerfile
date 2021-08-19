FROM maven:3.8.1-jdk-11-slim AS build
COPY ./monthly-payslip /app
RUN mvn -f /app/pom.xml clean package


FROM openjdk:11

EXPOSE 8080

COPY --from=build /app/target/monthlyPayslip-0.0.1-SNAPSHOT.jar /usr/local/lib/monthlyPayslip.jar

ENTRYPOINT ["java", "-jar", "/usr/local/lib/monthlyPayslip.jar"]