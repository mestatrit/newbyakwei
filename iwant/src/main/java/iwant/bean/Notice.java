package iwant.bean;

import java.util.Date;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

/**
 * 系统通知
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
	 * 订阅的项目的id
	 */
	@Column
	private long projectid;

	/**
	 * 通知内容
	 */
	@Column
	private String content;

	@Column
	private long pptid;

	/**
	 * 通知创建时间
	 */
	@Column
	private Date createtime;

	public long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(long noticeid) {
		this.noticeid = noticeid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getPptid() {
		return pptid;
	}

	public void setPptid(long pptid) {
		this.pptid = pptid;
	}
}
