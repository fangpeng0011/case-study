package com.project.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class OrderController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/order")
    public String sendRabbitMq() {
        // 队列名称
        String queueName = "order.queue";
        // 消息
        String message = "订单号:";
        // 发送消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
        }
        return "success";
    }

    @GetMapping("/fanout")
    public String sendRabbitMqFanout() {
        // 交换机名称
        String exchangeName = "project.fanout";
        // 消息
        String message = "订单号:";
        // 发送消息
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(exchangeName, "", message + i);
        }
        return "success";
    }

    @GetMapping("/direct")
    public String sendRabbitMqDirect() {
        // 交换机名称
        String exchangeName = "direct.exchange";
        // 消息
        String message = "订单号:";
        // 发送消息
        for (int i = 0; i < 100; i++) {

            if (i % 2 == 0) {
                rabbitTemplate.convertAndSend(exchangeName, "direct1", message + i);
            } else {
                rabbitTemplate.convertAndSend(exchangeName, "direct2", message + i);
            }
        }
        return "success";
    }

    @GetMapping("/topic")
    public String sendRabbitMqTopic() {
        // 交换机名称
        String exchangeName = "topic.exchange";
        // 发送消息
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                if (i % 4 == 0) {
                    rabbitTemplate.convertAndSend(exchangeName, "topic.xxx.news", "这是复合新闻" + i);
                }else {
                    rabbitTemplate.convertAndSend(exchangeName, "topic.news","这是单一新闻" + i);
                }
            } else {
                rabbitTemplate.convertAndSend(exchangeName, "topic.weather", "这是天气" + i);
            }
        }
        return "success";
    }


}
