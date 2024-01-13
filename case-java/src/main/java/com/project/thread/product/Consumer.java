package com.project.thread.product;

public class Consumer extends Thread {

    public Consumer(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (Food.lock) {
            String name = Thread.currentThread().getName();
            while (true) {
                if (Food.foodSum > 0) {
                    Food.lock.notifyAll();
                    consume();
                    System.out.println(name + "消费者消费食物，食物剩余量:" + Food.foodSum);
                } else {
                    try {
                        Food.lock.wait();
                        System.out.println("快点给老子上菜！");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void consume() {
        Food.foodSum--;
    }
}
