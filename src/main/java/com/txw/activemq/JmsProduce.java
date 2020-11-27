package com.txw.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
*服务提供者
 */
@SuppressWarnings("all") //注解警告信息
public class JmsProduce {
    public static void main( String[] args ) throws Exception {
        final String ACTIVEMQ_URL = "tcp://192.168.3.3:61616";  //url的地址
        final  String QUEUE_NAME = "queue01";  //队列名称
        // 1.创建连接工厂
        //用户名和密码采用默认的admin
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2.通过连接工厂，获取 Connection对象
        Connection conn = activeMQConnectionFactory.createConnection();
        // 3.创建Session对象会话
        //第一个参数：事务
        //第二个参数：签收机制
        Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        // 6.发送消息到mq服务器上
        for (int i = 1; i <= 3; i++) {
            //创建消息
            TextMessage message = session.createTextMessage("message: " +i);
            producer.send(message);  //发送消息
        }
        // 7.释放资源
        producer.close();
        session.close();
        conn.close();
    }
}