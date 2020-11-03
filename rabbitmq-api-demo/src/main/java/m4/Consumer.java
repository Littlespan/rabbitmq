package m4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/2 16:15
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");

        Channel c = f.newConnection().createChannel();

        /*
         * 为什么两边都定义队列
         * 两边都定义，则不用关心生产者，消费者的启动顺序，谁先启动谁创建队列
         * */

        //定义名字为 logs 的交换机, 它的类型是 fanout
        c.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);

        //自动生成对列名,
        //非持久,独占,自动删除
        String queueName = c.queueDeclare().getQueue();

        System.out.println("输入接收的日志级别,用空格隔开:");
        String[] a = new Scanner(System.in).nextLine().split("\\s");

        //把该队列,绑定到 direct_logs 交换机
        //允许使用多个 bindingKey
        for (String level : a) {
            c.queueBind(queueName,"direct_logs",level);
        }

        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String s = new String(message.getBody(), StandardCharsets.UTF_8);
            String routingKey = message.getEnvelope().getRoutingKey();
            System.out.println("收到:" + routingKey + "-" + s);
        };

        CancelCallback cancelCallback = consumerTag -> {

        };
        //消费数据
        c.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}
