package com.zhuuangxiaoyan.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * 在第五种情况下，设置了key的过期时间，解决了key无法删除的问题，但问题又来了
 * 我们设置了key的过期时间为10秒，如果我们的业务逻辑比较复杂，需要调用其他微服务，需要15秒
 * 10秒钟过去之后，这个key就过期了，其他请求就又可以设置这个key了
 * 但是如果耗时的请求处理完了，回来继续执行程序，就会把别人设置的key给删除了，这是个很严重的问题
 * 所以，谁上的锁，谁才能删除
 * @date: 2022/4/9 21:25
 * @return:
 * @author: xjl
 */
@RestController
public class RedislockControllerV3 {

    public static final String REDIS_LOCK = "good_lock";

    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/buyV3")
    public String index() {

        // 每个人进来先要进行加锁，key值为"good_lock"
        String value = UUID.randomUUID().toString().replace("-", "");
        try {
            // 为key加一个过期时间10s
            Boolean flag = template.opsForValue().setIfAbsent(REDIS_LOCK, value, 10L, TimeUnit.SECONDS);
            // 加锁失败
            if (!flag) {
                return "抢锁失败！";
            }
            System.out.println(value + " 抢锁成功");
            String result = template.opsForValue().get("goods");
            int total = result == null ? 0 : Integer.parseInt(result);
            if (total > 0) {
                // 如果在此处需要调用其他微服务，处理时间较长。。。
                int realTotal = total - 1;
                template.opsForValue().set("goods", String.valueOf(realTotal));
                System.out.println("购买商品成功，库存还剩：" + realTotal + "件");
                return "购买商品成功，库存还剩：" + realTotal + "件";
            } else {
                System.out.println("购买商品失败");
            }
            return "购买商品失败";
        } finally {
            // 谁加的锁，谁才能删除
            if (template.opsForValue().get(REDIS_LOCK).equals(value)) {
                template.delete(REDIS_LOCK);
            }
        }
    }
}
