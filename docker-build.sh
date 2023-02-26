#!/usr/bin/env bash

./mvnw clean compile jib:dockerBuild --projects product-api
./mvnw clean compile jib:dockerBuild --projects product-ui
