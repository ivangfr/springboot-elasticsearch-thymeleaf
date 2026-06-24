#!/usr/bin/env bash

set -e

echo "-----------------------"
echo "Fix High Disk Watermark"
echo "-----------------------"
# Reference: https://stackoverflow.com/questions/63880017/elasticsearch-docker-flood-stage-disk-watermark-95-exceeded

curl -X PUT --fail localhost:9200/_cluster/settings \
  -H "Content-Type: application/json" \
  -d '{ "transient": { "cluster.routing.allocation.disk.threshold_enabled": false } }'

echo
echo "-----------------"
echo "Configuring index"
echo "-----------------"

read -p "Type index alias (ecommerce.products): " index_alias
index_alias="${index_alias:-ecommerce.products}"

read -p "Type index name (ecommerce.products.v1): " index_name
index_name="${index_name:-ecommerce.products.v1}"

read -p  "Inform the path to mapping json file (elasticsearch/mapping-v1.json): " mapping_file
mapping_file="${mapping_file:-elasticsearch/mapping-v1.json}"

echo "------------"
echo "Create index"
echo "------------"
curl -X PUT --fail localhost:9200/"${index_name}" -H "Content-Type: application/json" -d @"${mapping_file}"

echo
echo "------------"
echo "Create alias"
echo "------------"
curl -X POST --fail localhost:9200/_aliases -H 'Content-Type: application/json' \
     -d '{ "actions": [{ "add": {"alias": "'"${index_alias}"'", "index": "'"${index_name}"'" }}]}'
echo