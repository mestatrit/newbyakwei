package tuxiazi.svr.impl.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class HkMessageConverter implements MessageConverter {

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		TextMessage textMessage = (TextMessage) message;
		return textMessage.getText();
	}

	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText(object.toString());
		return textMessage;
	}
}