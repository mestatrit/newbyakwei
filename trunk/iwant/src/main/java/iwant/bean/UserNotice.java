package iwant.bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

/**
 * 用户订阅项目的通知,发送apns通知到用户手机
 * 
 * @author akwei
 */
@Table(name = "usernotice")
public class UserNotice {

	/**
	 * 系统id
	 */
	@Id
	private long sysid;

	/**
	 * 通知id
	 */
	@Column
	private long noticeid;

	@Column
	private long userid;

	private Notice notice;

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Notice getNotice() {
		return notice;
	}

	public long getSysid() {
		return sysid;
	}

	public void setSysid(long sysid) {
		this.sysid = sysid;
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
}
