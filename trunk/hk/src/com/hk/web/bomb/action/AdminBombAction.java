package com.hk.web.bomb.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Bomber;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.UserService;
import com.hk.svr.bomb.exception.NotEnoughBombException;
import com.hk.svr.bomb.exception.NotEnoughPinkException;
import com.hk.web.pub.action.BaseAction;

@Component("/adminbomb/bomb")
public class AdminBombAction extends BaseAction {

	@Autowired
	private BombService bombService;

	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/bomb/admin/admin.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(size);
		List<Bomber> list = this.bombService.getBomberList(page.getBegin(),
				size);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		return "/WEB-INF/page/bomb/admin/list.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/bomb/admin/addbomber.jsp";
	}

	/**
	 * 添加炸弹或者添加爆破手
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) {
		String returnAction = "/adminbomb/bomb_toadd.do";
		String nickName = req.getString("nickName");
		int count = req.getInt("count");
		int pinkCount = req.getInt("pinkCount");
		byte userLevel = req.getByte("userLevel");
		req.reSetAttribute("nickName");
		req.reSetAttribute("count");
		req.reSetAttribute("pwd");
		if (nickName == null) {
			req.setMessage("请填写昵称");
			return returnAction;
		}
		if (count < 0) {
			req.setMessage("请填写炸弹数量");
			return returnAction;
		}
		if (pinkCount < 0) {
			req.setMessage("请填写精华数量");
			return returnAction;
		}
		boolean superAdmin = (Boolean) req.getAttribute("superAdmin");
		boolean admin = (Boolean) req.getAttribute("admin");
		if (userLevel < 0) {
			req.setMessage("请选择权限");
			return returnAction;
		}
		if (admin && userLevel != 0) {
			req.setMessage("请选择权限");
			return returnAction;
		}
		if (superAdmin && userLevel == 2) {
			req.setMessage("请选择权限");
			return returnAction;
		}
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			req.setSessionMessage("没有找到" + nickName);
			return returnAction;
		}
		Bomber bomber = this.bombService.getBomber(user.getUserId());
		if (bomber != null) {
			req.setSessionMessage(nickName + "已经是爆破手");
			return "r:" + returnAction;
		}
		User loginUser = this.getLoginUser(req);
		try {
			this.bombService.createBomber(loginUser.getUserId(), user
					.getUserId(), count, userLevel, pinkCount);
		}
		catch (NotEnoughBombException e) {
			req.setSessionMessage("炸弹数量不足");
			return returnAction;
		}
		catch (NotEnoughPinkException e) {
			req.setSessionMessage("精华数量不足");
			return returnAction;
		}
		req.setSessionMessage("添加爆破手成功");
		return "r:" + returnAction;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdate(HkRequest req, HkResponse resp) {
		return "/WEB-INF/page/bomb/admin/updatebomber.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) {
		String nickName = req.getString("nickName");
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			req.setSessionMessage("没有找到" + nickName);
			return "r:/adminbomb/bomb_list.do";
		}
		Bomber bomber = this.bombService.getBomber(user.getUserId());
		boolean admin = (Boolean) req.getAttribute("admin");
		boolean superAdmin = (Boolean) req.getAttribute("superAdmin");
		if (bomber != null && admin
				&& bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
			req.setSessionMessage(nickName + "是管理员,你不能分配炸弹给管理员");
			return "r:/adminbomb/bomb_list.do";
		}
		if (bomber != null && superAdmin
				&& bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
			req.setSessionMessage(nickName + "是管理员,你不能分配炸弹给管理员");
			return "r:/adminbomb/bomb_list.do";
		}
		try {
			int count = req.getInt("count");
			int pinkCount = req.getInt("pinkCount");
			this.bombService.addBomb(this.getLoginUser(req).getUserId(), user
					.getUserId(), count);
			this.bombService.addPink(this.getLoginUser(req).getUserId(), user
					.getUserId(), pinkCount);
			req.setSessionMessage("操作成功");
		}
		catch (NotEnoughBombException e) {
			req.setSessionMessage("炸弹数量不足");
		}
		catch (NotEnoughPinkException e) {
			req.setSessionMessage("炸弹数量不足");
		}
		return "r:/adminbomb/bomb_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clearbomb(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		Bomber bomber = this.bombService.getBomber(userId);
		if (bomber != null) {
			User user = this.userService.getUser(userId);
			String nickName = user.getNickName();
			boolean admin = (Boolean) req.getAttribute("admin");
			boolean superAdmin = (Boolean) req.getAttribute("superAdmin");
			if (admin && bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
				req.setSessionMessage(nickName + "是管理员,你不能对管理员操作");
				return "r:/adminbomb/bomb_list.do";
			}
			if (superAdmin
					&& bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
				req.setSessionMessage(nickName + "是管理员,你不能对管理员操作");
				return "r:/adminbomb/bomb_list.do";
			}
			try {
				this.bombService.addBomb(this.getLoginUser(req).getUserId(),
						userId, -bomber.getRemainCount());
			}
			catch (NotEnoughBombException e) {// 
			}
		}
		req.setSessionMessage("操作成功");
		return "r:/adminbomb/bomb_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clearpink(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		Bomber bomber = this.bombService.getBomber(userId);
		if (bomber != null) {
			User user = this.userService.getUser(userId);
			String nickName = user.getNickName();
			boolean admin = (Boolean) req.getAttribute("admin");
			boolean superAdmin = (Boolean) req.getAttribute("superAdmin");
			if (admin && bomber.getUserLevel() != Bomber.USERLEVEL_NORMAL) {
				req.setSessionMessage(nickName + "是管理员,你不能对管理员操作");
				return "r:/adminbomb/bomb_list.do";
			}
			if (superAdmin
					&& bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
				req.setSessionMessage(nickName + "是管理员,你不能对管理员操作");
				return "r:/adminbomb/bomb_list.do";
			}
			try {
				this.bombService.addPink(this.getLoginUser(req).getUserId(),
						userId, -bomber.getRemainPinkCount());
			}
			catch (NotEnoughPinkException e) {// 
			}
		}
		req.setSessionMessage("操作成功");
		return "r:/adminbomb/bomb_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String back(HkRequest req, HkResponse resp) {
		String from = req.getString("from", "");
		if (from.equals("list")) {
			return "r:/adminbomb/bomb_list.do?page=" + req.getInt("repage");
		}
		return "r:/adminbomb/bomb.do";
	}
}