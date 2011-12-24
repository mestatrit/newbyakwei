package com.hk.web.chgcard.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.ChgCardAct;
import com.hk.bean.ChgCardActUser;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ChgCardActService;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/chgcard/act")
public class ActAction extends BaseAction {
	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	private UserCardService userCardService;

	@Autowired
	private UserService userService;

	private int size = 20;

	/**
	 * 最新参加的活动
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(size);
		List<ChgCardAct> list = this.chgCardActService
				.getChgCardActListByJoinUserId(loginUser.getUserId(), page
						.getBegin(), size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return "/WEB-INF/page/chgcard/list.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addtip(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (!info.isMobileAlreadyBind()) {
			req.setSessionMessage(req.getText("func.mobilenotbind"));
			return "r:/more.do";
		}
		return "/WEB-INF/page/chgcard/addtip.jsp";
	}

	/**
	 * 删除活动中的会员
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deluser(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		ChgCardAct o = this.chgCardActService.getChgCardAct(actId);
		if (o.getUserId() == loginUser.getUserId()) {// 如果是活动管理员，才可以删除
			this.chgCardActService.deleteChgCardActUser(actId, userId);
			req.setSessionMessage(req.getText("op.exeok"));
		}
		return "r:/chgcard/act_view.do?actId=" + actId;
	}

	/**
	 * 查看单个活动
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		User loginUser = this.getLoginUser(req);
		// 查看自己是否是活动中的成员，如果不是就不能查看
		ChgCardActUser actUser = this.chgCardActService.getChgCardActUser(
				actId, loginUser.getUserId());
		if (actUser == null) {
			req.setSessionMessage(req.getText("view.nopurview"));
			return "r:/card/card_list.do";
		}
		ChgCardAct o = this.chgCardActService.getChgCardAct(actId);
		SimplePage page = req.getSimplePage(size);
		String key = req.getString("key");
		List<ChgCardActUser> ulist = this.chgCardActService
				.getChgCardActUserList(key, actId, page.getBegin(), size);
		page.setListSize(ulist.size());
		List<Long> idList = new ArrayList<Long>();
		for (ChgCardActUser cu : ulist) {
			idList.add(cu.getUserId());
		}
		Map<Long, UserCard> map = this.userCardService
				.getUserCardMapInUserId(idList);
		for (ChgCardActUser cu : ulist) {
			cu.setUserCard(map.get(cu.getUserId()));
		}
		req.setAttribute("o", o);
		req.setAttribute("ulist", ulist);
		req.setAttribute("actId", actId);
		req.setEncodeAttribute("key", key);
		return "/WEB-INF/page/chgcard/view.jsp";
	}

	/**
	 * 到修改活动信息页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		ChgCardAct o = (ChgCardAct) req.getAttribute("o");
		if (o == null) {
			o = this.chgCardActService.getChgCardAct(actId);
		}
		req.setAttribute("o", o);
		req.setAttribute("actId", actId);
		return "/WEB-INF/page/chgcard/edit.jsp";
	}

	/**
	 * 修改活动信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		ChgCardAct o = this.chgCardActService.getChgCardAct(actId);
		String name = req.getString("name");
		int persistHour = req.getInt("persistHour");
		byte actStatus = req.getByte("actStatus");
		byte chgflg = req.getByte("chgflg");
		o.setName(DataUtil.toHtmlRow(name));
		o.setPersistHour(persistHour);
		o.setChgflg(chgflg);
		o.setActStatus(actStatus);
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/chgcard/act_toedit.do?actId=" + actId;
		}
		this.chgCardActService.updateChgCardAct(o);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/chgcard/act_view.do?actId=" + actId;
	}

	/**
	 * 活动发起者可以编辑活动人员的姓名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditactusername(HkRequest req, HkResponse resp)
			throws Exception {
		if (this.validateAccess(req) == null) {
			return null;
		}
		long userId = req.getLong("userId");
		UserOtherInfo o = (UserOtherInfo) req.getAttribute("o");
		if (o == null) {
			o = this.userService.getUserOtherInfo(userId);
		}
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		req.setAttribute("o", o);
		req.reSetAttribute("actId");
		req.reSetAttribute("userId");
		return "/WEB-INF/page/chgcard/editname.jsp";
	}

	/**
	 * 编辑名片活动中的人的姓名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editactusername(HkRequest req, HkResponse resp)
			throws Exception {
		if (this.validateAccess(req) == null) {
			return null;
		}
		long actId = req.getLong("actId");
		long userId = req.getLong("userId");
		String name = req.getString("name");
		name = DataUtil.toHtmlRow(name);
		UserOtherInfo o = this.userService.getUserOtherInfo(userId);
		UserCard userCard = this.userCardService.getUserCard(userId);
		o.setName(name);
		userCard.setName(name);
		int code = UserOtherInfo.validateName(name);
		if (code != Err.SUCCESS) {
			req.setAttribute("o", o);
			req.setMessage(req.getText(code + ""));
			return "/chgcard/act_toeditactusername.do";
		}
		this.userService.updateUserOtherInfo(o);
		this.userCardService.updateUserCard(userCard);
		req.setSessionMessage(req.getText("func.editactusername_success"));
		return "r:/chgcard/act_view.do?actId=" + actId;
	}

	private String validateAccess(HkRequest req) {
		long actId = req.getLong("actId");
		long userId = req.getLong("userId");
		ChgCardAct act = this.chgCardActService.getChgCardAct(actId);
		if (act == null) {// 非正常进入
			return null;
		}
		User loginUser = this.getLoginUser(req);
		if (act.getUserId() != loginUser.getUserId()) {// 非正常进入
			return null;
		}
		ChgCardActUser actUser = this.chgCardActService.getChgCardActUser(
				actId, userId);
		if (actUser == null) {// 非正常进入
			return null;
		}
		return "0";
	}
}