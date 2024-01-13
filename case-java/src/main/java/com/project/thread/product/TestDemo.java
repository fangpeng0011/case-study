package com.project.thread.product;

public class TestDemo {

    public static void main(String[] args) {
        Cooker cooker1 = new Cooker("厨师1");
        Cooker cooker2 = new Cooker("厨师2");
        Cooker cooker3 = new Cooker("厨师3");


        Consumer consumer1 = new Consumer("消费者1");
        Consumer consumer2 = new Consumer("消费者2");
        Consumer consumer3 = new Consumer("消费者3");
        Consumer consumer4 = new Consumer("消费者4");

        cooker1.start();
        cooker2.start();
        cooker3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
    }

}
