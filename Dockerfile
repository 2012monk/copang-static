FROM openjdk:8-jre-alpine

ARG ID
ARG KEY
ENV AWS_ACCESS_KEY_ID $ID
ENV AWS_SECRET_ACCESS_KEY $KEY


COPY /build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]