package tuxiazi.bean;

import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;
import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

/**
 * 粉丝（用户被哪些人关注）
 * 
 * @author akwei
 */
@Table(name = "fans", partitionClass = TuxiaziDbPartitionHelper.class)
public class Fans {

	/**
	 * 系统id
	 */
	@Id
	private long oid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 关注id
	 */
	@Column
	private long fansid;

	/**
	 * 好友类型，是否是相互关注
	 */
	@Column
	private byte flg;

	private User fansUser;

	/**
	 * 是否是好友关系
	 */
	private boolean friendRef;

	public boolean isFriendRef() {
		return friendRef;
	}

	public void setFriendRef(boolean friendRef) {
		this.friendRef = friendRef;
	}

	public void setFansUser(User fansUser) {
		this.fansUser = fansUser;
	}

	public User getFansUser() {
		return fansUser;
	}

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

	public long getFansid() {
		return fansid;
	}

	public void setFansid(long fansid) {
		this.fansid = fansid;
	}

	public byte getFlg() {
		return flg;
	}

	public void setFlg(byte flg) {
		this.flg = flg;
	}
}