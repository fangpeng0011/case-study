package com.project.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作String类型的数据
     */
    @Test
    public void testString() {
        //只有K不存在的情况下才进行操作，否则不进行任何操作
        Boolean absent = redisTemplate.opsForValue().setIfAbsent("absent", "Absent", -1, TimeUnit.SECONDS);
        if (absent) {
            System.out.println("设置absent成功");
        } else {
            System.out.println("absent已经存在");
        }
        redisTemplate.opsForValue().set("present", "present");
        //只有K已经存在的情况下才进行操作，否则不进行任何操作
        Boolean present = redisTemplate.opsForValue().setIfPresent("present", "Present", -1, TimeUnit.SECONDS);
        if (present) {
            System.out.println("设置present成功");
        } else {
            System.out.println("absent不存在");
        }
        //获取过期时间
        Long presentTtl = redisTemplate.getExpire("present");
        String string = (String) redisTemplate.opsForValue().get("present");
        System.out.println("present过期时间：" + presentTtl + string);

        redisTemplate.opsForValue().set("delete", "delete");
        Boolean deleteBoolean = redisTemplate.delete("delete");
        Boolean presentBoolean = redisTemplate.delete("present");
        Boolean absentBoolean = redisTemplate.delete("absent");
        if (deleteBoolean & presentBoolean & absentBoolean) {
            System.out.println("删除成功");
        }
    }

    /**
     * 操作hash类型的数据
     */
    @Test
    public void testHash() {
//hset
        redisTemplate.opsForHash().put("person", "name", "张三");
        redisTemplate.opsForHash().put("person", "age", "21");
        redisTemplate.opsForHash().put("person", "address", "万科糖业中心");
        redisTemplate.opsForHash().put("person", "email", "dashjklsdahjkj");

        //hget
        Object name = redisTemplate.opsForHash().get("person", "name");
        Object age = redisTemplate.opsForHash().get("person", "age");
        Object address = redisTemplate.opsForHash().get("person", "address");
        Object email = redisTemplate.opsForHash().get("person", "email");
        System.out.println("name：" + name + "age：" + age + "address：" + address + "email：" + email);
        Long person = redisTemplate.getExpire("person");
        System.out.println("person过期时间：" + person);


        //hmset
        Map<String, String> map = new HashMap<>();
        map.put("name", "李四");
        map.put("age", "22");
        map.put("address", "万科唐冶中心");
        map.put("email", "dashjklsdahjkj");
        redisTemplate.opsForHash().putAll("person", map);
        //hmget
        List list = redisTemplate.opsForHash().multiGet("person", Arrays.asList("name", "age", "address", "email"));

        //HVALS
        List personValue = redisTemplate.opsForHash().values("person");
        for (Object o : personValue) {
            System.out.println(o);
        }
        ;
    }

    /**
     * 操作List类型的数据
     */
    @Test
    public void testList() {

        Long list = redisTemplate.opsForList().leftPush("list", "1");
        System.out.println(list);
        Long list1 = redisTemplate.opsForList().rightPush("list", "2");
        System.out.println(list1);
        Object o = redisTemplate.opsForList().leftPop("list");
        System.out.println(o);

        Long list2 = redisTemplate.opsForList().leftPushAll("list", "1", "2", "3", "4", "5");
        System.out.println(list2);

        Object list3 = redisTemplate.opsForList().index("list", 0);
        System.out.println(list3);

        Long list4 = redisTemplate.opsForList().size("list");

        List list5 = redisTemplate.opsForList().range("list", 0, -1);
        System.out.println(list5);
    }

    /**
     * 操作Set类型的数据
     */
    @Test
    public void testSet() {
        redisTemplate.opsForSet().add("myset","a","b","c","a");


        Set<String> myset = redisTemplate.opsForSet().members("myset");

        for (String o : myset) {
            System.out.println(o);
        }

    }

    /**
     * 操作ZSet类型的数据
     */
    @Test
    public void testZset() {
        //获取操作zSet类型的接口对
        //存值
        //Boolean add(K var1, V var2, double var3)  var1 表示键  var2 表示值   var3表示分数
        redisTemplate.opsForZSet().add("myZset","a",10.0);//myZset 表示键  a 表示值   10.0 表示分数
        redisTemplate.opsForZSet().add("myZset","b",11.0);
        redisTemplate.opsForZSet().add("myZset","c",12.0);
        redisTemplate.opsForZSet().add("myZset","a",13.0);

        //取值
        //命令：zrange 键 开始索引 结束索引
        //获取指定范围的元素，得到所有的元素，索引是0到-1
        Set<String> myZset = redisTemplate.opsForZSet().range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }
        //修改分数
        //下面的方法表示在原来分数上进行加20
        redisTemplate.opsForZSet().incrementScore("myZset","c",20.0);

        //删除成员
        redisTemplate.opsForZSet().remove("myZset","a","b");

        //取值
        Set<ZSetOperations.TypedTuple> myZsetList = redisTemplate.opsForZSet().rangeWithScores("myZset", 0, -1);
        for (ZSetOperations.TypedTuple typedTuple : myZsetList) {
            Double score = typedTuple.getScore();
            Object value = typedTuple.getValue();
            System.out.println(score+"---"+value);
        }
    }

    /**
     * 通用操作，针对不同的数据类型都可以操作
     */
    @Test
    public void testCommon() {
//获取Redis中所有的key
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        //判断某个key是否存在
        Boolean itcast = redisTemplate.hasKey("itcast");
        System.out.println(itcast);

        //删除指定key
        redisTemplate.delete("myZset");

        //获取指定key对应的value的数据类型
        DataType dataType = redisTemplate.type("myset");
        System.out.println(dataType.name());


    }
}
