package com.project.thread;


/**
 * 1、实现多线程方式一：继承Thread类
 */
public class ThreadDemo1 extends Thread {
    public ThreadDemo1(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("测试MyThread方法:" + i);
        }
    }
}

