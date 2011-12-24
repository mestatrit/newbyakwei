package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;

@Table(name = "labareply")
public class LabaReply {
	private long labaId;

	private long replyLabaId;

	private Laba replyLaba;

	public long getLabaId() {
		return labaId;
	}

	public void setLabaId(long labaId) {
		this.labaId = labaId;
	}

	public long getReplyLabaId() {
		return replyLabaId;
	}

	public void setReplyLabaId(long replyLabaId) {
		this.replyLabaId = replyLabaId;
	}

	public Laba getReplyLaba() {
		if (replyLaba == null) {
			LabaService labaService = (LabaService) HkUtil
					.getBean("labaService");
			replyLaba = labaService.getLaba(replyLabaId);
		}
		return replyLaba;
	}

	public void setReplyLaba(Laba replyLaba) {
		this.replyLaba = replyLaba;
	}
}