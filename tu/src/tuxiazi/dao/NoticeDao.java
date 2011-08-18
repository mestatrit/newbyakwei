package tuxiazi.dao;

import halo.dao.query.BaseDao;
import tuxiazi.bean.Notice;

public class NoticeDao extends BaseDao<Notice> {

	@Override
	public Class<Notice> getClazz() {
		return Notice.class;
	}
}
