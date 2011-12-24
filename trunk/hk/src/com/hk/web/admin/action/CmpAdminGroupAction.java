package com.hk.web.admin.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpAdminGroup;
import com.hk.bean.CmpAdminGroupRef;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdminGroupService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/cmpadmingroup")
public class CmpAdminGroupAction extends BaseAction {
	@Autowired
	private CmpAdminGroupService cmpAdminGroupService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		List<CmpAdminGroup> list = this.cmpAdminGroupService
				.getCmpAdminGroupList(null, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("admin/group/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String creategroup(HkRequest req, HkResponse resp) throws Exception {
		if (req.getInt("ch") == 0) {
			return this.getWapJsp("admin/group/creategroup.jsp");
		}
		String name = req.getString("name");
		CmpAdminGroup o = new CmpAdminGroup();
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/admin/cmpadmingroup_creategroup.do?ch=0";
		}
		req.setAttribute("o", o);
		if (!this.cmpAdminGroupService.createCmpAdminGroup(o)) {
			req.setText(String.valueOf(Err.CMPADMINGROUP_NAME_DUPLICATE));
			return "/admin/cmpadmingroup_creategroup.do?ch=0";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/cmpadmingroup.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editgroup(HkRequest req, HkResponse resp) throws Exception {
		long groupId = req.getLongAndSetAttr("groupId");
		CmpAdminGroup o = (CmpAdminGroup) req.getAttribute("o");
		if (o == null) {
			o = this.cmpAdminGroupService.getCmpAdminGroup(groupId);
		}
		req.setAttribute("o", o);
		if (req.getInt("ch") == 0) {
			return this.getWapJsp("admin/group/editgroup.jsp");
		}
		String name = req.getString("name");
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/admin/cmpadmingroup_editgroup.do?ch=0";
		}
		if (!this.cmpAdminGroupService.updateCmpAdminGroup(o)) {
			req.setText(String.valueOf(Err.CMPADMINGROUP_NAME_DUPLICATE));
			return "/admin/cmpadmingroup_editgroup.do?ch=0";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/cmpadmingroup.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deletegroup(HkRequest req, HkResponse resp) throws Exception {
		long groupId = req.getLong("groupId");
		this.cmpAdminGroupService.deleteCmpAdminGroup(groupId);
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/cmpadmingroup.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmplist(HkRequest req, HkResponse resp) throws Exception {
		long groupId = req.getLongAndSetAttr("groupId");
		SimplePage page = req.getSimplePage(20);
		List<CmpAdminGroupRef> list = this.cmpAdminGroupService
				.getCmpAdminGroupRefListByGroupId(groupId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		return this.getWapJsp("admin/group/cmplist/jsp");
	}
}