#!/usr/bin/env bash

read -p "Type index alias (ecommerce): " index_alias
index_alias=${index_alias:-ecommerce}

read -p "Type index name (ecommerce_v1): " index_name
index_name=${index_name:-ecommerce_v1}

read -p  "Inform the path to mapping json file (mapping-v1.json): " mapping_file
mapping_file=${mapping_file:-mapping-v1.json}

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