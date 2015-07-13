package com.suhee.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class ActiveMQTest {
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Test
	public void activeMQTest() {
		System.out.println("============================ start");
		// send
		send();
	}
	
	public void send() {
		jmsTemplate.send(new MessageCreator() {
			public ObjectMessage createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject("ActiveMQ Test");
				return message;
			}
		});
		System.out.println("MESSAGE SENT TO myMessageQueue");
	}
	
	public void receive() throws JMSException {
		Message receivedMessage = jmsTemplate.receive("myMessageQueue");
		ObjectMessage msg = (ObjectMessage) receivedMessage;
		System.out.println("Message Received :" + msg.getObject().toString());
	}
}