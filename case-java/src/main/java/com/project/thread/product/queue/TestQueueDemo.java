package com.project.thread.product.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class TestQueueDemo {

    public static void main(String[] args) {

        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue(5);


        CookerQueue cooker1 = new CookerQueue("厨师1",arrayBlockingQueue);
        CookerQueue cooker2 = new CookerQueue("厨师2",arrayBlockingQueue);
        CookerQueue cooker3 = new CookerQueue("厨师3",arrayBlockingQueue);


        ConsumerQueue consumer1 = new ConsumerQueue("消费者1",arrayBlockingQueue);
        ConsumerQueue consumer2 = new ConsumerQueue("消费者2",arrayBlockingQueue);
        ConsumerQueue consumer3 = new ConsumerQueue("消费者3",arrayBlockingQueue);
        ConsumerQueue consumer4 = new ConsumerQueue("消费者4",arrayBlockingQueue);

        cooker1.start();
        cooker2.start();
        cooker3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
    }

}
