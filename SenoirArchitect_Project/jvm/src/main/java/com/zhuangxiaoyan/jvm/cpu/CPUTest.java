package com.zhuangxiaoyan.jvm.cpu;

import java.util.Map;

/**
 * @Classname CPUTest
 * @Description TODO
 * @Date 2022/6/28 20:05
 * @Created by xjl
 */
public class CPUTest {

    public int computer(){
        int a=1;
        int b=2;
        int c=(a+b)*10;
        return c;
    }

    public static void main(String[] args) {
        // 设置死循环导致CPU飙高
        CPUTest cpuTest=new CPUTest();
        while (true){
            cpuTest.computer();
        }
    }
}
