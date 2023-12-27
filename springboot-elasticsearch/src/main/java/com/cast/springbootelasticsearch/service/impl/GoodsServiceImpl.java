package com.cast.springbootelasticsearch.service.impl;

import com.cast.springbootelasticsearch.entity.Goods;
import com.cast.springbootelasticsearch.repository.GoodsRepository;
import com.cast.springbootelasticsearch.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodsRepository repository;

    @Override
    public void saveGoods() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setTitle("华为手机快人一步");
        goods.setPrice(4230.0);
        goods.setCreateTime(new Date());
        goods.setTmId(1L);
        goods.setCategory1Id(1L);
        goods.setTmName("华为");

        Goods goods1 = new Goods();
        goods1.setId(2L);
        goods1.setTitle("华为手机快人二步");
        goods1.setPrice(4240.0);
        goods1.setCreateTime(new Date());
        goods1.setCategory1Id(2L);
        goods1.setTmId(2L);
        goods1.setTmName("华为");

        Goods goods2 = new Goods();
        goods2.setId(3L);
        goods2.setTitle("苹果手机");
        goods2.setPrice(9240.0);
        goods2.setCreateTime(new Date());
        goods2.setTmId(3L);
        goods1.setCategory1Id(3L);
        goods2.setTmName("苹果");

        Goods goods3 = new Goods();
        goods3.setId(4L);
        goods3.setTitle("小米手机，屌丝专用");
        goods3.setPrice(5240.0);
        goods3.setCreateTime(new Date());
        goods3.setTmId(4L);
        goods1.setCategory1Id(4L);
        goods3.setTmName("小米");

        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(goods);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        repository.saveAll(goodsList);
    }

    @Override
    public List<Goods> selectByName() {
        List<Goods> list = new ArrayList<>();
        Iterable<Goods> all = repository.findAll();
        all.forEach(item -> {
            list.add(item);
        });
        return list;
    }




}
