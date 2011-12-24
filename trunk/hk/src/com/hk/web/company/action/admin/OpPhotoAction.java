package com.hk.web.company.action.admin;

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
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.PhotoService;
import com.hk.svr.processor.CompanyPhotoProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/photo/photo")
public class OpPhotoAction extends BaseAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 管理图片列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company == null) {
			return null;
		}
		req.setAttribute("headPath", company.getHeadPath());
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		req.setAttribute("list", list);
		return this.getWeb3Jsp("e/photo/op/list.jsp");
	}

	/**
	 * 小图片列表，选择头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String smallphoto(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = 5;
		Company company = this.companyService.getCompany(companyId);
		SimplePage page = req.getSimplePage(size);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, page.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("company", company);
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/photo/op/smallphoto.jsp";
	}

	/**
	 * 小图片列表，选择头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String showbig(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		Company company = this.companyService.getCompany(companyId);
		CompanyPhoto o = this.companyPhotoService.getCompanyPhoto(photoId);
		req.setAttribute("company", company);
		req.setAttribute("o", o);
		req.setAttribute("photoId", photoId);
		req.setAttribute("companyId", companyId);
		req.reSetAttribute("repage");
		return "/WEB-INF/page/e/photo/op/bigphoto.jsp";
	}

	/**
	 * 到上传图片页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 31);
		if (list.size() > 30) {
			req.setSessionMessage(req.getText("func.photocountoutofsize"));
			return "r:/e/cmp.do?companyId=" + companyId;
		}
		req.reSetAttribute("companyId");
		return "/WEB-INF/page/e/photo/op/upload.jsp";
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 31);
		if (list.size() > 30) {
			req.setSessionMessage(req.getText("func.photocountoutofsize"));
			return "r:/e/cmp.do?companyId=" + companyId;
		}
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			name = DataUtil.toHtmlRow(name);
		}
		User loginUser = this.getLoginUser(req);
		Photo photo = new Photo();
		photo.setName(name);
		photo.setUserId(loginUser.getUserId());
		req.setAttribute("o", photo);
		int code = photo.validate();
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/e/op/photo/photo_toadd.do";
		}
		File file = req.getFile("f");
		Company o = this.companyService.getCompany(companyId);
		if (o != null) {
			try {
				this.photoService.createPhoto(photo, file, 1);
				CompanyPhoto companyPhoto = new CompanyPhoto();
				companyPhoto.setName(name);
				companyPhoto.setCompanyId(companyId);
				companyPhoto.setUserId(loginUser.getUserId());
				companyPhoto.setPhotoId(photo.getPhotoId());
				companyPhoto.setPath(photo.getPath());
				companyPhoto.setCreateTime(photo.getCreateTime());
				this.companyPhotoService.createPhoto(companyPhoto);
				if (DataUtil.isEmpty(o.getHeadPath())) {
					this.companyService.updateHeadPath(companyId, companyPhoto
							.getPath());
				}
				req.setSessionMessage(req.getText("op.uploadimgok"));
			}
			catch (ImageException e) {
				e.printStackTrace();
				req.setSessionMessage(req.getText("func.imageuploaderror"));
				return "r:/e/op/photo/photo_toadd.do";
			}
			catch (NotPermitImageFormatException e) {
				e.printStackTrace();
				req.setSessionMessage(req.getText("func.imagetypeerror"));
				return "r:/e/op/photo/photo_toadd.do";
			}
			catch (OutOfSizeException e) {
				e.printStackTrace();
				req.setSessionMessage(req.getText("func.imageoutofsize"));
				return "r:/e/op/photo/photo_toadd.do";
			}
		}
		return "r:/e/photo.do?companyId=" + companyId;
	}

	/**
	 * 设置头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sethead(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto photo = this.companyPhotoService.getCompanyPhoto(photoId);
		if (photo != null) {
			this.companyService.updateHeadPath(companyId, photo.getPath());
			req.setSessionMessage(req.getText("op.setheadok"));
		}
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 删除企业中的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto photo = this.companyPhotoService.getCompanyPhoto(photoId);
		if (photo != null) {
			if (photo.getUserId() == this.getLoginUser(req).getUserId()) {
				this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
				req.setSessionMessage(req.getText("op.exeok"));
			}
		}
		return "r:/e/op/photo/photo_smallphoto.do?companyId=" + companyId
				+ "&page=" + req.getInt("repage");
	}

	/**
	 * 删除企业中的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delweb(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto photo = this.companyPhotoService.getCompanyPhoto(photoId);
		if (photo != null) {
			if (photo.getUserId() == this.getLoginUser(req).getUserId()) {
				this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
				req.setSessionText("op.exeok");
			}
		}
		return "r:/e/op/photo/photo_list.do?companyId=" + companyId;
	}

	/**
	 * 精华图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setpinkflg(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		this.companyPhotoService.updatePinkflg(photoId, CompanyPhoto.PINKFLG_Y);
		req.setSessionText("op.exeok");
		return "r:/e/op/photo/photo_list.do?companyId=" + companyId;
	}

	/**
	 * 删除精华图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delpinkflg(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		this.companyPhotoService.updatePinkflg(photoId, CompanyPhoto.PINKFLG_N);
		req.setSessionText("op.exeok");
		return "r:/e/op/photo/photo_list.do?companyId=" + companyId;
	}

	/**
	 * 设置头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setheadweb(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto photo = this.companyPhotoService.getCompanyPhoto(photoId);
		if (photo != null) {
			this.companyService.updateHeadPath(companyId, photo.getPath());
			req.setSessionText("op.exeok");
		}
		return "r:/e/op/photo/photo_list.do?companyId=" + companyId;
	}

	public String mgr(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLongAndSetAttr("companyId");
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, 0, 30);
		if (list.size() == 30) {
			resp.alertJSAndGoBack(req.getText("view.cmp.photo.toomany"));
			return null;
		}
		return this.getWeb3Jsp("/e/photo/op/upload_mgr.jsp");
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
					this.photoService.createPhoto(photo, files[i], 1);
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
	public String mgrtoedit(HkRequest req, HkResponse resp) {
		long[] pid = req.getLongs("pid");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < pid.length; i++) {
			idList.add(pid[i]);
		}
		List<Photo> list = this.photoService.getPhotoListInId(idList);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/photo/op/mgredit.jsp");
	}

	/**
	 * 编辑刚刚上传的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String mgredit(HkRequest req, HkResponse resp) {
		long[] pid = req.getLongs("pid");
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
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