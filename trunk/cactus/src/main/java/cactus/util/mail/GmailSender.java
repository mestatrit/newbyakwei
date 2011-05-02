package cactus.util.mail;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSender {
	public static void main(String[] args) throws AddressException,
			MessagingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		final String username = "wei@huoku.com";
		final String password = "3729306";
		Session session = Session.getDefaultInstance(props,
				new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		// -- Create a new message --
		Message msg = new MimeMessage(session);
		// -- Set the FROM and TO fields --
		msg.setFrom(new InternetAddress("wei@huoku.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
				"ak478288@163.com", false));
		msg.setSubject("Hello你好");
		msg.setText("你好");
		msg.setSentDate(new Date());
		System.out.println("begin send");
		long begin = System.currentTimeMillis();
		Transport.send(msg);
		// for (int i = 0; i < 5; i++) {
		// Transport.send(msg);
		// }
		long end = System.currentTimeMillis();
		System.out.println("Message sent.[" + (end - begin) + "]");
	}
}
