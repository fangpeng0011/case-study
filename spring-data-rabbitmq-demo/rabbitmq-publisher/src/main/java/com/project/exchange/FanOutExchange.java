package com.project.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanOutExchange {

    /**
     * 声明交换机
     *
     * @return Fanout类型交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("project.fanout");
    }

    /**
     * 第1个队列
     */
    @Bean
    public Queue queueOne() {
        //fanout.queue1表示队列名
        return new Queue("fanout.queue1");
    }


    /**
     * 第2个队列
     */
    @Bean
    public Queue queueTwo() {
        //fanout.queue2表示队列
        return new Queue("fanout.queue2");
    }


    /**
     * 绑定队列和交换机
     * 1.Queue fanoutQueue1 : fanoutQueue1表示IOC容器中Queue的bean的对应的key
     * 2.FanoutExchange fanoutExchange:fanoutExchange表示IOC容器中FanoutExchange的bean的对应的key是上述方法
     * public FanoutExchange fanoutExchange(){}的方法名
     *
     * @return
     */
    @Bean
    public Binding bindingOne() {
        return BindingBuilder.bind(queueOne()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingTwo() {
        return BindingBuilder.bind(queueTwo()).to(fanoutExchange());
    }
}
