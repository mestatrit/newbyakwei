package com.hk.web.cmpunion.action;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnion;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.CmpUnionService;
import com.hk.web.pub.action.BaseAction;

public abstract class CmpUnionBaseAction extends BaseAction {
	@Override
	protected User getLoginUser(HttpServletRequest req) {
		return CmpUnionUtil.getLoginUser(req);
	}

	protected CmpUnion getCmpUnion(HttpServletRequest req) {
		CmpUnion cmpUnion = (CmpUnion) req.getAttribute("cmpUnion");
		if (cmpUnion == null) {
			long uid = ServletUtil.getLong(req, "uid");
			CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
					.getBean("cmpUnionService");
			cmpUnion = cmpUnionService.getCmpUnion(uid);
			req.setAttribute("cmpUnion", cmpUnion);
		}
		return cmpUnion;
	}

	protected void removeLoginUser(HttpServletRequest req) {
		req.removeAttribute(CmpUnionUtil.CMPUNION_LOGINUSER_ATTR);
		req.getSession().removeAttribute(
				CmpUnionUtil.CMPUNION_LOGINUSER_ATTR);
	}
}