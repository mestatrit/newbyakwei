package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Friend_News;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_Newsid;
import com.hk.bean.taobao.Tb_User_News;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.Tb_NewsService;

public class Tb_NewsServiceImpl implements Tb_NewsService {

	@Autowired
	private QueryManager manager;

	@Override
	public void createTb_Friend_News(Tb_Friend_News tbFriendNews) {
		Query query = this.manager.createQuery();
		tbFriendNews.setOid(query.insertObject(tbFriendNews).longValue());
	}

	@Override
	public void createTb_News(Tb_News tbNews) {
		Query query = this.manager.createQuery();
		Tb_Newsid tbNewsid = new Tb_Newsid(new Date());
		tbNewsid.setNid(query.insertObject(tbNewsid).longValue());
		tbNews.setNid(tbNewsid.getNid());
		query.insertObject(tbNews);
		Tb_User_News tbUserNews = new Tb_User_News();
		tbUserNews.setNid(tbNews.getNid());
		tbUserNews.setUserid(tbNews.getUserid());
		tbUserNews.setCreate_time(tbNews.getCreate_time());
		query.insertObject(tbUserNews);
		int count = query.count(Tb_User_News.class, "userid=?",
				new Object[] { tbNews.getUserid() });
		if (count > 100) {// 多于100条的数据，把后面的删除
			List<Tb_User_News> list = query.listEx(Tb_User_News.class,
					"userid=?", new Object[] { tbUserNews.getUserid() },
					"oid desc", 99, count - 100);
			for (Tb_User_News o : list) {
				query.deleteById(Tb_User_News.class, o.getNid());
			}
		}
	}

	@Override
	public void deleteTb_Friend_News(long oid) {
		this.manager.createQuery().deleteById(Tb_Friend_News.class, oid);
	}

	@Override
	public List<Tb_Friend_News> getTb_Friend_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size) {
		return this.manager.createQuery().listEx(Tb_Friend_News.class,
				"userid=?", new Object[] { userid }, "oid desc", begin, size);
	}

	@Override
	public Tb_News getTb_News(long nid) {
		return this.manager.createQuery().getObjectById(Tb_News.class, nid);
	}

	@Override
	public Map<Long, Tb_News> getTb_NewsMapInId(List<Long> idList) {
		List<Tb_News> list = this.manager.createQuery().listInField(
				Tb_News.class, null, null, "nid", idList, null);
		Map<Long, Tb_News> map = new HashMap<Long, Tb_News>();
		for (Tb_News o : list) {
			map.put(o.getNid(), o);
		}
		return map;
	}

	@Override
	public List<Tb_User_News> getTb_User_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size) {
		return this.manager.createQuery().listEx(Tb_User_News.class,
				"userid=?", new Object[] { userid }, "nid desc", begin, size);
	}

	@Override
	public void deleteTb_Friend_NewsByUseridAndNews_userid(long userid,
			long newsUserid) {
		this.manager.createQuery().delete(Tb_Friend_News.class,
				"userid=? and news_userid=?",
				new Object[] { userid, newsUserid });
	}

	@Override
	public void deleteTb_NewsByNtypeAndOid(int ntype, long oid) {
		this.manager.createQuery().delete(Tb_News.class, "ntype=? and oid=?",
				new Object[] { ntype, oid });
	}

	@Override
	public void deleteTb_User_News(long userid, long nid) {
		this.manager.createQuery().delete(Tb_User_News.class,
				"userid=? and nid=?", new Object[] { userid, nid });
	}
}