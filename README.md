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


UPDATE AND DELETE Operation in Elasticsearch

Way 1 –> Updating a Sample String Value

UpdateRequest updateRequest = new UpdateRequest("employeeindex", "002");
updateRequest.doc("department", "Bussiness");
UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
System.out.println("updated response id: "+updateResponse.getId());


Way 2–> Updating Id with particular Map values

Map<String, Object> updateMap = new HashMap<String, Object>();
updateMap.put("firstname","Sundar");
updateMap.put("lastname","Pichai");
updateMap.put("company","Google");
updateMap.put("sector","IT");


UpdateRequest request = new UpdateRequest("employeeindex", "002").doc(updateMap);
UpdateResponse updateResponse= client.update(request, RequestOptions.DEFAULT);
System.out.println("updated response id: "+updateResponse.getId());
Another way of updating data in elasticsearch via java is we can use InsertAPI as well with where condition like below


//update way2
IndexRequest request = new IndexRequest("employeeindex");
request.id("001");
request.source("company","SpaceX");
IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
System.out.println("response id: "+indexResponse.getId());
System.out.println(indexResponse.getResult().name());



//update way2  
Map<String, Object> updateMap = new HashMap<String, Object>();
updateMap.put("firstname","Sundar");
updateMap.put("lastname","Pichai");
updateMap.put("company","Google");
updateMap.put("sector","IT");
IndexRequest request2 = new IndexRequest("employeeindex");
request2.id("002");
request2.source(updateMap);
IndexResponse indexResponseUpdate = client.index(request2, RequestOptions.DEFAULT);
System.out.println("response id: "+indexResponseUpdate.getId());		System.out.println(indexResponse.getResult().name());


Way 3 –> Inserting POJO mappings data
Create POJO Class with sample attributes like below example

public class EmployeePojo {

    public String firstName;
    public String lastName;
    private LocalDate startDate;
 
    public EmployeePojo(String firstName, String lastName, LocalDate startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
    }
 
    public String name() {
        return this.firstName + " " + this.lastName;
    }
 
    public LocalDate getStart() {
        return this.startDate;
    }
}


Updating to ElasticSearch for particular Uniquid with POJO

EmployeePojo emp = new EmployeePojo("Elon01", "Musk01",  LocalDate.now() );
IndexRequest request = new IndexRequest("employeeindex");
request.id("001");
request.source(new ObjectMapper().writeValueAsString(emp), XContentType.JSON);
IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
System.out.println("response id: +indexResponse.getId());		System.out.println(indexResponse.getResult().name());
Using API- UpdateByQueryRequest

Map<String, Object> updateMap2 = new HashMap<String, Object>();							
updateMap2.put("firstname","Sundar01");
updateMap2.put("lastname","Pichai01");
UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("employeeindex");
updateByQueryRequest.setConflicts("proceed");
updateByQueryRequest.setQuery(new TermQueryBuilder("_id", "001"));
Script script = new Script(ScriptType.INLINE, "painless","ctx._source = params",updateMap2);
updateByQueryRequest.setScript(script);

try {
BulkByScrollResponse bulkResponse = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
long totalDocs = bulkResponse.getTotal();
System.out.println("updated response id: "+totalDocs);
} catch (IOException e) {
e.printStackTrace();
}


Delete Operations
Delete particular record
DeleteRequest deleteRequest = new DeleteRequest("employeeindex","002");
DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
System.out.println("response id: "+deleteResponse.getId());


2.Delete entire index

//Delete the index
DeleteIndexRequest request = new DeleteIndexRequest("employeeindex");
client.indices().delete(request, RequestOptions.DEFAULT);
System.out.println("Delete Done ");




