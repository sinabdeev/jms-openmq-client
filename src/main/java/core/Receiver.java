package core;

import com.sun.messaging.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MessageProducer;

@Component
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @JmsListener(destination = "request.queue")
    public void receiveMessageFromRequestQueue(javax.jms.Message incoming, Session session) throws JMSException {
        logger.info("Message from request queue");
        logger.info(session.toString());
        logger.info(incoming.getJMSCorrelationID());
        logger.info(incoming.getJMSReplyTo().toString());

        MessageProducer producer = session.createProducer(incoming.getJMSReplyTo());
        javax.jms.Message responseMessage = session.createTextMessage("resp");
        responseMessage.setJMSCorrelationID(incoming.getJMSCorrelationID());
        producer.send(responseMessage);
    }


}
