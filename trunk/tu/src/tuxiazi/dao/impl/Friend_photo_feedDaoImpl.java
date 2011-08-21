package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.User;
import tuxiazi.dao.Friend_photo_feedDao;

public class Friend_photo_feedDaoImpl extends BaseDao<Friend_photo_feed>
		implements Friend_photo_feedDao {

	@Autowired
	private PhotoDaoImpl photoDao;

	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private PhotoUserLikeDaoImpl photoUserLikeDao;

	@Override
	public Class<Friend_photo_feed> getClazz() {
		return Friend_photo_feed.class;
	}

	public int countByUserid(long userid) {
		return this.count(null, "userid=?", new Object[] { userid });
	}

	public List<Friend_photo_feed> getListByUserid(long userid,
			boolean buildPhoto, boolean buildPhotoUser, long favUserid,
			int begin, int size) {
		List<Friend_photo_feed> list = this.getList("userid=?",
				new Object[] { userid }, "photoid desc", begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (Friend_photo_feed o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.photoDao.getMapInId(idList);
			for (Friend_photo_feed o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
			if (buildPhotoUser) {
				idList = new ArrayList<Long>();
				for (Entry<Long, Photo> e : map.entrySet()) {
					idList.add(e.getValue().getUserid());
				}
				Map<Long, User> usermap = this.userDao.getMapInId(idList);
				for (Friend_photo_feed o : list) {
					if (o.getPhoto() != null) {
						o.getPhoto().setUser(
								usermap.get(o.getPhoto().getUserid()));
					}
				}
			}
			if (favUserid > 0) {
				List<PhotoUserLike> photoUserLikes = this.photoUserLikeDao
						.getListByUserid(favUserid, 0, -1);
				Set<Long> photoidset = new HashSet<Long>();
				for (PhotoUserLike o : photoUserLikes) {
					photoidset.add(o.getPhotoid());
				}
				for (Friend_photo_feed o : list) {
					if (photoidset.contains(o.getPhotoid())) {
						if (o.getPhoto() != null) {
							o.getPhoto().setOpliked(true);
						}
					}
				}
			}
		}
		return list;
	}

	public void deleteByPhotoid(long photoid) {
		this.delete("photoid=?", new Object[] { photoid });
	}

	public void deleteByUseridAndPhotoUserid(long userid, long photoUserid) {
		this.delete(null, "userid=? and photo_userid=?", new Object[] { userid,
				photoUserid });
	}
}
