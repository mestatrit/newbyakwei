package com.hk.web.feed.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Feed;
import com.hk.web.feed.util.web.BadgeFeedMaker;
import com.hk.web.feed.util.web.CmpTipFeedMaker;
import com.hk.web.feed.util.web.CombinedMaker;
import com.hk.web.feed.util.web.EquFeedMaker;
import com.hk.web.feed.util.web.FeedMaker;
import com.hk.web.feed.util.web.FollowFeedMaker;
import com.hk.web.feed.util.web.InviteFeedMaker;
import com.hk.web.feed.util.web.MayorFeedMaker;
import com.hk.web.feed.util.web.OpenBoxFeedMaker;
import com.hk.web.feed.util.web.VenueFeedMaker;

public class FeedVo {

	public static String FEED_USERMAP_IN_ATTR = "feed_usermap";

	public static final Map<Byte, FeedMaker> map = new HashMap<Byte, FeedMaker>();

	public static final Map<Byte, CombinedMaker> COMBINEMAKERMAP = new HashMap<Byte, CombinedMaker>();

	public static final Map<Byte, Boolean> COMBINED_MAP = new HashMap<Byte, Boolean>();
	static {
		COMBINED_MAP.put(Feed.FEEDTYPE_FOLLOW, true);
		COMBINED_MAP.put(Feed.FEEDTYPE_OPENBOX, true);
		map.put(Feed.FEEDTYPE_FOLLOW, new FollowFeedMaker());
		map.put(Feed.FEEDTYPE_OPENBOX, new OpenBoxFeedMaker());
		map.put(Feed.FEEDTYPE_BECOME_MAYOR, new MayorFeedMaker());
		map.put(Feed.FEEDTYPE_GETBADGE, new BadgeFeedMaker());
		map.put(Feed.FEEDTYPE_WRITETIPS, new CmpTipFeedMaker());
		map.put(Feed.FEEDTYPE_EQU, new EquFeedMaker());
		map.put(Feed.FEEDTYPE_CREATEVENUE, new VenueFeedMaker());
		map.put(Feed.FEEDTYPE_INVITE, new InviteFeedMaker());
		// COMBINEMAKERMAP.put(Feed.FEEDTYPE_OPENBOX, new BoxCombinedMaker());
	}

	public static boolean isFeedCombined(byte feedType) {
		Boolean o = COMBINED_MAP.get(feedType);
		if (o == null) {
			return false;
		}
		return o;
	}

	private List<Feed> feedList = new ArrayList<Feed>();

	private String content;

	public FeedVo(Feed feed) {
		this.addFeed(feed);
	}

	public List<Feed> getFeedList() {
		return feedList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void addFeed(Feed feed) {
		this.feedList.add(feed);
	}

	public Feed getFirst() {
		if (feedList.size() > 0) {
			return feedList.get(0);
		}
		return null;
	}

	public Feed getLast() {
		if (feedList.size() > 0) {
			return feedList.get(feedList.size() - 1);
		}
		return null;
	}

	public static List<FeedVo> createList(HttpServletRequest request,
			List<Feed> list, boolean combined, boolean wap) {
		List<FeedVo> volist = new ArrayList<FeedVo>();
		if (combined) {
			for (Feed feed : list) {
				if (!combine(volist, feed)) {
					FeedVo vo = new FeedVo(feed);
					volist.add(vo);
				}
			}
		}
		else {
			for (Feed feed : list) {
				FeedVo vo = new FeedVo(feed);
				volist.add(vo);
			}
		}
		FeedMaker feedMaker = null;
		for (FeedVo vo : volist) {
			feedMaker = map.get(vo.getLast().getFeedType());
			if (feedMaker != null) {
				if (wap) {
					vo.setContent(feedMaker.getContentForWap(request, vo));
				}
				else {
					vo.setContent(feedMaker.getContentForWeb(request, vo));
				}
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
	private static boolean combine(List<FeedVo> volist, Feed feed) {
		CombinedMaker combinedMaker = null;
		for (FeedVo vo : volist) {
			Feed last = vo.getLast();
			if (isFeedCombined(feed.getFeedType())) {
				if (last.getFeedType() == feed.getFeedType()) {
					combinedMaker = COMBINEMAKERMAP.get(feed.getFeedType());
					if (combinedMaker == null) {
						if (last.getUserId() == feed.getUserId()) {
							vo.addFeed(feed);
							return true;
						}
					}
					else {
						if (combinedMaker.isCombined(last, feed)) {
							vo.addFeed(feed);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}