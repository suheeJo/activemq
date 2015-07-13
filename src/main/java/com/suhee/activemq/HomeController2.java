package com.suhee.activemq;

import java.util.Locale;

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
public class HomeController2 {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController2.class);
	
	@Autowired
	JmsTemplate jmsTemplate;
	
//	@Autowired
//	JmsMessageSender jmsMessageSender;
	
	@RequestMapping(value = "/2", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws JMSException {
		
		// 2번째 시도
	    /*// send to default destination 
	    jmsMessageSender.send("hello JMS");
	         
	    // send to a code specified destination
	    Queue queue = new ActiveMQQueue("AnotherDest");
	    jmsMessageSender.send(queue, "hello Another Message");*/

		// 1 번째 시도
		/*jmsTemplate.send(new MessageCreator() {
			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject("test message");
				return message;
			}
		});

		logger.info("MESSAGE SENT TO myMessageQueue");
		Message receivedMessage = jmsTemplate.receive("myMessageQueue");
		ObjectMessage msg = (ObjectMessage) receivedMessage;
		logger.info("Message Received :" + msg.getObject().toString());*/
		
		return "home";
	}
	
	public void send() {
		jmsTemplate.send(new MessageCreator() {
			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject("ActiveMQ Test");
				return message;
			}
		});
		logger.info("MESSAGE SENT TO myMessageQueue");
	}
	
	public void receive() throws JMSException {
		Message receivedMessage = jmsTemplate.receive("myMessageQueue");
		ObjectMessage msg = (ObjectMessage) receivedMessage;
		logger.info("Message Received :" + msg.getObject().toString());
	}
	
}
