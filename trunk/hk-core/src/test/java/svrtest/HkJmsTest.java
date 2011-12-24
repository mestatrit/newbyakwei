package svrtest;

import com.hk.jms.HkMsgProducer;

public class HkJmsTest extends HkServiceTest {

	private HkMsgProducer newsProducer;

	public void setHkMessageProducer(HkMsgProducer newsProducer) {
		this.newsProducer = newsProducer;
	}

	public void testSengMessageToJms() {
		String msg = "你好，我来了abc";
		this.newsProducer.send(msg);
	}
}