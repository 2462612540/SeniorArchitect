package com.zhuangxiaoyan.distributed;

import com.zhuangxiaoyan.distributed.snowflake.SnowFlake;

/**
 * @Classname SnowflakeTest
 * @Description  SnowflakeTest 测试
 * @Date 2022/6/21 21:34
 * @Created by xjl
 */
public class SnowflakeTest {

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        int n=1;
        for (int i = 0; i < (1 << 12); i++) {
            n++;
            System.out.println("Snowflake 计算的ID值为="+snowFlake.nextId());
        }
        System.out.println("总共产生——"+n);
    }
}
