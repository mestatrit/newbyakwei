package com.hk.svr.implex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Friend_News;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_User_News;
import com.hk.svr.Tb_NewsService;
import com.hk.svr.impl.Tb_NewsServiceImpl;

public class Tb_NewsServiceImplEx extends Tb_NewsServiceImpl {

	private Tb_NewsService tbNewsService;

	public void setTbNewsService(Tb_NewsService tbNewsService) {
		this.tbNewsService = tbNewsService;
	}

	@Override
	public List<Tb_Friend_News> getTb_Friend_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size) {
		List<Tb_Friend_News> list = tbNewsService
				.getTb_Friend_NewsListByUserid(userid, buildNews, begin, size);
		if (buildNews) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Friend_News o : list) {
				idList.add(o.getNid());
			}
			Map<Long, Tb_News> map = this.tbNewsService
					.getTb_NewsMapInId(idList);
			List<Tb_Friend_News> delList = new ArrayList<Tb_Friend_News>();
			for (Tb_Friend_News o : list) {
				o.setTbNews(map.get(o.getNid()));
				if (o.getTbNews() == null) {
					delList.add(o);
				}
			}
			for (Tb_Friend_News o : delList) {
				this.tbNewsService.deleteTb_Friend_News(o.getOid());
				list.remove(o);
			}
		}
		return list;
	}

	@Override
	public List<Tb_User_News> getTb_User_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size) {
		List<Tb_User_News> list = tbNewsService.getTb_User_NewsListByUserid(
				userid, buildNews, begin, size);
		if (buildNews) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_User_News o : list) {
				idList.add(o.getNid());
			}
			Map<Long, Tb_News> map = this.tbNewsService
					.getTb_NewsMapInId(idList);
			List<Tb_User_News> delList = new ArrayList<Tb_User_News>();
			for (Tb_User_News o : list) {
				o.setTbNews(map.get(o.getNid()));
				if (o.getTbNews() == null) {
					delList.add(o);
				}
			}
			for (Tb_User_News o : delList) {
				this.tbNewsService
						.deleteTb_User_News(o.getUserid(), o.getNid());
				list.remove(o);
			}
		}
		return list;
	}
}