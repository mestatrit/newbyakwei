package com.hk.web.hk4.venue.opvenue.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.processor.CompanyPhotoProcessor;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.UploadCompanyPhotoResult;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 足迹后台管理
 * 
 * @author akwei
 */
@Component("/h4/op/venue/photo")
public class PhotoAction extends BaseAction {

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private CmpSvrService cmpSvrService;

	public String execute(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CompanyPhoto> list = this.companyPhotoService
				.getPhotoListByCompanyId(companyId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/op2/photo/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-23
	 */
	public String view(HkRequest req, HkResponse resp) {
		req.reSetAttribute("companyId");
		long photoId = req.getLongAndSetAttr("photoId");
		int size = req.getInt("size", 600);
		req.setAttribute("size", size);
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		req.setAttribute("companyPhoto", companyPhoto);
		return this.getWeb4Jsp("venue/op2/photo/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String delpic(HkRequest req, HkResponse resp) {
		long photoId = req.getLongAndSetAttr("photoId");
		long companyId = req.getLongAndSetAttr("companyId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null || companyPhoto.getCompanyId() != companyId) {
			return null;
		}
		this.companyPhotoProcessor.deleteCompanyPhoto(photoId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
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
	public String upload(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("venue/op2/photo/upload.jsp");
		}
		long userId = this.getLoginUser(req).getUserId();
		UploadCompanyPhotoResult uploadCompanyPhotoResult = this.companyPhotoProcessor
				.createCompanyPhoto(companyId, userId, req.getFiles());
		if (uploadCompanyPhotoResult.getErrorCode() != Err.SUCCESS) {
			return this.onError(req, uploadCompanyPhotoResult.getErrorCode(),
					"uploaderror", null);
		}
		if (!uploadCompanyPhotoResult.isAllImgUploadSuccess()) {
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
	 * 创建图集
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String createphotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		if (this.isForwardPage(req)) {
			return this.getWeb4Jsp("venue/op2/photoset/create.jsp");
		}
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
		long companyId = req.getLongAndSetAttr("companyId");
		long setId = req.getLongAndSetAttr("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpPhotoSet", cmpPhotoSet);
			return this.getWeb4Jsp("venue/op2/photoset/update.jsp");
		}
		cmpPhotoSet.setName(req.getHtmlRow("name"));
		int code = cmpPhotoSet.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		this.companyPhotoService.updateCmpPhotoSet(cmpPhotoSet);
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
		long companyId = req.getLongAndSetAttr("companyId");
		List<CmpPhotoSet> list = this.companyPhotoService
				.getCmpPhotoSetListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWeb4Jsp("venue/op2/photoset/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-17
	 */
	public String delphotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long setId = req.getLong("setId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		if (cmpPhotoSet == null || cmpPhotoSet.getCompanyId() != companyId) {
			return null;
		}
		this.companyPhotoService.deleteCmpPhotoSet(companyId, setId);
		this.setDelSuccessMsg(req);
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
		long companyId = req.getLongAndSetAttr("companyId");
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
		return this.getWeb4Jsp("venue/op2/photoset/photoset.jsp");
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
	public String selphotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long photoId = req.getLongAndSetAttr("photoId");
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("companyPhoto", companyPhoto);
			List<CmpPhotoSetRef> reflist = this.companyPhotoProcessor
					.getCmpPhotoSetRefListByCompanyIdAndPhotoId(companyId,
							photoId, true);
			req.setAttribute("reflist", reflist);
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
			return this.getWeb4Jsp("venue/op2/photoset/selphotoset.jsp");
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
	 *         2010-8-23
	 */
	public String delcmpphotosetref(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpPhotoSetRef cmpPhotoSetRef = this.companyPhotoService
				.getCmpPhotoSetRef(companyId, oid);
		if (cmpPhotoSetRef == null
				|| cmpPhotoSetRef.getCompanyId() != companyId) {
			return null;
		}
		this.companyPhotoService.deleteCmpPhotosetRef(companyId, oid);
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
		CmpPhotoSet selCmpPhotoSet = null;
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpSvr", cmpSvr);
			List<CmpPhotoSet> list = this.companyPhotoService
					.getCmpPhotoSetListByCompanyId(companyId);
			for (CmpPhotoSet o : list) {
				if (o.getSetId() == cmpSvr.getPhotosetId()) {
					selCmpPhotoSet = o;
					list.remove(o);
					break;
				}
			}
			req.setAttribute("list", list);
			req.setAttribute("selCmpPhotoSet", selCmpPhotoSet);
			return this.getWeb4Jsp("venue/op2/photoset/selphotosetforsvr.jsp");
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
}