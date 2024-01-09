package com.cast.springbootelasticsearch;

import com.alibaba.fastjson2.JSON;
import com.cast.springbootelasticsearch.entity.Hotel;
import com.cast.springbootelasticsearch.entity.HotelDoc;
import com.cast.springbootelasticsearch.service.IHotelService;
import com.cast.springbootelasticsearch.service.impl.HotelService;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringbootElasticsearchApplicationTest {

    @Autowired
    IHotelService hotelService;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void saveGoodsList() throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
            new UsernamePasswordCredentials("elastic", "changeme"));  //es账号密码（默认用户名为elastic）
        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }));

        // 批量查询酒店数据
        List<Hotel> hotels = hotelService.list();
        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加多个新增的Request
        for (Hotel hotel : hotels) {
            // 2.1.转换为文档类型HotelDoc
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 2.2.创建新增文档的Request对象
            request.add(new IndexRequest("hotel")
                .id(hotelDoc.getId().toString())
                .source(
                    JSON.toJSONString(hotelDoc), XContentType.JSON)
            );
        }
        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    @Test
    void MatchAllQuery() {
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void mulitMatchQuery() {
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.multiMatchQuery(
            "如家", "name", "brand"
        ));
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void termQuery(){
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(
            QueryBuilders.termQuery("city","上海")
        );
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(nativeSearchQuery, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void rangQuery(){

//        NativeSearchQuery query = new NativeSearchQuery(
//            QueryBuilders.rangeQuery("price").gt("")
//        )
    }

}