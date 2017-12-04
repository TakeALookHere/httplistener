package com.miskevich.httplistener.service.jms;

import com.miskevich.httplistener.model.Document;
import com.miskevich.httplistener.util.JsonConverter;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.UUID;

public class DocumentServiceProducer {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String SUBJECT = "add.request.queue";
    private static volatile DocumentServiceProducer instance;
    private Session session;
    private MessageProducer producer;

    private DocumentServiceProducer() {
        init();
    }

    public static DocumentServiceProducer getInstance() {
        if (instance == null) {
            synchronized (DocumentServiceProducer.class) {
                if (instance == null) {
                    instance = new DocumentServiceProducer();
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
            Destination destination = session.createQueue(SUBJECT);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public String pushIntoQueue(Document document) {
        String correlationID;
        try {
            TextMessage message = session.createTextMessage(JsonConverter.toJson(document));
            correlationID = UUID.randomUUID().toString();
            message.setJMSCorrelationID(correlationID);
            producer.send(message);
            LOG.info("Sending document: " + document);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        return correlationID;
    }
}
