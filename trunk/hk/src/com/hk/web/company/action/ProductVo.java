package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.hk.bean.CmpProduct;
import com.hk.bean.ShoppingProduct;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpProductService;
import com.hk.web.util.HttpShoppingCard;

public class ProductVo {
	private CmpProduct cmpProduct;

	private boolean addToCard;

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductVo(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}

	public static List<ProductVo> createVoList(List<CmpProduct> list,
			HttpShoppingCard shoppingCard) {
		List<ProductVo> volist = new ArrayList<ProductVo>();
		for (CmpProduct o : list) {
			ProductVo vo = new ProductVo(o);
			vo.setAddToCard(shoppingCard.isHasProduct(o.getProductId()));
			volist.add(vo);
		}
		return volist;
	}

	public static List<ProductVo> createVoList(Collection<ShoppingProduct> list) {
		List<Long> idList = new ArrayList<Long>();
		for (ShoppingProduct o : list) {
			idList.add(o.getProductId());
		}
		List<ProductVo> volist = new ArrayList<ProductVo>();
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		Map<Long, CmpProduct> map = cmpProductService
				.getCmpProductMapInId(idList);
		for (ShoppingProduct o : list) {
			ProductVo vo = new ProductVo(map.get(o.getProductId()));
			vo.setCount(o.getCount());
			volist.add(vo);
		}
		return volist;
	}

	public CmpProduct getCmpProduct() {
		return cmpProduct;
	}

	public void setCmpProduct(CmpProduct cmpProduct) {
		this.cmpProduct = cmpProduct;
	}

	public boolean isAddToCard() {
		return addToCard;
	}

	public void setAddToCard(boolean addToCard) {
		this.addToCard = addToCard;
	}
}