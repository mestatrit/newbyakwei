package com.hk.web.company.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpTable;
import com.hk.bean.CmpTablePhoto;
import com.hk.bean.CmpTablePhotoSet;
import com.hk.bean.Photo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpTableService;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 管理产品图片，具有足迹管理权限的人可管理
 * 
 * @author akwei
 */
@Component("/e/op/auth/table/photo")
public class OpCmpTablePhotoAction extends BaseAction {

	@Autowired
	private CmpTableService cmpTableService;

	@Autowired
	private PhotoService photoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long tableId = req.getLongAndSetAttr("tableId");
		long setId = req.getLongAndSetAttr("setId");
		CmpTable cmpTable = this.cmpTableService.getCmpTable(tableId);
		req.setAttribute("cmpTable", cmpTable);
		List<CmpTablePhoto> list = this.cmpTableService
				.getCmpTablePhotoListBySetId(setId);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/table/photo/op/photolist.jsp");
	}

	/**
	 * 最多只能上传30张
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String upload(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int setId = req.getInt("setId");
		CmpTablePhotoSet set = this.cmpTableService.getCmpTablePhotoSet(setId);
		if (set == null) {
			return null;
		}
		File[] files = req.getFiles();
		User loginUser = this.getLoginUser(req);
		int successnum = 0;
		int errornum = 0;
		List<Long> idList = new ArrayList<Long>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					continue;
				}
				List<CmpTablePhoto> list = this.cmpTableService
						.getCmpTablePhotoListBySetId(setId);
				if (list.size() == 5) {// 多于5张，就不能上传了
					return this.onError(req, Err.CMPTABLEPHOTO_OUT_OF_LIMIT,
							"onuploaderror", successnum);
				}
				// 生成用户图片库记录
				Photo photo = new Photo();
				photo.setUserId(loginUser.getUserId());
				try {
					this.photoService.createPhoto(photo, files[i], 2);
					// 生成产品图片库记录
					CmpTablePhoto cmpTablePhoto = new CmpTablePhoto();
					cmpTablePhoto.setCompanyId(companyId);
					cmpTablePhoto.setPhotoId(photo.getPhotoId());
					cmpTablePhoto.setPath(photo.getPath());
					cmpTablePhoto.setSetId(setId);
					this.cmpTableService.createCmpTablePhoto(cmpTablePhoto);
					idList.add(cmpTablePhoto.getOid());
					if (DataUtil.isEmpty(set.getPath())) {
						set.setPath(photo.getPath());
						this.cmpTableService.updateCmpTablePhotoSet(set);
					}
					successnum++;
				}
				catch (Exception e) {
					errornum++;
					e.printStackTrace();
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (Long id : idList) {
			sb.append(id).append(",");
		}
		return this.onSuccess2(req, "onuploadsuccess", errornum + ";"
				+ sb.toString());
	}

	/**
	 * 删除台面图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		CmpTablePhoto cmpTablePhoto = this.cmpTableService
				.getCmpTablePhoto(oid);
		if (cmpTablePhoto != null && cmpTablePhoto.getCompanyId() == companyId) {
			this.cmpTableService.deleteCmpTablePhoto(oid);
			this.setOpFuncSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 到创建图集页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String photosetlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpTableService
				.countCmpTablePhotoSetByCompanyId(companyId));
		List<CmpTablePhotoSet> setlist = this.cmpTableService
				.getCmpTablePhotoSetListByCompanyId(companyId, page.getBegin(),
						page.getSize());
		req.setAttribute("setlist", setlist);
		req.reSetAttribute("tableId");
		return this.getWeb3Jsp("e/table/photo/op/setlist.jsp");
	}

	/**
	 * 到图集页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String photoset(HkRequest req, HkResponse resp) {
		long setId = req.getLongAndSetAttr("setId");
		CmpTablePhotoSet cmpTablePhotoSet = this.cmpTableService
				.getCmpTablePhotoSet(setId);
		req.setAttribute("cmpTablePhotoSet", cmpTablePhotoSet);
		List<CmpTablePhoto> photolist = this.cmpTableService
				.getCmpTablePhotoListBySetId(setId);
		req.setAttribute("photolist", photolist);
		req.reSetAttribute("companyId");
		req.reSetAttribute("tableId");
		return this.getWeb3Jsp("e/table/photo/op/photoset.jsp");
	}

	/**
	 * 创建图集
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createphotoset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String title = req.getString("title");
		String intro = req.getString("intro");
		CmpTablePhotoSet set = new CmpTablePhotoSet();
		set.setTitle(DataUtil.toHtmlRow(title));
		set.setIntro(DataUtil.toHtmlRow(intro));
		set.setCompanyId(companyId);
		int code = set.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "oncreateseterror", null);
		}
		this.cmpTableService.createCmpTablePhotoSet(set);
		return this.onSuccess2(req, "oncreatesetsuccess", set.getSetId());
	}

	/**
	 * 修改图片标题
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatephotoname(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpTablePhoto cmpTablePhoto = this.cmpTableService
				.getCmpTablePhoto(oid);
		if (cmpTablePhoto == null) {
			return null;
		}
		if (cmpTablePhoto.getCompanyId() == companyId) {
			String name = req.getString("name");
			cmpTablePhoto.setName(DataUtil.toHtmlRow(name));
			int code = cmpTablePhoto.validate();
			if (code != Err.SUCCESS) {
				return this.onError(req, code, "onupdatephotonameerror", null);
			}
			this.cmpTableService.updateCmpTablePhoto(cmpTablePhoto);
		}
		return this.onSuccess2(req, "onupdatephotonamesuccess", null);
	}

	/**
	 * 修改图片标题
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadphoto(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		CmpTablePhoto cmpTablePhoto = this.cmpTableService
				.getCmpTablePhoto(oid);
		req.setAttribute("cmpTablePhoto", cmpTablePhoto);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/table/photo/op/photo_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLongAndSetAttr("setId");
		CmpTablePhotoSet set = this.cmpTableService.getCmpTablePhotoSet(setId);
		if (set == null) {
			return null;
		}
		if (set.getCompanyId() != companyId) {
			return null;
		}
		this.cmpTableService.deleteCmpTablePhotoSet(setId);
		this.setOpFuncSuccessMsg(req);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selset(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLong("setId");
		long tableId = req.getLong("tableId");
		CmpTablePhotoSet set = this.cmpTableService.getCmpTablePhotoSet(setId);
		if (set == null || set.getCompanyId() != companyId) {
			return null;
		}
		CmpTable table = this.cmpTableService.getCmpTable(tableId);
		if (table == null || table.getCompanyId() != companyId) {
			return null;
		}
		this.cmpTableService.updateCmpTableSetId(tableId, setId);
		req.setSessionText("func.company.mgr.table.selsetok");
		return "r:/e/op/auth/table.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setphotosethead(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long setId = req.getLong("setId");
		long oid = req.getLong("oid");
		CmpTablePhotoSet set = this.cmpTableService.getCmpTablePhotoSet(setId);
		if (set == null || set.getCompanyId() != companyId) {
			return null;
		}
		CmpTablePhoto photo = this.cmpTableService.getCmpTablePhoto(oid);
		if (photo == null || photo.getCompanyId() != companyId) {
			return null;
		}
		set.setPath(photo.getPath());
		this.cmpTableService.updateCmpTablePhotoSet(set);
		this.setOpFuncSuccessMsg(req);
		return null;
	}
}