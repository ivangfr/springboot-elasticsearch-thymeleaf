#!/usr/bin/env bash

echo "-----------------------"
echo "Fix High Disk Watermark"
echo "-----------------------"
# Reference: https://www.elastic.co/guide/en/elasticsearch/reference/current/fix-watermark-errors.html

curl -X PUT localhost:9200/_cluster/settings \
  -H "Content-Type: application/json" \
  -d '{ "persistent": { "cluster.routing.allocation.disk.watermark.low": "90%", "cluster.routing.allocation.disk.watermark.low.max_headroom": "100GB", "cluster.routing.allocation.disk.watermark.high": "95%", "cluster.routing.allocation.disk.watermark.high.max_headroom": "20GB", "cluster.routing.allocation.disk.watermark.flood_stage": "97%", "cluster.routing.allocation.disk.watermark.flood_stage.max_headroom": "5GB", "cluster.routing.allocation.disk.watermark.flood_stage.frozen": "97%", "cluster.routing.allocation.disk.watermark.flood_stage.frozen.max_headroom": "5GB" } }'

echo
echo "-----------------"
echo "Configuring index"
echo "-----------------"

read -p "Type index alias (ecommerce.products): " index_alias
index_alias=${index_alias:-ecommerce.products}

read -p "Type index name (ecommerce.products.v1): " index_name
index_name=${index_name:-ecommerce.products.v1}

read -p  "Inform the path to mapping json file (elasticsearch/mapping-v1.json): " mapping_file
mapping_file=${mapping_file:-elasticsearch/mapping-v1.json}

echo "------------"
echo "Create index"
echo "------------"
curl -X PUT localhost:9200/${index_name} -H "Content-Type: application/json" -d @${mapping_file}

echo
echo "------------"
echo "Create alias"
echo "------------"
curl -X POST localhost:9200/_aliases -H 'Content-Type: application/json' \
     -d '{ "actions": [{ "add": {"alias": "'${index_alias}'", "index": "'${index_name}'" }}]}'
echo