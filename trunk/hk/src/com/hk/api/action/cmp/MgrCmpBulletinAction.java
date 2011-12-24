package com.hk.api.action.cmp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CmpBulletin;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBulletinService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/protect/cmp/bulletin/mgr")
public class MgrCmpBulletinAction extends BaseApiAction {

	@Autowired
	private CmpBulletinService cmpBulletinService;

	public String execute(HkRequest req, HkResponse resp) {
		return null;
	}

	public String create(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String content = req.getString("content");
		String title = req.getString("title");
		CmpBulletin o = new CmpBulletin();
		o.setCompanyId(companyId);
		o.setTitle(DataUtil.toHtmlRow(title));
		o.setContent(DataUtil.toHtml(content));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.cmpBulletinService.cretaeCmpBulletin(o);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bulletincreate", true);
		map.put("bulletinId", o.getBulletinId());
		APIUtil.sendSuccessRespStatus(resp, map);
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int bulletinId = req.getInt("bulletinId");
		CmpBulletin o = this.cmpBulletinService.getCmpBulletin(bulletinId);
		if (o == null || o.getCompanyId() != companyId) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.cmpBulletinService.deleteCmpBulletin(bulletinId);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String update(HkRequest req, HkResponse resp) {
		String content = req.getString("content");
		String title = req.getString("title");
		int bulletinId = req.getInt("bulletinId");
		CmpBulletin o = this.cmpBulletinService.getCmpBulletin(bulletinId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		o.setContent(DataUtil.toHtml(content));
		o.setTitle(DataUtil.toHtmlRow(title));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.cmpBulletinService.updateCmpBulletin(o);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}