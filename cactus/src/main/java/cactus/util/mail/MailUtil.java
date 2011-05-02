package cactus.util.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {
	private boolean deleteMail;

	private JavaMailSenderImpl mailSender;

	private int maxAttachmentLen = 3;

	private int mailReadPer = 10;

	private int mailReadStart = 1;

	public void sendSimpleMail(String to, String title, String content) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(title);
		mailMessage.setText(content);
		mailMessage.setFrom(this.mailSender.getUsername());
		mailSender.send(mailMessage);
	}

	public void sendHtmlMail(String to, String title, String content)
			throws MessagingException {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "GBK");
		// messageHelper.setTo("m47121@163.com");// 测试使用
		messageHelper.setTo(to);
		messageHelper.setSubject(title);
		messageHelper.setText(content, true);
		mailMessage.setFrom(new InternetAddress(this.mailSender.getUsername()));
		mailSender.send(mailMessage);
	}

	public List<UserMailInfo> readMulitpartMail() throws IOException,
			MessagingException {
		Session session = mailSender.getSession();
		Store store = session.getStore("pop3");
		Folder folder = null;
		try {
			store.connect(mailSender.getHost(), mailSender.getUsername(),
					mailSender.getPassword());
			folder = store.getFolder("INBOX");
			if (this.deleteMail) {
				folder.open(Folder.READ_WRITE);
			}
			else {
				folder.open(Folder.READ_ONLY);
			}
			Message[] messages = folder.getMessages(this.mailReadStart,
					this.mailReadPer);
			List<UserMailInfo> userMailInfoList = new ArrayList<UserMailInfo>();
			for (int i = 0, n = messages.length; i < n; i++) {
				System.out.println(i + ": " + messages[i].getFrom()[0] + "\t"
						+ messages[i].getSubject() + "\t"
						+ messages[i].getContentType());
				MimeMessage mimeMessage = (MimeMessage) messages[i];
				MimeMultipart mimeMultipart = (MimeMultipart) mimeMessage
						.getContent();
				UserMailInfo userMailInfo = new UserMailInfo();
				userMailInfoList.add(userMailInfo);
				userMailInfo.setUserMail(messages[i].getFrom()[0].toString());
				List<byte[]> attachmentList = new ArrayList<byte[]>();
				userMailInfo.setAttachmentList(attachmentList);
				if (this.deleteMail) {
					mimeMessage.setFlag(javax.mail.Flags.Flag.DELETED, true);
				}
				int len = mimeMultipart.getCount();
				if (len > this.maxAttachmentLen) {
					len = this.maxAttachmentLen;
				}
				for (int k = 0; k < len; k++) {
					Part part = mimeMultipart.getBodyPart(k);
					String s = part.getDisposition();
					if (s != null) {
						InputStream partInputStream = null;
						try {
							partInputStream = part.getInputStream();
							byte[] by = new byte[partInputStream.available()];
							partInputStream.read(by);
							attachmentList.add(by);
						}
						catch (IOException e) {
							throw e;
						}
						finally {
							if (partInputStream != null) {
								partInputStream.close();
							}
						}
					}
				}
			}
			return userMailInfoList;
		}
		catch (MessagingException e1) {
			throw e1;
		}
		finally {
			// Close connection
			if (folder != null) {
				if (this.deleteMail) {
					folder.close(true);// 删除邮件
				}
				else {
					folder.close(false);// 不删除邮件
				}
			}
			store.close();
		}
	}

	public void setDeleteMail(boolean deleteMail) {
		this.deleteMail = deleteMail;
	}

	public void setMaxAttachmentLen(int maxAttachmentLen) {
		this.maxAttachmentLen = maxAttachmentLen;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setMailReadPer(int mailReadPer) {
		this.mailReadPer = mailReadPer;
	}

	public void setMailReadStart(int mailReadStart) {
		this.mailReadStart = mailReadStart;
	}
}