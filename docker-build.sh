#!/usr/bin/env bash

#if [ "$1" = "native" ];
#then
#  ./mvnw clean spring-boot:build-image --projects eureka-server
#  ./mvnw clean spring-boot:build-image --projects product-api
#  ./mvnw clean spring-boot:build-image --projects product-ui
#else
  ./mvnw clean compile jib:dockerBuild --projects eureka-server
  ./mvnw clean compile jib:dockerBuild --projects product-api
  ./mvnw clean compile jib:dockerBuild --projects product-ui
#fi