package com.hk.web.feed.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Feed;
import com.hk.bean.IpCityRange;
import com.hk.bean.User;
import com.hk.frame.util.ListWrapper;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.IpCityService;
import com.hk.svr.impl.FeedServiceWrapper;
import com.hk.web.pub.action.BaseAction;

@Component("/feed/feed")
public class FeedAction extends BaseAction {
	@Autowired
	private IpCityService ipCityService;

	private int size = 21;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String w = req.getString("w", "");
		if (isEmpty(w)) {
			this.friend(req, resp);
		}
		else if (w.equals("all")) {
			this.all(req, resp);
		}
		else if (w.equals("ip")) {
			this.ip(req, resp);
		}
		else if (w.equals("range")) {
			this.range(req, resp);
		}
		else if (w.equals("city")) {
			this.city(req, resp);
		}
		req.setAttribute("w", w);
		req.reSetAttribute("from");
		return "/WEB-INF/page/feed/list.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 */
	private void friend(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		SimplePage page = req.getSimplePage(size);
		List<Feed> list = wrapper.getFriendFeedList(loginUser.getUserId(), page
				.getBegin(), size);
		List<FeedVo> volist = FeedVo.createList(req, list, true, true);
		page.setListSize(list.size());
		req.setAttribute("volist", volist);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private void all(HkRequest req, HkResponse resp) throws Exception {
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		ListWrapper<Feed> listWrapper = wrapper.getFeedList(0, size);
		List<FeedVo> volist = FeedVo.createList(req, listWrapper.getList(),
				true, true);
		req.setAttribute("volist", volist);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private void ip(HkRequest req, HkResponse resp) throws Exception {
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		ListWrapper<Feed> listWrapper = wrapper.getIpFeedList(req
				.getRemoteAddr(), 0, size);
		List<FeedVo> volist = FeedVo.createList(req, listWrapper.getList(),
				true, true);
		req.setAttribute("volist", volist);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private void city(HkRequest req, HkResponse resp) throws Exception {
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		String ip = req.getRemoteAddr();
		IpCityRange range = ipCityService.getIpCityRange(ip);
		if (range != null) {
			ListWrapper<Feed> listWrapper = wrapper.getIpCityFeedList(range
					.getCityId(), 0, size);
			List<FeedVo> volist = FeedVo.createList(req, listWrapper.getList(),
					true, true);
			req.setAttribute("volist", volist);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private void range(HkRequest req, HkResponse resp) throws Exception {
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		String ip = req.getRemoteAddr();
		IpCityRange range = ipCityService.getIpCityRange(ip);
		if (range != null) {
			ListWrapper<Feed> listWrapper = wrapper.getIpCityRangeFeedList(
					range.getRangeId(), 0, size);
			List<FeedVo> volist = FeedVo.createList(req, listWrapper.getList(),
					true, true);
			req.setAttribute("volist", volist);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		String from = req.getString("from", "");
		if (from.equals("notice")) {
			return "r:/notice/notice.do";
		}
		return "/home.do";
	}
}