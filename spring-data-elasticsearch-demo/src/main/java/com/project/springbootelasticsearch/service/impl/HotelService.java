package com.project.springbootelasticsearch.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.springbootelasticsearch.entity.Hotel;
import com.project.springbootelasticsearch.mapper.HotelMapper;
import com.project.springbootelasticsearch.service.IHotelService;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

}
