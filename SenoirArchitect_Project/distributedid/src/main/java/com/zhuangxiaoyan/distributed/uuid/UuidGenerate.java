package com.zhuangxiaoyan.distributed.uuid;

import java.util.UUID;

/**
 * @Classname UuidGenerate
 * @Description TODO
 * @Date 2022/6/22 21:14
 * @Created by xjl
 */
public class UuidGenerate {
    public static void main(String[] args) {
        // 随机数版本实现
        UUID uuid = UUID.randomUUID();
        System.out.println("随机数版本实现uuid="+uuid);
        // 基于的MD5实现的UUID算法
        UUID uuid1 = UUID.nameUUIDFromBytes("庄小焱".getBytes());
        System.out.println("基于的MD5实现的UUID="+uuid1);
        // 基于字符串格式8-4-4-4-12的UUID输入，重新解析出mostSigBits和leastSigBits，这个静态工厂方法也不常用
        UUID number=UUID.fromString("83f6c-d58a-3231-b884-0eb0767");
        System.out.println(number);
        // 基于处理好的字节数组，实例化String，并且编码指定为LATIN1
        UUID id=new UUID((long) 3.0, (long) 4.0);
        System.out.println(id);
    }
}
