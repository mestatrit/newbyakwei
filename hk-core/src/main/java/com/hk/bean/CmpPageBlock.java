package com.hk.bean;

import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.JsonObj;
import com.hk.svr.pub.CmpPageModUtil;
import com.hk.svr.pub.Err;

/**
 * 企业网站页面区块定义
 * 
 * @author akwei
 */
@Table(name = "cmppageblock")
public class CmpPageBlock {

	@Id
	private long blockId;

	@Column
	private String name;

	@Column
	private int pageModId;

	@Column
	private long companyId;

	/**
	 * 页面类型,1:首页,2:二级页面,3:三级页面
	 */
	@Column
	private byte pageflg;

	@Column
	private int orderflg;

	/**
	 * 条件表达式可以是groupid,navid,tagid,select:1时为推荐的数据(数据为json格式)
	 */
	@Column
	private String expression;

	private int remainSize;

	private CmpPageMod cmpPageMod;

	private JsonObj jsonObj;

	public void setJsonObj(JsonObj jsonObj) {
		this.jsonObj = jsonObj;
	}

	public JsonObj getJsonObj() {
		return jsonObj;
	}

	public CmpPageMod getCmpPageMod() {
		if (this.cmpPageMod == null) {
			this.cmpPageMod = CmpPageModUtil.getCmpPageMod(this.pageModId);
		}
		return cmpPageMod;
	}

	public long getBlockId() {
		return blockId;
	}

	public void setBlockId(long blockId) {
		this.blockId = blockId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPageModId() {
		return pageModId;
	}

	public void setPageModId(int pageModId) {
		this.pageModId = pageModId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public byte getPageflg() {
		return pageflg;
	}

	public void setPageflg(byte pageflg) {
		this.pageflg = pageflg;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public int getRemainSize() {
		return remainSize;
	}

	public void setRemainSize(int remainSize) {
		this.remainSize = remainSize;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setCmpPageMod(CmpPageMod cmpPageMod) {
		this.cmpPageMod = cmpPageMod;
	}

	/**
	 * 是否是根据条件自动获取内容
	 * 
	 * @return
	 *         2010-7-4
	 */
	public boolean isAuto() {
		this.initJsonObj();
		if (this.jsonObj == null) {
			return false;
		}
		if ("1".equals(this.jsonObj.getString("auto"))) {
			return true;
		}
		return false;
	}

	public long getGroupId() {
		this.initJsonObj();
		if (this.jsonObj == null) {
			return 0;
		}
		return this.jsonObj.getLong("groupid");
	}

	public long getNavId() {
		this.initJsonObj();
		if (this.jsonObj == null) {
			return 0;
		}
		return this.jsonObj.getLong("navid");
	}

	public long getTagId() {
		this.initJsonObj();
		if (this.jsonObj == null) {
			return 0;
		}
		return this.jsonObj.getLong("tagid");
	}

	private void initJsonObj() {
		if (this.expression == null) {
			return;
		}
		Map<String, String> map = DataUtil.getMapFromJson(this.expression);
		if (map != null) {
			this.jsonObj = new JsonObj(map);
		}
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 100)) {
			return Err.CMPPAGEBLOCK_NAME_ERROR;
		}
		if (this.pageModId <= 0) {
			return Err.CMPPAGEBLOCK_PAGEMODID_ERROR;
		}
		return Err.SUCCESS;
	}
}