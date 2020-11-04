package cn.tedu.rabitmqspringboot.m3;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/4 11:51
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        while (true) {
            System.out.print("输入:");
            String s = new Scanner(System.in).nextLine();

            //spring 默认将消息的 DeliveryMode 设置为 PERSISTENT 持久化,
            amqpTemplate.convertAndSend("task_queue", s);
        }
    }

}
