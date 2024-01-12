package com.project.thread;

import java.util.Date;

public class ThreadDemo3 {
    public static void main(String[] args) {
        MyThreadTest myThread1 = new MyThreadTest("myThread1");

        System.out.println("======================线程启动之前状态"+myThread1.isAlive());
        myThread1.start();

        System.out.println("======================线程优先级"+myThread1.getPriority());
        /**
         *  The minimum priority that a thread can have. 最低优先级
         *      public final static int MIN_PRIORITY = 1;
         *
         *  The default priority that is assigned to a thread. 普通优先级
         *      public final static int NORM_PRIORITY = 5;
         *
         *  The maximum priority that a thread can have. 最高优先级
         *      public final static int MAX_PRIORITY = 10;
         */
        myThread1.setPriority(Thread.MAX_PRIORITY);
        System.out.println("======================线程优先级调整之后"+myThread1.getPriority());
        System.out.println("======================线程启动启动之后状态"+myThread1.isAlive());


        try {
            System.currentTimeMillis();
            Thread.sleep(1000);
            System.currentTimeMillis();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //当前线程暂停一下
        Thread.yield();

        MyThreadTest myThread2 = new MyThreadTest("myThread2");
        myThread2.start();
        try {
            myThread2.join(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}


class MyThreadTest extends Thread{

    public MyThreadTest() {
    }

    public MyThreadTest(String name) {
        super(name);
    }

    @Override
    public void run() {
        /**
         * 获取当前线程名称
         *  currentThread() :返回对当前正在执行的线程对象的引用;
         */
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }
}
