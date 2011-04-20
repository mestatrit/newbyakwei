package tuxiazi.dao;

import tuxiazi.bean.Friend_photo_feed;

import com.hk.frame.dao.query2.BaseDao;

public class Friend_photo_feedDao extends BaseDao<Friend_photo_feed> {

	@Override
	public Class<Friend_photo_feed> getClazz() {
		return Friend_photo_feed.class;
	}
}
