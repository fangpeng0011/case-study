package com.project.thread.runnable;

public class TestDemo {

    public static void main(String[] args) {
        String name = Thread.currentThread().getName();
        //1、实现多线程方式一：继承Thread类
        ThreadDemo1 myThread = new ThreadDemo1("myThread");
        myThread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("当前执行线程:" + name + i);
        }
        //实现多线程方式二：实现Runnable接口【应用】

        RunnableDemo runnableDemo = new RunnableDemo();

        Thread thread = new Thread(runnableDemo);
        thread.setName("Runnable接口方式");
        thread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println("当前执行线程:" + name + i);
        }
    }
}
