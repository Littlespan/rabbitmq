package m3;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        c.exchangeDeclare("logs","fanout");

        //自动生成对列名,
        //非持久,独占,自动删除
        String queueName =UUID.randomUUID().toString();
        c.queueDeclare(queueName,false,true,true,null);

        c.queueBind(queueName,"logs","");
        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String s = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("收到:" + s);
        };

        CancelCallback cancelCallback = consumerTag -> {

        };
        //消费数据
        c.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}
