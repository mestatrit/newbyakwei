package com.hk.web.company.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.Photo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/op/uploadcmpphoto")
public class UploadCmpPhotoAction extends BaseAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		if (list.size() == 30) {
			resp.alertJSAndGoBack(req.getText("view.cmp.photo.toomany"));
			return null;
		}
		return this.getWeb3Jsp("/e/photo/op/upload.jsp");
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String upload(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		if (list.size() == 30) {
			return this.initError(req, Err.CMPPHOTO_OUT_OF_LIMIT, "upload");
		}
		User loginUser = this.getLoginUser(req);
		File[] files = req.getFiles();
		List<Long> plist = new ArrayList<Long>();
		int errornum = 0;
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					continue;
				}
				Photo photo = new Photo();
				photo.setName(null);
				photo.setUserId(loginUser.getUserId());
				try {
					this.photoService.createPhoto(photo, files[i], 2);
					plist.add(photo.getPhotoId());
				}
				catch (Exception e) {
					e.printStackTrace();
					errornum++;
				}
			}
		}
		if (errornum > 0) {
			req.setSessionText("funct.pic.upload.error.info", errornum + "");
		}
		StringBuilder sb = new StringBuilder();
		if (plist.size() > 0) {
			for (Long l : plist) {
				sb.append(l).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return this.onSuccess(req, sb.toString(), "upload");
	}

	/**
	 * 编辑刚刚上传的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toedit(HkRequest req, HkResponse resp) {
		long[] pid = req.getLongs("pid");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < pid.length; i++) {
			idList.add(pid[i]);
		}
		List<Photo> list = this.photoService.getPhotoListInId(idList);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/photo/op/edit.jsp");
	}

	/**
	 * 编辑刚刚上传的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String edit(HkRequest req, HkResponse resp) {
		long[] pid = req.getLongs("pid");
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		if (pid != null) {
			for (int i = 0; i < pid.length; i++) {
				String name = req.getString("name" + pid[i]);
				this.photoService.updateName(pid[i], name);
				Photo photo = this.photoService.getPhoto(pid[i]);
				if (photo != null) {
					CompanyPhoto companyPhoto = new CompanyPhoto();
					companyPhoto.setName(photo.getName());
					companyPhoto.setCompanyId(companyId);
					companyPhoto.setUserId(photo.getUserId());
					companyPhoto.setPhotoId(photo.getPhotoId());
					companyPhoto.setPath(photo.getPath());
					companyPhoto.setCreateTime(photo.getCreateTime());
					this.companyPhotoService.createPhoto(companyPhoto);
					if (DataUtil.isEmpty(o.getHeadPath())) {
						this.companyService.updateHeadPath(companyId,
								companyPhoto.getPath());
					}
				}
			}
		}
		req.setSessionText("func.uploadimg.ok");
		return this.initSuccess(req, "edit");
	}
}