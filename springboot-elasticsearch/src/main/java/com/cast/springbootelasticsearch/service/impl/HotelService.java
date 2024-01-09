package com.cast.springbootelasticsearch.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cast.springbootelasticsearch.entity.Hotel;
import com.cast.springbootelasticsearch.mapper.HotelMapper;
import com.cast.springbootelasticsearch.service.IHotelService;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

}
