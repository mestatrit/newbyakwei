package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;

import tuxiazi.bean.Friend_photo_feed;

public interface Friend_photo_feedDao extends IDao<Friend_photo_feed> {

	public int countByUserid(long userid);

	public List<Friend_photo_feed> getListByUserid(long userid,
			boolean buildPhoto, boolean buildPhotoUser, long favUserid,
			int begin, int size);

	public void deleteByPhotoid(long photoid);

	public void deleteByUseridAndPhotoUserid(long userid, long photoUserid);
}
