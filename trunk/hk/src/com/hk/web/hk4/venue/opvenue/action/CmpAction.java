package com.hk.web.hk4.venue.opvenue.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpOtherInfo;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 足迹后台管理
 * 
 * @author akwei
 */
@Component("/h4/op/venue/cmp")
public class CmpAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.update(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		if (ch == 0) {
			return this.getWeb4Jsp("venue/op2/update.jsp");
		}
		company.setName(req.getHtmlRow("name"));
		company.setAddr(req.getHtml("addr"));
		company.setTel(req.getHtmlRow("tel"));
		company.setPcityId(req.getInt("pcityId"));
		company.setTraffic(req.getHtmlRow("traffic"));
		company.setSname(req.getHtmlRow("sname"));
		String intro = req.getString("intro");
		company.setIntro(DataUtil.toHtml(DataUtil.subString(intro, 2000)));
		List<Integer> errlist = company.validateList();
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		this.companyService.updateCompany(company);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatetime(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("company", company);
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		if (ch == 0) {
			req.setAttribute("cmpOtherInfo", cmpOtherInfo);
			return this.getWeb4Jsp("venue/op2/updatetime.jsp");
		}
		String begin = req.getHtmlRow("begin");
		String end = req.getHtmlRow("end");
		Date begintime = DataUtil.parseTime(begin, "HH:mm");
		Date endtime = DataUtil.parseTime(end, "HH:mm");
		if (cmpOtherInfo == null) {
			cmpOtherInfo = new CmpOtherInfo();
			cmpOtherInfo.setCompanyId(companyId);
			if (begintime != null && endtime != null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("b", DataUtil.getFormatTimeData(begintime, "HH:mm"));
				map.put("e", DataUtil.getFormatTimeData(endtime, "HH:mm"));
				cmpOtherInfo.setDurationdata(DataUtil.toJson(map));
			}
			int code = cmpOtherInfo.validate();
			if (code != Err.SUCCESS) {
				return this.onError(req, code, "updateerr", null);
			}
			this.companyService.createCmpOtherInfo(cmpOtherInfo);
		}
		else {
			if (begintime != null && endtime != null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("b", DataUtil.getFormatTimeData(begintime, "HH:mm"));
				map.put("e", DataUtil.getFormatTimeData(endtime, "HH:mm"));
				cmpOtherInfo.setDurationdata(DataUtil.toJson(map));
			}
			int code = cmpOtherInfo.validate();
			if (code != Err.SUCCESS) {
				return this.onError(req, code, "updateerr", null);
			}
			this.companyService.updateCmpOtherInfo(cmpOtherInfo);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatesvrrate(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		CmpOtherInfo cmpOtherInfo = this.companyService
				.getCmpOtherInfo(companyId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpOtherInfo", cmpOtherInfo);
			return this.getWeb4Jsp("venue/op2/updatesvrrate.jsp");
		}
		if (cmpOtherInfo == null) {
			cmpOtherInfo = new CmpOtherInfo();
			cmpOtherInfo.setCompanyId(companyId);
			cmpOtherInfo.setSvrrate(req.getInt("svrrate"));
			this.companyService.createCmpOtherInfo(cmpOtherInfo);
		}
		else {
			cmpOtherInfo.setSvrrate(req.getInt("svrrate"));
			this.companyService.updateCmpOtherInfo(cmpOtherInfo);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}
}