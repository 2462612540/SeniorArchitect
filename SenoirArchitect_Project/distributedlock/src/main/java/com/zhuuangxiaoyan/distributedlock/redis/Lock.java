package com.zhuuangxiaoyan.distributedlock.redis;

import lombok.Data;

/**
 * @Classname Lock
 * @Description TODO
 * @Date 2022/7/2 9:01
 * @Created by xjl
 */
@Data
public class Lock {

    /**
     * key名
     */
    private String name;
    /**
     * value值
     */
    private String value;

    public Lock(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
