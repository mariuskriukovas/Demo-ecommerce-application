FROM node:lts-alpine as build-stage

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine-perl as production-stage
COPY nginx/ /etc/nginx/
COPY --from=build-stage /app/dist /usr/share/nginx/html

EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]