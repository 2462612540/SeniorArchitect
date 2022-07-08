package com.zhuuangxiaoyan.distributedlock.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname NoneController
 * @Description TODO
 * @Date 2022/7/2 9:24
 * @Created by xjl
 */
@RestController
public class NoneController {

    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/buy")
    public String index() {
        // Redis中存有goods:001号商品，数量为100  相当于是的redis中的get("goods")的操作。
        String result = template.opsForValue().get("goods");
        // 获取到剩余商品数
        int total = result == null ? 0 : Integer.parseInt(result);
        if (total > 0) {
            // 剩余商品数大于0 ，则进行扣减
            int realTotal = total - 1;
            // 将商品数回写数据库  相当于设置新的值的结果
            template.opsForValue().set("goods", String.valueOf(realTotal));
            System.out.println("购买商品成功，库存还剩：" + realTotal + "件");
            return "购买商品成功，库存还剩：" + realTotal + "件";
        } else {
            System.out.println("购买商品失败");
        }
        return "购买商品失败";
    }
}
