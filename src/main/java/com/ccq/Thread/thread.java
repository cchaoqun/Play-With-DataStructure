package com.ccq.Thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Chaoqun Cheng
 * @date 2021-07-2021/7/8-21:26
 */

public class thread {

    @Test
    public void test(){
        Thread t = new Thread();
        ThreadLocal tl = new ThreadLocal();
        ReentrantLock rl = new ReentrantLock();
        ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
    }
}
