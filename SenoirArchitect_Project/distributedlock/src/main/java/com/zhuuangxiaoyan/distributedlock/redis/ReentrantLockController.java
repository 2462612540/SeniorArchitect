package com.zhuuangxiaoyan.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname ReentrantLockController
 * @Description TODO
 * @Date 2022/7/2 9:25
 * @Created by xjl
 */
@RestController
public class ReentrantLockController {
    // 引入的ReentrantLock 锁机制
    ReentrantLock lock = new ReentrantLock();

    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/buyV6")
    public String index() {
        // 加锁
        lock.lock();
        try {
            // Redis中存有goods:001号商品，数量为100  相当于是的redis中的get("goods")的操作。
            String result = template.opsForValue().get("goods");
            // 获取到剩余商品数
            int total = result == null ? 0 : Integer.parseInt(result);
            if (total > 0) {
                int realTotal = total - 1;
                // 将商品数回写数据库  相当于设置新的值的结果
                template.opsForValue().set("goods", String.valueOf(realTotal));
                System.out.println("购买商品成功，库存还剩：" + realTotal + "件");
                return "购买商品成功，库存还剩：" + realTotal + "件";
            } else {
                System.out.println("购买商品失败");
            }
        } catch (Exception e) {
            //解锁
            lock.unlock();
        } finally {
            //解锁
            lock.unlock();
        }
        return "购买商品失败";
    }
}