package com.hk.web.user.action;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.ShoppingProduct;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.web.company.action.ProductVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.util.HttpShoppingCard;

@Component("/shoppingcard")
public class ShoppingCardAction extends BaseAction {
	@Autowired
	private CmpProductService cmpProductService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		HttpShoppingCard httpShoppingCard = this.getShoppingCard(req);
		Collection<ShoppingProduct> c = httpShoppingCard
				.getShoppingProductList();
		List<ProductVo> productvolist = ProductVo.createVoList(c);
		double totalPrice = 0;
		for (ProductVo vo : productvolist) {
			totalPrice += vo.getCmpProduct().getMoney() * vo.getCount();
		}
		req.setAttribute("totalPrice", totalPrice);
		req.setAttribute("productvolist", productvolist);
		req.setAttribute("companyId", httpShoppingCard.getCompanyId());
		return this.getWeb3Jsp("user/shoppingcard.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		HttpShoppingCard httpShoppingCard = this.getShoppingCard(req);
		Collection<ShoppingProduct> c = httpShoppingCard
				.getShoppingProductList();
		List<ProductVo> productvolist = ProductVo.createVoList(c);
		Collections.reverse(productvolist);
		double totalPrice = 0;
		for (ProductVo vo : productvolist) {
			totalPrice += vo.getCmpProduct().getMoney() * vo.getCount();
		}
		req.setAttribute("totalPrice", totalPrice);
		req.setAttribute("productvolist", productvolist);
		req.setAttribute("companyId", httpShoppingCard.getCompanyId());
		return this.getWapJsp("shoppingcard/shoppingcard.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toupdate(HkRequest req, HkResponse resp) throws Exception {
		long pid = req.getLongAndSetAttr("pid");
		HttpShoppingCard card = this.getShoppingCard(req);
		ShoppingProduct product = card.getShoppingProduct(pid);
		if (product != null) {
			product.setCmpProduct(this.cmpProductService.getCmpProduct(pid));
		}
		req.setAttribute("product", product);
		return this.getWapJsp("shoppingcard/update.jsp");
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		long pid = req.getLong("pid");
		int count = req.getInt("count");
		HttpShoppingCard card = this.getShoppingCard(req);
		card.updateProduct(pid, count);
		card.saveShoppingCard(resp);
		this.setOpFuncSuccessMsg(req);
		return "r:/shoppingcard_wap.do";
	}

	public String updateProductPrice(HkRequest req, HkResponse resp)
			throws Exception {
		long pid = req.getLong("pid");
		int count = req.getInt("count");
		HttpShoppingCard card = this.getShoppingCard(req);
		card.updateProduct(pid, count);
		card.saveShoppingCard(resp);
		Collection<ShoppingProduct> c = card.getShoppingProductList();
		List<ProductVo> productvolist = ProductVo.createVoList(c);
		double totalPrice = 0;
		for (ProductVo vo : productvolist) {
			if (vo.getCmpProduct() == null) {
				continue;
			}
			totalPrice += vo.getCmpProduct().getMoney() * vo.getCount();
		}
		CmpProduct product = this.cmpProductService.getCmpProduct(pid);
		double productprice = 0;
		if (product != null) {
			productprice = count * product.getMoney();
		}
		resp.sendHtml(productprice + ";" + totalPrice);// 修改成功
		return null;
	}

	/**
	 * 从购物车删除物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delproduct(HkRequest req, HkResponse resp) throws Exception {
		int pid = req.getInt("pid");
		HttpShoppingCard card = this.getShoppingCard(req);
		card.remoreProduct(pid);
		card.saveShoppingCard(resp);
		req.setSessionText("op.delinfook");
		return "r:/shoppingcard.do";
	}

	/**
	 * 从购物车删除物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String clean(HkRequest req, HkResponse resp) throws Exception {
		HttpShoppingCard card = this.getShoppingCard(req);
		card.clean();
		card.saveShoppingCard(resp);
		req.setSessionText("op.delinfook");
		return "r:/shoppingcard.do";
	}

	/**
	 * 从购物车删除物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cleanwap(HkRequest req, HkResponse resp) throws Exception {
		HttpShoppingCard card = this.getShoppingCard(req);
		card.clean();
		card.saveShoppingCard(resp);
		return "r:/shoppingcard_wap.do";
	}
}