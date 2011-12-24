package com.hk.web.company.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AuthCompany;
import com.hk.bean.BizCircle;
import com.hk.bean.BuildingTag;
import com.hk.bean.City;
import com.hk.bean.CmpAdminHkbLog;
import com.hk.bean.CmpHkbLog;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpInfoTml;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpSvrCnf;
import com.hk.bean.Company;
import com.hk.bean.CompanyAward;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.CompanyMoney;
import com.hk.bean.CompanyTag;
import com.hk.bean.HkbLog;
import com.hk.bean.Province;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserTool;
import com.hk.bean.ZoneInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BizCircleService;
import com.hk.svr.BuildingTagService;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.CmpSvrCnfService;
import com.hk.svr.CompanyService;
import com.hk.svr.CompanyTagService;
import com.hk.svr.MsgService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.company.BizCircleValidate;
import com.hk.svr.company.BuildingTagValidate;
import com.hk.svr.company.CompanyTagValidate;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.CreateCompanyResult;
import com.hk.svr.pub.CmpInfoTmlUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.UserToolConfig;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.IpZoneInfo;

/**
 * 系统管理员对企业的管理
 * 
 * @author akwei
 */
@Component("/e/admin/admin")
public class AdminAction extends BaseAction {

	private int size = 20;

	@Autowired
	private CompanyTagService companyTagService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private BizCircleService bizCircleService;

	@Autowired
	private BuildingTagService buildingTagService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserToolConfig userToolConfig;

	@Autowired
	private MsgService msgService;

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private CmpSvrCnfService cmpSvrCnfService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	@Autowired
	private CmpInfoService cmpInfoService;

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
	public String findUserZone(HkRequest req, HkResponse resp) throws Exception {
		IpZoneInfo info = this.getIpZoneInfo(req);
		int cityId = info.getCityId();
		int provinceId = info.getProvinceId();
		if (cityId == 0 && provinceId == 0) {
			long userId = this.getLoginUser(req).getUserId();
			User user = this.userService.getUser(userId);
			cityId = user.getPcityId();
		}
		String f = req.getString("f");
		if (cityId == 0) {
			return "r:/e/admin/admin_findcity.do?f=" + f;
		}
		String method = null;
		if (f.equals("bizcle")) {
			method = "toaddbizcle";
		}
		else if (f.equals("build")) {
			method = "toaddbuild";
		}
		if (f.equals("bizclelist")) {
			method = "bizclelist";
		}
		if (f.equals("buildlist")) {
			method = "buildlist";
		}
		return "r:/e/admin/admin_" + method + ".do?cityId=" + cityId
				+ "&provinceId=" + provinceId;
	}

