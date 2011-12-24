package com.hk.web.company.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpFrLink;
import com.hk.bean.CmpInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpFrLinkService;
import com.hk.svr.CmpInfoService;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/authcmp/cmpinfo")
public class CmpInfoAction extends BaseAction {

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpFrLinkService cmpFrLinkService;

	/**
	 * 如果企业没有设置过网站域名，到添加新域名页面，如果设置过，就到修改域名页面
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		if (cmpInfo == null) {
			return "r:/e/op/authcmp/cmpinfo_toadd.do?companyId=" + companyId;
		}
		req.setAttribute("cmpInfo", cmpInfo);
		req.setAttribute("companyId", companyId);
		return this.getWapJsp("e/cmpinfo/cmpinfo.jsp");
	}

	/**
	 * 创建企业域名信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("companyId");
		return this.getWapJsp("e/cmpinfo/add.jsp");
	}

	/**
	 * 创建企业域名信息，有了域名信息，就可以通过企业域名访问企业网站
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String domain = req.getString("domain");
		int language = req.getInt("language");
		CmpInfo cmpInfo = new CmpInfo();
		cmpInfo.setCompanyId(companyId);
		cmpInfo.setDomain(domain);
		cmpInfo.setLanguage(language);
		if (!this.cmpInfoService.createCmpInfo(cmpInfo)) {
			req.setAttribute("cmpInfo", cmpInfo);
			req.setText("func.domain_already_exist");
			return "/e/op/authcmp/cmpinfo_toadd.do";
		}
		// 添加默认友情链接
		CmpFrLink cmpFrLink = new CmpFrLink();
		cmpFrLink.setCompanyId(companyId);
		cmpFrLink.setName("博色网");
		cmpFrLink.setUrl("www.bosee.cn");
		this.cmpFrLinkService.createCmpFrLink(cmpFrLink);
		req.setSessionText("func.domain_create_ok");
		return "r:/e/op/authcmp/cmpinfo.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		req.setAttribute("cmpInfo", cmpInfo);
		req.setAttribute("companyId", companyId);
		return this.getWapJsp("e/cmpinfo/edit.jsp");
	}

	/**
	 * 修改域名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String domain = req.getString("domain");
		int language = req.getInt("language");
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		cmpInfo.setDomain(domain);
		cmpInfo.setLanguage(language);
		if (!this.cmpInfoService.updateCmpInfo(cmpInfo)) {
			req.setAttribute("domain", domain);
			req.setText("func.domain_already_exist");
			return "/e/op/authcmp/cmpinfo_toedit.do";
		}
		req.setSessionText("func.domain_update_ok");
		return "r:/e/op/authcmp/cmpinfo.do?companyId=" + companyId;
	}
}