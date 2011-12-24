package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;

import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CompanyPhotoProcessor;

@Deprecated
// @Component("/epp/mgr/photo")
public class Sys_DelMgrPhotoAction extends EppBaseAction {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

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
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		return this.processtoadd(req);
	}

	private String processtoadd(HkRequest req) {
		return this.getMgrJspPath(req, "photo/add.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	// public String add(HkRequest req, HkResponse resp) throws Exception {
	// long companyId = req.getLong("companyId");
	// File file = req.getFile("f");
	// if (file == null) {
	// return "r:/epp/mgr/photo_toadd.do?companyId=" + companyId;
	// }
	// String name = req.getString("name");
	// User loginUser = this.getLoginUser2(req);
	// List<CompanyPhoto> list = this.companyPhotoService
	// .getPhotoListByCompanyId(companyId, 0, 301);
	// if (list.size() > 300) {
	// req.setSessionText(String.valueOf(Err.COMPANYPHOTO_OUT_OF_COUNT));
	// return "r:/epp/mgr/photo_list.do?companyId=" + companyId;
	// }
	// if (!DataUtil.isEmpty(name)) {
	// name = DataUtil.subString(name, 20);
	// name = DataUtil.toHtmlRow(name);
	// }
	// Photo photo = new Photo();
	// photo.setName(name);
	// photo.setUserId(loginUser.getUserId());
	// req.setAttribute("o", photo);
	// Company o = this.companyService.getCompany(companyId);
	// try {
	// this.photoService.createPhoto(photo, file, 2);
	// CompanyPhoto companyPhoto = new CompanyPhoto();
	// companyPhoto.setName(name);
	// companyPhoto.setCompanyId(companyId);
	// companyPhoto.setUserId(loginUser.getUserId());
	// companyPhoto.setPhotoId(photo.getPhotoId());
	// companyPhoto.setPath(photo.getPath());
	// companyPhoto.setCreateTime(photo.getCreateTime());
	// this.companyPhotoService.createPhoto(companyPhoto);
	// if (DataUtil.isEmpty(o.getHeadPath())) {
	// this.companyService.updateHeadPath(companyId, companyPhoto
	// .getPath());
	// }
	// return "r:/epp/mgr/photo_list.do?companyId=" + companyId;
	// }
	// catch (ImageException e) {
	// req.setSessionText(String.valueOf(Err.IMG_UPLOAD_ERROR));
	// return "r:/epp/mgr/photo_toadd.do?companyId=" + companyId;
	// }
	// catch (NotPermitImageFormatException e) {
	// req.setSessionText(String.valueOf(Err.IMG_FMT_ERROR));
	// return "r:/epp/mgr/photo_toadd.do?companyId=" + companyId;
	// }
	// catch (OutOfSizeException e) {
	// req.setSessionText(String.valueOf(Err.IMG_OUTOFSIZE_ERROR), "2M");
	// return "r:/epp/mgr/photo_toadd.do?companyId=" + companyId;
	// }
	// }
	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = ServletUtil.getSimplePage(req, 6);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getMgrJspPath(req, "photo/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String big(HkRequest req, HkResponse resp) throws Exception {
		long photoId = req.getLongAndSetAttr("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		req.setAttribute("cmpPhoto", companyPhoto);
		return this.getMgrJspPath(req, "photo/big.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sethead(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		CompanyPhoto o = this.companyPhotoService.getCompanyPhoto(photoId);
		if (o == null) {
			return "r:/epp/mgr/photo_list.do?companyId=" + companyId;
		}
		this.companyService.updateHeadPath(companyId, o.getPath());
		req.setSessionText("func.mgrsite.photo.sethead_ok");
		return "r:/epp/mgr/photo_list.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long photoId = req.getLong("photoId");
		this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
		return "r:/epp/mgr/photo_list.do?companyId=" + req.getLong("companyId");
	}
}