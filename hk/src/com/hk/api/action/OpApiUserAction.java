package com.hk.api.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.ApiUser;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ApiUserService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/apiuser")
public class OpApiUserAction extends BaseAction {
	@Autowired
	private ApiUserService apiUserService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/apiuser/add.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		String url = req.getString("url");
		ApiUser o = new ApiUser();
		o.setCheckflg(ApiUser.CHECKFLG_Y);
		o.setName(DataUtil.toHtmlRow(name));
		o.setUrl(DataUtil.toHtmlRow(url));
		o.setUserId(this.getLoginUser(req).getUserId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			req.setAttribute("o", o);
			return "/admin/apiuser_toadd.do";
		}
		this.apiUserService.createApiUser(o);
		return "r:/admin/apiuser_view.do?apiUserId=" + o.getApiUserId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		int apiUserId = req.getInt("apiUserId");
		ApiUser o = this.apiUserService.getApiUser(apiUserId);
		req.setAttribute("o", o);
		return "/WEB-INF/page/apiuser/view.jsp";
	}
}