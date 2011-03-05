package tuxiazi.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;

/**
 * 图片收藏
 * 
 * @author akwei
 */
public class PhotoFav {

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private long photoid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}
}