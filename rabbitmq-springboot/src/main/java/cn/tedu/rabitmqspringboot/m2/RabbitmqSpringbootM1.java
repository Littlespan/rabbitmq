package cn.tedu.rabitmqspringboot.m2;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author 作者：hyh
 * @version v.1.0 创建时间：2020/11/4 11:46
 * @email 邮箱：15205698133@163.com
 * @description 描述：
 */
@SpringBootApplication
public class RabbitmqSpringbootM1 {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSpringbootM1.class,args);
    }

    @Bean
    public Queue getQueue(){
        return new Queue("helloWorld", false);
    }

    @Autowired
    private Producer producer;

    @PostConstruct  //spring完全加载完成，扫描创建所有对象，并完成所有注入操作后执行
    public void getProducer() {
        producer.send();
    }
}
