package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.User_photo;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.PhotoUserLikeDao;
import tuxiazi.dao.User_photoDao;

@Component("user_photoDao")
public class User_photoDaoImpl extends BaseDao<User_photo> implements
		User_photoDao {

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private PhotoUserLikeDao photoUserLikeDao;

	@Override
	public Class<User_photo> getClazz() {
		return User_photo.class;
	}

	public int deleteByUseridAndPhotoid(long userid, long photoid) {
		return this.delete(null, "userid=? and photoid=?", new Object[] {
				userid, photoid });
	}

	public int countByUserid(long userid) {
		return this.count("userid=?", new Object[] { userid });
	}

	public List<User_photo> getListByUserid(long userid, int begin, int size) {
		return this.getList("userid=?", new Object[] { userid },
				"photoid desc", begin, size);
	}

	@Override
	public List<User_photo> getListByUserid(long userid, boolean buildPhoto,
			long favUserid, int begin, int size) {
		List<User_photo> list = this.getListByUserid(userid, begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (User_photo o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.photoDao.getMapInId(idList);
			for (User_photo o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
		}
		if (favUserid > 0 && favUserid != userid) {
			List<PhotoUserLike> photoUserLikes = this.photoUserLikeDao
					.getListByUserid(favUserid, begin, -1);
			Set<Long> photoidset = new HashSet<Long>();
			for (PhotoUserLike o : photoUserLikes) {
				photoidset.add(o.getPhotoid());
			}
			for (User_photo o : list) {
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
