FROM maven:3-amazoncorretto-17 as build

WORKDIR /usr/src/app

# Copy the POM file into the image
COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.16-jdk

COPY --from=build /usr/src/app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]