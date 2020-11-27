package com.txw.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
/**
 消费消息
 */
@SuppressWarnings("all") //注解警告信息
public class JmsConsumer {
    public static void main(String[] args) throws Exception {
        final String ACTIVEMQ_URL = "tcp://192.168.3.3:61616";  //url的地址
        final  String QUEUE_NAME = "queue01";  //队列名称
        // 1.创建连接工厂
        //用户名和密码采用默认的admin
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2.通过连接工厂，获取 Connection对象
        Connection conn = activeMQConnectionFactory.createConnection();
        conn.start();  //启动
        // 3.创建Session对象会话
        //第一个参数：事务
        //第二个参数：签收机制
        Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 6.消费消息
        // 6.1 通过阻塞方式 receive()
      /*  while (true){
            TextMessage message = (TextMessage) consumer.receive();
            if (message != null){
                System.out.println("----消费者消费消息---" +message.getText());
            }else {
                break;  //跳出循环
            }
        }*/
        // 6.2 通过监听方式（异步方式）
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof  TextMessage){
                TextMessage textMessage = (TextMessage) message;
                if (textMessage != null){
                    try {
                        System.out.println("----消费者消费消息---" +((TextMessage) message).getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();  //让控制台一直不关闭
        // 7.释放资源
        consumer.close();
        session.close();
        conn.close();
    }
}