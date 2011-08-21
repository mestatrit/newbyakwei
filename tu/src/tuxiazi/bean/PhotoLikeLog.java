package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.util.HaloUtil;

import java.util.Date;

import tuxiazi.dao.PhotoLikeLogDao;

/**
 * 保存对图片的like日志，用来进行时间热门统计
 * 
 * @author akwei
 */
public class PhotoLikeLog {

	@Id
	private long logid;

	@Column
	private long photoid;

	@Column
	private long userid;

	@Column
	private Date createtime;

	public long getLogid() {
		return logid;
	}

	public void setLogid(long logid) {
		this.logid = logid;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
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

	public void save() {
		PhotoLikeLogDao dao = (PhotoLikeLogDao) HaloUtil
				.getBean("photoLikeLogDao");
		dao.save(this);
	}
}
