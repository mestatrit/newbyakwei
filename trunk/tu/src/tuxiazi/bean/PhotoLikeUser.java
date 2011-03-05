package tuxiazi.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 喜欢图片的用户数据
 * 
 * @author Administrator
 */
@Table(name = "photo_likeuser")
public class PhotoLikeUser {

	@Id
	private long oid;

	@Column
	private long photoid;

	@Column
	private long userid;

	private boolean friendRef;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public boolean isFriendRef() {
		return friendRef;
	}

	public void setFriendRef(boolean friendRef) {
		this.friendRef = friendRef;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
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
}
