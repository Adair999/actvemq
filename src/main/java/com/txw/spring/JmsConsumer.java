package com.txw.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
/**
 * Queue模型（P2P）消息消费者
 * @author 唐兴旺
 */
@SuppressWarnings("all") //注解警告信息
@Service
public class JmsConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JmsConsumer jmsConsumer = (JmsConsumer)applicationContext.getBean("jmsConsumer");
        // 接收消息
        String msg = (String)jmsConsumer.jmsTemplate.receiveAndConvert();
        System.out.println(msg);
    }
}