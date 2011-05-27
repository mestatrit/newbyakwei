package iwant.bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

/**
 * 订阅项目的人
 * 
 * @author akwei
 */
@Table(name = "projectfans")
public class ProjectFans {

	/**
	 * id
	 */
	@Id
	private long sysid;

	/**
	 * 项目id
	 */
	@Column
	private long projectid;

	/**
	 * 订阅者id
	 */
	@Column
	private long userid;

	public long getSysid() {
		return sysid;
	}

	public void setSysid(long sysid) {
		this.sysid = sysid;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
