FROM openjdk:21-slim AS build
WORKDIR /app
COPY . ./
RUN apt-get update && apt-get install -y findutils
RUN ./gradlew build -x test # -x test is temporary

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/task-mega-1.jar .
CMD ["java", "-jar", "task-mega-1.jar"]