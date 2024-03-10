package com.project.springbootelasticsearch;

import com.alibaba.fastjson2.JSON;
import com.project.springbootelasticsearch.entity.Hotel;
import com.project.springbootelasticsearch.entity.HotelDoc;
import com.project.springbootelasticsearch.service.IHotelService;
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
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.util.List;

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
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http"))
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
            request.add(new IndexRequest("hotel").id(hotelDoc.getId().toString())
                .source(JSON.toJSONString(hotelDoc), XContentType.JSON));
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
        NativeSearchQuery query = new NativeSearchQuery(QueryBuilders.multiMatchQuery("如家", "name", "brand"));
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void termQuery() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(QueryBuilders.termQuery("city", "上海"));
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(nativeSearchQuery, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void rangQuery() {
        RangeQueryBuilder query = QueryBuilders.rangeQuery("price");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(query);
        query.gt(Integer.valueOf(100));
        query.lt(Integer.valueOf(1000));
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(nativeSearchQuery, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    @Test
    void boolQuery() {
        GeoDistanceQueryBuilder location = new GeoDistanceQueryBuilder("location");
        //经纬度坐标
        location.point(31.034661, 121.612282);
        location.distance(10, DistanceUnit.KILOMETERS);
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("name", "如家"))
                    .mustNot(QueryBuilders.rangeQuery("price").gt(Integer.valueOf(10000))).filter(location))
            .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
            .withHighlightFields(new HighlightBuilder.Field("name"), new HighlightBuilder.Field("price")).build();
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }


    /**
     * 案例需求：实现黑马旅游的酒店搜索功能，完成关键字搜索和分页 - 请求参数：JSON对象，包含4个字段： - key：搜索关键字 - page：页码 - size：每页大小 - sortBy：排序，目前暂不实现 -
     * 返回值：分页查询，需要返回分页结果PageResult，包含两个属性： - `total`：总条数 - `List<HotelDoc>`：当前页的数据
     */
    @Test
    void queryOneCase() {
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("all", "如家"))).withPageable(PageRequest.of(1, 20))
            .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC)).build();
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    /**
     * 需求：添加品牌、城市、星级、价格等过滤功能
     */
    @Test
    void queryTwoCase() {
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("all", "如家")))
            .withFilter(QueryBuilders.termQuery("city", "上海")).withFilter(QueryBuilders.termQuery("brand", "如家"))
            .withFilter(QueryBuilders.termQuery("starName", "二钻"))
            .withFilter(QueryBuilders.rangeQuery("price").gte(100).lte(1000)).withPageable(PageRequest.of(1, 20))
            .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC)).build();
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

    /**
     * 我周边的酒店
     *      需求：我附近的酒店
     */
    @Test
    void queryThreeCase() {
        GeoDistanceQueryBuilder distanceQueryBuilder = new GeoDistanceQueryBuilder("location");
        distanceQueryBuilder.point(31.034661, 121.612282);
        distanceQueryBuilder.distance(10, DistanceUnit.KILOMETERS);
        GeoDistanceSortBuilder distanceSortBuilder = new GeoDistanceSortBuilder("location", 31.034661, 121.612282);
        NativeSearchQuery query = new NativeSearchQueryBuilder().withFilter(distanceQueryBuilder)
            .withSort(distanceSortBuilder.order(SortOrder.ASC)).build();
        SearchHits<Hotel> search = elasticsearchRestTemplate.search(query, Hotel.class);
        List<SearchHit<Hotel>> searchHits = search.getSearchHits();
        for (SearchHit<Hotel> searchHit : searchHits) {
            Hotel content = searchHit.getContent();
            System.out.println(content);
        }
    }

}