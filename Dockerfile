FROM maven:3.8.1-openjdk-17
RUN cd ./springtube
RUN mvn spring-boot:run
RUN cd ./src/main/frontend && npm start
EXPOSE 3000
