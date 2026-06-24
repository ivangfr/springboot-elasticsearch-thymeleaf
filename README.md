# springboot-elasticsearch-thymeleaf

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Buy Me A Coffee](https://img.shields.io/badge/Buy%20Me%20A%20Coffee-ivan.franchin-FFDD00?logo=buymeacoffee&logoColor=black)](https://buymeacoffee.com/ivan.franchin)

The goal of this project is to implement an application called `product-app`. It consists of two [`Spring Boot`](https://docs.spring.io/spring-boot/index.html) services: `product-api` (backend) and `product-ui` (frontend). The data will be stored in [`Elasticsearch`](https://www.elastic.co/elasticsearch).

## Proof-of-Concepts & Articles

On [ivangfr.github.io](https://ivangfr.github.io), I have compiled my Proof-of-Concepts (PoCs) and articles. You can easily search for the technology you are interested in by using the filter. Who knows, perhaps I have already implemented a PoC or written an article about what you are looking for.

## Project Overview

```mermaid
flowchart TB
    subgraph users ["Users"]
        HTTP["REST Clients"]
        Browser["Browser"]
    end

    subgraph product-ui ["product-ui\nport 9080\n(Spring Boot Thymeleaf)"]
        UIProductController["ProductController\nMVC"]
        UIProductReviewController["ProductReviewController\nMVC"]
        ProductApiClient["ProductApiClient\n(HTTP Interface)"]
    end

    subgraph product-api ["product-api\nport 8080\n(Spring Boot REST)"]
        RestCtrl["ProductController\n/api/products"]
        ReviewCtrl["ProductReviewController\n/api/products/{id}/reviews"]
        SwaggerUI["Swagger UI"]
        ProductService["ProductService"]
        ProductRepo["ProductRepository"]
    end

    subgraph elasticsearch ["Elasticsearch"]
        db[("ecommerce.products")]
    end

    subgraph init_scripts ["Initialization Scripts"]
        CreateIndex["create-index.sh"]
        InsertProducts["insert-products.sh"]
        Reindex["reindex.sh"]
    end

    Browser -->|"HTTP"| UIProductController
    Browser -->|"HTTP"| UIProductReviewController
    Browser -->|"HTTP"| SwaggerUI

    HTTP -->|"HTTP"| RestCtrl
    HTTP -->|"HTTP"| ReviewCtrl

    UIProductController -->|"uses"| ProductApiClient
    UIProductReviewController -->|"uses"| ProductApiClient

    ProductApiClient -->|"calls"| RestCtrl
    ProductApiClient -->|"calls"| ReviewCtrl

    RestCtrl -->|"uses"| ProductService
    ReviewCtrl -->|"uses"| ProductService
    ProductService -->|"queries"| ProductRepo
    ProductRepo -->|"Spring Data ES"| db

    SwaggerUI -->|"uses"| RestCtrl
    SwaggerUI -->|"uses"| ReviewCtrl

    CreateIndex -->|"creates index/alias"| db
    InsertProducts -->|"bulk inserts"| db
    Reindex -->|"reindexes"| db
```

## Applications

- ### product-api

  `Spring Boot` Web Java application that exposes a REST API to manage products. Product information is stored in `Elasticsearch`. `product-api` uses [`Spring Data Elasticsearch`](https://docs.spring.io/spring-data/elasticsearch/reference/) to persist/query/delete data in `Elasticsearch`.

- ### product-ui

  `Spring Boot` Web application that was implemented using [`Thymeleaf`](https://www.thymeleaf.org/) as HTML template. Also, it uses [`Http Interfaces`](https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface) to simplify HTTP remote access to `product-api`.

## Prerequisites

- [`Java 25`](https://www.oracle.com/java/technologies/downloads/#java25) or higher.
- A containerization tool (e.g., [`Docker`](https://www.docker.com), [`Podman`](https://podman.io), etc.)

## Start Environment

- Open a terminal and navigate to the `springboot-elasticsearch-thymeleaf` root folder and run:
  ```bash
  docker compose up -d
  ```

- Wait for `Elasticsearch` Docker container to be up and running. To verify it, run:
  ```bash
  docker ps -a
  ```

## Initialize Elasticsearch

> **Note:** In the following steps, we will create an index, an alias and do a reindex using pre-defined scripts. In case you prefer to do it step-by-step calling `Elasticsearch` API, refer to [Creating indexes, alias and reindexing using Elasticsearch API](create-index-alias-reindex.md).

- In a terminal, make sure you are in the `springboot-elasticsearch-thymeleaf` root folder.

- Run the following script to create the index `ecommerce.products.v1` with the alias `ecommerce.products` (you can use the default values by just pressing `Enter` on every user input):
  ```bash
  ./create-index.sh
  ```

- If you want to insert some products, run:
  ```bash
  ./insert-products.sh
  ```

- If you want to fix the `reference` property mapping error (explained below), run:
  ```bash
  ./reindex.sh
  ```

The script `./reindex.sh` is used to reindex one index to another. The default will reindex from `ecommerce.products.v1` to `ecommerce.products.v2`. The only difference between `elasticsearch/mapping-v1.json` (used by `ecommerce.products.v1`) and `elasticsearch/mapping-v2.json` (used by `ecommerce.products.v2`) is the `type` of the `reference` property. In the former, the type is set to `text` and, in the latter, to `keyword`.

It's interesting because the `reference` property has some special characters. An example of `reference` code is `SBES@DDR4-10000`. As it has the type `text`, `Elasticsearch` (using the `standard` analyzer) splits the content into tokens ['SBES', 'DDR4', 10000]. So, for example, if you are looking for a product with `DDR4` RAM and, for some reason, the string `DDR4` is present in the reference code of some product X, the product X will be selected, even if it doesn't have `DDR4` in its description.

So, the script `./reindex.sh` aims to fix it, setting the type `keyword` to the `reference` property. The `DDR4` search issue won't happen again because, from now on, `Elasticsearch` won't tokenize the content present in the `reference` property.

## Running applications using Maven

Below are the steps to start and run the applications using `Maven`. We will need to open a terminal for each one. Make sure you are in the `springboot-elasticsearch-thymeleaf` root folder while running the commands.

- **product-api**
  ```bash
  ./mvnw clean spring-boot:run --projects product-api
  ```

- **product-ui**
  ```bash
  ./mvnw clean spring-boot:run --projects product-ui -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
  ```

## Running applications as Docker containers

- ### Build Docker Images

  In a terminal and inside the `springboot-elasticsearch-thymeleaf` root folder, run the following script:
  ```bash
  ./build-docker-images.sh
  ```

- ### Environment Variables

  - **product-api**

   | Environment Variable | Description                                                                         |
   |----------------------|-------------------------------------------------------------------------------------|
   | `ELASTICSEARCH_URIS` | Specify URIs of the `Elasticsearch` search engine to use (default `localhost:9200`) |

  - **product-ui**

   | Environment Variable | Description                                                                       |
   |----------------------|-----------------------------------------------------------------------------------|
   | `PRODUCT_API_URL`    | Specify URL of the `product-api` service to use (default `http://localhost:8080`) |

- ### Run Docker containers 

  In a terminal and inside the `springboot-elasticsearch-thymeleaf` root folder, run the following script:
  ```bash
  ./start-apps.sh
  ```

## Application's URL

| Application | URL                                   |
|-------------|---------------------------------------|
| product-api | http://localhost:8080/swagger-ui.html |
| product-ui  | http://localhost:9080                 |

## Demo

Below is a simple demo showing a user interacting with `product-ui`

![demo](documentation/demo-user-interaction.gif)

## Shutdown

- To stop applications:
  - If they were started with `Maven`, go to `product-api` and `product-ui` terminals and press `Ctrl+C`.
  - If they were started as Docker containers, go to a terminal and, inside the `springboot-elasticsearch-thymeleaf` root folder, run the script below:
    ```bash
    ./stop-apps.sh
    ```

- To stop and remove docker compose containers, network and volumes, go to a terminal and, inside the `springboot-elasticsearch-thymeleaf` root folder, run the following command:
  ```bash
  docker compose down -v
  ```

## Running Tests

In a terminal, make sure you are inside the `springboot-elasticsearch-thymeleaf` root folder:

- **product-api**
  ```bash
  ./mvnw clean test --projects product-api
  ```

- **product-ui**
  ```bash
  ./mvnw clean test --projects product-ui
  ```

## Cleanup

To remove the Docker images created by this project, go to a terminal and, inside the `springboot-elasticsearch-thymeleaf` root folder, run the script below:
```bash
./remove-docker-images.sh
```

## Code Formatting

Uses [Spotless Maven Plugin](https://github.com/diffplug/spotless/tree/main/plugin-maven) + [Google Java Format](https://github.com/google/google-java-format) (Java) and [Prettier](https://prettier.io/) (JS/HTML) for automated formatting.

- **Check formatting:**

  ```bash
  ./mvnw spotless:check
  ```

- **Auto-fix formatting:**

  ```bash
  ./mvnw spotless:apply
  ```

Formatting is enforced automatically during `./mvnw verify`.

## How to optimize the GIF in the documentation folder

\[**Medium**\]: [**How I Reduce GIF and Screenshot Sizes for My Technical Articles on macOS**](https://medium.com/itnext/how-i-reduce-gif-and-screenshot-sizes-for-my-technical-articles-on-macos-7fea331afc68)

## Support

If you find this useful, consider buying me a coffee:

<a href="https://buymeacoffee.com/ivan.franchin"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" height="50"></a>

## License

This project is licensed under the [MIT License](./LICENSE).
