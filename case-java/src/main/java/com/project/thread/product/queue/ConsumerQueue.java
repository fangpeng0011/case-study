package com.project.thread.product.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class ConsumerQueue extends Thread {

    private ArrayBlockingQueue<String> bd;

    public ConsumerQueue(String name, ArrayBlockingQueue<String> bd) {
        super(name);
        this.bd = bd;
    }

    @Override
    public void run() {
        while (true){
            try {
                String take = bd.take();
                System.out.println("顾客" + Thread.currentThread().getName() + "吃了一个" + take);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
