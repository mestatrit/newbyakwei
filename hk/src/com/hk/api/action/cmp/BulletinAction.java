package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.CmpBulletin;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBulletinService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/cmp/bulletin")
public class BulletinAction extends BaseApiAction {

	@Autowired
	private CmpBulletinService cmpBulletinService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int bulletinId = req.getInt("bulletinId");
		CmpBulletin o = this.cmpBulletinService.getCmpBulletin(bulletinId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		VelocityContext context = new VelocityContext();
		context.put("o", o);
		this.write(resp, "vm/e/bulletin.vm", context);
		return null;
	}

	public String list(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<CmpBulletin> list = this.cmpBulletinService.getCmpBulletinList(
				companyId, page.getBegin(), size);
		VelocityContext context = new VelocityContext();
		context.put("list", list);
		this.write(resp, "vm/e/bulletinlist.vm", context);
		return null;
	}
}