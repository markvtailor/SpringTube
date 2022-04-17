FROM maven:3.8.1-openjdk-17
CMD aws --endpoint-url=http://localhost:4566 s3 mb s3://bucket1
RUN git clone https://github.com/markvtailor/SpringTube.git && cd SpringTube && git checkout SpringTube-with-Front
WORKDIR /SpringTube
RUN mvn spring-boot:run
FROM node
RUN git clone https://github.com/markvtailor/SpringTube.git && cd SpringTube && git checkout SpringTube-with-Front
WORKDIR /SpringTube/src/main/frontend
RUN npm i react-scripts
RUN npm start

