package com.project.thread;

public class ThreadDemo4 {
    public static void main(String[] args) {
        MyDaemon myThread1 = new MyDaemon();
        myThread1.setDaemon(true);
        myThread1.isDaemon();
        myThread1.start();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
        }
    }
}

class MyDaemon extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("我一直守护着你==========");
        }
    }
}