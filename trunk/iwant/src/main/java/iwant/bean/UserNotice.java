package iwant.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

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
