package iwant.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户订阅的项目
 * 
 * @author akwei
 */
@Table(name = "followproject")
public class FollowProject {

	/**
	 * 系统id
	 */
	@Id
	private long sysid;

	/**
	 * 订阅者id
	 */
	@Column
	private long userid;

	/**
	 * 项目id
	 */
	@Column
	private long projectid;

	public long getSysid() {
		return sysid;
	}

	public void setSysid(long sysid) {
		this.sysid = sysid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
}