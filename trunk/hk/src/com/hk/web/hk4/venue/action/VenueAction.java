package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.CmpActor;
import com.hk.bean.CmpCheckInUser;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpPhotoVote;
import com.hk.bean.CmpRefUser;
import com.hk.bean.CmpSvr;
import com.hk.bean.CmpTag;
import com.hk.bean.CmpTagRef;
import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.Country;
import com.hk.bean.Coupon;
import com.hk.bean.HkAd;
import com.hk.bean.PhotoCmt;
import com.hk.bean.Province;
import com.hk.bean.User;
import com.hk.bean.UserCmpPoint;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpRefUserService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CmpTagService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.HkAdService;
import com.hk.svr.PhotoService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpActorProcessor;
import com.hk.svr.processor.CmpCheckInProcessor;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.processor.HkAdProcessor;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HkWebUtil;

@Component("/h4/venue")
public class VenueAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private CmpTagService cmpTagService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private HkAdService hkAdService;

	@Autowired
	private HkAdProcessor hkAdProcessor;

	@Autowired
	private CouponProcessor couponProcessor;

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpRefUserService cmpRefUserService;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpActorProcessor cmpActorProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("to_venue", true);
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		// if (company.getKindId() == CompanyKind.KINDID_MEIFA
		// || req.getInt("mf") == 1) {
		// return this.exemeifainfo(company, req);
		// }
		if (req.getInt("mf") == 1) {
			return this.exemeifainfo(company, req);
		}
		return this.exenormalinfo(company, req);
	}

	public String exemeifainfo(Company company, HkRequest req) {
		req.setAttribute("company", company);
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpSvr> cmpsvrlist = this.cmpSvrService.getCmpSvrListByCompanyId(
				companyId, null, 0, 7);
		if (cmpsvrlist.size() == 7) {
			req.setAttribute("more_svr", true);
			cmpsvrlist.remove(6);
		}
		req.setAttribute("cmpsvrlist", cmpsvrlist);
		List<CmpActor> cmpactorlist = this.cmpActorProcessor
				.getCmpActorListByCompanyIdForCanReserve(companyId, true, 0, 12);
		req.setAttribute("cmpactorlist", cmpactorlist);
		// 去过想去
		// 用户去过与想去
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		if (this.isAdminUser(req)) {
			req.setAttribute("canedit", true);
		}
		else {
			if (loginUser != null) {
				if (company.getUserId() > 0
						&& company.getUserId() == loginUser.getUserId()) {
					req.setAttribute("canedit", true);
				}
				else if (company.getUserId() == 0
						&& company.getCreaterId() == loginUser.getUserId()) {
					req.setAttribute("canedit", true);
				}
			}
		}
		return this.getWeb4Jsp("venue/meifa/venue.jsp");
	}

	public String exenormalinfo(Company company, HkRequest req) {
		req.setAttribute("to_venue", true);
		long companyId = req.getLongAndSetAttr("companyId");
		req.setAttribute("company", company);
		User loginUser = this.getLoginUser(req);
		int checkincount = this.cmpCheckInService
				.countEffectCmpCheckinUserLogBycompanyId(companyId);
		req.setAttribute("checkincount", checkincount);
		if (loginUser != null) {
			int usercheckincount = this.cmpCheckInService
					.countEffectCmpCheckInUserLogByCompanyIdAndUserId(
							companyId, loginUser.getUserId());
			req.setAttribute("usercheckincount", usercheckincount);
		}
		int usercount = this.cmpCheckInService
				.countCmpCheckInUserByCompanyId(companyId);
		req.setAttribute("usercount", usercount);
		List<CmpCheckInUser> cmpcheckinuserlist = this.cmpCheckInService
				.getCmpCheckInUserListByCompanyId(companyId, 0, 40);
		List<Long> idList = new ArrayList<Long>();
		for (CmpCheckInUser o : cmpcheckinuserlist) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CmpCheckInUser o : cmpcheckinuserlist) {
			o.setUser(map.get(o.getUserId()));
		}
		req.setAttribute("cmpcheckinuserlist", cmpcheckinuserlist);
		// tip list
		List<CmpTip> cmptiplist = null;
		List<CmpTip> mycmptiplist = null;
		if (loginUser == null) {
			cmptiplist = this.cmpTipService.getCmpTipListByCompanyId(companyId,
					CmpTip.DONEFLG_DONE, 0, 13);
		}
		else {
			cmptiplist = this.cmpTipService
					.getCmpTipListByCompanyIdExcluedUserId(companyId, loginUser
							.getUserId(), 0, 13);
			mycmptiplist = this.cmpTipService
					.getCmpTipListByCompanyIdAndUserId(companyId, loginUser
							.getUserId(), 0, 3);
		}
		if (cmptiplist.size() == 13) {
			req.setAttribute("more_tip", true);
			cmptiplist.remove(12);
		}
		if (mycmptiplist != null) {
			cmptiplist.addAll(mycmptiplist);
		}
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList(cmptiplist,
				loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		// todo
		List<CmpTip> todocmptiplist = this.cmpTipService
				.getCmpTipListByCompanyId(companyId, CmpTip.DONEFLG_TODO, 0, 6);
		if (cmptiplist.size() == 6) {
			req.setAttribute("more_todo", true);
			cmptiplist.remove(5);
		}
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<CmpTipVo> todocmptipvolist = CmpTipVo.createVoList(todocmptiplist,
				loginUserId, true);
		req.setAttribute("todocmptipvolist", todocmptipvolist);
		if (loginUser != null) {
			if (company.getUserId() == loginUser.getUserId()
					|| company.getCreaterId() == loginUser.getUserId()) {
				req.setAttribute("canedit", true);
			}
		}
		// tag
		List<CmpTagRef> cmptagreflist = this.cmpTagService
				.getCmpTagRefListByCompanyId(companyId, 0, 10);
		req.setAttribute("cmptagreflist", cmptagreflist);
		if (company.getMayorUserId() > 0) {
			User mayor = this.userService.getUser(company.getMayorUserId());
			req.setAttribute("mayor", mayor);
		}
		if (loginUser != null) {
			if (company.getMayorUserId() == loginUser.getUserId()
					|| company.getCreaterId() == loginUser.getUserId()
					|| company.getUserId() == loginUser.getUserId()
					|| this.isAdminUser(req)) {
				req.setAttribute("canedit", true);
			}
		}
		City city = ZoneUtil.getCity(company.getPcityId());
		if (city != null) {
			Province province = ZoneUtil.getProvince(city.getProvinceId());
			Country country = ZoneUtil.getCountry(city.getCountryId());
			req.setAttribute("province", province);
			req.setAttribute("country", country);
		}
		// 用户去过与想去
		if (loginUser != null) {
			CompanyUserStatus companyUserStatus = this.companyService
					.getCompanyUserStatus(companyId, loginUser.getUserId());
			req.setAttribute("companyUserStatus", companyUserStatus);
		}
		// 图片数量
		int photoCount = this.companyPhotoService
				.countCompanyPhotoByCompanyId(companyId);
		req.setAttribute("photoCount", photoCount);
		if (loginUser != null) {
			UserCmpPoint userCmpPoint = this.cmpCheckInService
					.getUserCmpPointByUserIdAndCompanyId(loginUser.getUserId(),
							companyId);
			req.setAttribute("userCmpPoint", userCmpPoint);
			CmpRefUser cmpRefUser = this.cmpRefUserService
					.getCmpRefUserByCompanyIdAndUserId(companyId, loginUserId);
			req.setAttribute("cmpRefUser", cmpRefUser);
		}
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		if (cmpInfo != null) {
			if (loginUser != null) {
				CmpRefUser cmpRefUser = this.cmpRefUserService
						.getCmpRefUserByCompanyIdAndUserId(companyId,
								loginUserId);
				req.setAttribute("cmpRefUser", cmpRefUser);
			}
			req.setAttribute("cmpInfo", cmpInfo);
		}
		return this.getWeb4Jsp("venue/venue.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tagvenue(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("to_venue", true);
		long tagId = req.getLongAndSetAttr("tagId");
		CmpTag tag = this.cmpTagService.getCmpTag(tagId);
		req.setAttribute("tag", tag);
		SimplePage page = req.getSimplePage(20);
		int pcityId = this.getPcityId(req);
		City city = ZoneUtil.getCity(pcityId);
		req.setAttribute("city", city);
		List<CmpTagRef> list = this.cmpTagService.getCmpTagRefListByTagId(
				tagId, pcityId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<Long> idList = new ArrayList<Long>();
		for (CmpTagRef o : list) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (CmpTagRef o : list) {
			o.setCompany(map.get(o.getCompanyId()));
		}
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/tagvenuelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) {
		req.setAttribute("to_venue", true);
		int pcityId = this.getPcityId(req);
		SimplePage page = req.getSimplePage(20);
		int all = req.getIntAndSetAttr("all");
		if (all == 1) {
			pcityId = 0;
		}
		List<Company> list = this.companyService.getCompanyListByPcityId(
				pcityId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/venuelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String intro(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		return this.getWeb4Jsp("venue/intro.jsp");
	}

	/**
	 * 按照投票数进行排序显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String first(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getFirstCompanyPhoto(companyId);
		if (companyPhoto == null) {
			return "r:/venue/" + companyId;
		}
		return "r:/venue/" + companyId + "/pic/" + companyPhoto.getPhotoId();
	}

	/**
	 * 按照投票数进行排序显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String pic(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long photoId = req.getLongAndSetAttr("photoId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		req.setAttribute("companyPhoto", companyPhoto);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (company.getUserId() == loginUser.getUserId()
					|| company.getCreaterId() == loginUser.getUserId()
					|| this.isAdminUser(req)) {
				req.setAttribute("cmpedit", true);
			}
		}
		return this.getWeb4Jsp("venue/pic.jsp");
	}

	/**
	 * 加载图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadpic(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyIdVoteStyle(companyId, page.getBegin(),
						page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/piclist_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadpicpreandnext(HkRequest req, HkResponse resp)
			throws Exception {
		long photoId = req.getLongAndSetAttr("photoId");
		long companyId = req.getLongAndSetAttr("companyId");
		// 取出当前图片前后各一张
		this.getRangePhotoId(req, companyId, photoId);
		return this.getWeb4Jsp("venue/pic_inc.jsp");
	}

	/**
	 * 在图片页面加载tips
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadtips(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpTip> cmptiplist = this.cmpTipService.getCmpTipListByCompanyId(
				companyId, CmpTip.DONEFLG_DONE, 0, 13);
		if (cmptiplist.size() == 13) {
			req.setAttribute("more_tip", true);
			cmptiplist.remove(12);
		}
		long loginUserId = 0;
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList(cmptiplist,
				loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWeb4Jsp("venue/tips_inc.jsp");
	}

	/**
	 * 在图片页面加载图片评论以及投票信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadphotocmt(HkRequest req, HkResponse resp) {
		long photoId = req.getLongAndSetAttr("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		List<PhotoCmt> list = this.photoService.getPhotoCmtListByPhotoId(
				photoId, 0, 30);
		List<Long> idList = new ArrayList<Long>();
		for (PhotoCmt o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (PhotoCmt o : list) {
			o.setUser(map.get(o.getUserId()));
		}
		req.setAttribute("list", list);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			CmpPhotoVote vote = this.companyPhotoService.getCmpPhotoVote(
					photoId, loginUser.getUserId());
			if (vote != null) {
				req.setAttribute("votestat", 1);
			}
			else {
				req.setAttribute("votestat", 0);
			}
			Company company = this.companyService.getCompany(companyPhoto
					.getCompanyId());
			if (companyPhoto.getUserId() == loginUser.getUserId()
					|| this.isAdminUser(req)
					|| company.getUserId() == loginUser.getUserId()
					|| company.getCreaterId() == loginUser.getUserId()) {
				req.setAttribute("caneditpic", true);
			}
		}
		return this.getWeb4Jsp("venue/pic_cmtlist_inc.jsp");
	}

	/**
	 *报到完成后到广告和优惠券的展示页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String rest(HkRequest req, HkResponse resp) {
		int cityId = this.getPcityId(req);
		long companyId = req.getLongAndSetAttr("companyId");
		int randnum = DataUtil.getRandomNumber(9);
		boolean couponfirst = true;
		if (randnum % 2 == 0) {
			couponfirst = false;
		}
		if (couponfirst) {
			// 优惠券
			List<Coupon> couponlist = this.couponProcessor
					.getRandomUsefulCouponList(cityId, 1);
			if (couponlist.size() == 0) {
				// 没有优惠券，就取广告
				String viewerId = HkWebUtil.getViewerId(req, resp);
				List<HkAd> hkadlist = this.hkAdProcessor
						.getRandomUsefulHkAdList(cityId, viewerId, 1);
				// 用户浏览广告
				if (hkadlist.size() > 0) {
					HkAd hkAd = hkadlist.get(0);
					this.hkAdService.viewHkAd(viewerId, hkAd.getOid(), req
							.getRemoteAddr(), 1);
				}
				else {
					req.setAttribute("showGoogleAd", true);
				}
				req.setAttribute("hkadlist", hkadlist);
			}
			else {
				req.setAttribute("couponlist", couponlist);
			}
		}
		else {
			// 广告
			String viewerId = HkWebUtil.getViewerId(req, resp);
			List<HkAd> hkadlist = this.hkAdProcessor.getRandomUsefulHkAdList(
					cityId, viewerId, 1);
			if (hkadlist.size() == 0) {
				// 优惠券
				List<Coupon> couponlist = this.couponProcessor
						.getRandomUsefulCouponList(cityId, 1);
				req.setAttribute("couponlist", couponlist);
			}
			else {
				// 用户浏览广告
				if (hkadlist.size() > 0) {
					HkAd hkAd = hkadlist.get(0);
					this.hkAdService.viewHkAd(viewerId, hkAd.getOid(), req
							.getRemoteAddr(), 1);
				}
				else {
					req.setAttribute("showGoogleAd", true);
				}
				req.setAttribute("hkadlist", hkadlist);
			}
		}
		// 谁来过这里
		List<CmpCheckInUser> checkinlist = this.cmpCheckInProcessor
				.getCmpCheckInUserListByCompanyId(companyId, true, 0, 5);
		req.setAttribute("checkinlist", checkinlist);
		// 谁想到这里
		List<CompanyUserStatus> wantlist = this.companyProcessor
				.getCompanyUserStatusListByCompanyIdAndUserStatus(companyId,
						CompanyUserStatus.OK_FLG, true, 0, 5);
		req.setAttribute("wantlist", wantlist);
		return this.getWeb4Jsp("venue/rest.jsp");
	}

	/**
	 * 与当前足迹脱离关系，脱离关系后，不再是企业会员，企业会员中不会查询到该用户的数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-31
	 */
	public String opclrinvenue(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		User loginUser = this.getLoginUser(req);
		this.cmpRefUserService.deleteCmpRefUserByCompanyIdAndUserId(companyId,
				loginUser.getUserId());
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-31
	 */
	public String meifa(HkRequest req, HkResponse resp) {
		int kindId = 4;// 美容美发
		req.setAttribute("to_venue", true);
		int pcityId = this.getPcityId(req);
		SimplePage page = req.getSimplePage(20);
		int all = req.getIntAndSetAttr("all");
		if (all == 1) {
			pcityId = 0;
		}
		List<Company> list = this.companyService
				.getCompanyListByPcityIdAndKindId(pcityId, kindId, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/meifa/list.jsp");
	}
}