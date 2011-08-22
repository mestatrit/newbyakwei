package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.DataUtil;
import halo.util.HaloUtil;
import halo.util.JsonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.bean.helper.noticedata.FollowNoticeCreater;
import tuxiazi.bean.helper.noticedata.NoticeCreater;
import tuxiazi.bean.helper.noticedata.PhotoCmtNoticeCreater;
import tuxiazi.bean.helper.noticedata.PhotoLikeNoticeCreater;
import tuxiazi.dao.NoticeDao;
import tuxiazi.dao.dbpartitionhelper.Tuxiazi_FeedDbPartitionHelper;
import tuxiazi.util.PhotoUtil;

/**
 * 用户通知
 * 
 * @author akwei
 */
@Table(name = "notice", partitionClass = Tuxiazi_FeedDbPartitionHelper.class)
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
	 * 通知发送人 ,=0时，为系统发送通知
	 */
	@Column
	private long senderid;

	public long getSenderid() {
		return senderid;
	}

	public void setSenderid(long senderid) {
		this.senderid = senderid;
	}

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

	public boolean isReaded() {
		if (this.readflg == NoticeReadEnum.READED.getValue()) {
			return true;
		}
		return false;
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

	public void save() {
		NoticeDao dao = (NoticeDao) HaloUtil.getBean("noticeDao");
		dao.save(this);
	}
}