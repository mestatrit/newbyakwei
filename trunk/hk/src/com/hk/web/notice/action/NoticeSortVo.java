package com.hk.web.notice.action;

import java.util.List;
import com.hk.bean.Notice;

public class NoticeSortVo {
	private byte noticeType;

	private String noticeTypeIntro;

	private int noReadCount;

	private boolean closeSysNotice;

	private boolean showSwitch = true;// 是否显示开关

	private List<NoticeVo> list;

	public boolean isShowSwitch() {
		return showSwitch;
	}

	public void setShowSwitch(boolean showSwitch) {
		this.showSwitch = showSwitch;
	}

	public void setCloseSysNotice(boolean closeSysNotice) {
		this.closeSysNotice = closeSysNotice;
	}

	public boolean isCloseSysNotice() {
		return closeSysNotice;
	}

	public boolean isLabaReplyNoticeType() {
		if (this.noticeType == Notice.NOTICETYPE_LABAREPLY) {
			return true;
		}
		return false;
	}

	public void setNoticeTypeIntro(String noticeTypeIntro) {
		this.noticeTypeIntro = noticeTypeIntro;
	}

	public String getNoticeTypeIntro() {
		return noticeTypeIntro;
	}

	public void setNoReadCount(int noReadCount) {
		this.noReadCount = noReadCount;
	}

	public int getNoReadCount() {
		return noReadCount;
	}

	public byte getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(byte noticeType) {
		this.noticeType = noticeType;
	}

	public List<NoticeVo> getList() {
		return list;
	}

	public void setList(List<NoticeVo> list) {
		this.list = list;
	}
}