package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.CmpSellNet;
import com.hk.bean.Company;
import com.hk.frame.util.DesUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.CmpSellNetService;
import com.hk.svr.CompanyService;

@Component("/pub/cmpfunc")
public class CmpFuncAction extends BaseAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpSellNetService cmpSellNetService;

	@Autowired
	private CmpAdminUserService cmpAdminUserService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String savecmpsellnetpos(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		CmpSellNet cmpSellNet = this.cmpSellNetService.getCmpSellNet(oid);
		if (cmpSellNet == null) {
			return null;
		}
		long companyId = cmpSellNet.getCompanyId();
		String op = req.getString("op");
		if (op == null) {
			return null;
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		long userId = Long.valueOf(DesUtil.decode("opuserid", op));
		if (company.getUserId() != userId) {
			CmpAdminUser cmpAdminUser = this.cmpAdminUserService
					.getCmpAdminUserByCompanyIdAndUserId(companyId, userId);
			if (cmpAdminUser == null) {
				return null;
			}
		}
		double marker_x = req.getDouble("marker_x");
		double marker_y = req.getDouble("marker_y");
		cmpSellNet.setMapData(marker_x + "," + marker_y);
		this.cmpSellNetService.updateCmpSellNet(cmpSellNet);
		return null;
	}
}