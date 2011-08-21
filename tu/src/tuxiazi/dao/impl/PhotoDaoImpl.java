package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.Photoid;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.UserDao;
import tuxiazi.dao.User_photoDao;

@Component("photoDao")
public class PhotoDaoImpl extends BaseDao<Photo> implements PhotoDao {

	@Autowired
	PhotoidDaoImpl photoidDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private User_photoDao user_photoDao;

	@Autowired
	private PhotoUserLikeDaoImpl photoUserLikeDao;

	@Override
	public Class<Photo> getClazz() {
		return Photo.class;
	}

	public Photo getById(Object idValue, long favUserid, boolean buildUser) {
		Photo photo = this.getById(idValue);
		if (photo == null) {
			return null;
		}
		if (buildUser) {
			photo.setUser(this.userDao.getById(photo.getUserid()));
		}
		if (favUserid > 0) {
			PhotoUserLike photoUserLike = this.photoUserLikeDao
					.getByUseridAndPhotoid(favUserid, photo.getPhotoid());
			if (photoUserLike != null) {
				photo.setOpliked(true);
			}
		}
		return photo;
	}

	@Override
	public Object save(Photo t) {
		long photoid = NumberUtil.getLong(this.photoidDao.save(new Photoid()));
		t.setPhotoid(photoid);
		return photoid;
	}

	public List<Photo> getListInId(List<Long> idList) {
		return this.getListInField("photoid", idList);
	}

	public Map<Long, Photo> getMapInId(List<Long> idList) {
		if (idList.isEmpty()) {
			return new HashMap<Long, Photo>();
		}
		Map<Long, Photo> map = new HashMap<Long, Photo>();
		List<Photo> list = this.getListInId(idList);
		for (Photo o : list) {
			map.put(o.getPhotoid(), o);
		}
		return map;
	}

	public void addCmt_num(long photoid, int add) {
		this.updateBySQL("cmt_num=cmt_num+?", "photoid=?", new Object[] { add,
				photoid });
	}
}
