package com.project.springbootelasticsearch.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.springbootelasticsearch.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {
}
