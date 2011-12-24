package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.TagService;
import com.hk.svr.UserService;

@Component("/help")
public class HelpAction extends BaseAction {
	@Autowired
	private TagService tagService;

	@Autowired
	private UserService userService;

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String serviceNickName = "客服";
		String helpName = "火酷帮助";
		User user = this.userService.getUserByNickName(serviceNickName);
		long userId = 0;
		long tagId = 0;
		if (user != null) {
			userId = user.getUserId();
		}
		else {
			return null;
		}
		Tag tag = this.tagService.getTagByName(helpName);
		if (tag != null) {
			tagId = tag.getTagId();
			return "r:/laba/taglabalist.do?tagId=" + tagId
					+ "&fromhelp=1&helpUserId=" + userId;
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendfrom(HkRequest req, HkResponse resp) throws Exception {
		int source = req.getInt("source");
		long labaId = req.getLong("labaId");
		if (source == Laba.SENDFROM_MSN) {
			return "r:/help_frommsn.do?labaId=" + labaId;
		}
		if (source == Laba.SENDFROM_WAP) {
			return "r:/help_fromwap.do?labaId=" + labaId;
		}
		if (source == Laba.SENDFROM_SMS) {
			return "r:/help_fromsms.do?labaId=" + labaId;
		}
		if (source == Laba.SENDFROM_BOSEE) {
			return "r:http://www.bosee.cn";
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fromwap(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("query", req.getQueryString());
		return "/WEB-INF/page/help/fromwap.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fromsms(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("query", req.getQueryString());
		return "/WEB-INF/page/help/fromsms.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String fromweb(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("query", req.getQueryString());
		return "/WEB-INF/page/help/fromweb.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String frommsn(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("query", req.getQueryString());
		return "/WEB-INF/page/help/frommsn.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		String hfrom = req.getString("hfrom", "");
		if (hfrom.equals("reply")) {
			return "r:/laba/laba.do?" + req.getQueryString();
		}
		if (hfrom.equals("sendsms")) {
			return "r:/laba/op/op_tosendsms.do?labaId=" + req.getLong("labaId");
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String how(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("from");
		req.reSetAttribute("w");
		req.reSetAttribute("ouserId");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/help/how.jsp";
	}

	/**
	 * 赚取火酷币的页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String hkbhelp(HkRequest req, HkResponse resp) throws Exception {
		this.reSetQueryString(req);
		return "/WEB-INF/page/help/hkb_help.jsp";
	}

	/**
	 * 火酷币充值
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addhkb(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/help/addhkb.jsp";
	}

	/**
	 * 火酷币充值
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String overview(HkRequest req, HkResponse resp) throws Exception {
		return this.getWeb4Jsp("help/overview.jsp");
	}
}