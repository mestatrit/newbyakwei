package com.hk.web.hk4.admin;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

/**
 * 足迹后台管理
 * 
 * @author akwei
 */
@Component("/h4/admin/mgr")
public class MgrAction extends BaseAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "r:/h4/admin/cmp.do";
	}
}