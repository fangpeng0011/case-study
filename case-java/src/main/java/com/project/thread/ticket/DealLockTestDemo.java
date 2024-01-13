package com.project.thread.ticket;

public class DealLockTestDemo {


    public static void main(String[] args) {
        Object objA = new Object();

        Object objB = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (objA){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (objB){
                        System.out.println("小康同学正在走路");
                    }
                }
            }
        },"线程1");


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (objB){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (objA){
                        System.out.println("小红同学正在走路");
                    }
                }
            }
        },"线程2");

        t1.start();
        t2.start();
    }

}
