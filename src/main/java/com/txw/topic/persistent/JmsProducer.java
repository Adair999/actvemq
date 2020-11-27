package com.txw.topic.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * Topic模型（PUB/SUB）消息生产者
 * @author 唐兴旺
 * topic 采用 pub/sub 持久化模式：
 *  1）producer 设置持久化
 *      messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
 *  2）consumer 采用 sub 方式订阅消息
 *      connection.setClientID("忙里偷闲");
 *      TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "忙里偷闲");
 */
@SuppressWarnings("all") //注解警告信息
public class JmsProducer {
    // activemq 连接 url
    public static final String ACTIVEMQ_URL = "tcp://192.168.3.16:61616";
    // 消息主题名
    public static final String TOPIC_NAME = "topic01";
    public static void main( String[] args ) throws JMSException {
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
        // 5. 创建消息生产者 MessageProducer
        MessageProducer messageProducer = session.createProducer(topic);
        // 设置持久化模式
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 发送3条消息到 mq
        for (int i = 1; i <= 3; i++) {
            // 6. 创建消息 Message
            TextMessage textMessage = session.createTextMessage("message " + i);
            // 7. 消息生产者发送消息到 mq 服务器
            messageProducer.send(textMessage);
        }
        // 8. 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("******消息发布到MQ完成");
    }
}