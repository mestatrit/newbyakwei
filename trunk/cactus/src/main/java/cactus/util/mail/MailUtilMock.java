package cactus.util.mail;

import javax.mail.MessagingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class MailUtilMock extends MailUtil implements InitializingBean {
	private Log log = LogFactory.getLog(MailUtilMock.class);

	@Override
	public void sendHtmlMail(String to, String title, String content)
			throws MessagingException {
		log.info("=========== mail mock ===========");
		log.info("to : [ " + to + " ]");
		log.info("title : [ " + title + " ]");
		log.info("content : [ " + content + " ]");
	}

	@Override
	public void sendSimpleMail(String to, String title, String content) {
		log.info("=========== mail mock ===========");
		log.info("to : [ " + to + " ]");
		log.info("title : [ " + title + " ]");
		log.info("content : [ " + content + " ]");
	}

	public void afterPropertiesSet() throws Exception {
		log.info("===========================================================");
		log.info("===========================================================");
		log.info("===================   mail mock    ========================");
		log.info("===========================================================");
		log.info("===========================================================");
		log.info("===========================================================");
	}
}