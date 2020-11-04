package cn.tedu.rabitmqspringboot.m2;

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

    @RabbitListener(queues = "helloWorld")
    public void receive(String msg){
        System.out.println("收到：" +msg);
    }
}
