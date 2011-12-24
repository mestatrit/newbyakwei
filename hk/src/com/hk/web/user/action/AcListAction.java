package com.hk.web.user.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

/**
 * 活跃度排序
 * 
 * @author yuanwei
 */
@Component("/user/aclist")
public class AcListAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) {
		String w = req.getString("w", "all");
		req.reSetAttribute("w");
		if (w.equals("all")) {
			this.all(req);
		}
		else if (w.equals("city")) {
			this.city(req);
		}
		else if (w.equals("range")) {
			this.range(req);
		}
		else if (w.equals("ip")) {
			this.ip(req);
		}
		return "/WEB-INF/page/user/aclist.jsp";
	}

	/**
	 * @param req
	 */
	public void all(HkRequest req) {//
	}

	/**
	 * @param req
	 */
	public void city(HkRequest req) {//
	}

	/**
	 * @param req
	 */
	public void range(HkRequest req) {//
	}

	/**
	 * @param req
	 */
	public void ip(HkRequest req) {//
	}
}