package iwant.dao.impl;

import iwant.bean.Notice;
import iwant.dao.NoticeDao;

import java.util.List;

import com.hk.frame.dao.query2.BaseDao;

public class NoticeDaoImpl extends BaseDao<Notice> implements NoticeDao {

	@Override
	public Class<Notice> getClazz() {
		return Notice.class;
	}

	@Override
	public List<Notice> getList(int begin, int size) {
		return this.getList(null, null, null, null, begin, size);
	}
}