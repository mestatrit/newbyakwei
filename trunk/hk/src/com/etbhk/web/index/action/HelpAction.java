package com.etbhk.web.index.action;

import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/tb/help")
public class HelpAction extends BaseTaoBaoAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 返利解释页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-22
	 */
	public String buy(HkRequest req, HkResponse resp) {
		return this.getWebJsp("help/buy.jsp");
	}
}