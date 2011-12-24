package web.epp.mgr.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.CmpWebColor;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.frame.web.http.HttpUtil;
import com.hk.svr.CmpInfoService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.processor.CmpInfoProcessor;
import com.hk.svr.processor.CompanyPhotoProcessor;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.UploadCompanyPhotoResult;
import com.hk.svr.pub.Err;

/**
 * 企业基本信息
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/info")
public class InfoAction extends EppBaseAction {

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpInfoProcessor cmpInfoProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 企业环境图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String piclist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		req.setAttribute("active_21", 1);
		SimplePage page = req.getSimplePage(20);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		req.setReturnUrl("/epp/web/op/webadmin/info_piclist.do?companyId="
				+ companyId + "&page=" + req.getPage());
		return this.getWebPath("admin/info/piclist.jsp");
	}

	/**
	 * 企业环境图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String delpic(HkRequest req, HkResponse resp) {
		long photoId = req.getLong("photoId");
		this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 设置企业头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String sethead(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long photoId = req.getLong("photoId");
		this.companyProcessor.updateHeadPath(companyId, photoId);
		req.setSessionText("epp.setcmphead.ok");
		return null;
	}

	/**
	 * 企业环境图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String uploadcmppic(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long userId = this.getLoginUser2(req).getUserId();
		UploadCompanyPhotoResult uploadCompanyPhotoResult = this.companyPhotoProcessor
				.createCompanyPhoto(companyId, userId, req.getFiles());
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
		return this.onSuccess2(req, "uploadok", null);
	}

	/**
	 * 修改企业信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String update(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("active_20", 1);
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.setAttribute("company", company);
			return this.getWebPath("admin/info/update.jsp");
		}
		int oldPcityId = company.getPcityId();
		company.setName(req.getHtmlRow("name"));
		company.setTraffic(req.getHtml("traffic"));
		company.setIntro(req.getHtml("intro"));
		company.setTel(req.getHtmlRow("tel"));
		company.setAddr(req.getHtml("addr"));
		company.setPcityId(req.getInt("pcityId"));
		List<Integer> errorlist = company.validateList();
		if (errorlist.size() > 0) {
			return this.onErrorList(req, errorlist, "updateerrorlist");
		}
		this.companyProcessor.updateCompany(company, oldPcityId);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 上传logo
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String uploadlogo(HkRequest req, HkResponse resp) {
		req.setAttribute("active_14", 1);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/info/uploadlogo.jsp");
		}
		Company company = (Company) req.getAttribute("o");
		int error = this.companyProcessor.uploadLogo(company, req.getFile("f"),
				req.getFile("f2"));
		if (error != Err.SUCCESS) {
			return this.onError(req, error, "uploaderror", null);
		}
		return this.onSuccess2(req, "uploadok", null);
	}

	/**
	 * 自定义前端颜色设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String styledata(HkRequest req, HkResponse resp) {
		req.setAttribute("active_25", 1);
		return this.getWebPath("admin/info/styledata.jsp");
	}

	/**
	 * 自定义前端颜色设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String updatestyle(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		if (req.getInt("ch") == 0) {
			CmpWebColor cmpWebColor = new CmpWebColor(cmpInfo.getStyleData());
			req.setAttribute("active_25", 1);
			req.setAttribute("cmpWebColor", cmpWebColor);
			return this.getWebPath("admin/info/updatestyle.jsp");
		}
		CmpWebColor cmpWebColor = new CmpWebColor();
		cmpWebColor.setColumnBgColor(req.getHtmlRow("columnBgColor"));
		cmpWebColor.setColumnLinkHoverBgColor(req
				.getHtmlRow("columnLinkHoverBgColor"));
		cmpWebColor.setColumnLinkColor(req.getHtmlRow("columnLinkColor"));
		cmpWebColor.setHomeProductBgColor(req.getHtmlRow("homeProductBgColor"));
		cmpWebColor.setHomeTitleLinkColor(req.getHtmlRow("homeTitleLinkColor"));
		cmpWebColor.setLinkColor(req.getHtmlRow("linkColor"));
		cmpWebColor.setColumn2BgColor(req.getHtmlRow("column2BgColor"));
		cmpWebColor.setColumn2Color(req.getHtmlRow("column2Color"));
		cmpWebColor.setButtonBgColor(req.getHtmlRow("buttonBgColor"));
		cmpWebColor.setButtonBorderColor(req.getHtmlRow("buttonBorderColor"));
		cmpWebColor.setButtonColor(req.getHtmlRow("buttonColor"));
		cmpWebColor.setUserNavBgColor(req.getHtmlRow("userNavBgColor"));
		cmpWebColor.setUserNavLinkColor(req.getHtmlRow("userNavLinkColor"));
		cmpWebColor.setHomeModTitleLinkColor(req
				.getHtmlRow("homeModTitleLinkColor"));
		cmpWebColor.setColumn2NavLinkActiveColor(req
				.getHtmlRow("column2NavLinkActiveColor"));
		cmpWebColor.setColumn2NavLinkColor(req
				.getHtmlRow("column2NavLinkColor"));
		cmpWebColor.setFontColor(req.getHtmlRow("fontColor"));
		cmpInfo.setStyleData(cmpWebColor.toJsonData());
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 自定义前端颜色设置
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String setstyleinuse(HkRequest req, HkResponse resp) {
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		int userstyle = req.getInt("userstyle");
		if (userstyle == 0) {
			cmpInfo.setStyleflg(CmpInfo.STYLEFLG_N);
		}
		else {
			cmpInfo.setStyleflg(CmpInfo.STYLEFLG_Y);
		}
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		return null;
	}

	/**
	 * 刷新网站首页
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String refreshhomepage(HkRequest req, HkResponse resp)
			throws Exception {
		long companyId = req.getLong("companyId");
		HttpUtil httpUtil = new HttpUtil();
		httpUtil.executegetMethodWithoutResponse("http://"
				+ req.getServerName() + "/epp/index.do?companyId=" + companyId
				+ "&needRefresh=true");
		req.setSessionText("epp.homepage.refresh.success");
		return null;
	}

	/**
	 * 修改企业广告代码，文章页面广告，栏目页面广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String updatecmpinfoad(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/info/updatecmpinfoad.jsp");
		}
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		cmpInfo.setArticlead(req.getString("articlead"));
		cmpInfo.setColumnad(req.getString("columnad"));
		int code = cmpInfo.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 创建图集
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String createphotoset(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/photoset/create.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpPhotoSet cmpPhotoSet = new CmpPhotoSet();
		cmpPhotoSet.setCompanyId(companyId);
		cmpPhotoSet.setName(req.getHtmlRow("name"));
		int code = cmpPhotoSet.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.companyPhotoService.createCmpPhotoSet(cmpPhotoSet);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改图集
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String updatephotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLongAndSetAttr("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpPhotoSet", cmpPhotoSet);
			return this.getWebPath("admin/photoset/update.jsp");
		}
		cmpPhotoSet.setName(req.getHtmlRow("name"));
		int code = cmpPhotoSet.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.companyPhotoService.createCmpPhotoSet(cmpPhotoSet);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String photosetlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CmpPhotoSet> list = this.companyPhotoService
				.getCmpPhotoSetListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWebPath("admin/photoset/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String selphotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long photoId = req.getLongAndSetAttr("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("companyPhoto", companyPhoto);
			List<CmpPhotoSetRef> reflist = this.companyPhotoService
					.getCmpPhotoSetRefListByCompanyIdAndPhotoId(companyId,
							photoId);
			List<Long> idList = new ArrayList<Long>();
			for (CmpPhotoSetRef o : reflist) {
				idList.add(o.getSetId());
			}
			List<CmpPhotoSet> list = this.companyPhotoService
					.getCmpPhotoSetListByCompanyId(companyId);
			List<CmpPhotoSet> newlist = new ArrayList<CmpPhotoSet>();
			for (CmpPhotoSet o : list) {
				if (!idList.contains(o.getSetId())) {
					newlist.add(o);
				}
			}
			req.setAttribute("list", newlist);
			return this.getWebPath("admin/photoset/selphotoset.jsp");
		}
		long setId = req.getLong("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (cmpPhotoSet == null) {
			return null;
		}
		CmpPhotoSetRef cmpPhotoSetRef = new CmpPhotoSetRef();
		cmpPhotoSetRef.setCompanyId(companyId);
		cmpPhotoSetRef.setPhotoId(photoId);
		cmpPhotoSetRef.setSetId(setId);
		this.companyPhotoService.createCmpPhotoSetRef(cmpPhotoSetRef);
		if (DataUtil.isEmpty(cmpPhotoSet.getPicPath())) {
			cmpPhotoSet.setPicPath(companyPhoto.getPath());
			this.companyPhotoService.updateCmpPhotoSet(cmpPhotoSet);
		}
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String selphotosetforsvr(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long svrId = req.getLongAndSetAttr("svrId");
		CmpSvr cmpSvr = this.cmpSvrService.getCmpSvr(companyId, svrId);
		if (cmpSvr == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpSvr", cmpSvr);
			List<CmpPhotoSet> list = this.companyPhotoService
					.getCmpPhotoSetListByCompanyId(companyId);
			req.setAttribute("list", list);
			return this.getWebPath("admin/photoset/selphotosetforsvr.jsp");
		}
		long setId = req.getLong("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (cmpPhotoSet == null) {
			return null;
		}
		cmpSvr.setPhotosetId(req.getLong("setId"));
		this.cmpSvrService.updateCmpSvr(cmpSvr);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String photoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLongAndSetAttr("setId");
		SimplePage page = req.getSimplePage(20);
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		List<CmpPhotoSetRef> list = this.companyPhotoProcessor
				.getCmpPhotoSetRefListByCompanyIdAndSetId(companyId, setId,
						true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("cmpPhotoSet", cmpPhotoSet);
		req.setAttribute("list", list);
		req.setReturnUrl("/epp/web/op/webadmin/info_photoset.do?companyId="
				+ companyId + "&setId=" + setId + "&page=" + req.getPage());
		return this.getWebPath("admin/photoset/photoset.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String makesethead(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLong("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (cmpPhotoSet == null || cmpPhotoSet.getCompanyId() != companyId) {
			return null;
		}
		long photoId = req.getLong("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null || companyPhoto.getCompanyId() != companyId) {
			return null;
		}
		cmpPhotoSet.setPicPath(companyPhoto.getPath());
		this.companyPhotoService.updateCmpPhotoSet(cmpPhotoSet);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String rmphotofromset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		this.companyPhotoProcessor.deleteCmpPhotosetRef(companyId, oid);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String photo(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long photoId = req.getLongAndSetAttr("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		req.setAttribute("companyPhoto", companyPhoto);
		List<CmpPhotoSetRef> reflist = this.companyPhotoProcessor
				.getCmpPhotoSetRefListByCompanyIdAndPhotoId(companyId, photoId,
						true);
		req.setAttribute("reflist", reflist);
		return this.getWebPath("admin/info/photo.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-2
	 */
	public String updatebgimage(HkRequest req, HkResponse resp) {
		req.setAttribute("active_39", 1);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/info/bgimage.jsp");
		}
		long companyId = req.getLong("companyId");
		int code = this.cmpInfoProcessor.updateBgImage(companyId, req
				.getFile("f"));
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-2
	 */
	public String delbgimage(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		this.cmpInfoProcessor.deleteBgImage(companyId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-2
	 */
	public String updatecpinfo(HkRequest req, HkResponse resp) {
		req.setAttribute("active_40", 1);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/info/updatecpinfo.jsp");
		}
		CmpInfo cmpInfo = (CmpInfo) req.getAttribute("cmpInfo");
		cmpInfo.setCpinfo(req.getHtml("cpinfo"));
		int code = cmpInfo.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}
}