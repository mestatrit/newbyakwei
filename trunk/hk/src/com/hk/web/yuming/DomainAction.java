package com.hk.web.yuming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.yuming.Domain;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.DomainService;

@Component("/yuming/domain")
public class DomainAction implements Action {

	@Autowired
	private DomainService domainService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getServerName();
		if (name.startsWith("www")) {
			name = name.replaceFirst("www\\.", "");
		}
		Domain o = this.domainService.getDomainByName(name);
		if (o != null) {
			o.setDescr(DataUtil.toHtml(o.getDescr()));
		}
		req.setAttribute("o", o);
		return this.getPath("index");
	}

	private String getPath(String name) {
		return "/WEB-INF/yuming/" + name + ".jsp";
	}
}
