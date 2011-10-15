package iwant.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import iwant.util.PicUtil;

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
