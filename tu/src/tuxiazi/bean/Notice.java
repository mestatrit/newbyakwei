package tuxiazi.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.helper.noticedata.FollowNoticeCreater;
import tuxiazi.bean.helper.noticedata.NoticeCreater;
import tuxiazi.bean.helper.noticedata.PhotoCmtNoticeCreater;
import tuxiazi.bean.helper.noticedata.PhotoLikeNoticeCreater;
import tuxiazi.util.PhotoUtil;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;

/**
 * 用户通知
 * 
 * @author akwei
 */
@Table(name = "notice")
public class Notice {

	/**
	 * 通知id
	 */
	@Id
	private long noticeid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 通知类型 {@link NoticeEnum}
	 */
	@Column
	private int notice_flg;

	/**
	 * 通知创建时间
	 */
	@Column
	private Date createtime;

	/**
	 * 通知内容
	 */
	@Column
	private String data;

	@Column
	private int readflg;

	@Column
	private long refoid;

	public void setRefoid(long refoid) {
		this.refoid = refoid;
	}

	public long getRefoid() {
		return refoid;
	}

	public void setReadflg(int readflg) {
		this.readflg = readflg;
	}

	public int getReadflg() {
		return readflg;
	}

	public long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(long noticeid) {
		this.noticeid = noticeid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getNotice_flg() {
		return notice_flg;
	}

	public void setNotice_flg(int noticeFlg) {
		notice_flg = noticeFlg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFmtTime() {
		return PhotoUtil.getFmtTime(this.createtime);
	}

	private Map<String, String> dataMap = null;

	public Map<String, String> getDataMap() {
		if (dataMap == null) {
			if (DataUtil.isEmpty(this.data)) {
				dataMap = new HashMap<String, String>(1);
			}
			else {
				dataMap = JsonUtil.getMapFromJson(this.data);
			}
		}
		return dataMap;
	}

	public String getNoticeIntro() {
		NoticeCreater creater = null;
		if (this.notice_flg == NoticeEnum.ADD_FOLLOW.getValue()) {
			creater = new FollowNoticeCreater();
		}
		else if (this.notice_flg == NoticeEnum.ADD_PHOTOCMT.getValue()) {
			creater = new PhotoCmtNoticeCreater();
		}
		else if (this.notice_flg == NoticeEnum.ADD_PHOTOLIKE.getValue()) {
			creater = new PhotoLikeNoticeCreater();
		}
		if (creater != null) {
			return creater.getIntro(this);
		}
		return null;
	}
}