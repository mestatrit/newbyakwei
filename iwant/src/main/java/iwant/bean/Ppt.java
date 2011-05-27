package iwant.bean;

import iwant.util.PicUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

/**
 * ppt，一个项目可以有多期
 * 
 * @author akwei
 */
@Table(name = "ppt")
public class Ppt {

	private static final String DATE_FMT = "EEE MMM d HH:mm:ss Z yyyy";

	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT,
			Locale.ENGLISH);
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	}

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

	public String getPic1Url() {
		return PicUtil.getSlidePic1Url(this.pic_path);
	}

	public String getPic2Url() {
		return PicUtil.getSlidePic2Url(this.pic_path);
	}
}
