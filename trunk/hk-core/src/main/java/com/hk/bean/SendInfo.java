package com.hk.bean;

public class SendInfo {
	private PvtChat senderPvtChat;

	private PvtChat receiverPvtChat;

	public PvtChat getSenderPvtChat() {
		return senderPvtChat;
	}

	public void setSenderPvtChat(PvtChat senderPvtChat) {
		this.senderPvtChat = senderPvtChat;
	}

	public PvtChat getReceiverPvtChat() {
		return receiverPvtChat;
	}

	public void setReceiverPvtChat(PvtChat receiverPvtChat) {
		this.receiverPvtChat = receiverPvtChat;
	}
}