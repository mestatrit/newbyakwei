package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloValidate;

import java.util.Date;

import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;
import tuxiazi.util.Err;
import tuxiazi.util.PhotoUtil;

/**
 * 图片评论
 * 
 * @author akwei
 */
@Table(name = "photocmt", partitionClass = TuxiaziDbPartitionHelper.class)
public class PhotoCmt {

	@Id
	private long cmtid;

	@Column
	private long photoid;

	@Column
	private long userid;

	@Column
	private String content;

	@Column
	private Date create_time;

	@Column
	private long replyuserid;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getCmtid() {
		return cmtid;
	}

	public void setCmtid(long cmtid) {
		this.cmtid = cmtid;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public String getFmtCreate_time() {
		return PhotoUtil.getFmtTime(this.create_time);
	}

	public int validate() {
		if (!HaloValidate.validateEmptyAndLength(this.content, true, 200)) {
			return Err.PHOTOCMT_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public long getReplyuserid() {
		return replyuserid;
	}

	public void setReplyuserid(long replyuserid) {
		this.replyuserid = replyuserid;
	}
}