package com.project.springbootelasticsearch.controller;

import com.project.springbootelasticsearch.entity.Goods;
import com.project.springbootelasticsearch.service.GoodsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GoodsController
 * @Author : fangpeng
 * @Date :2023/12/27  9:50
 * @Description: TODO
 * @Version :1.0
 */
@RestController
@RequestMapping("/es")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("/query/match_all")
    public List<Goods> matchAll() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/match")
    public List<Goods> match() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("title", "小米")).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/prefix")
    public List<Goods> prefix() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.prefixQuery("title", "华为")).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }


    @GetMapping("/query/term")
    public List<Goods> term() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("title", "苹果手机")).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/terms")
    public List<Goods> terms() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.termsQuery("title", "苹果手机", "华为手机")).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/range")
    public List<Goods> range() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder()
            .withQuery(
                QueryBuilders.rangeQuery("price").gt("9000")
            )
            .build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/must")
    public List<Goods> must() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("华为"))).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/should")
    public List<Goods> should() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.boolQuery().should(QueryBuilders.queryStringQuery("华为"))).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/must_not")
    public List<Goods> mustNot() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.boolQuery().mustNot(QueryBuilders.queryStringQuery("华为"))).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }


    @GetMapping("/query/filter")
    public List<Goods> filter() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder().withQuery(
            QueryBuilders.boolQuery()
                .filter(QueryBuilders.queryStringQuery("华为"))).build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/query/agg")
    public List<Goods> agg() {
        List<Goods> goodsList = new ArrayList<>();
        Query query = new NativeSearchQueryBuilder()
            .addAggregation(AggregationBuilders.max("price_max").field("price"))
            .addAggregation(AggregationBuilders.min("price_min").field("price"))
            .addAggregation(AggregationBuilders.avg("price_avg").field("price"))
            .addAggregation(AggregationBuilders.sum("price_sum").field("price"))
            .addAggregation(AggregationBuilders.terms("price_count").field("price"))
            .addAggregation(AggregationBuilders.stats("price_stats").field("price"))
            .build();
        SearchHits<Goods> search = elasticsearchRestTemplate.search(query, Goods.class);
        if (search.getTotalHits() > 0) {
            search.getSearchHits().forEach(item -> {
                Goods content = item.getContent();
                goodsList.add(content);
            });
        }
        return goodsList;
    }

    @GetMapping("/selectByName")
    public List<Goods> selectByName() {
        List<Goods> goodsList = goodsService.selectByName();
        return goodsList;
    }
}