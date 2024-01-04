package com.cast.springbootelasticsearch;

import com.cast.springbootelasticsearch.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SpringbootElasticsearchApplicationTest {

    @Autowired
    GoodsService goodsService;

    @Test
    void saveGoodsList(){
        goodsService.saveGoods();
    }
}