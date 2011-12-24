package com.hk.web.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.bean.ShoppingProduct;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.ServletUtil;

public class HttpShoppingCard {

	private long companyId;

	private int totalCount;

	private final Map<Long, ShoppingProduct> cardMap = new LinkedHashMap<Long, ShoppingProduct>();

	private static DesUtil desUtil = new DesUtil("hkshoppingcardakweiflyshow");

	public static final String cookiekey = "hkshoppingcard";

	private static String version = "v1";

	private String cookieDomain;

	private static final int MAX_AGE = 1 * 7 * 24 * 60 * 60;

	public HttpShoppingCard(HttpServletRequest request, String cookieDomain) {
		this(request);
		this.cookieDomain = cookieDomain;
	}

	private void createFromValue(String value) {
		int idx = value.indexOf(";");
		String s_cmpid = value.substring(0, idx);
		this.companyId = Long.valueOf(s_cmpid);
		String othervalue = value.substring(idx + 1);
		String[] t = othervalue.split(",");
		for (String key_value : t) {
			String[] tmp = key_value.split("=");
			this
					.addProduct(Long.valueOf(tmp[0]), true, Integer
							.valueOf(tmp[1]));
		}
	}

	public HttpShoppingCard(HttpServletRequest request) {
		this.cookieDomain = request.getServerName();
		Cookie cookie = ServletUtil.getCookie(request, cookiekey);
		if (cookie != null) {
			try {
				String v = cookie.getValue();
				if (!DataUtil.isEmpty(v)) {
					v = desUtil.decrypt(v);
					int idx = v.indexOf(";");
					if (idx != -1) {
						String cookie_version = v.substring(0, idx);
						if (cookie_version.equals(version)) {// 版本号正确
							String cardv = v.substring(idx + 1);
							this.createFromValue(cardv);
						}
					}
				}
			}
			catch (Exception e) {
			}
		}
	}

	public void saveShoppingCard(HttpServletResponse response) {
		String v = desUtil.encrypt(version + ";" + this.toCardVlaue());
		Cookie cookie = new Cookie(cookiekey, v);
		cookie.setMaxAge(MAX_AGE);
		cookie.setDomain(cookieDomain);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public void addProduct(long productId, boolean add, int count) {
		if (add) {
			this.processAdd(productId, count);
		}
		else {
			this.processDel(productId, count);
		}
	}

	public long getCompanyId() {
		return companyId;
	}

	public void remoreProduct(long productId) {
		ShoppingProduct product = cardMap.get(productId);
		if (product != null) {
			this.totalCount = this.totalCount - product.getCount();
			cardMap.remove(productId);
		}
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String toCardVlaue() {
		Collection<ShoppingProduct> c = this.cardMap.values();
		StringBuilder sb = new StringBuilder();
		sb.append(companyId).append(";");
		for (ShoppingProduct o : c) {
			sb.append(o.getProductId()).append("=").append(o.getCount())
					.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isHasProduct(long productId) {
		return cardMap.containsKey(productId);
	}

	public ShoppingProduct getShoppingProduct(long productId) {
		return cardMap.get(productId);
	}

	public Collection<ShoppingProduct> getShoppingProductList() {
		return this.cardMap.values();
	}

	public void clean() {
		this.companyId = 0;
		this.cardMap.clear();
		this.totalCount = 0;
	}

	/**
	 * 处理向购物车添加物品
	 * 
	 * @param productId
	 * @param count
	 */
	private void processAdd(long productId, int count) {
		ShoppingProduct p = cardMap.get(productId);
		if (p == null) {
			p = new ShoppingProduct(productId);
			cardMap.put(productId, p);
		}
		p.addCount(count);
		this.totalCount = this.totalCount + count;
	}

	/**
	 * 处理减少物品
	 * 
	 * @param productId
	 * @param count
	 */
	private void processDel(long productId, int count) {
		ShoppingProduct p = cardMap.get(productId);
		if (p != null) {
			if (p.addCount(count)) {
				cardMap.remove(productId);
				this.totalCount = this.totalCount - count;
			}
		}
	}

	public void updateProduct(long productId, int count) {
		this.remoreProduct(productId);
		this.addProduct(productId, true, count);
	}
}