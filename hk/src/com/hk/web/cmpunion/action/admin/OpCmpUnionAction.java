package com.hk.web.cmpunion.action.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.util.CmpUnionModuleUtil;
import web.pub.util.CmpUnionSite;

import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionCmdKind;
import com.hk.bean.CmpUnionKind;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/cmpunion/op/union")
public class OpCmpUnionAction extends BaseAction {
	@Autowired
	private CmpUnionService cmpUnionService;

	public String execute(HkRequest req, HkResponse resp) {
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/welcome.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion o = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("o", o);
		req.setAttribute("op_func", 0);
		// List<Pcity> clist = zoneService.getPcityListByCountryId(1);// 选取中国
		// List<Province> provincelist = zoneService.getProvinceList(1);
		// req.setAttribute("provincelist", provincelist);
		// req.setAttribute("clist", clist);
		return this.getWeb3Jsp("unionadmin/edit.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String logo(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion o = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("o", o);
		req.setAttribute("op_func", 1);
		return this.getWeb3Jsp("unionadmin/logo.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadlogo(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		try {
			this.cmpUnionService.updateCmpUnionLogo(uid, req.getFile("f"));
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "uploadok", null);
		}
		catch (Exception e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "uploaderror", null);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		String name = req.getString("name");
		String domain = req.getString("domain");
		String webName = req.getString("webName");
		String traffic = req.getString("traffic");
		String intro = req.getString("intro");
		String addr = req.getString("addr");
		int pcityId = req.getInt("pcityId");
		CmpUnion o = this.cmpUnionService.getCmpUnion(uid);
		o.setName(DataUtil.toHtmlRow(name));
		o.setDomain(DataUtil.toHtmlRow(domain));
		o.setWebName(DataUtil.toHtmlRow(webName));
		o.setTraffic(DataUtil.toHtml(traffic));
		o.setAddr(DataUtil.toHtml(addr));
		o.setIntro(DataUtil.toHtml(intro));
		o.setPcityId(pcityId);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "onediterror", null);
		}
		int result = this.cmpUnionService.updateCmpUnion(o);
		if (result != 0) {
			return this.onError(req, result, "onediterror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "oneditsuccess", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String kindlist(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		long parentId = req.getLongAndSetAttr("parentId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionService.countCmpUnionKindByUid(uid,
				parentId));
		List<CmpUnionKind> list = this.cmpUnionService
				.getCmpUnionKindListByUid(uid, parentId, page.getBegin(), page
						.getSize());
		req.setAttribute("list", list);
		List<CmpUnionKind> list2 = new ArrayList<CmpUnionKind>();
		this.loadCmpUnionKindList(list2, parentId);
		req.setAttribute("list2", list2);
		req.setAttribute("op_func", 2);
		return this.getWeb3Jsp("unionadmin/kindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmdkindlist(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		List<CmpUnionCmdKind> kindlist = this.cmpUnionService
				.getCmpUnionCmdKindListByUid(uid, 0, -1);
		List<Long> idList = new ArrayList<Long>();
		for (CmpUnionCmdKind kind : kindlist) {
			idList.add(kind.getKindId());
		}
		List<CmpUnionKind> cmdkindlist = cmpUnionService
				.getCmpUnionKindListInId(uid, idList);
		req.setAttribute("cmdkindlist", cmdkindlist);
		req.setAttribute("op_func", 7);
		return this.getWeb3Jsp("unionadmin/cmdkindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String loadkind(HkRequest req, HkResponse resp) {
		long kindId = req.getLongAndSetAttr("kindId");
		CmpUnionKind cmpUnionKind = this.cmpUnionService
				.getCmpUnionKind(kindId);
		req.setAttribute("cmpUnionKind", cmpUnionKind);
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/kind_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createkind(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		if (cmpUnion == null) {
			return null;
		}
		long parentId = req.getLong("parentId");
		CmpUnionKind parent = null;
		if (parentId > 0) {
			parent = this.cmpUnionService.getCmpUnionKind(parentId);
			if (parent == null) {
				return null;
			}
			if (parent.getUid() != uid) {
				return null;
			}
		}
		String name = req.getString("name");
		CmpUnionKind o = new CmpUnionKind();
		o.setUid(uid);
		o.setName(DataUtil.toHtmlRow(name));
		if (parent != null) {
			o.setKindLevel(parent.getKindLevel() + 1);
		}
		o.setParentId(parentId);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "addkinderror", null);
		}
		if (this.cmpUnionService.createCmpUnionKind(o)) {
			this.setOpFuncSuccessMsg(req);
		}
		else {
			return this.onError(req, Err.CMPUNIONKIND_NAME_DUPLICATE,
					"addkinderror", null);
		}
		return this.onSuccess2(req, "addkindsuccess", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editkind(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long kindId = req.getLong("kindId");
		String name = req.getString("name");
		CmpUnionKind o = this.cmpUnionService.getCmpUnionKind(kindId);
		if (o == null) {
			return null;
		}
		if (o.getUid() != uid) {
			return null;
		}
		o.setName(DataUtil.toHtmlRow(name));
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "addkinderror", null);
		}
		if (this.cmpUnionService.updateCmpUnionKind(o)) {
			this.setOpFuncSuccessMsg(req);
		}
		else {
			return this.onError(req, Err.CMPUNIONKIND_NAME_DUPLICATE,
					"addkinderror", null);
		}
		return this.onSuccess2(req, "addkindsuccess", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delkind(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long kindId = req.getLong("kindId");
		CmpUnionKind o = this.cmpUnionService.getCmpUnionKind(kindId);
		if (o == null) {
			return null;
		}
		if (o.getUid() != uid) {
			return null;
		}
		if (this.cmpUnionService.deleteCmpUnionKind(kindId)) {
			resp.sendHtml(0);
			return null;
		}
		resp.sendHtml(1);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sitemod(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("cmpUnion", cmpUnion);
		if (DataUtil.isEmpty(cmpUnion.getData())) {
			cmpUnion.setData(CmpUnionModuleUtil.getDefaultOrder());
			this.cmpUnionService.updateCmpUnionData(uid, cmpUnion.getData());
		}
		CmpUnionSite cmpUnionSite = new CmpUnionSite(cmpUnion.getData());
		req.setAttribute("cmpUnionSite", cmpUnionSite);
		req.setAttribute("op_func", 3);
		return this.getWeb3Jsp("unionadmin/sitemod.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updatemod(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		int[] mod = req.getInts("mod");
		if (mod != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mod.length; i++) {
				sb.append(mod[i]).append(":").append(0).append(";");
			}
			sb.deleteCharAt(sb.length() - 1);
			CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
			cmpUnion.setData(sb.toString());
			this.cmpUnionService.updateCmpUnionData(uid, cmpUnion.getData());
			this.setOpFuncSuccessMsg(req);
		}
		return this.onSuccess2(req, "updatemodok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createcmdkind(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long kindId = req.getLong("kindId");
		CmpUnionCmdKind cmpUnionCmdKind = new CmpUnionCmdKind();
		cmpUnionCmdKind.setUid(uid);
		cmpUnionCmdKind.setKindId(kindId);
		this.cmpUnionService.createCmpUnionCmdKind(cmpUnionCmdKind);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmdkind(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long kindId = req.getLong("kindId");
		this.cmpUnionService.deleteCmpUnionCmdKind(uid, kindId);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosetcmpcreate(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("cmpUnion", cmpUnion);
		req.setAttribute("op_func", 10);
		return this.getWeb3Jsp("unionadmin/setcmpcreate.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setcancmpcreate(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		byte cmpcreateflg = req.getByte("cmpcreateflg");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		cmpUnion.setCmpcreateflg(cmpcreateflg);
		this.cmpUnionService.updateCmpUnion(cmpUnion);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "cmpcreateflgok", null);
	}
}