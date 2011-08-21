package tuxiazi.bean;

import tuxiazi.dao.FriendDao;
import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import halo.util.NumberUtil;

/**
 * 用户关注的人
 * 
 * @author Administrator
 */
@Table(name = "friend")
public class Friend {

	public static final byte FLG_NOBOTH = 0;

	public static final byte FLG_BOTH = 1;

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
	 * 好友id
	 */
	@Column
	private long friendid;

	/**
	 * 好友类型，是否是相互关注
	 */
	@Column
	private byte flg;

	/**
	 * 是否是好友关系
	 */
	private boolean friendRef;

	private User friendUser;

	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}

	public User getFriendUser() {
		return friendUser;
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

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getFriendid() {
		return friendid;
	}

	public void setFriendid(long friendid) {
		this.friendid = friendid;
	}

	public byte getFlg() {
		return flg;
	}

	public void setFlg(byte flg) {
		this.flg = flg;
	}

	public void save() {
		FriendDao dao = (FriendDao) HaloUtil.getBean("friendDao");
		dao.save(this);
		this.friendid = NumberUtil.getLong(dao.save(this));
	}

	public void update() {
		FriendDao dao = (FriendDao) HaloUtil.getBean("friendDao");
		dao.update(this);
	}
}