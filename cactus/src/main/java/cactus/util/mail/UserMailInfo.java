package cactus.util.mail;

import java.util.List;

public class UserMailInfo {
	private String userMail;

	private List<byte[]> attachmentList;

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public List<byte[]> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<byte[]> attachmentList) {
		this.attachmentList = attachmentList;
	}
}