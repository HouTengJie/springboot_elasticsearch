package com.usian.test;

import com.usian.ElasticsearchApp;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ElasticsearchApp.class})
public class IndexWriterTest {
	@Autowired
    private RestHighLevelClient restHighLevelClient;

   //创建索引库
    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("java1906");
        createIndexRequest.settings(Settings.builder().
									put("number_of_shards",2).
                                    put("number_of_replicas",0));
        createIndexRequest.mapping("course", "{\r\n" +
        		"  \"_source\": {\r\n" +
        		"    \"excludes\":[\"description\"]\r\n" +
        		"  }, \r\n" +
        		" 	\"properties\": {\r\n" +
        		"           \"name\": {\r\n" +
        		"              \"type\": \"text\",\r\n" +
        		"              \"analyzer\":\"ik_max_word\",\r\n" + 
        		"              \"search_analyzer\":\"ik_smart\"\r\n" + 
        		"           },\r\n" + 
        		"           \"description\": {\r\n" + 
        		"              \"type\": \"text\",\r\n" + 
        		"              \"analyzer\":\"ik_max_word\",\r\n" + 
        		"              \"search_analyzer\":\"ik_smart\"\r\n" + 
        		"           },\r\n" + 
        		"           \"studymodel\": {\r\n" + 
        		"              \"type\": \"keyword\"\r\n" + 
        		"           },\r\n" + 
        		"           \"price\": {\r\n" + 
        		"              \"type\": \"float\"\r\n" + 
        		"           },\r\n" + 
        		"           \"timestamp\": {\r\n" + 
        		"          		\"type\":   \"date\",\r\n" + 
        		"          		\"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\r\n" + 
        		"        	}\r\n" + 
        		"  }\r\n" + 
        		"}", XContentType.JSON);
        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

	//删除索引库
	@Test
	public void testDeleteIndex() throws IOException {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("java1906");
		DeleteIndexResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
		System.out.println(response.isAcknowledged());
	}


  }