package tuxiazi.webapi;

import halo.util.ResourceConfig;
import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;

public class NoticeInfo {

	private Notice notice;

	private String text;

	public NoticeInfo(Notice notice) {
		this.notice = notice;
		this.buildText();
	}

	public Notice getNotice() {
		return notice;
	}

	public String getText() {
		return text;
	}

	private void buildText() {
		if (notice.getNotice_flg() == NoticeEnum.ADD_FOLLOW.getValue()) {
			this.buildAddFollowText();
		}
		else if (notice.getNotice_flg() == NoticeEnum.ADD_PHOTOCMT.getValue()) {
			this.buildAddPhotoCmtText();
		}
		else if (notice.getNotice_flg() == NoticeEnum.ADD_PHOTOLIKE.getValue()) {
			this.buildAddPhotoLikeText();
		}
	}

	private void buildAddFollowText() {
		this.text = ResourceConfig.getTextFromResource("i18n", "followed_you");
	}

	private void buildAddPhotoCmtText() {
		this.text = ResourceConfig.getTextFromResource("i18n", "posted_cmt");
	}

	private void buildAddPhotoLikeText() {
		this.text = ResourceConfig.getTextFromResource("i18n",
				"user_like_your_photo");
	}
}
