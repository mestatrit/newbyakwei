package com.hk.web.union.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionOpUser;
import com.hk.bean.Pcity;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/admin/union")
public class AdminUnionAction extends BaseAction {
	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(20);
		List<CmpUnion> list = this.cmpUnionService.getCmpUnionList(name, page
				.getBegin(), page.getSize() + 1);
		if (list.size() > page.getSize()) {
			page.setHasNext(true);
		}
		req.setAttribute("list", list);
		return this.getWapJsp("admin/unionlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tocreate(HkRequest req, HkResponse resp) throws Exception {
		return this.getWapJsp("admin/createunion.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion o = (CmpUnion) req.getAttribute("o");
		if (o == null) {
			o = this.cmpUnionService.getCmpUnion(uid);
		}
		req.setAttribute("o", o);
		String zoneName = (String) req.getAttribute("zoneName");
		if (zoneName == null) {
			Pcity pcity = ZoneUtil.getPcity(o.getPcityId());
			if (pcity != null) {
				zoneName = pcity.getName();
			}
		}
		req.setAttribute("zoneName", zoneName);
		return this.getWapJsp("admin/editunion.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgstatus(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		String run = req.getString("run");
		String stop = req.getString("stop");
		String limit = req.getString("limit");
		byte status = 0;
		if (run != null) {
			status = CmpUnion.UNIONSTATUS_RUN;
		}
		else if (limit != null) {
			status = CmpUnion.UNIONSTATUS_LIMIT;
		}
		else if (stop != null) {
			status = CmpUnion.UNIONSTATUS_STOP;
		}
		this.cmpUnionService.updateCmpUnionStatus(uid, status);
		return "r:/admin/union_toedit.do?uid=" + uid;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		String domain = req.getString("domain");
		String name = req.getString("name");
		String zoneName = req.getStringAndSetAttr("zoneName");
		CmpUnion o = new CmpUnion();
		req.setAttribute("o", o);
		o.setName(DataUtil.toHtmlRow(name));
		o.setDomain(DataUtil.toHtmlRow(domain));
		City city=this.zoneService.getCityLike(zoneName);
		if (city == null) {
			req.setText("func.pcity.notexist");
			return "/admin/union_tocreate.do";
		}
		o.setPcityId(city.getCityId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/admin/union_tocreate.do";
		}
		int result = this.cmpUnionService.createCmpUnion(o);
		if (result != 0) {
			req.setText(result + "");
			return "/admin/union_tocreate.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/union_toselopuser.do?uid=" + o.getUid();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		String domain = req.getString("domain");
		String name = req.getString("name");
		String zoneName = req.getStringAndSetAttr("zoneName");
		CmpUnion o = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("o", o);
		o.setName(DataUtil.toHtmlRow(name));
		o.setDomain(DataUtil.toHtmlRow(domain));
		City city=this.zoneService.getCityLike(zoneName);
		if (city == null) {
			req.setText("func.pcity.notexist");
			return "/admin/union_toedit.do";
		}
		o.setPcityId(city.getCityId());
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/admin/union_toedit.do";
		}
		int result = this.cmpUnionService.updateCmpUnion(o);
		if (result != 0) {
			req.setText(result + "");
			return "/admin/union_toedit.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/union_toedit.do?uid=" + o.getUid();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toselopuser(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLongAndSetAttr("uid");
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(uid);
		req.setAttribute("cmpUnion", cmpUnion);
		List<CmpUnionOpUser> opuserlist = this.cmpUnionService
				.getCmpUnionOpUserListByUid(uid);
		List<Long> idList = new ArrayList<Long>();
		for (CmpUnionOpUser o : opuserlist) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CmpUnionOpUser o : opuserlist) {
			o.setUser(map.get(o.getUserId()));
		}
		req.setAttribute("opuserlist", opuserlist);
		return this.getWapJsp("admin/unionseluser.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selopuser(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		String nickName = req.getStringAndSetAttr("nickName");
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			req.setText(Err.USER_NOT_EXIST + "");
			return "/admin/union_toselopuser.do";
		}
		long userId = user.getUserId();
		CmpUnionOpUser cmpUnionOpUser = new CmpUnionOpUser();
		cmpUnionOpUser.setUid(uid);
		cmpUnionOpUser.setUserId(userId);
		this.cmpUnionService.createCmpUnionOpUser(cmpUnionOpUser);
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/union_toselopuser.do?uid=" + uid;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delopuser(HkRequest req, HkResponse resp) throws Exception {
		long uid = req.getLong("uid");
		long userId = req.getLong("userId");
		this.cmpUnionService.deleteCmpUnionOpUser(uid, userId);
		this.setOpFuncSuccessMsg(req);
		return "r:/admin/union_toselopuser.do?uid=" + uid;
	}
}