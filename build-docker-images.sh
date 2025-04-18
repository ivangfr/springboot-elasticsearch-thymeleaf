#!/usr/bin/env bash

DOCKER_IMAGE_PREFIX="ivanfranchin"
APP_VERSION="1.0.0"

PRODUCT_API_APP_NAME="product-api"
PRODUCT_UI_APP_NAME="product-ui"

PRODUCT_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${PRODUCT_API_APP_NAME}:${APP_VERSION}"
PRODUCT_UI_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${PRODUCT_UI_APP_NAME}:${APP_VERSION}"

SKIP_TESTS="true"

./mvnw clean spring-boot:build-image \
  --projects "$PRODUCT_API_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$PRODUCT_API_DOCKER_IMAGE_NAME"

./mvnw clean spring-boot:build-image \
  --projects "$PRODUCT_UI_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$PRODUCT_UI_DOCKER_IMAGE_NAME"
