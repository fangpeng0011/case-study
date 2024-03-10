package com.project.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanOutListener {

    @RabbitListener(queues = "fanout.queue1")
    public void receiveOne(String msg) {
        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
    }


    @RabbitListener(queues = "fanout.queue2")
    public void receiveTwo(String msg) {
        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
    }


}
