package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.PhotoCmt;

public interface PhotoCmtDao extends IDao<PhotoCmt> {

	public int deleteByPhotoid(long photoid);

	public List<PhotoCmt> getListByPhotoid(long photoid, int begin, int size);

	/**
	 * 获得某图片的评论集合
	 * 
	 * @param photoid
	 * @param buildUser 是否构造user对象
	 * @param begin
	 * @param size
	 * @return
	 *         2010-11-27
	 */
	List<PhotoCmt> getListByPhotoid(long photoid, boolean buildUser, int begin,
			int size);

	int countByPhotoid(long photoid);
}
