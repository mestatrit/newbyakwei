package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.util.NumberUtil;

import java.util.List;

import org.springframework.stereotype.Component;

import tuxiazi.bean.HotPhoto;
import tuxiazi.dao.HotPhotoDao;

@Component("hotPhotoDao")
public class HotPhotoDaoImpl extends BaseDao<HotPhoto> implements HotPhotoDao {

	@Override
	public Class<HotPhoto> getClazz() {
		return HotPhoto.class;
	}

	@Override
	public Object save(HotPhoto t) {
		long oid = NumberUtil.getLong(super.save(t));
		t.setOid(oid);
		return oid;
	}

	@Override
	public int deleteAll() {
		return this.delete(null, null);
	}

	public int deleteByPhotoid(long photoid) {
		return this.delete(null, "photoid=?", new Object[] { photoid });
	}

	public List<HotPhoto> getList(int begin, int size) {
		return this.getList(null, null, "oid desc", begin, size);
	}
}
