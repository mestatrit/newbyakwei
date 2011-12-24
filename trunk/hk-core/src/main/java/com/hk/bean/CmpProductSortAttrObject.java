package com.hk.bean;

import java.util.HashMap;
import java.util.Map;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.JsonObj;
import com.hk.frame.util.JsonUtil;

/**
 * 企业分类属性模板字典数据.例如某模板1:材质,2:重量
 * 
 * @author akwei
 */
public class CmpProductSortAttrObject {

	private JsonObj jsonObj;

	private String attr1Name;

	private String attr2Name;

	private String attr3Name;

	private String attr4Name;

	private String attr5Name;

	private String attr6Name;

	private String attr7Name;

	private String attr8Name;

	private String attr9Name;

	private boolean attr1Pink;

	private boolean attr2Pink;

	private boolean attr3Pink;

	private boolean attr4Pink;

	private boolean attr5Pink;

	private boolean attr6Pink;

	private boolean attr7Pink;

	private boolean attr8Pink;

	private boolean attr9Pink;

	/**
	 *被推荐的属性
	 */
	private String[] pink;

	public CmpProductSortAttrObject(String json) {
		jsonObj = JsonUtil.getJsonObj(json);
		String v = this.jsonObj.getString("pink");
		if (!DataUtil.isEmpty(v)) {
			pink = v.split(",");
		}
		this.attr1Name = this.jsonObj.getString("1");
		this.attr2Name = this.jsonObj.getString("2");
		this.attr3Name = this.jsonObj.getString("3");
		this.attr4Name = this.jsonObj.getString("4");
		this.attr5Name = this.jsonObj.getString("5");
		this.attr6Name = this.jsonObj.getString("6");
		this.attr7Name = this.jsonObj.getString("7");
		this.attr8Name = this.jsonObj.getString("8");
		this.attr9Name = this.jsonObj.getString("9");
		this.attr1Pink = this.isPink("1");
		this.attr2Pink = this.isPink("2");
		this.attr3Pink = this.isPink("3");
		this.attr4Pink = this.isPink("4");
		this.attr5Pink = this.isPink("5");
		this.attr6Pink = this.isPink("6");
		this.attr7Pink = this.isPink("7");
		this.attr8Pink = this.isPink("8");
		this.attr9Pink = this.isPink("9");
	}

	public CmpProductSortAttrObject() {
	}

	public String getAttr(String key) {
		return this.jsonObj.getString(key);
	}

	/**
	 * 属性是否被推荐
	 * 
	 * @param attr
	 * @return
	 *         2010-6-9
	 */
	public boolean isPink(String attr) {
		if (pink == null) {
			return false;
		}
		for (String t : pink) {
			if (t.equals(attr)) {
				return true;
			}
		}
		return false;
	}

	public String getAttr1Name() {
		return attr1Name;
	}

	public void setAttr1Name(String attr1Name) {
		this.attr1Name = attr1Name;
	}

	public String getAttr2Name() {
		return attr2Name;
	}

	public void setAttr2Name(String attr2Name) {
		this.attr2Name = attr2Name;
	}

	public String getAttr3Name() {
		return attr3Name;
	}

	public void setAttr3Name(String attr3Name) {
		this.attr3Name = attr3Name;
	}

	public String getAttr4Name() {
		return attr4Name;
	}

	public void setAttr4Name(String attr4Name) {
		this.attr4Name = attr4Name;
	}

	public String getAttr5Name() {
		return attr5Name;
	}

	public void setAttr5Name(String attr5Name) {
		this.attr5Name = attr5Name;
	}

	public String getAttr6Name() {
		return attr6Name;
	}

	public void setAttr6Name(String attr6Name) {
		this.attr6Name = attr6Name;
	}

	public String getAttr7Name() {
		return attr7Name;
	}

	public void setAttr7Name(String attr7Name) {
		this.attr7Name = attr7Name;
	}

	public String getAttr8Name() {
		return attr8Name;
	}

	public void setAttr8Name(String attr8Name) {
		this.attr8Name = attr8Name;
	}

	public String getAttr9Name() {
		return attr9Name;
	}

	public void setAttr9Name(String attr9Name) {
		this.attr9Name = attr9Name;
	}

	public boolean isAttr1Pink() {
		return attr1Pink;
	}

