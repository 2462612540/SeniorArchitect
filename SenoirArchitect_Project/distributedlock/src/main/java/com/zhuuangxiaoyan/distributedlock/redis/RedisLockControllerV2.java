package com.zhuuangxiaoyan.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description 在第四种情况下，如果在程序运行期间，部署了微服务的jar包的机器突然挂了，代码层面根本就没有走到finally代码块
 * 没办法保证解锁，所以这个key就没有被删除
 * 这里需要对这个key加一个过期时间，设置过期时间有两种方法
 * 1. template.expire(REDIS_LOCK,10, TimeUnit.SECONDS);第一种方法需要单独的一行代码，并没有与加锁放在同一步操作，所以不具备原子性，也会出问题
 * 2. template.opsForValue().setIfAbsent(REDIS_LOCK, value,10L,TimeUnit.SECONDS);第二种方法在加锁的同时就进行了设置过期时间，所有没有问题
 * @date: 2022/4/9 21:25
 * @return:
 * @author: xjl
 */
@RestController
public class RedisLockControllerV2 {

    public static final String REDIS_LOCK = "good_lock";

    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/buyV2")
    public String index() {

        // 每个人进来先要进行加锁，key值为"good_lock"
        String value = UUID.randomUUID().toString().replace("-", "");
        try {
            // 为key加一个过期时间  10s
            Boolean flag = template.opsForValue().setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
            // 加锁失败
            if (!flag) {
                return "抢锁失败！";
            }
            System.out.println(value + " 抢锁成功");
            String result = template.opsForValue().get("goods");
            int total = result == null ? 0 : Integer.parseInt(result);
            if (total > 0) {
                int realTotal = total - 1;
                template.opsForValue().set("goods", String.valueOf(realTotal));
                System.out.println("购买商品成功，库存还剩：" + realTotal + "件");
                return "购买商品成功，库存还剩：" + realTotal + "件";
            } else {
                System.out.println("购买商品失败");
            }
            return "购买商品失败";
        } finally {
            // 如果在抢到所之后，删除锁之前，发生了异常，锁就无法被释放，所以要在finally处理  template.delete(REDIS_LOCK);
            template.delete(REDIS_LOCK);
        }
    }
}
