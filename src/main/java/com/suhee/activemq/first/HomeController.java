package com.suhee.activemq.first;

import java.util.Locale;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws JMSException {
		
		logger.info("================================ start");
		
		send("java config test");
		
//		Queue queue = new ActiveMQQueue("myMessageQueue");
//		send(queue, "first text");
		
		receive();
		
		logger.info("================================ end");
		
		return "home";
	}
	
	public void send(final String text) {
		jmsTemplate.send(new MessageCreator() {
			@Override
			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject(text);
				return message;
			}
		});

		logger.info("MESSAGE SENT TO myMessageQueue");
	}
	
	public void receive() throws JMSException {
		Message receivedMessage = jmsTemplate.receive("javaconfig.activemq");
		
		logger.info(":::: " + receivedMessage);
		logger.info(":::: " + receivedMessage.getJMSCorrelationID());
		logger.info(":::: " + receivedMessage.getJMSMessageID());
		logger.info(":::: " + receivedMessage.getJMSType());
		logger.info(":::: " + receivedMessage.getJMSCorrelationIDAsBytes());
		logger.info(":::: " + receivedMessage.getJMSDestination());
		logger.info(":::: " + receivedMessage.getJMSReplyTo());
		logger.info(":::: " + receivedMessage.getJMSDeliveryMode());
		logger.info(":::: " + receivedMessage.getJMSExpiration());
		logger.info(":::: " + receivedMessage.getJMSPriority());
		logger.info(":::: " + receivedMessage.getJMSRedelivered());
		logger.info(":::: " + receivedMessage.getPropertyNames());
		
		ObjectMessage msg = (ObjectMessage) receivedMessage;
		logger.info("Message Received :" + msg.getObject().toString());
	}
	
	public void send(final Destination dest, final String text) {
		jmsTemplate.send(dest, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(text);
				return message;
			}
		});
		logger.info("MESSAGE SEND TO " + dest);
	}
}
