package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业论坛分类，有管理员定义分类
 * 
 * @author akwei
 */
@Table(name = "cmpbbskind")
public class CmpBbsKind {

	public static final byte MUSTPIC_N = 0;

	public static final byte MUSTPIC_Y = 1;

	/**
	 * id
	 */
	@Id
	private long kindId;

	/**
	 * 企业id
	 */
	@Column
	private long companyId;

	/**
	 * 分类名称
	 */
	@Column
	private String name;

	/**
	 * 主贴是否必须有图片
	 */
	@Column
	private byte mustpic;

	public void setMustpic(byte mustpic) {
		this.mustpic = mustpic;
	}

	public byte getMustpic() {
		return mustpic;
	}

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

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPBBSKIND_NAME_ERROR;
		}
		return Err.SUCCESS;
	}
}