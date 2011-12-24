package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.CheckInResult;
import com.hk.bean.City;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.CmpPhotoVote;
import com.hk.bean.CmpTag;
import com.hk.bean.CmpTagRef;
import com.hk.bean.CmpTip;
import com.hk.bean.CmpTipDel;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.IpCity;
import com.hk.bean.Laba;
import com.hk.bean.Mayor;
import com.hk.bean.Photo;
import com.hk.bean.PhotoCmt;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.bean.UserDateCheckInCmp;
import com.hk.bean.UserEquipment;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.ContentFilterUtil;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpTagService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.EquipmentService;
import com.hk.svr.IpCityService;
import com.hk.svr.LabaService;
import com.hk.svr.PhotoService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.equipment.EquipmentMsg;
import com.hk.svr.equipment.HandleEquipmentProcessor;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.processor.CmpCheckInProcessor;
import com.hk.svr.processor.CmpTipProcessor;
import com.hk.svr.processor.CompanyPhotoProcessor;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.EquipmentProcessor;
import com.hk.svr.processor.UploadCompanyPhotoResult;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.EquipmentConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.equipment.EquipmentMsgUtil;

@Component("/h4/op/user/venue")
public class UserOpVenueAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private CmpTagService cmpTagService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private BombService bombService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private CmpTipProcessor cmpTipProcessor;

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private EquipmentProcessor equipmentProcessor;

	@Autowired
	private ContentFilterUtil contentFilterUtil;

	@Autowired
	private LabaService labaService;

	@Autowired
	private HandleEquipmentProcessor handleEquipmentProcessor;

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	public String execute(HkRequest req, HkResponse resp) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 修改tip
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String edittip(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long tipId = req.getLongAndSetAttr("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		req.setAttribute("cmpTip", cmpTip);
		req.setAttribute("text_content", DataUtil.toText(cmpTip.getContent()));
		Company company = this.companyService.getCompany(cmpTip.getCompanyId());
		req.setAttribute("company", company);
		if (ch == 0) {
			return this.getWeb4Jsp("venue/op/edittip.jsp");
		}
		String content = req.getString("content");
		int doneflg = req.getInt("doneflg");
		if (doneflg == CmpTip.DONEFLG_TODO) {
			cmpTip.setDoneflg(CmpTip.DONEFLG_TODO);
			cmpTip.setShowflg(CmpTip.SHOWFLG_N);
		}
		else {
			cmpTip.setShowflg(CmpTip.SHOWFLG_Y);
			cmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
		}
		cmpTip.setContent(DataUtil.toHtml(content));
		int code = cmpTip.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "editerror", null);
		}
		this.cmpTipProcessor.updateCmpTip(cmpTip);
		return this.onSuccess2(req, "editok", null);
	}

	/**
	 * 创建tip与用户事件
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createtip(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		req.setAttribute("company", company);
		if (ch == 0) {
			req.setAttribute("doneflg", req.getInt("doneflg"));
			req.reSetAttribute("companyId");
			return this.getWeb4Jsp("venue/op/createtip.jsp");
		}
		Date date = new Date();
		int doneflg = req.getInt("doneflg");
		String content = req.getString("content");
		CmpTip cmpTip = new CmpTip();
		cmpTip.setUserId(this.getLoginUser(req).getUserId());
		cmpTip.setCompanyId(companyId);
		cmpTip.setContent(DataUtil.toHtml(content));
		cmpTip.setCreateTime(date);
		cmpTip.setIp(req.getRemoteAddr());
		cmpTip.setPcityId(company.getPcityId());
		if (doneflg == CmpTip.DONEFLG_TODO) {
			cmpTip.setShowflg(CmpTip.SHOWFLG_N);
		}
		else {
			cmpTip.setShowflg(CmpTip.SHOWFLG_Y);
			cmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
		}
		int code = cmpTip.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createtiperror", null);
		}
		if (!req.isTokenValid()) {
			return this.onError(req, 0, "onduplicate", null);
		}
		req.saveToken();
		this.cmpTipProcessor.createCmpTip(cmpTip);
		UserCmpTip userCmpTip = new UserCmpTip();
		userCmpTip.setCreateTime(date);
		userCmpTip.setTipId(cmpTip.getTipId());
		if (doneflg == CmpTip.DONEFLG_TODO) {
			userCmpTip.setDoneflg(CmpTip.DONEFLG_TODO);
		}
		else {
			userCmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
		}
		userCmpTip.setUserId(this.getLoginUser(req).getUserId());
		userCmpTip.setPcityId(company.getPcityId());
		userCmpTip.setData(getData(company));
		userCmpTip.setCompanyId(companyId);
		this.cmpTipProcessor.createUserCmpTip(userCmpTip);
		return this.onSuccess2(req, "createtipok", cmpTip.getTipId());
	}

	/**
	 * 用户报到
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkin2(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser(req);
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CmpCheckInUserLog lastCmpCheckInUserLog = this.cmpCheckInService
				.getLastCmpCheckInUserLogByUserId(loginUser.getUserId());
		// 切换城市速度太快，有作弊嫌疑
		if (lastCmpCheckInUserLog != null) {
			Company last = this.companyService.getCompany(companyId);
			if (last != null) {
				if (last.getPcityId() != company.getPcityId()
						&& (System.currentTimeMillis()
								- lastCmpCheckInUserLog.getCreateTime()
										.getTime() <= 1200000)) {
					req.setSessionText(String
							.valueOf(Err.CHECKIN_SPEED_TOO_FAST));
					return "r:/venue/" + companyId;
				}
			}
		}
		if (loginUser.getPcityId() != company.getPcityId()) {
			req.setSessionText(String.valueOf(Err.CHECKIN_PCITYID_ERROR),
					new Object[] { company.getPcity().getName() });
			return "r:/venue/" + companyId;
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		int oldpoints = info.getPoints();
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setCompanyId(companyId);
		cmpCheckInUserLog.setUserId(loginUser.getUserId());
		cmpCheckInUserLog.setSex(loginUser.getSex());
		cmpCheckInUserLog.setKindId(company.getKindId());
		cmpCheckInUserLog.setParentId(company.getParentKindId());
		cmpCheckInUserLog.setPcityId(loginUser.getPcityId());
		CheckInResult checkInResult = this.cmpCheckInProcessor.checkIn(
				cmpCheckInUserLog, false, company, req.getRemoteAddr());
		info = this.userService.getUserOtherInfo(loginUser.getUserId());
		int newpoints = info.getPoints();
		int respoints = newpoints - oldpoints;
		List<String> msglist = new ArrayList<String>();
		if (respoints > 0) {
			if (newpoints >= CheckInPointConfig.getOpenBoxPoints()) {
				int box_open_count = newpoints
						/ CheckInPointConfig.getOpenBoxPoints();
				// req.setSessionText("func2.checkinokandpoints2", respoints,
				// newpoints, box_open_count);
				msglist.add(req.getText("func2.checkinokandpoints2", respoints,
						newpoints, box_open_count));
			}
			else {
				// req.setSessionText("func2.checkinokandpoints", respoints);
				msglist.add(req.getText("func2.checkinokandpoints", respoints));
			}
		}
		else {
			// req.setSessionText("func2.checkinok");
			msglist.add(req.getText("func2.checkinok"));
		}
		EquipmentMsg equipmentMsg = checkInResult.getEquipmentMsg();
		if (equipmentMsg != null) {
			String msg = EquipmentMsgUtil.getEquipmentMessage(equipmentMsg,
					req, false);
			if (msg != null) {
				msglist.add(msg);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String s : msglist) {
			sb.append("<div>").append(s).append("</div>");
		}
		req.setSessionMessage(sb.toString());
		return "r:/venue/" + companyId;
		// 报到完成后，到广告和优惠券展示页面
		// return "r:/h4/venue_rest.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deleteusercmptip(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long tipId = req.getLong("tipId");
		this.cmpTipService.deleteUserCmpTipByUserIdAndTipId(loginUser
				.getUserId(), tipId);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletecmptip(HkRequest req, HkResponse resp) {
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip != null) {
			User loginUser = this.getLoginUser(req);
			if (cmpTip.getUserId() == loginUser.getUserId()) {
				this.cmpTipProcessor.deleteCmpTip(tipId);
			}
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createusercmptipdone(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		long companyId = cmpTip.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		UserCmpTip userCmpTip = new UserCmpTip();
		userCmpTip.setUserId(loginUser.getUserId());
		userCmpTip.setTipId(tipId);
		userCmpTip.setCreateTime(new Date());
		userCmpTip.setPcityId(company.getPcityId());
		userCmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
		userCmpTip.setData(getData(company));
		userCmpTip.setCompanyId(companyId);
		this.cmpTipProcessor.createUserCmpTip(userCmpTip);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createusercmptiptodo(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		long companyId = cmpTip.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		UserCmpTip userCmpTip = new UserCmpTip();
		userCmpTip.setUserId(loginUser.getUserId());
		userCmpTip.setTipId(tipId);
		userCmpTip.setCreateTime(new Date());
		userCmpTip.setPcityId(company.getPcityId());
		userCmpTip.setDoneflg(CmpTip.DONEFLG_TODO);
		userCmpTip.setData(getData(company));
		userCmpTip.setCompanyId(companyId);
		this.cmpTipProcessor.createUserCmpTip(userCmpTip);
		return null;
	}

	/**
	 * 查询足迹(为创建或者填写tip)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String searchvenue(HkRequest req, HkResponse resp) {
		if (req.getIntAndSetAttr("ch") == 0) {
			return this.getWeb4Jsp("venue/op/searchvenue.jsp");
		}
		String name = req.getString("name", "");
		User loginUser = this.getLoginUser(req);
		if (loginUser.getPcityId() > 0) {
			List<Company> list = this.companyService
					.getCompanyListByPcityIdAndNameLike(loginUser.getPcityId(),
							name, 0, 21);
			if (list.size() == 21) {
				req.setAttribute("more_in_city", true);
				list.remove(20);
			}
			req.setAttribute("list", list);
		}
		// 北京之外的其他地区搜索
		List<Long> idList = this.companyService
				.getCompanyIdListWithSearchNotPcityId(loginUser.getPcityId(),
						name, 0, 21);
		List<Company> otherlist = this.companyService.getCompanyListInId(
				idList, null);
		if (otherlist.size() > 21) {
			req.setAttribute("more_in_other", true);
			otherlist.remove(20);
		}
		req.setAttribute("otherlist", otherlist);
		req.setEncodeAttribute("name", name);
		return this.getWeb4Jsp("venue/op/searchvenue.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String searchincity(HkRequest req, HkResponse resp) {
		String name = req.getString("name", "");
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<Company> list = this.companyService
				.getCompanyListByPcityIdAndNameLike(loginUser.getPcityId(),
						name, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWeb4Jsp("venue/op/searchvenue_incity.jsp");
	}

	/**
	 * 查询足迹(为创建或者填写tip)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String searchinothercity(HkRequest req, HkResponse resp) {
		String name = req.getString("name", "");
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<Long> idList = this.companyService
				.getCompanyIdListWithSearchNotPcityId(loginUser.getPcityId(),
						name, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, idList);
		List<Company> list = this.companyService.getCompanyListInId(idList,
				null);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		return this.getWeb4Jsp("venue/op/searchvenue_inothercity.jsp");
	}

	/**
	 * 创建一个足迹，并可以填写感受
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createvenue(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("name");
			User loginUser = this.getLoginUser(req);
			City city = ZoneUtil.getCity(loginUser.getPcityId());
			int pcityId = 0;
			if (city == null) {
				IpCity ipCity = this.ipCityService.getIpCityByIp(req
						.getRemoteAddr());
				if (ipCity != null) {
					String zoneName = DataUtil.filterZoneName(ipCity.getName());
					city = this.zoneService.getCityLike(zoneName);
					if (city != null) {
						pcityId = city.getCityId();
					}
				}
			}
			else {
				pcityId = city.getCityId();
			}
			req.setAttribute("city", city);
			req.setAttribute("pcityId", pcityId);
			return this.getWeb4Jsp("venue/op/createvenue.jsp");
		}
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		int pcityId = req.getInt("pcityId");
		Company company = new Company();
		company.setCreaterId(this.getLoginUser(req).getUserId());
		company.setName(DataUtil.toHtmlRow(name));
		company.setAddr(DataUtil.toHtmlRow(addr));
		company.setTel(DataUtil.toHtmlRow(tel));
		company.setPcityId(pcityId);
		int code = company.validate(true);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createvenueerror", null);
		}
		this.companyProcessor.createCompany(company, req.getRemoteAddr());
		req.setSessionText("func2.createvenuepointsandeditmap",
				CheckInPointConfig.getCompanyCreate(), company.getName());
		// 添加tip
		this.createTipOnCreateCompany(req, company);
		return this.onSuccess2(req, "createvenueok", company.getCompanyId());
	}

	private void createTipOnCreateCompany(HkRequest req, Company company) {
		String content = req.getString("content");
		if (!DataUtil.isEmpty(content)) {
			content = DataUtil.subString(content, 500);
			Date date = new Date();
			int doneflg = req.getInt("doneflg");
			CmpTip cmpTip = new CmpTip();
			cmpTip.setUserId(this.getLoginUser(req).getUserId());
			cmpTip.setCompanyId(company.getCompanyId());
			cmpTip.setContent(DataUtil.toHtml(content));
			cmpTip.setCreateTime(date);
			cmpTip.setIp(req.getRemoteAddr());
			cmpTip.setPcityId(company.getPcityId());
			if (doneflg == CmpTip.DONEFLG_TODO) {
				cmpTip.setShowflg(CmpTip.SHOWFLG_N);
				cmpTip.setDoneflg(CmpTip.DONEFLG_TODO);
			}
			else {
				cmpTip.setShowflg(CmpTip.SHOWFLG_Y);
				cmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
			}
			this.cmpTipProcessor.createCmpTip(cmpTip);
			UserCmpTip userCmpTip = new UserCmpTip();
			userCmpTip.setCreateTime(date);
			userCmpTip.setTipId(cmpTip.getTipId());
			if (doneflg == CmpTip.DONEFLG_TODO) {
				userCmpTip.setDoneflg(CmpTip.DONEFLG_TODO);
			}
			else {
				userCmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
			}
			userCmpTip.setUserId(this.getLoginUser(req).getUserId());
			userCmpTip.setPcityId(company.getPcityId());
			userCmpTip.setData(getData(company));
			userCmpTip.setCompanyId(company.getCompanyId());
			this.cmpTipProcessor.createUserCmpTip(userCmpTip);
		}
	}

	/**
	 * 修改足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editvenue(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (ch == 0) {
			return this.getWeb4Jsp("venue/op/editvenue.jsp");
		}
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String traffic = req.getString("traffic");
		String intro = req.getString("intro");
		int pcityId = req.getInt("pcityId");
		company.setName(DataUtil.toHtmlRow(name));
		company.setAddr(DataUtil.toHtmlRow(addr));
		company.setTel(DataUtil.toHtmlRow(tel));
		company.setPcityId(pcityId);
		company.setTraffic(DataUtil.toHtmlRow(traffic));
		company.setIntro(DataUtil.subString(DataUtil.toHtmlRow(intro), 2000));
		List<Integer> errlist = company.validateList();
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "editerror");
		}
		this.companyService.updateCompany(company);
		return this.onSuccess2(req, "editok", company.getCompanyId());
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createtag(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		CmpTag cmpTag = new CmpTag();
		cmpTag.setName(name);
		int code = cmpTag.validate();
		if (code != Err.SUCCESS) {
			this.onError(req, code, "createtagerror", null);
			return null;
		}
		if (this.cmpTagService.createCmpTag(cmpTag, companyId, loginUser
				.getUserId(), company.getPcityId())) {
			return this.onSuccess2(req, "createtagok", cmpTag.getTagId());
		}
		return this.onError(req, Err.CMPTAGREF_EXIST, "createtagerror", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String deltag(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long tagId = req.getLong("tagId");
		CmpTagRef ref = this.cmpTagService.getCmpTagRefByCompanyIdAndTagId(
				companyId, tagId);
		if (ref != null) {
			User loginUser = this.getLoginUser(req);
			if (ref.getUserId() == loginUser.getUserId()) {
				this.cmpTagService.deleteCmpTagRefByCompanyIdAndTagId(
						companyId, tagId);
			}
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delmayor(HkRequest req, HkResponse resp) {
		long mayorId = req.getLong("mayorId");
		User loginUser = this.getLoginUser(req);
		Mayor mayor = this.cmpCheckInService.getMayor(mayorId);
		if (mayor != null && mayor.getUserId() == loginUser.getUserId()) {
			this.cmpCheckInService.deleteMayor(mayorId);
			Company company = this.companyService.getCompany(mayor
					.getCompanyId());
			company.setMayorUserId(0);
			this.companyService.updateCompany(company);
		}
		return null;
	}

	/**
	 * 更新用户在足迹中的状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateusercmpstatus(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		byte status = req.getByte("status");
		if (status == CompanyUserStatus.USERSTATUS_DONE) {// 去过
			this.companyService.createCompanyUserStatus(companyId, loginUser
					.getUserId(), status);
		}
		else if (status == CompanyUserStatus.USERSTATUS_WANT) {// 想去
			this.companyService.createCompanyUserStatus(companyId, loginUser
					.getUserId(), status);
		}
		return null;
	}

	/**
	 * 更新用户在足迹中的状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String deleteusercmpstatus(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		byte status = req.getByte("status");
		CompanyUserStatus companyUserStatus = this.companyService
				.getCompanyUserStatus(companyId, loginUser.getUserId());
		if (companyUserStatus != null) {
			if (status == CompanyUserStatus.USERSTATUS_DONE) {
				companyUserStatus.setDoneStatus(CompanyUserStatus.NONE_FLG);
			}
			else {
				companyUserStatus.setUserStatus(CompanyUserStatus.NONE_FLG);
			}
			this.companyService.updateCompanyUserStatus(companyUserStatus);
		}
		return null;
	}

	/**
	 * 更新用户在足迹中的状态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String votepic(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		CmpPhotoVote cmpPhotoVote = new CmpPhotoVote();
		cmpPhotoVote.setUserId(loginUser.getUserId());
		cmpPhotoVote.setCompanyId(companyPhoto.getCompanyId());
		cmpPhotoVote.setPhotoId(photoId);
		this.companyPhotoService.createCmpPhotoVote(cmpPhotoVote);
		resp.sendHtml(companyPhoto.getVoteCount() + 1);
		return null;
	}

	/**
	 * 修改图片名称
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editpicname(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		String name = req.getString("name");
		if (name != null) {
			name = DataUtil.subString(name, 30);
			name = DataUtil.toHtmlRow(name);
		}
		User loginUser = this.getLoginUser(req);
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		Company company = this.companyService.getCompany(companyPhoto
				.getCompanyId());
		if (companyPhoto.getUserId() == loginUser.getUserId()
				|| this.isAdminUser(req)
				|| company.getUserId() == loginUser.getUserId()
				|| company.getCreaterId() == loginUser.getUserId()) {
			this.companyPhotoService.updateName(photoId, name);
			this.photoService.updateName(photoId, name);
			return this.onSuccess2(req, "oneditnameok", name);
		}
		return null;
	}

	/**
	 * 删除图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delpic(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long photoId = req.getLong("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		this.getRangePhotoId(req, companyPhoto.getCompanyId(), photoId);
		Company company = this.companyService.getCompany(companyPhoto
				.getCompanyId());
		if (companyPhoto.getUserId() == loginUser.getUserId()
				|| this.isAdminUser(req)
				|| company.getUserId() == loginUser.getUserId()
				|| company.getCreaterId() == loginUser.getUserId()) {
			this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
			return null;
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long companyId = req.getLongAndSetAttr("companyId");
		if (ch == 0) {
			Company company = this.companyService.getCompany(companyId);
			req.setAttribute("company", company);
			return this.getWeb4Jsp("venue/op/uploadpic.jsp");
		}
		User loginUser = this.getLoginUser(req);
		UploadCompanyPhotoResult uploadCompanyPhotoResult = this.companyPhotoProcessor
				.createCompanyPhoto(companyId, loginUser.getUserId(), req
						.getFiles());
		if (uploadCompanyPhotoResult.getErrorCode() != Err.SUCCESS) {
			return this.onError(req, uploadCompanyPhotoResult.getErrorCode(),
					"uploaderror", null);
		}
		if (uploadCompanyPhotoResult.isAllImgUploadSuccess()) {
		}
		else {
			StringBuilder sb = new StringBuilder();
			if (uploadCompanyPhotoResult.getFmtErrCount() > 0) {
				sb.append(req.getText(String
						.valueOf(Err.IMG_UPLOAD_FMT_ERROR_NUM),
						uploadCompanyPhotoResult.getFmtErrCount()));
				sb.append("<br/>");
			}
			if (uploadCompanyPhotoResult.getOutOfSizeCount() > 0) {
				sb.append(req.getText(String
						.valueOf(Err.IMG_UPLOAD_OUTOFSIZE_ERROR_NUM),
						uploadCompanyPhotoResult.getOutOfSizeCount(), 2));
			}
			if (sb.length() > 0) {
				req.setSessionMessage(sb.toString());
			}
		}
		StringBuilder sb = new StringBuilder();
		if (uploadCompanyPhotoResult.getList().size() > 0) {
			for (CompanyPhoto companyPhoto : uploadCompanyPhotoResult.getList()) {
				sb.append(companyPhoto.getPhotoId()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return this.onSuccess2(req, "upload", sb.toString());
	}

	/**
	 * 上传图片完成后，可以编辑图片名称
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editpic(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			long companyId = req.getLongAndSetAttr("companyId");
			Company company = this.companyService.getCompany(companyId);
			req.setAttribute("company", company);
			long[] photoId = req.getLongs("photoId");
			if (photoId == null) {
				return "r:/h4/op/user/venue_uploadpic.do?companyId="
						+ companyId;
			}
			List<Long> idList = new ArrayList<Long>();
			for (int i = 0; i < photoId.length; i++) {
				idList.add(photoId[i]);
			}
			List<Photo> list = this.photoService.getPhotoListInId(idList);
			req.setAttribute("list", list);
			return this.getWeb4Jsp("venue/op/editpic.jsp");
		}
		long companyId = req.getLong("companyId");
		long[] photoId = req.getLongs("photoId");
		if (photoId == null) {
			return "r:/h4/op/user/venue_uploadpic.do?companyId=" + companyId;
		}
		for (int i = 0; i < photoId.length; i++) {
			String name = req.getString("name" + photoId[i]);
			if (!DataUtil.isEmpty(name)) {
				name = DataUtil.subString(name, 20);
				name = DataUtil.toHtmlRow(name);
				this.photoService.updateName(Long.valueOf(photoId[i]), name);
				this.companyPhotoService.updateName(Long.valueOf(photoId[i]),
						name);
			}
		}
		if (photoId.length > 0) {
			return "r:/venue/" + companyId + "/pic/" + photoId[0];
		}
		return "r:/h4/venue_first.do?companyId=" + companyId;
	}

	/**
	 *图片评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createphotocmt(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		Photo photo = this.photoService.getPhoto(photoId);
		if (photo == null) {
			return null;
		}
		String content = req.getString("content");
		PhotoCmt cmt = new PhotoCmt();
		cmt.setPhotoId(photoId);
		cmt.setContent(DataUtil.toHtml(content));
		cmt.setUserId(this.getLoginUser(req).getUserId());
		int code = cmt.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "cmterror", null);
		}
		this.photoService.createPhotoCmt(cmt);
		req.setAttribute("cmt", cmt);
		return this.getWeb4Jsp("venue/pic_cmt_inc.jsp");
	}

	/**
	 *删除图片评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delphotocmt(HkRequest req, HkResponse resp) {
		long cmtId = req.getLong("cmtId");
		PhotoCmt cmt = this.photoService.getPhotoCmt(cmtId);
		if (cmt == null
				|| cmt.getUserId() != this.getLoginUser(req).getUserId()) {
			return null;
		}
		this.photoService.deletePhotoCmt(cmtId);
		return null;
	}

	/**
	 *删除图片投票
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delphotovote(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		User loginUser = this.getLoginUser(req);
		this.companyPhotoService.deleteCmpPhotoVote(photoId, loginUser
				.getUserId());
		return null;
	}

	/**
	 *删除图片评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updatephotocmt(HkRequest req, HkResponse resp) {
		long cmtId = req.getLong("cmtId");
		String content = req.getString("content");
		PhotoCmt cmt = this.photoService.getPhotoCmt(cmtId);
		if (cmt == null) {
			return null;
		}
		cmt.setContent(DataUtil.toHtml(content));
		int code = cmt.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "editcmterror", null);
		}
		this.photoService.updatePhotoCmt(cmt);
		return this.onSuccess2(req, "editcmtok", null);
	}

	/**
	 *删除图片评论
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sethead(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		User loginUser = this.getLoginUser(req);
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			resp.sendHtml(0);
			return null;
		}
		if (loginUser.getUserId() == company.getUserId()
				|| loginUser.getUserId() == company.getCreaterId()
				|| this.isAdminUser(req)) {
			CompanyPhoto companyPhoto = this.companyPhotoService
					.getCompanyPhoto(photoId);
			if (companyPhoto == null) {
				resp.sendHtml(0);
				return null;
			}
			company.setHeadPath(companyPhoto.getPath());
			this.companyService.updateCompany(company);
			resp.sendHtml(1);
		}
		else {
			resp.sendHtml(0);
		}
		return null;
	}

	/**
	 * 炸弹炸掉tip
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String bombtip(HkRequest req, HkResponse resp) {
		long tipId = req.getLong("tipId");
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		if (cmpTip == null) {
			return null;
		}
		User loginUser = this.getLoginUser(req);
		Bomber bomber = this.bombService.getBomber(loginUser.getUserId());
		if (bomber != null && bomber.getBombCount() > 0) {
			CmpTipDel cmpTipDel = new CmpTipDel(cmpTip);
			cmpTipDel.setOpuserId(loginUser.getUserId());
			cmpTipDel.setOptime(System.currentTimeMillis());
			this.cmpTipProcessor.bombCmpTip(cmpTipDel);
			this.bombService.addRemainBombCount(loginUser.getUserId(), -1);
		}
		return null;
	}

	/**
	 *在报道之前检查是否有使用的卡
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-29
	 */
	public String checkequ(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long companyId = req.getLong("companyId");
		if (this.handleEquipmentProcessor.isUserEquEnjoyFirst(loginUser
				.getUserId(), companyId)) {
			return "r:/h4/op/user/venue_checkin2.do?companyId=" + companyId;
		}
		if (this.handleEquipmentProcessor.isHasUserCmpEnjoy(loginUser
				.getUserId(), companyId)) {
			return "r:/h4/op/user/venue_checkin2.do?companyId=" + companyId;
		}
		return "r:/h4/op/user/venue_selequ.do?companyId=" + companyId;
	}

	/**
	 * 对足迹选择道具，当报到时触发
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-14
	 */
	public String selequ(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long userId = this.getLoginUser(req).getUserId();
		long companyId = req.getLongAndSetAttr("companyId");
		User loginUser = this.getLoginUser(req);
		Company company = this.companyService.getCompany(companyId);
		CmpCheckInUserLog lastCmpCheckInUserLog = this.cmpCheckInService
				.getLastCmpCheckInUserLogByUserId(loginUser.getUserId());
		// 切换城市速度太快，有作弊嫌疑
		if (lastCmpCheckInUserLog != null) {
			Company last = this.companyService.getCompany(companyId);
			if (last != null) {
				if (last.getPcityId() != company.getPcityId()
						&& (System.currentTimeMillis()
								- lastCmpCheckInUserLog.getCreateTime()
										.getTime() <= 1200000)) {
					req.setSessionText(String
							.valueOf(Err.CHECKIN_SPEED_TOO_FAST));
					return "r:/venue/" + companyId;
				}
			}
		}
		if (loginUser.getPcityId() != company.getPcityId()) {
			req.setSessionText(String.valueOf(Err.CHECKIN_PCITYID_ERROR),
					new Object[] { company.getPcity().getName() });
			return "r:/venue/" + companyId;
		}
		if (ch == 0) {
			req.setAttribute("company", company);
			SimplePage page = req.getSimplePage(20);
			List<UserEquipment> list = this.equipmentProcessor
					.getUserEquipmentToCmp(userId, page.getBegin(), page
							.getSize() + 1);
			this.processListForPage(page, list);
			int todayEffectCheckInCount = this.cmpCheckInService
					.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
							companyId, userId, new Date());
			int totalEffectCheckInCount = this.cmpCheckInService
					.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
							companyId, userId);
			Map<String, Object> ctxMap = new HashMap<String, Object>();
			ctxMap.put("totalEffectCheckInCount", totalEffectCheckInCount);
			ctxMap.put("todayEffectCheckInCount", todayEffectCheckInCount);
			for (UserEquipment e : list) {
				e.setPrePoints(EquipmentConfig.getPoints(e.getEid(), ctxMap));
			}
			req.setAttribute("list", list);
			// 直接报道获得的点数
			req.setAttribute("onlycheckpoints", CheckInPointConfig.getPoints(
					totalEffectCheckInCount, todayEffectCheckInCount));
			UserDateCheckInCmp userDateCheckInCmp = this.cmpCheckInService
					.getUserDateCheckInCmp(userId);
			if (userDateCheckInCmp != null) {
				if (userDateCheckInCmp.isInOneDate()) {
					// 每天只能报到10个地方
					if (userDateCheckInCmp.getCompanyIdListSize() >= 10) {
						req.setAttribute("onlycheckpoints", 0);
					}
				}
			}
			return this.getWeb4Jsp("venue/selequ.jsp");
		}
		long oid = req.getLong("oid");
		UserEquipment userEquipment = this.equipmentService
				.getUserEquipment(oid);
		if (userEquipment != null) {
			this.equipmentService.useEquipmentToCmp(companyId, userEquipment);
			req.setSessionText("view2.userequipment.usetocmp.success");
		}
		// 顺便说一句喇叭
		this.createlaba(req);
		return "r:/h4/op/user/venue_checkin2.do?companyId=" + companyId;
	}

	/**
	 * 发新喇叭
	 * 
	 * @param req
	 * @return
	 */
	private void createlaba(HkRequest req) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return;
		}
		User loginUser = this.getLoginUser(req);
		String content = req.getString("content");
		if (DataUtil.isEmpty(content)) {
			return;
		}
		content = DataUtil.subString(content, 420);
		if (this.contentFilterUtil.hasFilterString(content)) {
			return;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse("我在{[" + companyId + ","
				+ company.getName() + "}。" + content);
		if (labaInfo.isEmptyContent()) {
			return;
		}
		labaInfo.setIp(req.getRemoteAddr());
		labaInfo.setUserId(loginUser.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_WEB);
		this.labaService.createLaba(labaInfo);
	}
}