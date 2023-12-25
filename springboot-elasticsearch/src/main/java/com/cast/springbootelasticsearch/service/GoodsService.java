package com.cast.springbootelasticsearch.service;


import com.cast.springbootelasticsearch.entity.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> selectByName();

    void saveGoods();
}
