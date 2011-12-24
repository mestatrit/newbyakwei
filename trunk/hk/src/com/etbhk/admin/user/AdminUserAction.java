package com.etbhk.admin.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Admin;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_AdminService;
import com.hk.svr.Tb_UserService;

public class AdminUserAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_AdminService tbAdminService;

	@Autowired
	private Tb_UserService tbUserService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String add(HkRequest req, HkResponse resp) throws Exception {
		long userid = req.getLong("userid");
		Tb_User tbUser = this.tbUserService.getTb_User(userid);
		if (tbUser == null) {
			resp.sendHtml("no userid [ " + userid + " ]");
		}
		Tb_Admin tbAdmin = new Tb_Admin();
		tbAdmin.setUserid(userid);
		this.tbAdminService.createTb_Admin(tbAdmin);
		resp.sendHtml("add ok");
		return null;
	}

	public String del(HkRequest req, HkResponse resp) throws Exception {
		long userid = req.getLong("userid");
		Tb_User tbUser = this.tbUserService.getTb_User(userid);
		if (tbUser == null) {
			resp.sendHtml("no userid [ " + userid + " ]");
		}
		this.tbAdminService.deleteTb_AdminByUserid(userid);
		resp.sendHtml("del ok");
		return null;
	}
}