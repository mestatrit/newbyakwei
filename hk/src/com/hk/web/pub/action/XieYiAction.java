package com.hk.web.pub.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/pub/xieyi")
public class XieYiAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi1.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p1(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi1.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p2(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi2.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p3(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi3.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p4(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi4.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p5(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi5.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p6(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi6.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String p7(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("inviteId");
		req.reSetAttribute("inviterId");
		req.reSetAttribute("prouserId");
		return "/WEB-INF/page/xieyi/xieyi7.jsp";
	}
}