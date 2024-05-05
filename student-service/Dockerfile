FROM golang:1.22-alpine as build

WORKDIR /build

COPY . ./

RUN go mod download
RUN go build -o student-service

FROM debian:buster-slim

WORKDIR /app

COPY --from=build /build/student-service /app

EXPOSE 8443

ENTRYPOINT [ "/app/student-service" ]