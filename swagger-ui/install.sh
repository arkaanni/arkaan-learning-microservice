wget -O swagger-ui.tar.gz https://github.com/swagger-api/swagger-ui/archive/refs/tags/v5.11.1.tar.gz
mkdir swagger
tar -xvf swagger-ui.tar.gz --strip 1 -C swagger

docker build . -t  $containerRegistry/swagger-ui:latest
docker push $containerRegistry/swagger-ui:latest
docker rmi $containerRegistry/swagger-ui:latest

rm -r swagger
rm swagger-ui.tar.gz
