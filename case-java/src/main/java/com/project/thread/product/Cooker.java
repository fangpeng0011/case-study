package com.project.thread.product;

public class Cooker extends Thread {


    public Cooker(String name) {
        super(name);
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (Food.lock) {
            while (true) {
                if (Food.foodSum <= 0) {
                    Food.lock.notifyAll();
                    create();
                    System.out.println(name + "厨师生产食物，食物剩余量:" + Food.foodSum);
                } else {
                    try {
                        Food.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void create() {
        //每次一位厨师只能生产一个食物
        Food.foodSum += 10;
    }
}
