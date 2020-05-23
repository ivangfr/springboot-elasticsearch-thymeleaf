#!/usr/bin/env bash

read -p "Type the index alias (ecommerce.products): " index_alias
index_alias=${index_alias:-ecommerce.products}

read -p "Type the old index name (ecommerce.products.v1): " old_index_name
old_index_name=${old_index_name:-ecommerce.products.v1}

read -p "Type new index name (ecommerce.products.v2): " new_index_name
new_index_name=${new_index_name:-ecommerce.products.v2}

read -p "Inform the path to the new mapping json file (mapping-v2.json): " new_mapping_file
new_mapping_file=${new_mapping_file:-mapping-v2.json}

echo "----------------"
echo "Create new index"
echo "----------------"
curl -X PUT localhost:9200/${new_index_name} -H "Content-Type: application/json" -d @${new_mapping_file}

echo
echo "--------"
echo "Re-index"
echo "--------"
curl -X POST localhost:9200/_reindex -H 'Content-Type: application/json' \
     -d '{ "source": { "index": "'${old_index_name}'" }, "dest": { "index": "'${new_index_name}'" }}'

echo
echo "------------"
echo "Update alias"
echo "------------"
curl -X POST localhost:9200/_aliases -H 'Content-Type: application/json' \
     -d '{ "actions": [{ "remove": {"alias": "'${index_alias}'", "index": "'${old_index_name}'" }}, { "add": {"alias": "'${index_alias}'", "index": "'${new_index_name}'" }}]}'
echo

echo "----------------"
echo "Delete old index"
echo "----------------"
curl -X DELETE localhost:9200/${old_index_name}