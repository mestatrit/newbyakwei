package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.PhotoCmtid;
import tuxiazi.bean.User;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.PhotoCmtidDao;
import tuxiazi.dao.UserDao;

@Component("photoCmtDao")
public class PhotoCmtDaoImpl extends BaseDao<PhotoCmt> implements PhotoCmtDao {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PhotoCmtidDao photoCmtidDao;

	@Override
	public Class<PhotoCmt> getClazz() {
		return PhotoCmt.class;
	}

	@Override
	public Object save(PhotoCmt t) {
		long cmtid = NumberUtil.getLong(this.photoCmtidDao
				.save(new PhotoCmtid()));
		t.setCmtid(cmtid);
		super.save(t);
		return cmtid;
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete("photoid=?", new Object[] { photoid });
	}

	public List<PhotoCmt> getListByPhotoid(long photoid, int begin, int size) {
		return this.getList("photoid=?", new Object[] { photoid },
				"cmtid desc", begin, size);
	}

	public int countByPhotoid(long photoid) {
		return this.count("photoid=?", new Object[] { photoid });
	}

	@Override
	public List<PhotoCmt> getListByPhotoid(long photoid, boolean buildUser,
			int begin, int size) {
		List<PhotoCmt> list = this.getListByPhotoid(photoid, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (PhotoCmt o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, User> map = this.userDao.getMapInId(idList);
			for (PhotoCmt o : list) {
				o.setUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}
