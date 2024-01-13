package com.project.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {


    public static void main(String[] args) {
        //    参数一：核心线程数量
        //    参数二：最大线程数
        //    参数三：空闲线程最大存活时间 等待工作的空闲线程的超时（以纳秒为单位）。
        //    参数四：时间单位
        //    参数五：任务队列
        //    参数六：创建线程工厂
        //    参数七：任务的拒绝策略
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1 , 3 , 20 , TimeUnit.SECONDS ,
                new ArrayBlockingQueue<>(1) , Executors.defaultThreadFactory() , new ThreadPoolExecutor.CallerRunsPolicy()) ;


        // 提交5个任务
        for(int x = 0 ; x < 5 ; x++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "---->> 执行了任务");
            });
        }

    }


}
