package iwant.bean;

import iwant.bean.enumtype.ActiveType;
import iwant.util.PicUtil;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 主ppt，一个项目只能有一期
 * 
 * @author akwei
 */
@Table(name = "mainppt")
public class MainPpt {

	/**
	 * ppt id
	 */
	@Id
	private long pptid;

	/**
	 * 分类id
	 */
	@Column
	private int catid;

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

	/**
	 * 排序号
	 */
	@Column
	private long order_flag;

	/**
	 * 是否激活
	 */
	@Column
	private int active_flag;

	private Project project;

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
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

	public long getOrder_flag() {
		return order_flag;
	}

	public void setOrder_flag(long orderFlag) {
		order_flag = orderFlag;
	}

	public int getActive_flag() {
		return active_flag;
	}

	public void setActive_flag(int activeFlag) {
		active_flag = activeFlag;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public boolean isActived() {
		if (this.active_flag == ActiveType.ACTIVE.getValue()) {
			return true;
		}
		return false;
	}

	public String getPic1Url() {
		return PicUtil.getSlidePic1Url(this.pic_path);
	}

	public String getPic2Url() {
		return PicUtil.getSlidePic2Url(this.pic_path);
	}
}
