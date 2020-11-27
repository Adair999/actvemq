package com.txw.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
@SuppressWarnings("all") //注解警告信息
public class JmsConsumer {
    // activemq 连接 url
    public static final String ACTIVEMQ_URL = "tcp://192.168.3.16:61616";
    // 消息主题名
    public static final String TOPIC_NAME = "topic01";
    public static void main(String[] args) throws Exception {
        // 1. 创建连接工厂，按照给定的url，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 通过连接工厂，获取连接Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        // 启动
        connection.start();
        // 3. 创建会话Session
        // 第一个参数：事务
        // 第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地 Destination
        // Destination 有两个实现：Queue、Topic
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5. 创建消息消费者 MessageConsumer
        MessageConsumer messageConsumer = session.createConsumer(topic);
        // 6. 消费消息
        // 匿名内部类实现
//        messageConsumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                if (null != message && message instanceof TextMessage) {
//                    TextMessage textMessage = (TextMessage)message;
//                    try {
//                        System.out.println("******消费者消费消息 " + textMessage.getText());
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
        // Lambda 表达式实现
        messageConsumer.setMessageListener(message -> {
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println("******消费者消费消息: " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        // 7. 关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}