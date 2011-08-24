package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;

import java.util.Date;

import tuxiazi.dao.PhotoLikeLogDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 保存对图片的like日志，用来进行时间热门统计
 * 
 * @author akwei
 */
@Table(name = "photolikelog", partitionClass = TuxiaziDbPartitionHelper.class)
public class PhotoLikeLog {

	public PhotoLikeLog() {
	}

	public PhotoLikeLog(long oid, long photoid, long userid, Date createtime) {
		this.oid = oid;
		this.photoid = photoid;
		this.userid = userid;
		this.createtime = createtime;
	}

	@Id
	private long oid;

	@Column
	private long photoid;

	@Column
	private long userid;

	@Column
	private Date createtime;

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getOid() {
		return oid;
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
