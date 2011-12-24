package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Notice;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_NoticeService;

public class Tb_NoticeServiceImpl implements Tb_NoticeService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Notice(Tb_Notice tbNotice) {
		tbNotice.setNoticeid(this.manager.createQuery().insertObject(tbNotice)
				.longValue());
	}

	@Override
	public void deleteTb_Notice(long noticeid) {
		this.manager.createQuery().deleteById(Tb_Notice.class, noticeid);
	}

	@Override
	public Tb_Notice getTb_Notice(long noticeid) {
		return this.manager.createQuery().getObjectById(Tb_Notice.class,
				noticeid);
	}

	@Override
	public List<Tb_Notice> getTb_NoticeListByuserid(long userid, int begin,
			int size) {
		return this.manager.createQuery().listEx(Tb_Notice.class, "userid=?",
				new Object[] { userid }, "readflg asc,noticeid desc", begin,
				size);
	}

	@Override
	public void updateTb_NoticeReadflg(long noticeid, byte readflg) {
		Query query = this.manager.createQuery();
		query.addField("readflg", readflg);
		query.updateById(Tb_Notice.class, noticeid);
	}

	@Override
	public int countNoReadNoticeByUserid(long userid) {
		return this.manager.createQuery().count(Tb_Notice.class,
				"userid=? and readflg=?",
				new Object[] { userid, Tb_Notice.READFLG_N });
	}
}