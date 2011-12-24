package com.etbhk.web.user.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.etbhk.web.vo.Tb_Item_User_RefVo;
import com.etbhk.web.vo.Tb_NewsVo;
import com.hk.bean.taobao.Tb_Follow;
import com.hk.bean.taobao.Tb_Followed;
import com.hk.bean.taobao.Tb_Friend_News;
import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.bean.taobao.Tb_User_Ask;
import com.hk.bean.taobao.Tb_User_News;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_FollowService;
import com.hk.svr.Tb_Item_User_RefService;
import com.hk.svr.Tb_NewsService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.processor.taobao.Tb_AskServiceEx;

@Component("/tb/user")
public class UserAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private Tb_FollowService tb_FollowService;

	@Autowired
	private Tb_Item_User_RefService tb_Item_User_RefService;

	@Autowired
	private Tb_AskServiceEx tbAskServiceEx;

	@Autowired
	private Tb_NewsService tbNewsService;

	public String execute(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		Tb_User loginTbUser = this.getLoginTb_User(req);
		// 关注的朋友
		List<Tb_Follow> followlist = this.tb_FollowService
				.getTb_FollowListByUserid(userid, true, 0, 8);
		req.setAttribute("followlist", followlist);
		if (loginTbUser != null) {
			Tb_Follow tbFollow = this.tb_FollowService
					.getTb_FollowByUseridAndFriendid(loginTbUser.getUserid(),
							userid);
			req.setAttribute("tbFollow", tbFollow);
		}
		List<Tb_User_Ask> askedlist = this.tbAskServiceEx.getTb_User_AskList(
				userid, Tb_User_Ask.ASKFLG_ASK, true, false, false, 0, 5);
		req.setAttribute("askedlist", askedlist);
		List<Tb_User_Ask> answeredlist = this.tbAskServiceEx
				.getTb_User_AskList(userid, Tb_User_Ask.ASKFLG_ANSWER, true,
						false, false, 0, 5);
		req.setAttribute("answeredlist", answeredlist);
		Tb_User_Api sina_tbUserApi = this.tb_UserService.getTb_User_Api(userid,
				Tb_User_Api.REG_SOURCE_SINA);
		req.setAttribute("sina_tbUserApi", sina_tbUserApi);
		return this.getWebJsp("user/user.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-3
	 */
	public String loaditemcmt(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_CMT, true, true, 0, 11);
		if (list.size() == 11) {
			req.setAttribute("more_itemcmt", true);
			list.remove(10);
		}
		long current_login_userid = 0;
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser != null) {
			current_login_userid = loginTbUser.getUserid();
		}
		List<Tb_Item_User_RefVo> item_user_refvolist = Tb_Item_User_RefVo
				.creatVoList(list, current_login_userid);
		req.setAttribute("item_user_refvolist", item_user_refvolist);
		return this.getWebJsp("user/itemcmt_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-3
	 */
	public String loadholditem(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_HOLD, true, true, 0, 11);
		if (list.size() == 11) {
			req.setAttribute("more_holditem", true);
			list.remove(10);
		}
		long current_login_userid = 0;
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser != null) {
			current_login_userid = loginTbUser.getUserid();
		}
		List<Tb_Item_User_RefVo> item_user_refvolist = Tb_Item_User_RefVo
				.creatVoList(list, current_login_userid);
		req.setAttribute("item_user_refvolist", item_user_refvolist);
		return this.getWebJsp("user/holditem_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-3
	 */
	public String loadwantitem(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_WANT, true, false, 0, 13);
		if (list.size() == 13) {
			req.setAttribute("more_wantitem", true);
			list.remove(12);
		}
		req.setAttribute("list", list);
		return this.getWebJsp("user/wantitem_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-30
	 */
	public String home(HkRequest req, HkResponse resp) throws Exception {
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser == null) {
			return null;
		}
		return "r:/tb/user?userid=" + loginTbUser.getUserid();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-3
	 */
	public String prvaddfollow(HkRequest req, HkResponse resp) {
		long friendid = req.getLong("friendid");
		Tb_Follow tbFollow = new Tb_Follow();
		tbFollow.setFriendid(friendid);
		tbFollow.setUserid(this.getLoginTb_User(req).getUserid());
		this.tb_FollowService.createTb_Follow(tbFollow, req
				.getBoolean("follow_to_sina"));
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-3
	 */
	public String prvdelfollow(HkRequest req, HkResponse resp) {
		long friendid = req.getLong("friendid");
		Tb_Follow tbFollow = this.tb_FollowService
				.getTb_FollowByUseridAndFriendid(this.getLoginTb_User(req)
						.getUserid(), friendid);
		this.tb_FollowService.deleteTb_Follow(tbFollow);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @param userid
	 * @return
	 *         2010-9-10
	 */
	public String asked(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_User_Ask> list = this.tbAskServiceEx.getTb_User_AskList(userid,
				Tb_User_Ask.ASKFLG_ASK, true, false, true, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebJsp("user/asked.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @param userid
	 * @return
	 *         2010-9-10
	 */
	public String answered(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_User_Ask> list = this.tbAskServiceEx.getTb_User_AskList(userid,
				Tb_User_Ask.ASKFLG_ANSWER, true, true, true, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebJsp("user/answered.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String getfriendnews(HkRequest req, HkResponse resp) {
		long userid = req.getLong("userid");
		List<Tb_Friend_News> list = this.tbNewsService
				.getTb_Friend_NewsListByUserid(userid, true, 0, 11);
		if (list.size() == 11) {
			req.setAttribute("more_news", true);
			list.remove(10);
		}
		List<Tb_News> newslist = new ArrayList<Tb_News>();
		for (Tb_Friend_News o : list) {
			newslist.add(o.getTbNews());
		}
		List<Tb_NewsVo> newsvolist = Tb_NewsVo.createList(newslist, false);
		req.setAttribute("newsvolist", newsvolist);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String getusernews(HkRequest req, HkResponse resp) {
		long userid = req.getLong("userid");
		List<Tb_User_News> list = this.tbNewsService
				.getTb_User_NewsListByUserid(userid, true, 0, 10);
		if (list.size() == 11) {
			req.setAttribute("more_news", true);
			list.remove(10);
		}
		List<Tb_News> newslist = new ArrayList<Tb_News>();
		for (Tb_User_News o : list) {
			newslist.add(o.getTbNews());
		}
		List<Tb_NewsVo> newsvolist = Tb_NewsVo.createList(newslist, false);
		req.setAttribute("newsvolist", newsvolist);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String friend(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Follow> list = this.tb_FollowService.getTb_FollowListByUserid(
				userid, true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebJsp("user/friend.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String fans(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Followed> list = this.tb_FollowService
				.getTb_FollowedListByUserid(userid, true, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebJsp("user/fans.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String holditem(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_HOLD, true, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		long current_login_userid = 0;
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser != null) {
			current_login_userid = loginTbUser.getUserid();
		}
		List<Tb_Item_User_RefVo> item_user_refvolist = Tb_Item_User_RefVo
				.creatVoList(list, current_login_userid);
		req.setAttribute("item_user_refvolist", item_user_refvolist);
		return this.getWebJsp("user/holditem.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String wantitem(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(25);
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_WANT, true, false,
						page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebJsp("user/wantitem.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String itemcmt(HkRequest req, HkResponse resp) {
		long userid = req.getLongAndSetAttr("userid");
		Tb_User tbUser = this.tb_UserService.getTb_User(userid);
		if (tbUser == null) {
			return null;
		}
		req.setAttribute("tbUser", tbUser);
		SimplePage page = req.getSimplePage(20);
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid,
						Tb_Item_User_Ref.FLG_CMT, true, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		long current_login_userid = 0;
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser != null) {
			current_login_userid = loginTbUser.getUserid();
		}
		List<Tb_Item_User_RefVo> item_user_refvolist = Tb_Item_User_RefVo
				.creatVoList(list, current_login_userid);
		req.setAttribute("item_user_refvolist", item_user_refvolist);
		return this.getWebJsp("user/itemcmt.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-16
	 */
	public String initstat(HkRequest req, HkResponse resp) {
		List<Tb_User> list = this.tb_UserService.getTb_UserListForNew(0, 100);
		for (Tb_User o : list) {
			int count = this.tb_Item_User_RefService
					.countTb_Item_User_RefByuseridAndFlg(o.getUserid(),
							Tb_Item_User_Ref.FLG_CMT);
			this.tb_UserService.updateItem_cmt_countByUserid(o.getUserid(),
					count);
			count = this.tb_Item_User_RefService
					.countTb_Item_User_RefByuseridAndFlg(o.getUserid(),
							Tb_Item_User_Ref.FLG_HOLD);
			this.tb_UserService.updateItem_hold_countByUserid(o.getUserid(),
					count);
			count = this.tb_Item_User_RefService
					.countTb_Item_User_RefByuseridAndFlg(o.getUserid(),
							Tb_Item_User_Ref.FLG_WANT);
			this.tb_UserService.updateItem_want_countByUserid(o.getUserid(),
					count);
		}
		resp.sendHtml("init stat ok");
		return null;
	}
}