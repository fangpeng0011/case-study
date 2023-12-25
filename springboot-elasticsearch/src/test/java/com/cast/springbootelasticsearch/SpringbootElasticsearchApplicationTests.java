package com.cast.springbootelasticsearch;

import com.cast.springbootelasticsearch.entity.Goods;
import com.cast.springbootelasticsearch.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class SpringbootElasticsearchApplicationTests {


    @Autowired
    private GoodsService goodsService;

    @Test
    void contextLoads() {
    }


    @Test
    void selectGoods(){
         goodsService.selectByName().forEach(item->{
             System.out.println();
             log.info(item.toString());
         });
    }

    @Test
    void saveGoods() {
        goodsService.saveGoods();
    }
}
