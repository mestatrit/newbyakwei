package com.hk.web.cmpunion.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnionFeed;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.web.cmpunion.valuecreater.feed.BoxValueCreater;
import com.hk.web.cmpunion.valuecreater.feed.CmpActValueCreater;
import com.hk.web.cmpunion.valuecreater.feed.CouponFeedValueCreater;
import com.hk.web.cmpunion.valuecreater.feed.FeedValueCreater;
import com.hk.web.cmpunion.valuecreater.feed.ProductFeedValueCreater;

public class CmpUnionFeedVo {
	private static final Map<Integer, FeedValueCreater> map = new HashMap<Integer, FeedValueCreater>();

	private List<CmpUnionFeed> cmpUnionFeedList = new ArrayList<CmpUnionFeed>();
	static {
		map.put(CmpUnionMessageUtil.FEED_CREATECOUPON,
				new CouponFeedValueCreater());
		map.put(CmpUnionMessageUtil.FEED_CREATEPRODUCT,
				new ProductFeedValueCreater());
		map.put(CmpUnionMessageUtil.FEED_CREATEBOX, new BoxValueCreater());
		map
				.put(CmpUnionMessageUtil.FEED_CREATECMPACT,
						new CmpActValueCreater());
	}

	private String content;

	private Date createTime;

	public List<CmpUnionFeed> getCmpUnionFeedList() {
		return cmpUnionFeedList;
	}

	public CmpUnionFeedVo(CmpUnionFeed cmpUnionFeed) {
		this.addCmpUnionFeed(cmpUnionFeed);
	}

	public void addCmpUnionFeed(CmpUnionFeed cmpUnionFeed) {
		this.cmpUnionFeedList.add(cmpUnionFeed);
	}

	public CmpUnionFeed getFirst() {
		if (cmpUnionFeedList.size() > 0) {
			return cmpUnionFeedList.get(0);
		}
		return null;
	}

	public CmpUnionFeed getLast() {
		if (cmpUnionFeedList.size() > 0) {
			return cmpUnionFeedList.get(cmpUnionFeedList.size() - 1);
		}
		return null;
	}

	/**
	 * 获得动态数据
	 * 
	 * @param list 动态数据记录
	 * @param combined 是否合并
	 * @return
	 */
	public static List<CmpUnionFeedVo> createList(HttpServletRequest request,
			List<CmpUnionFeed> list, boolean combined) {
		List<CmpUnionFeedVo> volist = new ArrayList<CmpUnionFeedVo>();
		if (combined) {
			for (CmpUnionFeed feed : list) {
				if (!combine(volist, feed)) {
					CmpUnionFeedVo vo = new CmpUnionFeedVo(feed);
					volist.add(vo);
				}
			}
		}
		else {
			for (CmpUnionFeed feed : list) {
				CmpUnionFeedVo vo = new CmpUnionFeedVo(feed);
				volist.add(vo);
			}
		}
		FeedValueCreater feedValueCreater = null;
		for (CmpUnionFeedVo vo : volist) {
			feedValueCreater = map.get(vo.getLast().getFeedflg());
			if (feedValueCreater != null) {
				vo.setContent(feedValueCreater.getValue(request, vo));
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
	private static boolean combine(List<CmpUnionFeedVo> volist,
			CmpUnionFeed cmpUnionFeed) {
		for (CmpUnionFeedVo vo : volist) {
			CmpUnionFeed last = vo.getLast();
			if (last.getObjId() == cmpUnionFeed.getObjId()
					&& last.getFeedflg() == cmpUnionFeed.getFeedflg()
					&& CmpUnionMessageUtil.isFeedCombined(cmpUnionFeed
							.getFeedflg())) {
				vo.addCmpUnionFeed(cmpUnionFeed);
				return true;
			}
		}
		return false;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}