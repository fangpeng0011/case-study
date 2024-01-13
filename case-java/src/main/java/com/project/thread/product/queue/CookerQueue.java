package com.project.thread.product.queue;

import com.project.thread.product.Food;

import java.util.concurrent.ArrayBlockingQueue;

public class CookerQueue extends Thread {

    private ArrayBlockingQueue<String> bd;

    public CookerQueue(String name, ArrayBlockingQueue<String> bd) {
        super(name);
        this.bd = bd;
    }

    @Override
    public void run() {
        while (true){
            try {
                bd.put("蜜汁");
                System.out.println("顾客" + Thread.currentThread().getName() + "制作了一个蜜汁");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void create() {
        //每次一位厨师只能生产一个食物
        Food.foodSum += 10;
    }
}
