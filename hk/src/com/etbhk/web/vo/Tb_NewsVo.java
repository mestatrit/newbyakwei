package com.etbhk.web.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etbhk.web.vo.news.CombinedNewsMaker;
import com.hk.bean.taobao.Tb_News;

public class Tb_NewsVo {

	public static final Map<Integer, CombinedNewsMaker> COMBINEMAKERMAP = new HashMap<Integer, CombinedNewsMaker>();

	public static final Map<Integer, Boolean> COMBINED_MAP = new HashMap<Integer, Boolean>();
	static {
	}

	public Tb_NewsVo(Tb_News tbNews) {
		this.addNews(tbNews);
	}

	private List<Tb_News> newsList = new ArrayList<Tb_News>();

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void addNews(Tb_News tbNews) {
		this.newsList.add(tbNews);
	}

	public Tb_News getFirst() {
		if (newsList.size() > 0) {
			return newsList.get(0);
		}
		return null;
	}

	public Tb_News getLast() {
		if (newsList.size() > 0) {
			return newsList.get(newsList.size() - 1);
		}
		return null;
	}

	public static boolean isFeedCombined(int ntype) {
		Boolean o = COMBINED_MAP.get(ntype);
		if (o == null) {
			return false;
		}
		return o;
	}

	public static List<Tb_NewsVo> createList(List<Tb_News> list,
			boolean combined) {
		List<Tb_NewsVo> volist = new ArrayList<Tb_NewsVo>();
		if (combined) {
			for (Tb_News feed : list) {
				if (!combine(volist, feed)) {
					Tb_NewsVo vo = new Tb_NewsVo(feed);
					volist.add(vo);
				}
			}
		}
		else {
			for (Tb_News feed : list) {
				Tb_NewsVo vo = new Tb_NewsVo(feed);
				volist.add(vo);
			}
		}
		return volist;
	}

	/**
	 * 合并动态
	 * 
	 * @param volist
	 * @param cmpUnionFeed
	 * @return
	 */
	private static boolean combine(List<Tb_NewsVo> volist, Tb_News feed) {
		CombinedNewsMaker combinedMaker = null;
		for (Tb_NewsVo vo : volist) {
			Tb_News last = vo.getLast();
			if (isFeedCombined(feed.getNtype())) {
				if (last.getNtype() == feed.getNtype()) {
					combinedMaker = COMBINEMAKERMAP.get(feed.getNtype());
					if (combinedMaker == null) {
						if (last.getUserid() == feed.getUserid()) {
							vo.addNews(feed);
							return true;
						}
					}
					else {
						if (combinedMaker.isCombined(last, feed)) {
							vo.addNews(feed);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}