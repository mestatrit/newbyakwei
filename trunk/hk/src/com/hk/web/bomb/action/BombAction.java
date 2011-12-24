package com.hk.web.bomb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AdminUser;
import com.hk.bean.BombLaba;
import com.hk.bean.Bomber;
import com.hk.bean.CmpTipDel;
import com.hk.bean.Laba;
import com.hk.bean.LabaDel;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.PinkLaba;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.CmpTipService;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;

@Component("/bomb/bomb")
public class BombAction extends BaseAction {

	@Autowired
	private BombService bombService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpTipService cmpTipService;

	private int size = 20;

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
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User user = this.getLoginUser(req);
		AdminUser adminUser = this.userService.getAdminUser(user.getUserId());
		if (adminUser == null) {
			userId = user.getUserId();
		}
		else {
			if (userId < 1) {
				userId = 0;
			}
		}
		SimplePage page = req.getSimplePage(size);
		List<BombLaba> list = null;
		if (userId == 0) {
			list = this.bombService.getBombLabaList(page.getBegin(), size);
		}
		else {
			list = this.bombService.getBombLabaList(userId, page.getBegin(),
					size);
		}
		List<Long> idList = new ArrayList<Long>();
		for (BombLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, LabaDel> map = labaService.getLabaDelMapInId(idList);
		List<BombLabaVo> bomblabavoList = new ArrayList<BombLabaVo>();
		for (BombLaba o : list) {
			BombLabaVo vo = new BombLabaVo();
			vo.setBombLaba(o);
			Laba laba = map.get(o.getLabaId());
			if (laba != null) {
				o.setLaba(laba);
				LabaVo labavo = LabaVo.create(laba, null);
				vo.setLabaVo(labavo);
			}
			bomblabavoList.add(vo);
		}
		Bomber bomber = this.bombService.getBomber(userId);
		page.setListSize(list.size());
		req.setAttribute("bomber", bomber);
		req.setAttribute("userId", userId);
		req.setAttribute("bomblabavoList", bomblabavoList);
		req.setAttribute("list", list);
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/bomb/list.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String pinklabalist(HkRequest req, HkResponse resp) throws Exception {
		User user = this.getLoginUser(req);
		long userId = req.getLong("userId");
		AdminUser adminUser = this.userService.getAdminUser(user.getUserId());
		if (adminUser == null) {
			userId = user.getUserId();
		}
		else {
			if (userId < 1) {
				userId = 0;
			}
		}
		SimplePage page = req.getSimplePage(size);
		List<PinkLaba> list = null;
		if (userId == 0) {
			list = this.bombService.getPinkLabaList(page.getBegin(), size);
		}
		else {
			list = this.bombService.getPinkLabaList(userId, page.getBegin(),
					size);
		}
		// UrlInfo urlInfo = this.getUrlInfo(req);
		// LabaOutPutParser parser = new LabaOutPutParser();
		// for (PinkLaba o : list) {
		// o.getLaba().setOutPutContent(
		// parser.getOutPutContent(urlInfo, o.getLaba().getContent(),
		// o.getLaba().getUserId()));
		// }
		List<Long> idList = new ArrayList<Long>();
		for (PinkLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		List<PinkLabaVo> pinklabavolist = new ArrayList<PinkLabaVo>();
		for (PinkLaba o : list) {
			PinkLabaVo vo = new PinkLabaVo();
			vo.setPinkLaba(o);
			Laba laba = map.get(o.getLabaId());
			if (laba != null) {
				o.setLaba(laba);
				LabaVo labavo = LabaVo.create(laba, null);
				vo.setLabaVo(labavo);
			}
			pinklabavolist.add(vo);
		}
		Bomber bomber = this.bombService.getBomber(userId);
		page.setListSize(list.size());
		req.setAttribute("bomber", bomber);
		req.setAttribute("userId", userId);
		req.setAttribute("pinklabavolist", pinklabavolist);
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/bomb/pinklabalist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delpinklaba(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		PinkLaba pinkLaba = this.bombService.getPinkLaba(labaId);
		boolean candelpink = false;
		if (pinkLaba != null) {
			User user = this.getLoginUser(req);
			if (pinkLaba.getPinkUserId() == user.getUserId()) {
				candelpink = true;
			}
			else {
				Bomber bomber = this.bombService.getBomber(user.getUserId());
				if (bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
					candelpink = true;
				}
			}
		}
		if (candelpink) {
			this.bombService.deletePinkLaba(labaId);
			req.setSessionMessage("操作成功");
		}
		return "r:/bomb/bomb_pinklabalist.do?page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String recoverlaba(HkRequest req, HkResponse resp) throws Exception {
		User user = this.getLoginUser(req);
		long sysId = req.getLong("sysId");
		BombLaba bombLaba = this.bombService.getBombLaba(sysId);
		boolean canRecover = false;
		if (bombLaba != null) {
			if (bombLaba.getUserId() == user.getUserId()) {
				canRecover = true;
			}
			else {
				Bomber bomber = this.bombService.getBomber(user.getUserId());
				if (bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
					canRecover = true;
				}
			}
			if (canRecover) {
				LabaDelInfo labaDelInfo = new LabaDelInfo();
				labaDelInfo.setLabaId(bombLaba.getLabaId());
				labaDelInfo.setOpuserId(bombLaba.getUserId());
				labaDelInfo.setOptime(bombLaba.getOptime());
				this.labaService.reRemoveLaba(labaDelInfo);
				req.setSessionMessage("恢复喇叭成功");
			}
		}
		return "r:/bomb/bomb_list.do?userId=" + req.getLong("userId")
				+ "&page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tips(HkRequest req, HkResponse resp) {
		List<CmpTipDel> list = null;
		SimplePage page = req.getSimplePage(20);
		User loginUser = this.getLoginUser(req);
		if (this.isAdminUser(req)) {
			list = this.cmpTipService.getCmpTipDelList(0, page.getBegin(), page
					.getSize() + 1);
		}
		else {
			list = this.cmpTipService.getCmpTipDelList(loginUser.getUserId(),
					page.getBegin(), page.getSize() + 1);
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpTipDel o : list) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CmpTipDel o : list) {
			o.setUser(map.get(o.getUserId()));
		}
		req.setAttribute("list", list);
		return this.getWapJsp("bomb/tips.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String recovertip(HkRequest req, HkResponse resp) {
		long tipId = req.getLong("tipId");
		this.cmpTipService.recoverCmpTip(tipId);
		this.setOpFuncSuccessMsg(req);
		return "r:/bomb/bomb_tips.do";
	}
}