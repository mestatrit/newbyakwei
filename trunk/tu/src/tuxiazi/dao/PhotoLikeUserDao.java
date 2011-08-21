package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.PhotoLikeUser;

public interface PhotoLikeUserDao extends IDao<PhotoLikeUser> {

	public int deleteByPhotoid(long photoid);

	public int deleteByUseridAndPhotoid(long userid, long photoid);

	public PhotoLikeUser getByUseridAndPhotoid(long userid, long photoid);

	public int countByPhotoid(long photoid);

	public List<PhotoLikeUser> getListByPhotoid(long photoid,
			boolean buildUser, long refuserid, int begin, int size);
}
