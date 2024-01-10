package com.project.springbootelasticsearch.repository;

import com.project.springbootelasticsearch.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {
}
