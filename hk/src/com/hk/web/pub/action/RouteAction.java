package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpUnion;
import com.hk.bean.yuming.Domain;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.DomainService;
import com.hk.web.cmpunion.action.CmpUnionUtil;

/**
 * 这块需要重写，尤其是epp的wap版，去掉api解析方式，
 * 
 * @author akwei
 */
@Component("/sys/route")
public class RouteAction extends BaseAction {

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private DomainService domainService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String domain = req.getServerName();
		if (domain.startsWith("www")) {
			domain = domain.replaceFirst("www\\.", "");
		}
		// 域名买卖
		Domain o = this.domainService.getDomainByName(domain);
		if (o != null) {
			o.setDescr(DataUtil.toHtml(o.getDescr()));
			req.setAttribute("o", o);
			return "/WEB-INF/yuming/index.jsp";
		}
		// 域名买卖 end
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnionByDomain(domain);
		if (cmpUnion == null) {
			CmpInfo cmpInfo = this.cmpInfoService.getCmpInfoByDomain(domain);
			if (cmpInfo == null) {
				return this.getNotFoundForward(resp);
			}
			return "/epp/index.do?companyId=" + cmpInfo.getCompanyId()
					+ "&wapflg=" + req.getInt("wapflg");
		}
		if (cmpUnion.isLimit()) {
			return this.getNotFoundForward(resp);
		}
		if (cmpUnion.isStop()) {
			return this.getNotFoundForward(resp);
		}
		CmpUnionUtil.getLoginUser(req);
		return "/union/union.do?uid=" + cmpUnion.getUid();
	}
}