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

	/**
	 * @param userid
	 * @param buildUser
	 *            true:组装fans中的fansUser对象
	 * @param relationUserid
	 *            如果需要获得关注关系，就赋值为当前访问用户的userid
	 * @param begin
	 * @param size
	 * @return 2010-12-5
	 */
	List<Fans> getListByUserid(long userid, boolean buildUser,
			long relationUserid, int begin, int size);
}
