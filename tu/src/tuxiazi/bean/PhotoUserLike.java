package tuxiazi.bean;

import java.util.Date;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import tuxiazi.dao.PhotoLikeUserDao;
import tuxiazi.dao.PhotoUserLikeDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 用户喜欢的图片
 * 
 * @author akwei
 */
@Table(name = "photo_userlike", partitionClass = TuxiaziDbPartitionHelper.class)
public class PhotoUserLike {

	@Id
	private long oid;

	@Column
	private long photoid;

	@Column
	private long userid;

	@Column
	private Date createtime;

	private String nick;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
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

	public void save(long userid, long photoid, Date createtime) {
		PhotoLikeUserDao photoLikeUserDao = (PhotoLikeUserDao) HaloUtil
				.getBean("photoLikeUserDao");
		this.userid = userid;
		this.photoid = photoid;
		this.createtime = createtime;
		this.save();
		if (photoLikeUserDao.getByUseridAndPhotoid(this.userid, this.photoid) != null) {
			return;
		}
		PhotoLikeUser photoLikeUser = new PhotoLikeUser();
		photoLikeUser.setUserid(this.userid);
		photoLikeUser.setPhotoid(this.photoid);
		photoLikeUserDao.save(photoLikeUser);
	}

	public void delete() {
		PhotoUserLikeDao dao = (PhotoUserLikeDao) HaloUtil
				.getBean("photoUserLikeDao");
		dao.deleteByUseridAndPhotoid(userid, photoid);
		PhotoLikeUserDao photoLikeUserDao = (PhotoLikeUserDao) HaloUtil
				.getBean("photoLikeUserDao");
		photoLikeUserDao.deleteByUseridAndPhotoid(userid, photoid);
	}

	private void save() {
		PhotoUserLikeDao dao = (PhotoUserLikeDao) HaloUtil
				.getBean("photoUserLikeDao");
		dao.save(this);
	}
}
