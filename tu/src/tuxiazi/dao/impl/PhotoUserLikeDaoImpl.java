package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.List;

import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoUserLike;
import tuxiazi.dao.PhotoUserLikeDao;

@Component("photoUserLikeDao")
public class PhotoUserLikeDaoImpl extends BaseDao<PhotoUserLike> implements
		PhotoUserLikeDao {

	@Override
	public Class<PhotoUserLike> getClazz() {
		return PhotoUserLike.class;
	}

	@Override
	public Object save(PhotoUserLike t) {
		long id = NumberUtil.getLong(super.save(t));
		t.setOid(id);
		return id;
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete("photoid=?", new Object[] { photoid });
	}

	public int deleteByUseridAndPhotoid(long userid, long photoid) {
		return this.delete(null, "userid=? and photoid=?", new Object[] {
				userid, photoid });
	}

	public PhotoUserLike getByUseridAndPhotoid(long userid, long photoid) {
		return this.getObject("userid=? and photoid=?", new Object[] { userid,
				photoid });
	}

	public List<PhotoUserLike> getListByPhotoid(long photoid, int begin,
			int size) {
		return this.getList("photoid=?", new Object[] { photoid }, "oid desc",
				begin, size);
	}

	public List<PhotoUserLike> getListByUserid(long userid, int begin, int size) {
		return this.getList(null, "userid=?", new Object[] { userid },
				"oid desc", begin, size);
	}
}
