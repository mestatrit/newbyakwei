package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpZoneInfo;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserTool;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.UserToolConfig;
import com.hk.svr.pub.ZoneUtil;

@Component("/union/createcmp")
public class CmpUnionCreateCmpAction extends CmpUnionBaseAction {

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserToolConfig userToolConfig;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CompanyProcessor companyProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		if (cmpUnion == null) {
			return this.getNotFoundForward(resp);
		}
		if (!cmpUnion.isCanCreateCmp()) {
			req.setSessionText("view.cmpunion.cmpcreateflg0");
			return "r:/union/union.do?uid=" + uid;
		}
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getGroundCount() <= 0) {
			req.setSessionText("func.noenoughground");
			return "r:/union/union.do?uid=" + uid;
		}
		int pcityId = req.getInt("pcityId");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		String traffic = req.getString("traffic");
		long unionKindId = req.getLong("unionKindId");
		int kindId = req.getInt("kindId");
		Company o = new Company();
		o.setUid(uid);
		o.setUserId(loginUser.getUserId());
		o.setPcityId(pcityId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtml(addr));
		o.setIntro(DataUtil.toHtml(intro));
		o.setKindId(kindId);
		o.setCreaterId(loginUser.getUserId());
		o.setTraffic(DataUtil.toHtml(traffic));
		o.setUnionKindId(unionKindId);
		req.setAttribute("o", o);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/union/createcmp_tocreate2.do";
		}
		if (o.getUnionKindId() <= 0) {
			req.setText(String.valueOf(Err.COMPANY_UNIONKINDID_ERROR));
			return "/union/createcmp_tocreate2.do";
		}
		this.companyProcessor.createCompany(o, req.getRemoteAddr());
		userTool.addGroundCount(-this.userToolConfig
				.getCreateCompanyAddGroundCount());
		// 扣除相应的地皮数量
		this.userService.updateuserTool(userTool);
		req.setSessionText("func.company.create.success2");
		String url = DataUtil
				.urlEncoder("http://www.huoku.com/e/op/op.do?companyId="
						+ o.getCompanyId());
		return "r:/loginfromunion.do?return_url=" + url;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		User user = this.getLoginUser(req);
		if (user == null) {
			req.setSessionValue("forcreatecmp", 1);
			req.setSessionText("func.cmpunion.createcmp.alertinfo");
			return "r:/union/reg_toreg.do?uid=" + uid;
		}
		req.removeSessionvalue("forcreatecmp");
		return "/union/createcmp_tocreate1.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate1(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		if (name != null) {
			name = DataUtil.filterZoneName(name);
		}
		City city = zoneService.getCityLike(name);
		if (city != null) {
			return "r:/union/createcmp_tocreate2.do?uid=" + req.getLong("uid")
					+ "&pcityId=" + city.getCityId();
		}
		List<CmpZoneInfo> cmpzoneinfoList = companyService.getCmpZoneInfoList();
		req.setAttribute("cmpzoneinfoList", cmpzoneinfoList);
		req.reSetAttribute("s");
		req.setEncodeAttribute("name", name);
		return this.getUnionWapJsp("cmp/create1.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate2(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		int pcityId = req.getIntAndSetAttr("pcityId");
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getGroundCount() <= 0) {
			req.setSessionText("func.noenoughground");
			return "r:/union/union.do?uid=" + req.getLong("uid");
		}
		Company o = (Company) req.getAttribute("o");
		Company lastCmp = this.companyService.getLastCreateCompany(loginUser
				.getUserId());
		req.setAttribute("lastCmp", lastCmp);
		req.setAttribute("pcity", ZoneUtil.getPcity(pcityId));
		req.setAttribute("o", o);
		List<CmpUnionKind> cmpunionkindlist = this.cmpUnionService
				.getLastLevelCmpUnionKindListByUid(uid);
		req.setAttribute("cmpunionkindlist", cmpunionkindlist);
		return this.getUnionWapJsp("cmp/create2.jsp");
	}
}