package com.miskevich.httplistener.service;

import com.miskevich.httplistener.model.Document;
import com.miskevich.httplistener.service.jms.DocumentServiceConsumer;
import com.miskevich.httplistener.service.jms.DocumentServiceProducer;
import com.miskevich.httplistener.util.MessageParser;

import javax.jms.Message;
import java.util.Optional;

public class DocumentService {

    private final DocumentServiceProducer PRODUCER = DocumentServiceProducer.getInstance();
    private final DocumentServiceConsumer CONSUMER = DocumentServiceConsumer.getInstance();

    public Optional<Document> add(Document document){
        String correlationID = PRODUCER.pushIntoQueue(document);
        String jmsSelector = "JMSCorrelationID = '" + correlationID + "'";

        Optional<Message> optional = CONSUMER.getFromQueue(jmsSelector);
        if (optional.isPresent()) {
            Message message = optional.get();
            Document toDocument = MessageParser.messageToDocument(message);
            return Optional.of(toDocument);
        }
        throw new RuntimeException("Document wasn't delivered from response queue");
    }
}

