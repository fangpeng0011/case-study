package com.project.springbootelasticsearch.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.springbootelasticsearch.entity.Hotel;
import com.project.springbootelasticsearch.entity.PageResult;
import com.project.springbootelasticsearch.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("hotel")
public class HotelController {

    @Autowired
    private IHotelService iHotelService;


    @GetMapping("/{id}")
    public Hotel queryById(@PathVariable("id") Long id){
        return iHotelService.getById(id);
    }

    @GetMapping("/list")
    public PageResult hotelList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1") Integer size
    ){
        Page<Hotel> result = iHotelService.page(new Page<>(page, size));
        List<Hotel> records = result.getRecords();
        return new PageResult(result.getTotal(), result.getRecords());
    }

    @PostMapping
    public void saveHotel(@RequestBody Hotel hotel){
        // 新增酒店
        iHotelService.save(hotel);
    }

    @PutMapping()
    public void updateById(@RequestBody Hotel hotel){
        if (hotel.getId() == null) {
            throw new InvalidParameterException("id不能为空");
        }
        iHotelService.updateById(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        iHotelService.removeById(id);
    }
}
