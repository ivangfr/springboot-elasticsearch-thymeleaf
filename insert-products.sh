#!/usr/bin/env bash

echo "------------------"
echo "Inserting products"
echo "------------------"

read -p "Type index name or alias (ecommerce.products): " index_name_alias
index_name_alias=${index_name_alias:-ecommerce.products}

read -p "Type path to products file (elasticsearch/products.json): " products_file
products_file=${products_file:-elasticsearch/products.json}

curl -X POST localhost:9200/${index_name_alias}/_bulk -H "Content-Type: application/json" --data-binary @${products_file}
echo