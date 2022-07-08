package com.zhuangxiaoyan.distributed;

import com.zhuangxiaoyan.distributed.redis.RedisIdGenerator;
import org.junit.jupiter.api.Test;


/**
 * @Classname RedisIdGeneratorTest
 * @Description 测试过程汇中需要连接的redis的服务器。因为是调用redis来实现的
 * @Date 2022/6/22 7:46
 * @Created by xjl
 */
public class RedisIdGeneratorTest {
    RedisIdGenerator redisIdGenerator=new RedisIdGenerator();

    @Test
    public void getId() {
        String key="hfalkshfalhf";
        long generate = redisIdGenerator.generate(key, redisIdGenerator.getTodayEndTime());
        System.out.println(generate);
    }
    @Test
    public void getIdByToday() {
        String key="hfalkshfalhf";
        String id = redisIdGenerator.generateIdByToday(key, 6);
        System.out.println(id);
    }
}
