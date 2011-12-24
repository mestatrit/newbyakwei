package com.etbhk.admin.ask;

import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/tb/admin")
public class AdminAction extends BaseTaoBaoAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "r:/tb/admin/item";
	}
}