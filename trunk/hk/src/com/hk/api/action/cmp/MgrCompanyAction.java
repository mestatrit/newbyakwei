package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;

// @Component("/pubapi/protect/cmp/mgr")
public class MgrCompanyAction extends BaseApiAction {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

//	public String uploadlogo(HkRequest req, HkResponse resp) {
//		File file = req.getFile("f");
//		if (file == null) {
//			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
//			return null;
//		}
//		long companyId = req.getLong("companyId");
//		if (HkSvrUtil.isNotCompany(companyId)) {
//			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
//			return null;
//		}
//		try {
//			this.companyService.updateLogo(companyId, file);
//			APIUtil.sendSuccessRespStatus(resp);
//			return null;
//		}
//		catch (IOException e) {
//			APIUtil.sendFailRespStatus(resp, Err.IMG_UPLOAD_ERROR);
//			return null;
//		}
//	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		String traffic = req.getString("traffic");
		Company o = this.companyService.getCompany(companyId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtmlRow(addr));
		o.setIntro(DataUtil.toHtmlRow(intro));
		o.setTraffic(DataUtil.toHtmlRow(traffic));
		req.setAttribute("o", o);
		HkSvrUtil.setZone(req.getString("zoneName"), o);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		this.companyService.updateCompany(o);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String sethead(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto o = this.companyPhotoService.getCompanyPhoto(photoId);
		if (o == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		this.companyService.updateHeadPath(companyId, o.getPath());
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	/**
	 * 管理我的企业，如果认领的企业只有1个，就直接进入.有多个，就列表显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updatemap(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		double markerX = req.getDouble("marker_x");
		double markerY = req.getDouble("marker_y");
		o.setMarkerX(markerX);
		o.setMarkerY(markerY);
		this.companyService.updateCompany(o);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}