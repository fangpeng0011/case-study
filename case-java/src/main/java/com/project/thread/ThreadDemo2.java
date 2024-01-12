package com.project.thread;

public class ThreadDemo2 {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        MyThread myThread1 = new MyThread("myThread1");
        MyThread myThread2 = new MyThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable形式");
            }
        });
        MyThread myThread3 = new MyThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable形式+指定线程名称形式");
            }
        },"myThread3");

        myThread.start();
        myThread1.start();
        myThread2.start();
        myThread3.start();

    }
}


class MyThread extends Thread{

    public MyThread() {
    }

    public MyThread(Runnable target) {
        super(target);
    }

    public MyThread(String name) {
        super(name);
    }

    public MyThread(Runnable target, String name) {
        super(target, name);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name);
    }
}
