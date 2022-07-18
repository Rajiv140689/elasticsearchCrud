[comment]: <> (Elasticsearch connection understanding with crud demo:)

	Elasticsearch:
	docker network create elastic
	docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.5
	docker run --name es01-test --net elastic -p 127.0.0.1:9200:9200 -p 127.0.0.1:9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.5


	Kibana:
	docker pull docker.elastic.co/kibana/kibana:7.17.5
	docker run --name kib01-test --net elastic -p 127.0.0.1:5601:5601 -e "ELASTICSEARCH_HOSTS=http://es01-test:9200" docker.elastic.co/kibana/kibana:7.17.5



	https://www.baeldung.com/spring-data-elasticsearch-tutorial

	https://betterjavacode.com/programming/elasticsearch-spring-boot

	https://github.com/yogsma/betterjavacode/tree/main/elasticsearchdemo/src/main/java/com/betterjavacode/elasticsearchdemo


	Elasticsearch Architecture:
	    https://www.youtube.com/watch?v=YsYUgZu9-Y4

	    https://www.youtube.com/watch?v=8r_IMTerZSY


	From 0 to Hero in Writing Elastic Search Query in 1 Hour | ELk | Kibana | Crash course

	Kibana Aggregations Explained [Kibana Tutorials]

	Elasticsearch - Aggregations

	Metric Aggregations in Elasticsearch | ElasticSearch 7 for Beginners #5.1


	Point in Time: https://www.elastic.co/guide/en/elasticsearch/reference/7.17/point-in-time-api.html


pom.xml

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        <version>2.7.1</version>
    </dependency>
    <dependency>
        <groupId>org.elasticsearch.client</groupId>
        <artifactId>elasticsearch-rest-high-level-client</artifactId>
        <version>7.17.5</version>
    </dependency>



localhost:8080/v1/elasticsearch/logdata/createInBulk
[
    {
        "id":"123",
        "host":"abc.com",
        "date":"2022-06-22",
        "message":"This is test message for elasticsearch",
        "size":30,
        "status":"success"
    },
    {
        "id":"234",
        "host":"google.com",
        "date":"2022-06-24",
        "message":"This is test message for elasticsearch",
        "size":20,
        "status":"success"
    }
]

localhost:8080/v1/elasticsearch/logdata/?host=abc.com

[comment]: <> (Search in message)
localhost:8080/v1/elasticsearch/logdata/search/?term=elasticsearch