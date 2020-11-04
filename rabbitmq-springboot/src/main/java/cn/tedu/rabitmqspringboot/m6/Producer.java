package cn.tedu.rabitmqspringboot.m6;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("输入:");
            String s = new Scanner(System.in).nextLine();
            System.out.println("请输入路由键：");
            String key = new Scanner(System.in).nextLine();
            //spring 默认将消息的 DeliveryMode 设置为 PERSISTENT 持久化,
            System.out.println("输出：" + key +" - " + s);
            amqpTemplate.convertAndSend("topic_logs",key,s);
        }
    }

}
