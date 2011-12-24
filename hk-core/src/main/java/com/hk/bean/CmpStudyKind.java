package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 专业分类,最多三级
 * 
 * @author akwei
 */
@Table(name = "cmpstudykind")
public class CmpStudyKind {

	public static final byte CHILDFLG_N = 0;

	public static final byte CHILDFLG_Y = 1;

	@Id
	private long kindId;

	@Column
	private long companyId;

	@Column
	private String name;

	/**
	 * 父类id，如果没有父类，就是顶级，默认为0
	 */
	@Column
	private long parentId;

	/**
	 * 分类级别
	 */
	@Column
	private int klevel;

	/**
	 * 是否有子类
	 */
	@Column
	private byte childflg;

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public byte getChildflg() {
		return childflg;
	}

	public void setChildflg(byte childflg) {
		this.childflg = childflg;
	}

	public boolean isHasChild() {
		if (this.childflg == CHILDFLG_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 50)) {
			return Err.CMPSTUDYKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public int getKlevel() {
		return klevel;
	}

	public void setKlevel(int klevel) {
		this.klevel = klevel;
	}
}