package com.hk.bean;

import java.util.HashMap;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;

/**
 * 企业功能关联表
 * 
 * @author akwei
 */
@Table(name = "cmpfuncref")
public class CmpFuncRef {

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long funcoid;

	/**
	 * 对于功能的配置信息(json格式保存)
	 */
	@Column
	private String cnfdata;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getFuncoid() {
		return funcoid;
	}

	public void setFuncoid(long funcoid) {
		this.funcoid = funcoid;
	}

	public String getCnfdata() {
		return cnfdata;
	}

	public void setCnfdata(String cnfdata) {
		this.cnfdata = cnfdata;
	}

	/**
	 * 初始化配置信息
	 * 2010-8-13
	 */
	public void initCnfData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("open", "1");// 企业自控开关
		map.put("adminopen", "1");// 网站控制开关
		this.cnfdata = DataUtil.toJson(map);
	}

	public boolean isHasFuncUserTable() {
		if (this.funcoid == 2) {
			return true;
		}
		return false;
	}
}