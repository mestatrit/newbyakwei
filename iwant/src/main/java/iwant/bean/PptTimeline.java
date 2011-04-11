package iwant.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户订阅的项目收到的ppt数据
 * 
 * @author akwei
 */
@Table(name = "ppttimeline")
public class PptTimeline {

	@Id
	private long sysid;

	@Column
	private long pptid;

	@Column
	private long projectid;

	@Column
	private long userid;

	@Column
	private int catid;

	/**
	 * 收到通知时间
	 */
	@Column
	private Date createtime;

	/**
	 * 读取时间
	 */
	@Column
	private Date readtime;

	/**
	 * 是否读取的标志
	 */
	@Column
	private int read_flag;

	private Ppt ppt;

	public void setPpt(Ppt ppt) {
		this.ppt = ppt;
	}

	public Ppt getPpt() {
		return ppt;
	}

	public long getSysid() {
		return sysid;
	}

	public void setSysid(long sysid) {
		this.sysid = sysid;
	}

	public long getPptid() {
		return pptid;
	}

	public void setPptid(long pptid) {
		this.pptid = pptid;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	public int getRead_flag() {
		return read_flag;
	}

	public void setRead_flag(int readFlag) {
		read_flag = readFlag;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}
}