package com.zhuuangxiaoyan.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname DistributedLockController
 * @Description TODO
 * @Date 2022/7/2 9:08
 * @Created by xjl
 */
public class DistributedLockController {
    @Autowired
    DistributedLockConfig distributedLockConfig;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("test")
    public String index() {
        distributedLockConfig.setTemplate(redisTemplate);
        Lock lock = new Lock("test", "test");
        if (distributedLockConfig.tryLock(lock)) {
            try {
                //为了演示锁的效果，这里睡眠5000毫秒
                System.out.println("执行方法");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            distributedLockConfig.releaseLock(lock);
        }
        return "hello world!";
    }
}
