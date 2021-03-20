#!/usr/bin/env bash

echo
echo "Starting eureka..."

docker run -d --rm --name eureka \
  -p 8761:8761 \
  --network springboot-elasticsearch-thymeleaf_default \
  docker.mycompany.com/eureka-server:1.0.0

sleep 2

echo
echo "Starting product-api..."

docker run -d --rm --name product-api \
  -p 9080:8080 \
  -e EUREKA_HOST=eureka -e ELASTICSEARCH_HOST=elasticsearch \
  --network springboot-elasticsearch-thymeleaf_default \
  docker.mycompany.com/product-api:1.0.0

sleep 2

echo
echo "Starting product-ui..."

docker run -d --rm --name product-ui \
  -p 9081:8080 \
  -e EUREKA_HOST=eureka \
  --network springboot-elasticsearch-thymeleaf_default \
  docker.mycompany.com/product-ui:1.0.0
