package com.miskevich.httplistener.service.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Optional;

public class DocumentServiceConsumer {
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String SUBJECT = "add.response.queue";
    private static volatile DocumentServiceConsumer instance;
    private Session session;
    private Destination destination;

    private DocumentServiceConsumer() {
        init();
    }

    public static DocumentServiceConsumer getInstance() {
        if (instance == null) {
            synchronized (DocumentServiceConsumer.class) {
                if (instance == null) {
                    instance = new DocumentServiceConsumer();
                }
            }
        }
        return instance;
    }

    private void init() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(SUBJECT);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Message> getFromQueue(String jmsSelector) {
        try {
            MessageConsumer consumer = session.createConsumer(destination, jmsSelector);
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received '" + textMessage.getText() + "'");
                return Optional.of(textMessage);
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
