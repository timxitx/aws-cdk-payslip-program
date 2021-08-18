FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE=target/monthlyPayslip-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} monthlyPayslip.jar

ENTRYPOINT ["java", "-jar", "monthlyPayslip.jar"]