package com.zhuuangxiaoyan.distributedlock.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @description
 * 在第六种情况下，规定了谁上的锁，谁才能删除
 * 1. 缓存续命
 * 2. redis异步复制造成的锁丢失：主节点没来得及把刚刚set进来这条数据给从节点，就挂了
 * @param: null
 * @date: 2022/4/9 21:25
 * @return:
 * @author: xjl
 */

@RestController
public class RedisLockControllerV5 {

    public static final String REDIS_LOCK = "good_lock";

    @Autowired
    StringRedisTemplate template;

    Redisson redisson;

    @RequestMapping("/buyV5")
    public String index() {

        RLock lock = redisson.getLock(REDIS_LOCK);
        lock.lock();
        // 每个人进来先要进行加锁，key值为"good_lock"
        String value = UUID.randomUUID().toString().replace("-", "");
        try {
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
            // 如果锁依旧在同时还是在被当前线程持有，那就解锁。 如果是其他的线程持有 那就不能释放锁资源
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
