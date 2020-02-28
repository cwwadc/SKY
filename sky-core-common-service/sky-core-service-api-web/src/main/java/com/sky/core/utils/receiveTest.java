package com.sky.core.utils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class receiveTest {
    private final static String QUEUE_NAME = "rabbit.q.offline.recording";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.77.179.74");
        factory.setPort(5672); //默认端口号
        factory.setUsername("rabbit");//默认用户名
        factory.setPassword("admin");//默认密码
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);


    }

}
