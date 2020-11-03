package m5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/2 16:03
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");

        Channel c = f.newConnection().createChannel();

        //定义名字为logs的交换机,交换机类型为direct
        //这一步是必须的，因为禁止发布到不存在的交换。
        c.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);

        //发送消息
        while(true){
            System.out.println("输入消息：");
            String msg = new Scanner(System.in).nextLine();
            if(msg.equals("exit")){
                break;
            }
            System.out.print("输入routingKey: ");
            String routingKey = new Scanner(System.in).nextLine();
            //第二个参数：路由键， 和绑定建进行匹配，将消息路由到匹配的队列
            c.basicPublish("topic_logs", routingKey , null , msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息已发送: "+routingKey+"-"+msg);
        }
    }
}