	public void setAttr1Pink(boolean attr1Pink) {
		this.attr1Pink = attr1Pink;
	}

	public boolean isAttr2Pink() {
		return attr2Pink;
	}

	public void setAttr2Pink(boolean attr2Pink) {
		this.attr2Pink = attr2Pink;
	}

	public boolean isAttr3Pink() {
		return attr3Pink;
	}

	public void setAttr3Pink(boolean attr3Pink) {
		this.attr3Pink = attr3Pink;
	}

	public boolean isAttr4Pink() {
		return attr4Pink;
	}

	public void setAttr4Pink(boolean attr4Pink) {
		this.attr4Pink = attr4Pink;
	}

	public boolean isAttr5Pink() {
		return attr5Pink;
	}

	public void setAttr5Pink(boolean attr5Pink) {
		this.attr5Pink = attr5Pink;
	}

	public boolean isAttr6Pink() {
		return attr6Pink;
	}

	public void setAttr6Pink(boolean attr6Pink) {
		this.attr6Pink = attr6Pink;
	}

	public boolean isAttr7Pink() {
		return attr7Pink;
	}

	public void setAttr7Pink(boolean attr7Pink) {
		this.attr7Pink = attr7Pink;
	}

	public boolean isAttr8Pink() {
		return attr8Pink;
	}

	public void setAttr8Pink(boolean attr8Pink) {
		this.attr8Pink = attr8Pink;
	}

	public boolean isAttr9Pink() {
		return attr9Pink;
	}

	public void setAttr9Pink(boolean attr9Pink) {
		this.attr9Pink = attr9Pink;
	}

	public String toJson() {
		Map<String, String> map = new HashMap<String, String>();
		if (!DataUtil.isEmpty(this.attr1Name)) {
			map.put("1", this.attr1Name);
		}
		if (!DataUtil.isEmpty(this.attr2Name)) {
			map.put("2", this.attr2Name);
		}
		if (!DataUtil.isEmpty(this.attr3Name)) {
			map.put("3", this.attr3Name);
		}
		if (!DataUtil.isEmpty(this.attr4Name)) {
			map.put("4", this.attr4Name);
		}
		if (!DataUtil.isEmpty(this.attr5Name)) {
			map.put("5", this.attr5Name);
		}
		if (!DataUtil.isEmpty(this.attr6Name)) {
			map.put("6", this.attr6Name);
		}
		if (!DataUtil.isEmpty(this.attr7Name)) {
			map.put("7", this.attr7Name);
		}
		if (!DataUtil.isEmpty(this.attr8Name)) {
			map.put("8", this.attr8Name);
		}
		if (!DataUtil.isEmpty(this.attr9Name)) {
			map.put("9", this.attr9Name);
		}
		StringBuilder sb = new StringBuilder();
		if (this.isAttr1Pink()) {
			sb.append(1).append(",");
		}
		if (this.isAttr2Pink()) {
			sb.append(2).append(",");
		}
		if (this.isAttr3Pink()) {
			sb.append(3).append(",");
		}
		if (this.isAttr4Pink()) {
			sb.append(4).append(",");
		}
		if (this.isAttr5Pink()) {
			sb.append(5).append(",");
		}
		if (this.isAttr6Pink()) {
			sb.append(6).append(",");
		}
		if (this.isAttr7Pink()) {
			sb.append(7).append(",");
		}
		if (this.isAttr8Pink()) {
			sb.append(8).append(",");
		}
		if (this.isAttr9Pink()) {
			sb.append(9).append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
			map.put("pink", sb.toString());
		}
		return JsonUtil.toJson(map);
	}

	/**
	 * 验证数据有效性，如果验证通过，返回0,验证不通过，返回有错误的位置，例如返回1，就是第一项验证不通过，下标从1开始
	 * 
	 * @return
	 *         2010-6-9
	 */
	public int getValidateErrorPos() {
		String[] t = new String[9];
		t[0] = attr1Name;
		t[1] = attr2Name;
		t[2] = attr3Name;
		t[3] = attr4Name;
		t[4] = attr5Name;
		t[5] = attr6Name;
		t[6] = attr7Name;
		t[7] = attr8Name;
		t[8] = attr9Name;
		for (int i = 0; i < t.length; i++) {
			if (!HkValidate.validateLength(t[i], true, 20)) {
				return i + 1;
			}
		}
		return 0;
	}
}