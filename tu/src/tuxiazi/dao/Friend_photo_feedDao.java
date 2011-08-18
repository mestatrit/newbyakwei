package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Friend_photo_feed;

public class Friend_photo_feedDao extends BaseDao<Friend_photo_feed> {

	@Override
	public Class<Friend_photo_feed> getClazz() {
		return Friend_photo_feed.class;
	}
}
