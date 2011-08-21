package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.PhotoCmt;

public interface PhotoCmtDao extends IDao<PhotoCmt> {

	public int deleteByPhotoid(long photoid);

	public List<PhotoCmt> getListByPhotoid(long photoid, int begin, int size);
}
