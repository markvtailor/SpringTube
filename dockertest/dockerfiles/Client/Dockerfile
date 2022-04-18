FROM node
RUN git clone https://github.com/markvtailor/SpringTube.git && cd SpringTube && git checkout SpringTube-with-Front-Docker
WORKDIR /SpringTube/src/main/frontend
RUN npm i
CMD ["npm", "run", "start"]

