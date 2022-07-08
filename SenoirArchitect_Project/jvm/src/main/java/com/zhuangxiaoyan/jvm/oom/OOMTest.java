package com.zhuangxiaoyan.jvm.oom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Classname OOMTest
 * @Description OOM溢出信息
 * @Date 2022/6/28 19:36
 * @Created by xjl
 */
public class OOMTest {

    public static List<Object> list=new ArrayList<>();

    // JVM设置
    // -Xms 1M -Xmx 1M  -XX:PrintGCDetails -Xx:HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump
    public static void main(String[] args) {
        List<Object> list=new ArrayList<>();
        while (true){
            list.add(new int[1024]);
            list.add(new HashMap<Integer, Integer>());
        }
    }
}
