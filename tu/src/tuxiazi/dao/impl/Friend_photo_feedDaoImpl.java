package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.dao.Friend_photo_feedDao;

@Component("friend_photo_feedDao")
public class Friend_photo_feedDaoImpl extends BaseDao<Friend_photo_feed>
		implements Friend_photo_feedDao {

	@Autowired
	private PhotoDaoImpl photoDao;

	@Autowired
	private PhotoUserLikeDaoImpl photoUserLikeDao;

	@Override
	public Class<Friend_photo_feed> getClazz() {
		return Friend_photo_feed.class;
	}

	@Override
	public Object save(Friend_photo_feed t) {
		long id = NumberUtil.getLong(super.save(t));
		t.setFeedid(id);
		return id;
	}

	public int countByUserid(long userid) {
		return this.count(null, "userid=?", new Object[] { userid });
	}

	public List<Friend_photo_feed> getListByUserid(long userid,
			boolean buildPhoto, boolean buildPhotoUser, long favUserid,
			boolean buildCmt, boolean buildCmtUser, int begin, int size) {
		List<Friend_photo_feed> list = this.getList("userid=?",
				new Object[] { userid }, "photoid desc", begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (Friend_photo_feed o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.photoDao.getMapInId(idList,
					buildPhotoUser, buildCmt, buildCmtUser);
			for (Friend_photo_feed o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
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
