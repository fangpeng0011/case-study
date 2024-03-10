package com.project.springbootelasticsearch.service;


import com.project.springbootelasticsearch.entity.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> selectByName();

    void saveGoods();
}
