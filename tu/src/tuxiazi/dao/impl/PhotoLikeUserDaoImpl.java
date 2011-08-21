package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.User;
import tuxiazi.dao.PhotoLikeUserDao;

public class PhotoLikeUserDaoImpl extends BaseDao<PhotoLikeUser> implements
		PhotoLikeUserDao {

	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private FriendDaoImpl friendDao;

	@Override
	public Class<PhotoLikeUser> getClazz() {
		return PhotoLikeUser.class;
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete("photoid=?", new Object[] { photoid });
	}

	public int deleteByUseridAndPhotoid(long userid, long photoid) {
		return this.delete("userid=? and photoid=?", new Object[] { userid,
				photoid });
	}

	public PhotoLikeUser getByUseridAndPhotoid(long userid, long photoid) {
		return this.getObject("userid=? and photoid=?", new Object[] { userid,
				photoid });
	}

	public int countByPhotoid(long photoid) {
		return this.count(null, "photoid=?", new Object[] { photoid });
	}

	public List<PhotoLikeUser> getListByPhotoid(long photoid,
			boolean buildUser, long refuserid, int begin, int size) {
		List<PhotoLikeUser> list = this.getList("photoid=?",
				new Object[] { photoid }, "oid desc", begin, size);
		List<Long> idList = new ArrayList<Long>();
		if (buildUser) {
			for (PhotoLikeUser o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, User> map = this.userDao.getMapInId(idList);
			for (PhotoLikeUser o : list) {
				o.setUser(map.get(o.getUserid()));
			}
		}
		if (refuserid > 0) {
			Set<Long> friendidset = new HashSet<Long>(
					this.friendDao.getFriendidListByUserid(refuserid));
			for (PhotoLikeUser o : list) {
				if (friendidset.contains(o.getUserid())) {
					o.setFriendRef(true);
				}
			}
		}
		return list;
	}
}
