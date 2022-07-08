package com.zhuuangxiaoyan.distributedlock.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * @Classname DistributedLock
 * @Description TODO
 * @Date 2022/7/2 8:57
 * @Created by xjl
 */
public class DistributedJedis {
    //redis连接
    private final JedisPool jedisPool;

    public DistributedJedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 加锁 -- 可设置超时时间
     *
     * @param lockName       锁的名字叫key
     * @param acquireTimeout 获取锁的超时时间  -----
     * @param timeout        拿到锁后的超时时间-----ms
     * @return 锁标识
     */
    public String lockWithTimeout(String lockName, long acquireTimeout, long timeout) {
        Jedis conn = null;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = jedisPool.getResource();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + lockName;

            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int) (timeout / 1000);//单位是: 秒

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;

            while (System.currentTimeMillis() < end) {//获取锁还没有超时
                //setnx操作向redis进行存储：
                //redis有key,返回0
                //redis没有key,返回1
                if (conn.setnx(lockKey, identifier) == 1) {
                    //设置锁的实效时间
                    conn.expire(lockKey, lockExpire);
                    // 返回锁的value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }

                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     *
     * @param lockName   锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();

            while (true) {
                // 监视lockKey，准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();//开启事务
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();//提交事务
                    if (results == null) {//删除锁失败
                        continue;
                    }
                    retFlag = true;//是否释放锁成功
                }
                conn.unwatch();
                break;
            }

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }
}
