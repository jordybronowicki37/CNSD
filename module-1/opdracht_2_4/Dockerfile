FROM openjdk:17-oracle
EXPOSE 80 443
ADD /target/cnsd-bank.jar cnsd-bank.jar
ENTRYPOINT ["java","-jar","cnsd-bank.jar"]