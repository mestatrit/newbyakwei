package com.etbhk.web.shop.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Shop;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_ShopService;

/**
 * @author akwei
 */
@Component("/tb/shop")
public class ShopAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_ShopService tb_ShopService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long sid = req.getLong("sid");
		Tb_Shop tbShop = this.tb_ShopService.getTb_Shop(sid);
		if (tbShop == null) {
			return null;
		}
		req.setAttribute("tbShop", tbShop);
		return this.getWebJsp("shop/shop.jsp");
	}
}