package svrtest;

import com.hk.svr.MsgService;

public class MsgServiceTest extends HkServiceTest {
	private MsgService msgService;

	public void setMsgService(MsgService msgService) {
		this.msgService = msgService;
	}

	public void ttestSend() {
		long userId = 1;
		long senderId = 2;
		String msg = "说话啊";
		this.msgService.sendMsg(userId, senderId, msg);
	}

	public void ttestdelete() {
		long userId = 2;
		long mainId = 3;
		this.msgService.deletePvtChatMain(userId, mainId);
	}
}