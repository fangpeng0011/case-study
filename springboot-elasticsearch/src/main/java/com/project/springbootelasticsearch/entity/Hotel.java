package com.project.springbootelasticsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@TableName("tb_hotel")
@Document(indexName = "hotel", shards = 1, replicas = 1)
public class Hotel {

    @TableId(type = IdType.INPUT)
    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Keyword,index = false)
    private String address;

    @Field(type = FieldType.Integer,index = false)
    private Integer price;

    @Field(type = FieldType.Integer,index = false)
    private Integer score;

    @Field(type = FieldType.Keyword,copyTo = "all")
    private String brand;

    @Field(type = FieldType.Keyword,copyTo = "all")
    private String city;

    @Field(type = FieldType.Keyword)
    private String starName;

    @Field(type = FieldType.Keyword)
    private String business;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text,analyzer = "ik_smart_word")
    private String pic;
}
