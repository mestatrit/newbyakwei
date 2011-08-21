package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.User_photo;

public interface User_photoDao extends IDao<User_photo> {

	public int deleteByUseridAndPhotoid(long userid, long photoid);

	public int countByUserid(long userid);

	public List<User_photo> getListByUserid(long userid, int begin, int size);
}
