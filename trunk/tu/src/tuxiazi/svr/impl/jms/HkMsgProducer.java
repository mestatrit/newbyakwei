package tuxiazi.svr.impl.jms;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

public class HkMsgProducer {

	private JmsTemplate jmsTemplate;

	private Set<Entry<String, Destination>> destinationSet;

	public void setDestinationMap(Map<String, Destination> destinationMap) {
		this.destinationSet = destinationMap.entrySet();
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestination(String msg) {
		for (Entry<String, Destination> entry : destinationSet) {
			if (msg.startsWith(entry.getKey() + "_")) {
				return entry.getValue();
			}
		}
		return null;
	}

	public void send(String msg) {
		this.getJmsTemplate().convertAndSend(this.getDestination(msg), msg);
	}
}
