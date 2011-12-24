package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CmpRecruit;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/protect/cmp/mgrrecruit/mgr")
public class MgrCmpRecruitAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String title = req.getString("title");
		String content = req.getString("content");
		CmpRecruit o = new CmpRecruit();
		o.setCompanyId(companyId);
		o.setTitle(DataUtil.toHtmlRow(title));
		o.setContent(DataUtil.toHtml(content));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.companyService.createCmpRecruit(o);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.companyService.deleteCmpRecruit(companyId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}