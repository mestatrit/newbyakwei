package com.hk.web.hk4.venue.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyService;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/cmptip/item")
public class CmpTipItemAction extends BaseAction {

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BombService bombService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long tipId = req.getLongAndSetAttr("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		long loginUserId = 0;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		CmpTipVo vo = CmpTipVo.createVo(cmpTip, loginUserId);
		req.setAttribute("vo", vo);
		Company company = this.companyService.getCompany(cmpTip.getCompanyId());
		req.setAttribute("company", company);
		if (loginUser != null) {
			Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
			req.setAttribute("bomber", bomber);
		}
		return this.getWeb4Jsp("cmptip/item.jsp");
	}

	/**
	 * 足迹页面 tips 更多
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		SimplePage page = req.getSimplePage(12);
		List<CmpTip> cmptiplist = this.cmpTipService.getCmpTipListByCompanyId(
				companyId, CmpTip.DONEFLG_DONE, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, cmptiplist);
		long loginUserId = 0;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList(cmptiplist,
				loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWeb4Jsp("/venue/tiplist.jsp");
	}

	/**
	 * 足迹页面 tips 更多
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String todolist(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		SimplePage page = req.getSimplePage(12);
		List<CmpTip> cmptiplist = this.cmpTipService.getCmpTipListByCompanyId(
				companyId, CmpTip.DONEFLG_TODO, page.getBegin(),
				page.getSize() + 1);
		this.processListForPage(page, cmptiplist);
		long loginUserId = 0;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList(cmptiplist,
				loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWeb4Jsp("/venue/tiptodolist.jsp");
	}
}