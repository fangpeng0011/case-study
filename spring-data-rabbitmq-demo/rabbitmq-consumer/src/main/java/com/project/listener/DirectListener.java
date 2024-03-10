package com.project.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectListener {


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "direct.queue1"),
                    exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
                    key = {"direct1"}
            )
    )
    public void receive(String msg) {
        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
    }


    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(name = "direct.queue2"),
                            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
                            key = {"direct2"}
                    ),
            }
    )
    public void receive2(String msg) {
        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
    }
}
