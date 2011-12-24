package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpproductsort")
public class CmpProductSort {

	public static final byte CHILDFLG_N = 0;

	public static final byte CHILDFLG_Y = 1;

	@Id
	private int sortId;

	@Column
	private String name;

	@Column
	private long companyId;

	@Column
	private int productCount;

	/**
	 * 父分类id
	 */
	@Column
	private int parentId;

	/**
	 *是否有子分类
	 */
	@Column
	private byte childflg;

	/**
	 * 分类级别最多到3级
	 */
	@Column
	private int nlevel;

	@Column
	private String parentData;

	private List<CmpProductSort> children;

	public void setChildren(List<CmpProductSort> children) {
		this.children = children;
	}

	public List<CmpProductSort> getChildren() {
		return children;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.name);
		if (DataUtil.isEmpty(s)) {
			return Err.CMPPRODUCTSORT_NAME_ERROR;
		}
		if (s.length() > 15) {
			return Err.CMPPRODUCTSORT_NAME_ERROR;
		}
		if (this.companyId <= 0) {
			return Err.HKOBJID_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public byte getChildflg() {
		return childflg;
	}

	public void setChildflg(byte childflg) {
		this.childflg = childflg;
	}

	public boolean isHasChildren() {
		if (this.childflg == CHILDFLG_Y) {
			return true;
		}
		return false;
	}

	public int getNlevel() {
		return nlevel;
	}

	public void setNlevel(int nlevel) {
		this.nlevel = nlevel;
	}

	public String getParentData() {
		return parentData;
	}

	public void setParentData(String parentData) {
		this.parentData = parentData;
	}

	public List<Integer> getParentIds() {
		List<Integer> list = new ArrayList<Integer>();
		if (!DataUtil.isEmpty(this.parentData)) {
			String[] t = this.parentData.split(",");
			for (String s : t) {
				if (s.equals("0")) {
					continue;
				}
				try {
					list.add(Integer.valueOf(s));
				}
				catch (NumberFormatException e) {
				}
			}
		}
		return list;
	}
}