package iwant.bean;

import iwant.util.PicUtil;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * ppt，一个项目可以有多期
 * 
 * @author akwei
 */
@Table(name = "ppt")
public class Ppt {

	/**
	 * ppt id
	 */
	@Id
	private long pptid;

	/**
	 * 项目id
	 */
	@Column
	private long projectid;

	/**
	 * ppt 名称
	 */
	@Column
	private String name;

	/**
	 * 创建时间
	 */
	@Column
	private Date createtime;

	/**
	 * 焦点图路径
	 */
	@Column
	private String pic_path;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String picPath) {
		pic_path = picPath;
	}

	public String getPic1Url() {
		return PicUtil.getSlidePic1Url(this.pic_path);
	}

	public String getPic2Url() {
		return PicUtil.getSlidePic2Url(this.pic_path);
	}
}