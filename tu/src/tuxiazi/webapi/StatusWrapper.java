package tuxiazi.webapi;

import halo.util.DataUtil;

import java.util.Date;
import java.util.Locale;

import weibo4j.Status;

public class StatusWrapper {

	private static final long serialVersionUID = 4107724086659303935L;

	public static final String DATE_FMT = "EEE MMM d HH:mm:ss z yyyy";

	private Status status;

	private Date createdAt;

	private long id;

	private String text;

	private String source;

	private int inReplyToUserId;

	private String inReplyToScreenName;

	private String thumbnail_pic;

	private String bmiddle_pic;

	private String original_pic;

	public StatusWrapper(Status status) {
		this.status = status;
		this.id = this.status.getId();
		this.text = this.status.getText();
		this.createdAt = this.status.getCreatedAt();
		this.source = this.status.getSource();
		this.inReplyToScreenName = this.status.getInReplyToScreenName();
		this.inReplyToUserId = this.status.getInReplyToUserId();
		this.thumbnail_pic = this.status.getThumbnail_pic();
		this.bmiddle_pic = this.status.getBmiddle_pic();
		this.original_pic = this.status.getOriginal_pic();
		if (this.status.isRetweet()) {
			this.text = this.text + " "
					+ this.status.getRetweetDetails().getText();
			this.thumbnail_pic = this.status.getRetweetDetails()
					.getThumbnail_pic();
			this.bmiddle_pic = this.status.getRetweetDetails().getBmiddle_pic();
			this.original_pic = this.status.getRetweetDetails()
					.getOriginal_pic();
		}
	}

	public String getFmtDate() {
		return DataUtil.getFormatTimeData(this.status.getCreatedAt(), DATE_FMT,
				Locale.ENGLISH);
	}

	public Status getStatus() {
		return status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public String getSource() {
		return source;
	}

	public int getInReplyToUserId() {
		return inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}
}