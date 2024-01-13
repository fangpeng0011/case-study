package com.project.thread.future;

import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) {

        MyCallable myCallable = new MyCallable();
        //可以获取线程执行完毕之后的结果.也可以作为参数传递给Thread对象
        FutureTask<String> ft = new FutureTask<>(myCallable);
        //创建线程对象
        Thread t1 = new Thread(ft);
        //开启线程
        t1.start();
        try {
            System.out.println(ft.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String s = ft.get();


    }
}
