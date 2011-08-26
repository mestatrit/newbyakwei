package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.Lasted_photo;

public interface Lasted_photoDao extends IDao<Lasted_photo> {

	public int count();

	public List<Lasted_photo> getList(int begin, int size);

	public List<Lasted_photo> getList(boolean buildPhoto,
			boolean buildPhotoUser, long favUserid, boolean buildCmt,
			boolean buildCmtUser, int begin, int size);
}