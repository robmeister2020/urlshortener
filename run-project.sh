echo "Creating directory to mount in container at c:/dockermount"
mkdir -p C:/dockermount
echo "Starting container..."
docker run -p 8090:8090 -v C:/dockermount:/mnt springio/gs-spring-boot-docker