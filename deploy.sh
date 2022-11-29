docker stop lib-service
docker rm lib-service
docker rmi libservice_lib-service
docker-compose -f /data03/hzh/data/libservice/docker-compose.yml up -d
