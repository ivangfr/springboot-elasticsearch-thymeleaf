#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting eureka..."

docker run -d --rm --name eureka \
  -p 8761:8761 \
  --network springboot-elasticsearch-thymeleaf_default \
  --health-cmd="curl -f http://localhost:8761/actuator/health || exit 1" --health-start-period=30s \
  ivanfranchin/eureka-server:1.0.0

wait_for_container_log "eureka" "Started"

echo
echo "Starting product-api..."

docker run -d --rm --name product-api \
  -p 9080:8080 \
  -e EUREKA_HOST=eureka -e ELASTICSEARCH_HOST=elasticsearch \
  --network springboot-elasticsearch-thymeleaf_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" --health-start-period=30s \
  ivanfranchin/product-api:1.0.0

wait_for_container_log "product-api" "Started"

echo
echo "Starting product-ui..."

docker run -d --rm --name product-ui \
  -p 9081:8080 \
  -e EUREKA_HOST=eureka \
  --network springboot-elasticsearch-thymeleaf_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" --health-start-period=30s \
  ivanfranchin/product-ui:1.0.0

wait_for_container_log "product-ui" "Started"

printf "\n"
printf "%12s | %37s |\n" "Application" "URL"
printf "%12s + %37s |\n" "------------" "-------------------------------------"
printf "%12s | %37s |\n" "eureka" "http://localhost:8761"
printf "%12s | %37s |\n" "product-api" "http://localhost:9080/swagger-ui.html"
printf "%12s | %37s |\n" "product-ui" "http://localhost:9081"
printf "\n"