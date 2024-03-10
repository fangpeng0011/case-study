package com.project.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {


    @RabbitListener(queues = "order.queue")
    public void receiveOne(String msg) {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Listener1收到消息：" + "[" + msg + "]");
    }

    @RabbitListener(queues = "order.queue")
    public void receiveTwo(String msg) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Listener2收到消息：" + "[" + msg + "]");
    }

}
