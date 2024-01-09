package com.cast.springbootelasticsearch.entity;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName: GeoJsonPoint
 * @Author : fangpeng
 * @Date :2024/1/9  16:15
 * @Description: TODO
 * @Version :1.0
 */
public class GeoJsonPoint {
    private String longitude;

    private String latitude;
}