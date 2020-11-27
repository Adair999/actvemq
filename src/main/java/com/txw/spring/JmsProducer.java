package com.txw.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import javax.jms.TextMessage;
/**
 * Queue模型（P2P）消息生产者
 * @author 唐兴旺
 */
@SuppressWarnings("all") //注解警告信息
@Service
public class JmsProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        JmsProducer jmsProducer = (JmsProducer)applicationContext.getBean("jmsProducer");
        // 发送消息
        jmsProducer.jmsTemplate.send(session -> {
            TextMessage textMessage = session.createTextMessage("spring-activemq-01");
            return textMessage;
        });
    }
}