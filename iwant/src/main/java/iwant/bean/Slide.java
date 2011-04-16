package iwant.bean;

import iwant.util.PicUtil;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * ppt中的幻灯片信息 与 Ppt对应
 * 
 * @author akwei
 */
@Table(name = "slide")
public class Slide {

	/**
	 * 系统id
	 */
	@Id
	private long slideid;

	/**
	 * pptid
	 */
	@Column
	private long pptid;

	/**
	 * 排序号
	 */
	@Column
	private int order_flag;

	/**
	 * 标题
	 */
	@Column
	private String title;

	/**
	 * 副标题
	 */
	@Column
	private String subtitle;

	/**
	 * 描述信息
	 */
	@Column
	private String descr;

	/**
	 * 图片路径
	 */
	@Column
	private String pic_path;

	@Column
	private long projectid;

	public long getSlideid() {
		return slideid;
	}

	public void setSlideid(long slideid) {
		this.slideid = slideid;
	}

	public long getPptid() {
		return pptid;
	}

	public void setPptid(long pptid) {
		this.pptid = pptid;
	}

	public int getOrder_flag() {
		return order_flag;
	}

	public void setOrder_flag(int orderFlag) {
		order_flag = orderFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String picPath) {
		pic_path = picPath;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public String getPic1Url() {
		return PicUtil.getSlidePic1Url(this.pic_path);
	}

	public String getPic2Url() {
		return PicUtil.getSlidePic2Url(this.pic_path);
	}
}
