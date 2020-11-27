package com.txw.topic.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * Topic模型（PUB/SUB）消息消费者
 * @author 唐兴旺
 * 消费者
 *  Topic 的传递模式为持久化 persistent
 *      1) 一定要先运行一次消费者，等于向 MQ 注册，相当于订阅了这个主题，可以在 MQ 控制台的 Subscribers 查看
 *      2) 然后再运行生产者发送消息
 *      3) 此时无论消费者是否在线，都会接收到，若不在线，下次连接时，会把没有收过的消息都接收到
 */
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
        // 设置客户端 ID
        connection.setClientID("忙里偷闲");
        // 3. 创建会话Session
        // 第一个参数：事务
        // 第二个参数：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4. 创建目的地 Destination
        // Destination 有两个实现：Queue、Topic
        Topic topic = session.createTopic(TOPIC_NAME);
        // 5. 订阅消息，（消息是持久化）
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "忙里偷闲");
        // 启动
        connection.start();
        // 6. 接收消息
        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage)message;
            System.out.println("******消费者消费消息: " + textMessage.getText());
            message = topicSubscriber.receive();
        }
        // 7. 关闭资源
        session.close();
        connection.close();
    }
}