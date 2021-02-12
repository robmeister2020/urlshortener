echo "Running gradle build"
./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar
echo "Gradle build complete, running docker build"
docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker .
echo "Docker build complete."
echo "Run ./run-project.sh to start the container"