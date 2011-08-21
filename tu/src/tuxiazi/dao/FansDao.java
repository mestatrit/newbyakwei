package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.Fans;

public interface FansDao extends IDao<Fans> {

	public Fans getByUseridAndFansid(long userid, long fansid);

	public int deleteByUseridAndFansid(long userid, long fansid);

	public int countByUserid(long userid);

	public List<Long> getFansidListByUserid(long userid);

	public List<Fans> getListByUserid(long userid, int begin, int size);
}
