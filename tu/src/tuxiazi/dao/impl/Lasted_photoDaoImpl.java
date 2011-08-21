package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.User;
import tuxiazi.dao.Lasted_photoDao;

public class Lasted_photoDaoImpl extends BaseDao<Lasted_photo> implements
		Lasted_photoDao {

	@Autowired
	private PhotoDaoImpl photoDao;

	@Autowired
	private UserDaoImpl userDao;

	@Autowired
	private PhotoUserLikeDaoImpl photoUserLikeDao;

	@Override
	public Class<Lasted_photo> getClazz() {
		return Lasted_photo.class;
	}

	public int count() {
		return this.count(null, null);
	}

	public List<Lasted_photo> getList(int begin, int size) {
		return this.getList(null, null, "photoid desc", begin, size);
	}

	public List<Lasted_photo> getList(boolean buildPhoto,
			boolean buildPhotoUser, long favUserid, int begin, int size) {
		List<Lasted_photo> list = this.getList(begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (Lasted_photo o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.photoDao.getMapInId(idList);
			for (Lasted_photo o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
			if (buildPhotoUser) {
				idList = new ArrayList<Long>();
				for (Entry<Long, Photo> e : map.entrySet()) {
					idList.add(e.getValue().getUserid());
				}
				Map<Long, User> usermap = this.userDao.getMapInId(idList);
				for (Lasted_photo o : list) {
					if (o.getPhoto() != null) {
						o.getPhoto().setUser(
								usermap.get(o.getPhoto().getUserid()));
					}
				}
			}
		}
		if (favUserid > 0) {
			List<PhotoUserLike> photoUserLikes = this.photoUserLikeDao
					.getListByUserid(favUserid, begin, -1);
			Set<Long> photoidset = new HashSet<Long>();
			for (PhotoUserLike o : photoUserLikes) {
				photoidset.add(o.getPhotoid());
			}
			for (Lasted_photo o : list) {
				if (photoidset.contains(o.getPhotoid())) {
					if (o.getPhoto() != null) {
						o.getPhoto().setOpliked(true);
					}
				}
			}
		}
		return list;
	}
}