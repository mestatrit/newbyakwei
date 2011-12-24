package com.hk.api.action.cmp;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;

// @Component("/pubapi/protect/cmpphoto")
public class ProtectPhotoAction extends BaseApiAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

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
	// public String create(HkRequest req, HkResponse resp) throws Exception {
	// SessionKey sessionKey = this.getSessionKey(req);
	// File file = req.getFile("f");
	// if (file == null) {
	// APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
	// return null;
	// }
	// long companyId = req.getLong("companyId");
	// List<CompanyPhoto> list = this.companyPhotoService
	// .getPhotoListByCompanyId(companyId, 0, 301);
	// if (list.size() > 300) {
	// APIUtil.sendFailRespStatus(resp, Err.COMPANYPHOTO_OUT_OF_COUNT);
	// return null;
	// }
	// String name = req.getString("name");
	// if (!DataUtil.isEmpty(name)) {
	// name = DataUtil.toHtmlRow(name);
	// }
	// Photo photo = new Photo();
	// photo.setName(name);
	// photo.setUserId(sessionKey.getUserId());
	// req.setAttribute("o", photo);
	// int code = photo.validate();
	// if (code != Err.SUCCESS) {
	// APIUtil.sendFailRespStatus(resp, code);
	// return null;
	// }
	// Company o = this.companyService.getCompany(companyId);
	// if (o == null) {
	// APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
	// return null;
	// }
	// try {
	// this.photoService.createPhoto(photo, file, 1);
	// CompanyPhoto companyPhoto = new CompanyPhoto();
	// companyPhoto.setName(name);
	// companyPhoto.setCompanyId(companyId);
	// companyPhoto.setUserId(sessionKey.getUserId());
	// companyPhoto.setPhotoId(photo.getPhotoId());
	// companyPhoto.setPath(photo.getPath());
	// companyPhoto.setCreateTime(photo.getCreateTime());
	// this.companyPhotoService.createPhoto(companyPhoto);
	// if (DataUtil.isEmpty(o.getHeadPath())) {
	// this.companyService.updateHeadPath(companyId, companyPhoto
	// .getPath());
	// }
	// APIUtil.sendSuccessRespStatus(resp);
	// return null;
	// }
	// catch (ImageException e) {
	// APIUtil.sendFailRespStatus(resp, Err.IMG_UPLOAD_ERROR);
	// return null;
	// }
	// catch (NotPermitImageFormatException e) {
	// APIUtil.sendFailRespStatus(resp, Err.IMG_FMT_ERROR);
	// return null;
	// }
	// catch (OutOfSizeException e) {
	// APIUtil.sendFailRespStatus(resp, Err.IMG_UPLOAD_ERROR);
	// return null;
	// }
	// }
	/**
	 * 足迹所有者和自己可以删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			APIUtil.sendFailRespStatus(resp, Err.NOOBJECT_ERROR);
			return null;
		}
		Company company = this.companyService.getCompany(companyId);
		if (company.getUserId() == o.getUserId()
				|| companyPhoto.getUserId() == o.getUserId()) {
			this.companyPhotoService.deleteCompanhPhoto(photoId);
		}
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}
}