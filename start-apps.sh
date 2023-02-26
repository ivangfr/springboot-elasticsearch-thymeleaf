#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting product-api..."

docker run -d --rm --name product-api \
  -p 8080:8080 \
  -e ELASTICSEARCH_URIS=elasticsearch:9200 \
  --network springboot-elasticsearch-thymeleaf_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" \
  ivanfranchin/product-api:1.0.0

wait_for_container_log "product-api" "Started"

echo
echo "Starting product-ui..."

docker run -d --rm --name product-ui \
  -p 9080:8080 \
  -e PRODUCT_API_URL=http://product-api:8080 \
  --network springboot-elasticsearch-thymeleaf_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" \
  ivanfranchin/product-ui:1.0.0

wait_for_container_log "product-ui" "Started"

printf "\n"
printf "%12s | %37s |\n" "Application" "URL"
printf "%12s + %37s |\n" "------------" "-------------------------------------"
printf "%12s | %37s |\n" "product-api" "http://localhost:8080/swagger-ui.html"
printf "%12s | %37s |\n" "product-ui" "http://localhost:9080"
printf "\n"