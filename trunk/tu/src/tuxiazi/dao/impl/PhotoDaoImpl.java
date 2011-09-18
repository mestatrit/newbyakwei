package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.Photoid;
import tuxiazi.bean.User;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.UserDao;

@Component("photoDao")
public class PhotoDaoImpl extends BaseDao<Photo> implements PhotoDao {

	@Autowired
	PhotoidDaoImpl photoidDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PhotoCmtDao photoCmtDao;

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
		super.save(t);
		return photoid;
	}

	public Map<Long, Photo> getMapInId(List<Long> idList, boolean buildUser,
			boolean buildCmt, boolean buildCmtUser) {
		if (idList.isEmpty()) {
			return new HashMap<Long, Photo>();
		}
		Map<Long, Photo> map = new HashMap<Long, Photo>();
		List<Photo> list = this.getListInField("photoid", idList);
		for (Photo o : list) {
			map.put(o.getPhotoid(), o);
		}
		if (buildUser) {
			this.buildPhotoUser(list);
		}
		if (buildCmt) {
			this.buildPhotoCmt(list, buildCmtUser);
		}
		return map;
	}

	private void buildPhotoUser(List<Photo> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Photo o : list) {
			idList.add(o.getUserid());
		}
		Map<Long, User> usermap = this.userDao.getMapInId(idList);
		for (Photo o : list) {
			o.setUser(usermap.get(o.getUserid()));
		}
	}

	private void buildPhotoCmt(List<Photo> list, boolean buildCmtUser) {
		for (Photo o : list) {
			o.setPhotoCmtList(this.photoCmtDao.getListByPhotoid(o.getPhotoid(),
					false, 0, 10));
		}
		if (buildCmtUser) {
			// 获取回复人id
			List<Long> idList = new ArrayList<Long>();
			for (Photo o : list) {
				for (PhotoCmt cmt : o.getPhotoCmtList()) {
					idList.add(cmt.getUserid());
				}
			}
			Map<Long, User> map = this.userDao.getMapInId(idList);
			for (Photo o : list) {
				// 获取回复人id
				for (PhotoCmt cmt : o.getPhotoCmtList()) {
					cmt.setUser(map.get(cmt.getUserid()));
				}
			}
		}
	}

	public void addCmt_num(long photoid, int add) {
		this.updateBySQL("cmt_num=cmt_num+?", "photoid=?", new Object[] { add,
				photoid });
	}
}
