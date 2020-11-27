package com.txw.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * Topic模型（PUB/SUB）消息生产者
 * @author 唐兴旺
 */
@SuppressWarnings("all") //注解警告信息
public class JmsProducer {
    // activemq 的连接地址
    public static final String ACTIVEMQ_URL = "tcp://192.168.3.16:61616";
    // 队列名称
    public static final String TOPIC_NAME = "topic01";
    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        // 用户名和密码采用默认 admin/admin
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 通过连接工厂，获取连接 Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        // 创建 session
        // 第一个参数：事务
        // 第二个参数：签收 AUTO_ACKNOWLEDGE 自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        for (int i = 1; i <= 3 ; i++) {
            // 创建消息
            TextMessage textMessage = session.createTextMessage("message " + i);
            // 发送消息到 mq 服务器
            messageProducer.send(textMessage);
        }
        // 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("******消息发布到MQ完成!");
    }
}