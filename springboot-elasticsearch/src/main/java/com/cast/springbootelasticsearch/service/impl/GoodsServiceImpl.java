package com.cast.springbootelasticsearch.service.impl;

import com.cast.springbootelasticsearch.entity.Goods;
import com.cast.springbootelasticsearch.repository.GoodsRepository;
import com.cast.springbootelasticsearch.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodsRepository repository;

    @Override
    public List<Goods> selectByName() {
        List<Goods> list = new ArrayList<>();
        Iterable<Goods> all = repository.findAll();
        all.forEach(item -> {
            list.add(item);
        });
        return list;
    }

    @Override
    public void saveGoods() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setTitle("华为手机快人一步");
        goods.setPrice(4230.0);
        goods.setCreateTime(new Date());
        goods.setTmId(1L);
        goods.setTmName("华为");
        Goods save = repository.save(goods);
        log.info(save.toString());
    }




}
