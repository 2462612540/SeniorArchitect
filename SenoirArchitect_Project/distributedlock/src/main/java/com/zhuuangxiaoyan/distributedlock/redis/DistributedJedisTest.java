package com.zhuuangxiaoyan.distributedlock.redis;

/**
 * @Classname JedisLockTest
 * @Description TODO
 * @Date 2022/7/2 9:19
 * @Created by xjl
 */
public class DistributedJedisTest {
    public static void main(String[] args) {

        //临界对象
        DistributedJedisService service = new DistributedJedisService();

        for (int i = 0; i < 50; i++) {
            ThreadA threadA = new ThreadA(service);
            threadA.setName("ThreadName->"+i);
            threadA.start();
        }
    }
}

class ThreadA extends Thread {

    private DistributedJedisService skillService;

    public ThreadA(DistributedJedisService skillService) {
        this.skillService = skillService;
    }

    @Override
    public void run() {
        skillService.seckill();
    }
}
