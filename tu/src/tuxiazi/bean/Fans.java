package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import tuxiazi.dao.FansDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

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

	public boolean saveFans(User user, User fansUser, boolean both) {
		if (user.getUserid() == fansUser.getUserid()) {
			throw new RuntimeException(
					"saveFans must be not only one user userid:fansUserid ["
							+ user.getUserid() + ":" + fansUser.getUserid()
							+ "]");
		}
		FansDao fansDao = (FansDao) HaloUtil.getBean("fansDao");
		Fans fans = fansDao.getByUseridAndFansid(user.getUserid(),
				fansUser.getUserid());
		if (fans != null) {
			return false;
		}
		this.userid = user.getUserid();
		this.fansid = fansUser.getUserid();
		if (both) {
			this.flg = Friend.FLG_BOTH;
		}
		else {
			this.flg = Friend.FLG_NOBOTH;
		}
		fansDao.save(this);
		return true;
	}

	public void update() {
		if (this.userid == this.fansid) {
			throw new RuntimeException(
					"saveFans must be not only one user userid:fansUserid ["
							+ userid + ":" + fansid + "]");
		}
		FansDao fansDao = (FansDao) HaloUtil.getBean("fansDao");
		fansDao.update(this);
	}
}