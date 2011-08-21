package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;

import java.util.List;

import tuxiazi.bean.PhotoCmt;
import tuxiazi.dao.PhotoCmtDao;

public class PhotoCmtDaoImpl extends BaseDao<PhotoCmt> implements PhotoCmtDao {

	@Override
	public Class<PhotoCmt> getClazz() {
		return PhotoCmt.class;
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete("photoid=?", new Object[] { photoid });
	}

	public List<PhotoCmt> getListByPhotoid(long photoid, int begin, int size) {
		return this.getList("photoid=?", new Object[] { photoid },
				"cmtid desc", begin, size);
	}
}
