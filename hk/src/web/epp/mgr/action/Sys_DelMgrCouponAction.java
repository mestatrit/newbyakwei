package web.epp.mgr.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpUnion;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.CouponService;
import com.hk.svr.processor.CouponProcessor;
import com.hk.svr.pub.Err;

@Deprecated
// @Component("/epp/mgr/coupon")
public class Sys_DelMgrCouponAction extends EppBaseAction {

	@Autowired
	private CouponService couponService;

	@Autowired
	private CouponProcessor couponProcessor;

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
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(20);
		byte useflg = req.getByteAndSetAttr("useflg", (byte) -1);
		List<com.hk.bean.Coupon> list = this.couponService.getCouponListEx(
				companyId, name, useflg, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return this.getMgrJspPath(req, "coupon/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		return this.processTocreate(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	private String processTocreate(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		req.setAttribute("companyId", companyId);
		return this.getMgrJspPath(req, "coupon/create.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser2(req);
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int year = req.getInt("year");
		int month = req.getInt("month");
		int date = req.getInt("date");
		int limitDay = req.getInt("limitDay");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		long uid = 0;
		Company company = companyService.getCompany(companyId);
		if (company != null && company.getUid() >= 0) {
			CmpUnion cmpUnion = cmpUnionService.getCmpUnion(company.getUid());
			if (cmpUnion != null) {
				uid = cmpUnion.getUid();
			}
		}
		com.hk.bean.Coupon o = new com.hk.bean.Coupon();
		o.setUid(uid);
		o.setUserId(loginUser.getUserId());
		o.setCompanyId(companyId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setContent(DataUtil.toHtml(content));
		o.setRemark(DataUtil.toHtml(remark));
		o.setOverdueflg(overdueflg);
		o.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (o.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		o.setData(DataUtil.toJson(datamap));
		int code = o.validate();
		req.setAttribute("coupon", o);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return tocreate(req, resp);
		}
		this.couponProcessor.createCoupon(o, false);
		req.setSessionText("func.mgrsite.coupon.create_ok");
		return "r:/epp/mgr/coupon_list.do?companyId=" + companyId;
	}

	public String toupdate(HkRequest req, HkResponse resp) throws Exception {
		return this.processToupdate(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String processToupdate(HkRequest req, HkResponse resp)
			throws Exception {
		long couponId = req.getLong("couponId");
		com.hk.bean.Coupon coupon = (com.hk.bean.Coupon) req
				.getAttribute("coupon");
		if (coupon == null) {
			coupon = this.couponService.getCoupon(couponId);
		}
		req.reSetAttribute("companyId");
		req.reSetAttribute("couponId");
		req.setAttribute("coupon", coupon);
		return this.getMgrJspPath(req, "coupon/update.jsp");
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser2(req);
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		String name = req.getString("name");
		String content = req.getString("content");
		String remark = req.getString("remark");
		byte overdueflg = req.getByte("overdueflg");
		int amount = req.getInt("amount");
		int year = req.getInt("year");
		int month = req.getInt("month");
		int date = req.getInt("date");
		int limitDay = req.getInt("limitDay");
		com.hk.bean.Coupon o = this.couponService.getCoupon(couponId);
		if (o == null) {
			return "r:/epp/mgr/coupon_list.do?companyId=" + companyId;
		}
		o.setName(DataUtil.toHtmlRow(name));
		o.setContent(DataUtil.toHtml(content));
		o.setRemark(DataUtil.toHtml(remark));
		o.setOverdueflg(overdueflg);
		o.setUserId(loginUser.getUserId());
		o.setAmount(amount);
		Map<String, String> datamap = new HashMap<String, String>();
		if (o.isLimitDayStyle()) {
			datamap.put("limitday", String.valueOf(limitDay));
		}
		else {
			Date d = DataUtil.createDate(year, month, date);
			if (d != null) {
				datamap.put("endtime", String.valueOf(d.getTime()));
			}
		}
		o.setData(DataUtil.toJson(datamap));
		req.setAttribute("coupon", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return this.processToupdate(req, resp);
		}
		this.couponProcessor.updateCoupon(o, false, null);
		req.setSessionText("func.mgrsite.coupon.update_ok");
		return "r:/epp/mgr/coupon_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setunavailable(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		this.couponService.setUserflg(couponId, Coupon.USEFLG_UNAVAILABLE);
		req.setSessionText("func.mgrsite.coupon.setunavailable_ok");
		return "r:/epp/mgr/coupon_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setavailable(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long couponId = req.getLong("couponId");
		this.couponService.setUserflg(couponId, Coupon.USEFLG_AVAILABLE);
		req.setSessionText("func.mgrsite.coupon.setavailable_ok");
		return "r:/epp/mgr/coupon_list.do?companyId=" + companyId;
	}
}