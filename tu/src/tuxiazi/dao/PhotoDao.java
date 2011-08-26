package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;
import java.util.Map;

import tuxiazi.bean.Photo;

public interface PhotoDao extends IDao<Photo> {

	public Photo getById(Object idValue, long favUserid, boolean buildUser);

	public Map<Long, Photo> getMapInId(List<Long> idList, boolean buildUser,
			boolean buildCmt, boolean buildCmtUser);

	public void addCmt_num(long photoid, int add);
}
