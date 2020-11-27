package com.txw.embed;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 * Queue模型（P2P）消息消费者
 * @author 唐兴旺
 */
@SuppressWarnings("all") //注解警告信息
public class JmsConsumer {
    // activemq 的连接地址
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    // 队列名称
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        // 用户名和密码采用默认 admin/admin
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 通过连接工厂，获取连接 Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        // 启动
        connection.start();
        // 创建 session
        // 第一个参数：事务
        // 第二个参数：签收 AUTO_ACKNOWLEDGE 自动签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        // 消费消息
        // 1）通过阻塞方式 receive()
        // 订阅者或消费者调用 MessageConsumer 的 receive() 方法来接收消息
        // receive() 方法在能够接收到消息之前（或超时之前）将一直阻塞
        // receive() 与 receive(long timeout) 区别？
        // receive() 会一直连接到 mq 服务器不断开，等待接收消息
        //receive(long timeout) 接收完消息后，在指定的时间内没有消息，则主动断开连接，控制台消费者数量-1
//        while (true) {
//            TextMessage textMessage = (TextMessage) messageConsumer.receive();
//            if (textMessage != null) {
//                System.out.println("-------------消费者消费消息：" + textMessage.getText());
//            } else {
//                break;
//            }
//        }
        // 2) 通过监听方式 （异步非阻塞方式）
        // 异步非阻塞方式：监听器 onMessage()
        // 订阅者或消费者通过 MessageConsumer 的 setMessageListener(MessageListener messageListener) 方法注册一个消息监听器
        // 当消息到达之后，系统自动调用监听器 MessageListener 的 onMessage(Message message) 方法
        messageConsumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                if (textMessage != null) {
                    try {
                        System.out.println("-------------消费者消费消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // 让控制台一直不关闭
        System.in.read();
        // 关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}