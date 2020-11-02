package m2;

import com.rabbitmq.client.*;

import java.io.IOException;
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

        c.queueDeclare("task_queue", true,false,false,null);

        c.basicQos(1);

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String s = new String(message.getBody(), "utf-8");
                System.out.println("收到:" + s);
                for (int i = 0; i < s.length(); i++) {
                    if(s.charAt(i) == '.'){
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("---------消息处理完成------------");
                c.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };

        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        };
        //消费数据
        c.basicConsume("task_queue",false,deliverCallback,cancelCallback);
    }
}
