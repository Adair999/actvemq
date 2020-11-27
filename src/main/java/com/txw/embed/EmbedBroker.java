package com.txw.embed;

import org.apache.activemq.broker.BrokerService;
/**
 * 嵌入式 Broker
 *  相当于嵌入式 tomcat，jetty
 * 开发中一般不会使用内嵌的 broker，都是使用 服务器版本的
 * @author 唐兴旺
 */
@SuppressWarnings("all") //注解警告信息
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        // ActiveMQ也支持在 vm 中通过基于嵌入式的 Broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}