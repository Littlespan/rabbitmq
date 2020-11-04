package cn.tedu.rabitmqspringboot.m6;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/4 11:51
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
@Component
public class Consumer {

    @RabbitListener(bindings = @QueueBinding(value = @Queue,exchange = @Exchange(name = "topic_logs",declare = "false"),key = {"*.orange.*"}))
    public void receive(String msg) throws InterruptedException {
        System.out.println("消费者1收到：" +msg);
        for (int i = 0; i < msg.length(); i++) {
            if(msg.charAt(i) == '.'){
                Thread.sleep(1000);
            }
        }
        System.out.println("处理完毕");
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue,exchange = @Exchange(name = "topic_logs",declare = "false"),key = {"*.*.rabbit"}))
    public void receive2(String msg) throws InterruptedException {
        System.out.println("消费者2收到：" +msg);
        for (int i = 0; i < msg.length(); i++) {
            if(msg.charAt(i) == '.'){
                Thread.sleep(1000);
            }
        }
        System.out.println("处理完毕");
    }
}
