package com.cast.springbootelasticsearch.repository;

import com.cast.springbootelasticsearch.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {
}
