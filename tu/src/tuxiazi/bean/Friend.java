package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import halo.util.NumberUtil;
import tuxiazi.dao.FriendDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 用户关注的人
 * 
 * @author akwei
 */
@Table(name = "friend", partitionClass = TuxiaziDbPartitionHelper.class)
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

	public boolean saveFriend(User user, User friendUser, boolean both) {
		if (user.getUserid() == friendUser.getUserid()) {
			throw new RuntimeException(
					"addFriend must be not only one user userid:friendUserid ["
							+ user.getUserid() + ":" + friendUser.getUserid()
							+ "]");
		}
		FriendDao friendDao = (FriendDao) HaloUtil.getBean("friendDao");
		if (friendDao.getByUseridAndFriendid(user.getUserid(),
				friendUser.getUserid()) != null) {
			// 已经是好友关系
			return false;
		}
		this.userid = user.getUserid();
		this.friendid = friendUser.getUserid();
		if (both) {
			this.flg = FLG_BOTH;
		}
		else {
			this.flg = FLG_NOBOTH;
		}
		friendDao.save(this);
		return true;
	}

	public void update() {
		if (this.userid == this.friendid) {
			throw new RuntimeException(
					"addFriend must be not only one user userid:friendUserid ["
							+ userid + ":" + friendid + "]");
		}
		FriendDao friendDao = (FriendDao) HaloUtil.getBean("friendDao");
		friendDao.update(this);
	}
}