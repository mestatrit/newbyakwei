package com.etbhk.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Sysuser;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_SysuserService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;

@Component("/tb/admin/sysuser")
public class SysUserAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_UserService tbUserService;

	@Autowired
	private Tb_SysuserService tbSysuserService;

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		req.setAttribute("sysuser_mod", true);
		List<Tb_Sysuser> list = this.tbSysuserService.getTb_SysuserList(true,
				0, 100);
		req.setAttribute("list", list);
		return this.getAdminJsp("sysuser/list.jsp");
	}

	/**
	 * 创建系统机器人用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-25
	 */
	public String create(HkRequest req, HkResponse resp) {
		Tb_User tbUser = new Tb_User();
		tbUser.setNick(req.getHtmlRow("nick"));
		int code = tbUser.validate(null);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerr", null);
		}
		code = this.tbUserService.createTb_User(tbUser);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerr", null);
		}
		Tb_Sysuser tbSysuser = new Tb_Sysuser();
		tbSysuser.setUserid(tbUser.getUserid());
		tbSysuser.setCreate_time(tbUser.getCreate_time());
		this.tbSysuserService.createTb_Sysuser(tbSysuser);
		return this.onSuccess(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-25
	 */
	public String del(HkRequest req, HkResponse resp) {
		long userid = req.getLong("userid");
		this.tbSysuserService.deleteTb_Sysuser(userid);
		this.setDelSuccessMsg(req);
		return null;
	}
}