	// /**
	// * 为企业充值
	// *
	// * @param req
	// * @param resp
	// * @return
	// * @throws Exception
	// */
	// public String toaddmoney(HkRequest req, HkResponse resp) throws Exception
	// {
	// long companyId = req.getLong("companyId");
	// Company company = this.companyService.getCompany(companyId);
	// req.setAttribute("company", company);
	// req.reSetAttribute("companyId");
	// // req.reSetReturnUrl();
	// return "/WEB-INF/page/e/admin/addmoney.jsp";
	// }
	private Date parseDate(String v, String pattern) {
		if (DataUtil.isEmpty(v)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date d = sdf.parse(v);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 0);
			return c.getTime();
		}
		catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 为企业充值
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addmoney(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int money = req.getInt("money");
		String endTime = req.getString("endTime");
		CompanyMoney o = new CompanyMoney();
		o.setCompanyId(companyId);
		o.setMoney(money);
		o.setOpuserId(this.getLoginUser(req).getUserId());
		o.setEndTime(this.parseDate(endTime, "yyyy-mm-dd"));
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/admin/admin_toaddmoney.do";
		}
		this.companyService.createCompanyMoney(o);
		req.setSessionText("func.company.addmoneyok");
		// 是否需要给企业持有人发送ms,sms
		return "r:/e/admin/admin_addmoneyok.do?companyId=" + companyId
				+ "&sysId=" + o.getSysId() + "&" + HkUtil.RETURN_URL + "="
				+ DataUtil.urlEncoder(req.getReturnUrl());// 到充值成功页面
	}

	/**
	 * 为企业充值成功，到一个结果页面显示充值数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addmoneyok(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long sysId = req.getLong("sysId");
		Company company = this.companyService.getCompany(companyId);
		CompanyMoney companyMoney = this.companyService.getCompanyMoney(sysId);
		req.setAttribute("company", company);
		req.setAttribute("companyMoney", companyMoney);
		req.setAttribute("companyId", companyId);
		req.setAttribute("sysId", sysId);
		// req.reSetReturnUrl();
		return "/WEB-INF/page/e/admin/addmoneyok.jsp";
	}

	/**
	 * 奖励企业足迹创建者
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String award(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		CompanyMoney companyMoney = this.companyService
				.getFirstCompanyMoney(companyId);
		if (companyMoney != null) {
			int awardhkb = (int) (companyMoney.getMoney() * 0.1);
			// 奖励酷币
			HkbLog hkbLog = HkbLog.create(company.getCreaterId(),
					HkLog.AWARD_CREATECOMPANY, companyId, awardhkb);
			this.userService.addHkb(hkbLog);
			// 更新奖励状态
			CompanyAward o = this.companyService.getCompanyAward(companyId);
			o.setAwardhkb(awardhkb);
			o.setAwardStatus(CompanyAward.AWARDSTATUS_Y);
			o.setMoney(companyMoney.getMoney());
			this.companyService.updateCompanyAward(o);
			// 发送msg给企业足迹创建人
			String msg = req.getText("func.msg.award_company", company
					.getName(), awardhkb + "");
			this.msgService.sendMsg(company.getCreaterId(), HkSvrUtil
					.getServiceUserId(), msg);
			req.setSessionText("op.exeok");
		}
		return "r:/e/admin/admin_awarduser.do?status=" + req.getByte("status");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String awarduser(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		byte status = req.getByte("status", (byte) 0);
		List<CompanyAward> list = this.companyService.getCompanyAwardList(
				status, page.getBegin(), size);
		page.setListSize(list.size());
		List<Long> createrIdList = new ArrayList<Long>();
		List<Long> companyIdList = new ArrayList<Long>();
		for (CompanyAward o : list) {
			createrIdList.add(o.getCreaterId());
			companyIdList.add(o.getCompanyId());
		}
		Map<Long, User> usermap = this.userService
				.getUserMapInId(createrIdList);
		Map<Long, Company> companymap = this.companyService
				.getCompanyMapInId(companyIdList);
		for (CompanyAward o : list) {
			o.setCreater(usermap.get(o.getCreaterId()));
			o.setCompany(companymap.get(o.getCompanyId()));
		}
		req.setAttribute("list", list);
		req.setAttribute("status", status);
		return "/WEB-INF/page/e/admin/awarduser.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditbuild(HkRequest req, HkResponse resp) throws Exception {
		int tid = req.getInt("tid");
		BuildingTag o = (BuildingTag) req.getAttribute("o");
		if (o == null) {
			o = this.buildingTagService.getBuildingTag(tid);
		}
		req.setAttribute("o", o);
		req.setAttribute("tid", tid);
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/admin/editbuild.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editbuild(HkRequest req, HkResponse resp) throws Exception {
		int tid = req.getInt("tid");
		String name = req.getString("name");
		BuildingTag o = this.buildingTagService.getBuildingTag(tid);
		o.setTagId(tid);
		o.setName(DataUtil.toHtmlRow(name));
		int code = BuildingTagValidate.validateBuildingTag(o);
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/e/admin/admin_toeditbuild.do?tid=" + tid;
		}
		req.setSessionMessage(req.getText("op.exeok"));
		this.buildingTagService.updateBuildingTag(o);
		return "r:/e/admin/admin_buildlist.do?cityId=" + req.getInt("cityId")
				+ "&provinceId=" + req.getInt("provinceId");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocfmdelbuild(HkRequest req, HkResponse resp)
			throws Exception {
		req.reSetAttribute("tid");
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/admin/cfmdelbuild.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cfmdelbuild(HkRequest req, HkResponse resp) throws Exception {
		int tid = req.getInt("tid");
		if (req.getString("ok") != null) {
			req.setSessionMessage(req.getText("op.exeok"));
			this.buildingTagService.deleteBuildingTag(tid);
		}
		return "r:/e/admin/admin_buildlist.do?cityId=" + req.getInt("cityId")
				+ "&provinceId=" + req.getInt("provinceId");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String buildlist(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		String name = req.getString("name");
		List<BuildingTag> list = this.buildingTagService.getBuildingTagList(
				name, cityId, provinceId);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		this.setZoneInfo(req);
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/admin/buildlist.jsp";
	}

	private void setZoneInfo(HkRequest req) {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		City city = ZoneUtil.getCity(cityId);
		Province province = null;
		if (city == null) {
			province = ZoneUtil.getProvince(provinceId);
		}
		req.setAttribute("city", city);
		req.setAttribute("province", province);
		req.setAttribute("cityId", cityId);
		req.setAttribute("provinceId", provinceId);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddbuild(HkRequest req, HkResponse resp) throws Exception {
		List<BuildingTag> list = this.buildingTagService.getBuildingTagList(
				null, req.getInt("cityId"), req.getInt("provinceId"));
		req.setAttribute("list", list);
		this.setZoneInfo(req);
		if (req.getInt("cityId") == 0 && req.getInt("provinceId") == 0) {
			return "r:/e/admin/admin_findcity.do?f=build";
		}
		return "/WEB-INF/page/e/admin/addbuild.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addbuild(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		String name = req.getString("name");
		BuildingTag o = new BuildingTag();
		o.setCityId(cityId);
		o.setName(name);
		o.setProvinceId(provinceId);
		req.setAttribute("o", o);
		int code = BuildingTagValidate.validateBuildingTag(o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/e/admin/admin_toaddbuild.do";
		}
		if (this.buildingTagService.createBuildingTag(o)) {
			req.setSessionMessage(req.getText("op.exeok"));
		}
		else {
			req.setSessionMessage(req.getText("buildingtag.already.exist"));
		}
		return "r:/e/admin/admin_toaddbuild.do?cityId=" + cityId
				+ "&provinceId=" + provinceId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocfmdelbizcle(HkRequest req, HkResponse resp)
			throws Exception {
		req.reSetAttribute("circleId");
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/admin/cfmdelbizcle.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cfmdelbizcle(HkRequest req, HkResponse resp) throws Exception {
		int circleId = req.getInt("circleId");
		if (req.getString("ok") != null) {
			req.setSessionMessage(req.getText("op.exeok"));
			this.bizCircleService.deleteBizCircle(circleId);
		}
		return "r:/e/admin/admin_bizclelist.do?cityId=" + req.getInt("cityId")
				+ "&provinceId=" + req.getInt("provinceId");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditbizcle(HkRequest req, HkResponse resp) throws Exception {
		int circleId = req.getInt("circleId");
		BizCircle o = (BizCircle) req.getAttribute("o");
		if (o == null) {
			o = this.bizCircleService.getBizCircle(circleId);
		}
		req.setAttribute("o", o);
		req.setAttribute("circleId", circleId);
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/admin/editbizcle.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editbizcle(HkRequest req, HkResponse resp) throws Exception {
		int circleId = req.getInt("circleId");
		String name = req.getString("name");
		BizCircle o = this.bizCircleService.getBizCircle(circleId);
		o.setCircleId(circleId);
		o.setName(DataUtil.toHtmlRow(name));
		int code = BizCircleValidate.validateBizCircle(o);
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			return "/e/admin/admin_toeditbizcle.do?circleId=" + circleId;
		}
		req.setSessionMessage(req.getText("op.exeok"));
		this.bizCircleService.updateBizCircle(o);
		return "r:/e/admin/admin_bizclelist.do?cityId=" + req.getInt("cityId")
				+ "&provinceId=" + req.getInt("provinceId");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String bizclelist(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<BizCircle> list = this.bizCircleService.getBizCircleList(name,
				cityId, provinceId);
		page.setListSize(list.size());
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		this.setZoneInfo(req);
		return "/WEB-INF/page/e/admin/bizclelist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String opcity(HkRequest req, HkResponse resp) throws Exception {
		String f = req.getString("f");
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		long userId = this.getLoginUser(req).getUserId();
		this.userService.updateUserPcityId(userId, cityId);
		if (f.equals("bizcle")) {
			return "r:/e/admin/admin_toaddbizcle.do?cityId=" + cityId
					+ "&provinceId=" + provinceId;
		}
		if (f.equals("build")) {
			return "r:/e/admin/admin_toaddbuild.do?cityId=" + cityId
					+ "&provinceId=" + provinceId;
		}
		if (f.equals("bizclelist")) {
			return "r:/e/admin/admin_bizclelist.do?cityId=" + cityId
					+ "&provinceId=" + provinceId;
		}
		if (f.equals("buildlist")) {
			return "r:/e/admin/admin_buildlist.do?cityId=" + cityId
					+ "&provinceId=" + provinceId;
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findcity(HkRequest req, HkResponse resp) throws Exception {
		String city = req.getString("city");
		if (city != null) {
			city = DataUtil.filterZoneName(city);
			SimplePage page = req.getSimplePage(size);
			List<City> clist = this.zoneService.getCityList(city, page
					.getBegin(), size);
			page.setListSize(clist.size());
			if (clist.size() == 0) {// 如果城市列表中不存在,就到省表里面查询
				List<Province> plist = this.zoneService.getProvinceList(city);
				req.setAttribute("plist", plist);
				// if (plist.size() > 0) {
				// clist = this.zoneService.getCityList(plist.iterator()
				// .next().getProvinceId());
				// }
			}
			req.setAttribute("clist", clist);
			req.setEncodeAttribute("city", city);
		}
		req.reSetAttribute("f");
		return "/WEB-INF/page/e/admin/findcity.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddbizcle(HkRequest req, HkResponse resp) throws Exception {
		Object o = req.getSessionValue("o");
		req.removeSessionvalue("o");
		req.setAttribute("o", o);
		if (req.getInt("cityId") == 0 && req.getInt("provinceId") == 0) {
			return "r:/e/admin/admin_findcity.do?f=bizcle";
		}
		this.setZoneInfo(req);
		List<BizCircle> list = this.bizCircleService.getBizCircleList(null, req
				.getInt("cityId"), req.getInt("provinceId"));
		req.setAttribute("list", list);
		return "/WEB-INF/page/e/admin/addbizcle.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addbizcle(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		BizCircle o = new BizCircle();
		o.setName(DataUtil.toHtmlRow(name));
		o.setCityId(cityId);
		o.setProvinceId(provinceId);
		int code = BizCircleValidate.validateBizCircle(o);
		if (code != Err.SUCCESS) {
			req.setSessionValue("o", o);
			return "r:/e/admin/admin_toaddbizcle.do?cityId=" + cityId
					+ "&provinceId=" + provinceId;
		}
		if (this.bizCircleService.createBizCircle(o)) {
			req.setSessionMessage(req.getText("op.exeok"));
		}
		return "r:/e/admin/admin_toaddbizcle.do?cityId=" + cityId
				+ "&provinceId=" + provinceId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocfmdeltag(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("tid");
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		req.reSetAttribute("s_parentId");
		return "/WEB-INF/page/e/admin/cfm_deltag.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cfmdeltag(HkRequest req, HkResponse resp) throws Exception {
		int tagId = req.getInt("tid");
		req.reSetAttribute("s_parentId");
		if (req.getString("ok") != null) {
			this.companyTagService.deleteCompanyTag(tagId);
			req.setSessionMessage(req.getText("op.exeok"));
		}
		return "r:/e/admin/admin_tlist.do?" + req.getQueryString();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedittag(HkRequest req, HkResponse resp) throws Exception {
		int tid = req.getInt("tid");
		int kindId = req.getInt("kindId");
		CompanyTag o = (CompanyTag) req.getAttribute("o");
		if (o == null) {
			o = this.companyTagService.getCompanyTag(tid);
		}
		req.setAttribute("o", o);
		req.setAttribute("tid", tid);
		req.reSetAttribute("s_parentId");
		req.reSetAttribute("kindId");
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("companyKind", companyKind);
		return "/WEB-INF/page/e/admin/edittag.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edittag(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		int kindId = req.getInt("kindId");
		int tid = req.getInt("tid");
		req.reSetAttribute("s_parentId");
		CompanyTag o = this.companyTagService.getCompanyTag(tid);
		if (o != null) {
			o.setName(name);
			o.setKindId(kindId);
			req.setAttribute("o", o);
			int code = CompanyTagValidate.validateTag(name, kindId);
			if (code != Err.SUCCESS) {
				req.setText(code + "");
				return "/e/admin/admin_toaddtag.do";
			}
			if (this.companyTagService.updateCompanyTag(o)) {
				req.setSessionMessage(req.getText("op.exeok"));
				return "r:/e/admin/admin_tlist.do?s_parentId="
						+ req.getInt("s_parentId") + "&kindId=" + kindId;
			}
			req.setText("company.tag.already.exist");
			return "/e/admin/admin_toedittag.do";
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddtag(HkRequest req, HkResponse resp) throws Exception {
		int kindId = req.getInt("kindId");
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("companyKind", companyKind);
		req.reSetAttribute("s_parentId");
		req.setAttribute("kindId", kindId);
		return "/WEB-INF/page/e/admin/addtag.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtag(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		int kindId = req.getInt("kindId");
		int code = CompanyTagValidate.validateTag(name, kindId);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/admin/admin_toaddtag.do";
		}
		if (this.companyTagService.createCompanyTag(name, kindId)) {
			req.setText("op.exeok");
			return "r:/e/admin/admin_tlist.do?s_parentId="
					+ req.getInt("s_parentId") + "&kindId=" + kindId;
		}
		req.setText("company.tag.already.exist");
		return "/e/admin/admin_toaddtag.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tlist(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		int kindId = req.getInt("kindId");
		SimplePage page = req.getSimplePage(size);
		List<CompanyTag> tlist = this.companyTagService.getCompanyTagList(name,
				kindId, page.getBegin(), size);
		page.setListSize(tlist.size());
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(kindId);
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("tlist", tlist);
		req.setAttribute("kindId", kindId);
		req.setEncodeAttribute("name", name);
		List<CompanyKind> kindlist = CompanyKindUtil.getCompanKindList();
		req.setAttribute("kindlist", kindlist);
		req.reSetAttribute("s_parentId");
		return "/WEB-INF/page/e/admin/tlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		return "r:/e/admin/admin_clist.do?" + req.getQueryString();
	}

	/**
	 * 企业管理列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clist(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		String zoneName = req.getString("zoneName");
		if (!DataUtil.isEmpty(zoneName)) {
			zoneName = DataUtil.filterZoneName(zoneName);
		}
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		if (cityId == 0 && provinceId == 0) {
			ZoneInfo info = this.zoneService.getZoneInfoByZoneName(zoneName);
			if (info != null) {
				cityId = info.getCityId();
				provinceId = info.getProvinceId();
			}
		}
		byte status = req.getByte("status", (byte) -100);
		byte freezeflg = req.getByte("freezeflg", (byte) -1);
		SimplePage page = req.getSimplePage(20);
		List<Company> list = this.companyService.getCompanyListByCdn(name,
				status, freezeflg, cityId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setAttribute("cityId", cityId);
		req.setAttribute("provinceId", provinceId);
		req.setEncodeAttribute("name", name);
		req.setEncodeAttribute("zoneName", zoneName);
		req.setAttribute("status", status);
		req.setReturnUrl("/e/admin/admin_clist.do?name="
				+ DataUtil.urlEncoder(name) + "&cityId=" + cityId
				+ "&provinceId=" + provinceId + "&status=" + status);
		return "/WEB-INF/page/e/admin/clist.jsp";
	}

	/**
	 * 查看足迹详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String viewcmp(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CompanyVo vo = CompanyVo.createVo(company);
		req.setAttribute("vo", vo);
		if (company.getUserId() > 0) {
			User user = this.userService.getUser(company.getUserId());
			req.setAttribute("user", user);
		}
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		req.setAttribute("cmpSvrCnf", cmpSvrCnf);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		req.setAttribute("cmpOtherWebInfo", cmpOtherWebInfo);
		return "/WEB-INF/page/e/admin/viewcmp.jsp";
	}

	/**
	 * 到修改企业状态页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditcompanystatus(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		// req.reSetReturnUrl();
		return "/WEB-INF/page/e/admin/editcompanystatus.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditstopflg(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		// req.reSetReturnUrl();
		return "/WEB-INF/page/e/admin/editstopflg.jsp";
	}

	/**
	 * 修改企业认领状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editstopflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		byte stopflg = req.getByte("stopflg");
		Company o = this.companyService.getCompany(companyId);
		o.setStopflg(stopflg);
		this.companyService.updateCompany(o);
		req.setSessionText("op.exeok");
		return "r:" + req.getReturnUrl();
	}

	/**
	 * 到修改企业状态页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editcompanystatus(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		byte status = req.getByte("status");
		Company o = this.companyService.getCompany(companyId);
		if (o != null) {
			if (Company.isStatus(status)) {
				if (o.getCompanyStatus() != status) {
					this.companyService.updateCompanyStatus(companyId, status);
					UserTool userTool = this.userService.checkUserTool(o
							.getCreaterId());
					if (status == Company.COMPANYSTATUS_NORMAL) {
						userTool.addGroundCount(this.userToolConfig
								.getCompanyStatusNormalAddGroundCount());
					}
					// else if (status == Company.COMPANYSTATUS_GOOD) {
					// userTool.addGroundCount(this.userToolConfig
					// .getCompanyStatusGoodAddGroundCount());
					// }
					// else if (status == Company.COMPANYSTATUS_VERYGOOD) {
					// userTool.addGroundCount(this.userToolConfig
					// .getCompanyStatusVeryGoodAddGroundCount());
					// }
					// else if (status == Company.COMPANYSTATUS_FREEZE) {
					// userTool.addGroundCount(-this.userToolConfig
					// .getCompanyStatusFreezeGroundCount());
					// }
					this.userService.updateuserTool(userTool);
					req.setSessionText("op.exeok");
				}
			}
			// else if (status == Company.COMPANYSTATUS_CANCELAUTH) {//
			// 取消认领，为审核通过状态
			// o.setUserId(0);
			// o.setCompanyStatus(Company.COMPANYSTATUS_CHECKED);
			// req.setSessionText("func.updateinfook");
			// this.companyService.updateCompany(o);
			// }
		}
		return "r:" + req.getReturnUrl();
	}

	/**
	 * 设置足迹冻结状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgfreeze(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.companyService.chgFreezeflgCompany(companyId, Company.FREEZEFLG_Y);
		req.setSessionText("func.company.freeze.chg" + Company.FREEZEFLG_Y);
		return "r:" + req.getReturnUrl();
	}

	/**
	 * 设置足迹冻结状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgunfreeze(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.companyService.chgFreezeflgCompany(companyId, Company.FREEZEFLG_N);
		req.setSessionText("func.company.freeze.chg" + Company.FREEZEFLG_N);
		return "r:" + req.getReturnUrl();
	}

	/**
	 * 认领企业列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String authlist(HkRequest req, HkResponse resp) {
		SimplePage page = req.getSimplePage(size);
		byte mainStatus = req.getByte("mainStatus", (byte) -10);
		String name = req.getString("name");
		List<AuthCompany> list = this.companyService.getAuthCompanyList(name,
				mainStatus, page.getBegin(), size);
		page.setListSize(list.size());
		List<AuthCompanyVo> authcompanyvolist = AuthCompanyVo
				.createVoList(list);
		req.setAttribute("authcompanyvolist", authcompanyvolist);
		req.setAttribute("mainStatus", mainStatus);
		req.setEncodeAttribute("name", name);
		return "/WEB-INF/page/e/admin/authlist.jsp";
	}

	/**
	 * 认领企业详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String authcompany(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		AuthCompany authCompany = (AuthCompany) req.getAttribute("authCompany");
		if (authCompany == null) {
			authCompany = this.companyService.getAuthCompany(sysId);
		}
		if (authCompany == null) {
			return null;
		}
		AuthCompanyVo vo = AuthCompanyVo.createVo(authCompany);
		req.setAttribute("vo", vo);
		req.setEncodeAttribute("sysId", sysId);
		return "/WEB-INF/page/e/admin/authcompany.jsp";
	}

	/**
	 * 到修改企业状态页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toeditcheckstatus(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		AuthCompany authCompany = (AuthCompany) req.getAttribute("authCompany");
		if (authCompany == null) {
			authCompany = this.companyService.getAuthCompany(sysId);
		}
		if (authCompany == null) {
			return null;
		}
		User user = this.userService.getUser(authCompany.getUserId());
		Company company = this.companyService.getCompany(authCompany
				.getCompanyId());
		req.setAttribute("authCompany", authCompany);
		req.setAttribute("user", user);
		req.setAttribute("company", company);
		req.setAttribute("sysId", sysId);
		return "/WEB-INF/page/e/admin/editcheckstatus.jsp";
	}

	/**
	 * 修改企业认领状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editcheckstatus(HkRequest req, HkResponse resp) {
		long sysId = req.getLong("sysId");
		byte mainStatus = req.getByte("mainStatus");
		AuthCompany authCompany = this.companyService.getAuthCompany(sysId);
		User user = this.userService.getUser(authCompany.getUserId());
		Company company = this.companyService.getCompany(authCompany
				.getCompanyId());
		if (user == null) {
			return null;
		}
		if (company == null) {
			return null;
		}
		if (req.getString("ok") != null) {
			mainStatus = AuthCompany.MAINSTATUS_CHECKED;
		}
		else {// 审核不通过，
			mainStatus = AuthCompany.MAINSTATUS_CHECKFAIL;
		}
		authCompany.setMainStatus(mainStatus);
		req.setAttribute("authCompany", authCompany);
		int code = authCompany.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/admin/admin_authcompany.do";
		}
		if (mainStatus == AuthCompany.MAINSTATUS_CHECKED) {
			company.setCmpflg(req.getByte("cmpflg"));
			this.companyService.updateCompany(company);
			this.companyService.checkAuthCompany(authCompany);// 认领企业，把userid置为有效id
			// UserOtherInfo info = this.userService.getUserOtherInfo(user
			// .getUserId());
			// this.msgService.sendMsg(user.getUserId(), HkSvrUtil
			// .getServiceUserId(), req.getText("msg.authcompany_ok",
			// company.getName()));
			// this.smsClient.sendIgnoreError(info.getMobile(), req.getText(
			// "sms.authcompany_ok", company.getName()));
		}
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/e/admin/admin_authcompany.do?sysId=" + sysId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findcmp(HkRequest req, HkResponse resp) {
		int fn = req.getInt("fn");
		int f = req.getInt("f");
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			Company company = this.companyService.getCompanyByName(name);
			req.setAttribute("company", company);
		}
		else {
			long companyId = req.getLong("companyId");
			Company company = this.companyService.getCompany(companyId);
			req.setAttribute("company", company);
		}
		req.setAttribute("f", f);
		req.setAttribute("fn", fn);
		return "/WEB-INF/page/e/admin/findcmp.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addhkb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int addCount = req.getInt("addCount");
		double money = req.getDouble("money");
		byte addflg = req.getByte("addflg");
		String remark = req.getString("remark");
		remark = DataUtil.toHtml(remark);
		CmpAdminHkbLog o = new CmpAdminHkbLog();
		o.setCompanyId(companyId);
		o.setAddflg(addflg);
		o.setMoney(money);
		o.setRemark(remark);
		o.setAddCount(addCount);
		o.setOpuserId(this.getLoginUser(req).getUserId());
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/e/admin/admin_findcmp.do";
		}
		CmpHkbLog cmpHkbLog = CmpHkbLog.create(companyId, HkLog.CMP_ADD_HKB, 0,
				addCount);
		this.companyService.addHkb(cmpHkbLog);
		this.companyService.createCmpAdminHkbLog(o);
		return "r:/e/admin/admin_addhkbok.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addhkbok(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		req.setText("func.company.hkb.addcmphkbok");
		return "/WEB-INF/page/e/admin/addhkbok.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delcmp(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			long companyId = req.getLongAndSetAttr("companyId");
			Company company = this.companyService.getCompany(companyId);
			req.setAttribute("company", company);
			return this.getWapJsp("e/admin/delcmp_cfm.jsp");
		}
		if (req.getString("ok") != null) {
			long companyId = req.getLong("companyId");
			this.companyService.deleteCompany(companyId);
		}
		String return_url = req.getReturnUrl();
		if (!DataUtil.isEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/e/admin/admin_clist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkok(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		this.companyService.updateCompanyStatus(companyId,
				Company.COMPANYSTATUS_CHECKED);
		this.setOpFuncSuccessMsg(req);
		String return_url = req.getReturnUrl();
		if (!DataUtil.isEmpty(return_url)) {
			return "r:" + return_url;
		}
		return "r:/e/admin/admin_clist.do";
	}

	/**
	 * 管理员创建企业的快速通道
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createcmp(HkRequest req, HkResponse resp) {
		if (req.getInt("ch") == 0) {
			Company o = (Company) req.getAttribute("o");
			String zoneName = req.getString("zoneName");
			if (zoneName == null) {
				zoneName = (String) req.getSessionValue("zoneName");
				req.removeSessionvalue("zoneName");
				if (zoneName == null) {
					zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
				}
				if (zoneName == null) {
					User loginUser = this.getLoginUser(req);
					City city = ZoneUtil.getCity(loginUser.getPcityId());
					if (city != null) {
						zoneName = city.getCity();
					}
				}
			}
			req.setAttribute("zoneName", zoneName);
			if (o == null) {
				long tmpoid = req.getLong("tmpoid");
				if (tmpoid > 0) {// 从临时数据中恢复
					TmpData tmpData = this.tmpDataService.getTmpData(tmpoid);
					if (tmpData != null
							&& tmpData.getDatatype() == TmpData.DATATYPE_CMP) {
						Map<String, String> map = JsonUtil
								.getMapFromJson(tmpData.getData());
						o = new Company();
						o.setName(map.get("name"));
						o.setTel(map.get("tel"));
						o.setAddr(map.get("addr"));
					}
				}
			}
			req.setAttribute("o", o);
			String name = null;
			if (o != null) {
				name = o.getName();
			}
			else {
				name = req.getString("name");
			}
			req.setAttribute("name", name);
			return this.getWapJsp("e/admin/createcmp.jsp");
		}
		User loginUser = this.getLoginUser(req);
		String zoneName = req.getStringAndSetAttr("zoneName");
		Company o = new Company();
		o.setName(req.getHtmlRow("name"));
		o.setCreaterId(loginUser.getUserId());
		o.setCompanyStatus(Company.COMPANYSTATUS_CHECKED);
		o.setUserId(loginUser.getUserId());
		req.setAttribute("o", o);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/e/admin/admin_createcmp.do";
		}
		CreateCompanyResult createCompanyResult = this.companyProcessor
				.createCompanyForWap(o, zoneName, req.getRemoteAddr());
		if (createCompanyResult.getErrorCode() != Err.SUCCESS) {
			if (createCompanyResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (createCompanyResult.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ createCompanyResult.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil
									.urlEncoder("/e/admin/admin_createcmp.do"
											+ "?tmpoid="
											+ createCompanyResult.getOid());
				}
			}
			req.setText(String.valueOf(createCompanyResult.getErrorCode()));
			return "/e/admin/admin_createcmp.do";
		}
		req.setText("func.company.create.success");
		return "r:/e/admin/admin_viewcmp.do?companyId=" + o.getCompanyId();
	}

	/**
	 * 把企业转移给某用户的查找，可以根据nickname email mobile进行查找人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmpuser(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (req.getInt("ch") == 0) {
			return this.getWapJsp("e/admin/chgcmpuser.jsp");
		}
		User user = null;
		UserOtherInfo info = null;
		String nickName = req.getString("nickName");
		String email = req.getString("email");
		String mobile = req.getString("mobile");
		if (!DataUtil.isEmpty(nickName)) {
			user = this.userService.getUserByNickName(nickName);
			if (user != null) {
				info = this.userService.getUserOtherInfo(user.getUserId());
			}
		}
		else if (!DataUtil.isEmpty(email)) {
			info = this.userService.getUserOtherInfoByeEmail(email);
			if (info != null) {
				user = this.userService.getUser(info.getUserId());
			}
		}
		else if (!DataUtil.isEmpty(mobile)) {
			info = this.userService.getUserOtherInfoByMobile(mobile);
			if (info != null) {
				user = this.userService.getUser(info.getUserId());
			}
		}
		if (user != null) {
			req.setAttribute("user", user);
			req.setAttribute("info", info);
		}
		return this.getWapJsp("e/admin/chgcmpuser.jsp");
	}

	/**
	 * 把企业转移给某用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-4
	 */
	public String cfmchgcmpuser(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		company.setUserId(userId);
		this.companyService.updateCompanyUserId(companyId, userId);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 修改企业网站类型
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmpflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			return this.getWapJsp("e/admin/chgcmpflg.jsp");
		}
		company.setCmpflg(req.getByte("cmpflg"));
		this.companyService.updateCompany(company);
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 修改企业网站类型
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setproductattrflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		company.setProductattrflg(req.getByte("productattrflg"));
		this.companyService.updateCompany(company);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 开启或关闭文件系统服务
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setsvrfileflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		if (cmpSvrCnf == null) {
			cmpSvrCnf = new CmpSvrCnf();
			cmpSvrCnf.setCompanyId(companyId);
			cmpSvrCnf.setFileflg(req.getByte("fileflg"));
			this.cmpSvrCnfService.createCmpSvrCnf(cmpSvrCnf);
		}
		else {
			cmpSvrCnf.setFileflg(req.getByte("fileflg"));
			this.cmpSvrCnfService.updateCmpSvrCnf(cmpSvrCnf);
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 开启或关闭视频系统服务
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setsvrvideoflg(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		CmpSvrCnf cmpSvrCnf = this.cmpSvrCnfService.getCmpSvrCnf(companyId);
		if (cmpSvrCnf == null) {
			cmpSvrCnf = new CmpSvrCnf();
			cmpSvrCnf.setCompanyId(companyId);
			cmpSvrCnf.setVideoflg(req.getByte("videoflg"));
			this.cmpSvrCnfService.createCmpSvrCnf(cmpSvrCnf);
		}
		else {
			cmpSvrCnf.setVideoflg(req.getByte("videoflg"));
			this.cmpSvrCnfService.updateCmpSvrCnf(cmpSvrCnf);
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 设置企业网站文件容量
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setfilesize(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(companyId);
		int size = req.getInt("size") * 1024;
		if (cmpOtherWebInfo == null) {
			cmpOtherWebInfo = new CmpOtherWebInfo();
			cmpOtherWebInfo.setCompanyId(companyId);
			cmpOtherWebInfo.setTotalFileSize(size);
			this.cmpOtherWebInfoService.createCmpOtherWebInfo(cmpOtherWebInfo);
		}
		else {
			cmpOtherWebInfo.setTotalFileSize(size);
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_viewcmp.do?companyId=" + companyId;
	}

	/**
	 * 更换企业模板
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String chgcmptml(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		req.setAttribute("cmpInfo", cmpInfo);
		req.setAttribute("company", company);
		if (this.isForwardPage(req)) {
			List<CmpInfoTml> tmllist = CmpInfoTmlUtil
					.getCmpInfoTmlByCmpflg(company.getCmpflg());
			req.setAttribute("tmllist", tmllist);
			return this.getWapJsp("e/admin/chgcmptml.jsp");
		}
		int tmlflg = req.getInt("tmlflg");
		cmpInfo.setTmlflg(tmlflg);
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		this.setOpFuncSuccessMsg(req);
		return "r:/e/admin/admin_chgcmptml.do?companyId=" + companyId;
	}
}