package com.zhuangxiaoyan.jvm.lock;

/**
 * @Classname DeadLockTest
 * @Description TODO
 * @Date 2022/6/28 19:46
 * @Created by xjl
 */
public class DeadLockTest {
    private static Object lock1=new Object();
    private static Object lock2=new Object();

    public static void main(String[] args) {
        System.out.println("main thread start");
        new Thread(()->{
            synchronized (lock1){
                try {
                    System.out.println("thread1 begin");
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }synchronized (lock2){
                    System.out.println("thread2 end");
                }
            }
        }).start();

        new Thread(()->{
            synchronized (lock2){
                try {
                    System.out.println("thread2 begin");
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }synchronized (lock1){
                    System.out.println("thread2 end");
                }
            }
        }).start();
        System.out.println("main thread end");
    }
}
