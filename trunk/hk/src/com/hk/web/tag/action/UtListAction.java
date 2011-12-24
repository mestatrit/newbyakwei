package com.hk.web.tag.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;

@Component("/tag/utlist")
public class UtListAction extends BaseAction {

	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String show = req.getString("show", "");
		long userId = req.getInt("userId");
		User user = userService.getUser(userId);
		SimplePage page = req.getSimplePage(size);
		List<Tag> list = null;
		if (show.equals("hot")) {
			list = this.tagService.getTagListByUserIdOrderByHot(userId, page
					.getBegin(), size);
		}
		else if (show.equals("num")) {
			list = this.tagService.getTagListByUserIdOrderByLabaCount(userId,
					page.getBegin(), size);
		}
		else {
			list = this.tagService.getTagListByUserId(userId, page.getBegin(),
					size);
		}
		page.setListSize(list.size());
		req.setAttribute("list", list);
		req.setAttribute("userId", userId);
		req.setAttribute("user", user);
		req.setAttribute("show", show);
		return "/WEB-INF/page/tag/utlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mt(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {
			return this.getLoginForward();
		}
		List<Tag> list = this.tagService.getTagListByUserIdOrderByHot(loginUser
				.getUserId(), 0, 11);
		List<Tag> hotlist = this.tagService.getTagListOrderByHot(0, 21);
		boolean more = false;
		if (list.size() == 11) {
			more = true;
			list.remove(10);
		}
		boolean hotmore = false;
		if (hotlist.size() == 21) {
			hotmore = true;
			hotlist.remove(20);
		}
		req.setAttribute("more", more);
		req.setAttribute("hotmore", hotmore);
		req.setAttribute("list", list);
		req.setAttribute("hotlist", hotlist);
		return "/WEB-INF/page/tag/mt.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hot(HkRequest req, HkResponse resp) throws Exception {
		int hot = req.getInt("hot");
		List<Tag> list = null;
		SimplePage page = req.getSimplePage(size);
		if (hot == 1) {
			list = this.tagService.getTagListOrderByHot(page.getBegin(), size);
		}
		else {
			list = this.tagService.getTagListOrderByLabaCount(page.getBegin(),
					size);
		}
		page.setListSize(list.size());
		req.setAttribute("hot", hot);
		req.setAttribute("list", list);
		return "/WEB-INF/page/tag/hot.jsp";
	}
}