package com.zhuangxiaoyan.distributed;

import com.zhuangxiaoyan.distributed.snowflake.SnowflakeUtilUpdate;

/**
 * @Classname SnowflakeUtilUpdateTest
 * @Description TODO
 * @Date 2022/6/21 21:53
 * @Created by xjl
 */
public class SnowflakeUtilUpdateTest {
    public static void main(String[] args) {
        for (int i=0;i<4096;i++){
            System.out.println("SnowflakeUtilUpdate的值="+SnowflakeUtilUpdate.generateId());
        }
    }
}
