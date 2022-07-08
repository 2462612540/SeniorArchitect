package com.zhuuangxiaoyan.distributedlock.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Classname RedisConnectTest
 * @Description 测试springboot的连接方式
 * @Date 2022/7/3 10:36
 * @Created by xjl
 */
public class RedisConnectTest {

    @Test
    public void testRedis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("http://192.168.25.138:6379");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        System.out.println("======================key==========================");
        //清除当前数据库所有数据
        jedis.flushDB();
        //设置键值对
        jedis.set("zhuangxiaoyan","我是庄小焱");
        //查看存储的键的总数
        System.out.println(jedis.dbSize());
        //取出设置的键值对并打印
        System.out.println(jedis.get("zhuangxiaoyan"));
    }
}
