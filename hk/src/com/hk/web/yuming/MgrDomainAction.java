package com.hk.web.yuming;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.yuming.Domain;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.DomainService;

@Component("/yuming/ymmgr/domain")
public class MgrDomainAction implements Action {

	@Autowired
	private DomainService domainService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isRootLogin(req)) {
			return "r:/yuming/login.do";
		}
		int domainid = req.getIntAndSetAttr("domainid");
		Domain o = this.domainService.getDomainById(domainid);
		req.setAttribute("o", o);
		return this.getPath("view");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isRootLogin(req)) {
			return "r:/yuming/login.do";
		}
		List<Domain> list = this.domainService.getDomainList();
		req.setAttribute("list", list);
		return this.getPath("list");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isRootLogin(req)) {
			return "r:/yuming/login.do";
		}
		if (this.isForwardPage(req)) {
			return this.getPath("create");
		}
		Domain o = new Domain();
		o.setName(req.getString("name"));
		o.setDescr(req.getString("descr"));
		if (DataUtil.isEmpty(o.getName()) || DataUtil.isEmpty(o.getDescr())) {
			return "r:/yuming/ymmgr/domain_list.do";
		}
		o.setName(o.getName().replaceFirst("www\\.", ""));
		this.domainService.createDomain(o);
		return "r:/yuming/ymmgr/domain_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isRootLogin(req)) {
			return "r:/yuming/login.do";
		}
		int domainid = req.getIntAndSetAttr("domainid");
		Domain o = this.domainService.getDomainById(domainid);
		if (this.isForwardPage(req)) {
			req.setAttribute("o", o);
			return this.getPath("update");
		}
		o.setName(req.getString("name"));
		o.setDescr(req.getString("descr"));
		if (DataUtil.isEmpty(o.getName()) || DataUtil.isEmpty(o.getDescr())) {
			return "r:/yuming/ymmgr/domain_list.do";
		}
		o.setName(o.getName().replaceFirst("www\\.", ""));
		this.domainService.updateDomain(o);
		return "r:/yuming/ymmgr/domain_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		if (!this.isRootLogin(req)) {
			return "r:/yuming/login.do";
		}
		int domainid = req.getIntAndSetAttr("domainid");
		this.domainService.deleteDomain(domainid);
		return "r:/yuming/ymmgr/domain_list.do";
	}

	private String getPath(String name) {
		return "/WEB-INF/yuming/admin/" + name + ".jsp";
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}

	private boolean isRootLogin(HkRequest req) {
		Boolean rootlogin = (Boolean) req.getSessionValue("rootlogin");
		if (rootlogin == null || rootlogin.booleanValue() == false) {
			return false;
		}
		return true;
	}
}
