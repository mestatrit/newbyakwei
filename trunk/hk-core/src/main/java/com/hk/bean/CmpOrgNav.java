package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;

/**
 * 机构的栏目
 * 
 * @author akwei
 */
@Table(name = "cmporgnav")
public class CmpOrgNav {

	public static List<CmpOrgNav> defCmpOrgNavs;

	public void setDefCmpOrgNavs(List<CmpOrgNav> defCmpOrgNavs) {
		CmpOrgNav.defCmpOrgNavs = defCmpOrgNavs;
	}

	public static List<CmpOrgNav> getDefCmpOrgNavs() {
		List<CmpOrgNav> list = new ArrayList<CmpOrgNav>();
		for (CmpOrgNav o : defCmpOrgNavs) {
			CmpOrgNav cmpOrgNav = new CmpOrgNav();
			cmpOrgNav.setName(o.getName());
			cmpOrgNav.setReffunc(o.getReffunc());
			cmpOrgNav.setShowflg(o.getShowflg());
			list.add(cmpOrgNav);
		}
		return list;
	}

	/**
	 * id
	 */
	@Id
	private long navId;

	/**
	 * 企业id
	 */
	@Column
	private long companyId;

	/**
	 * 机构id
	 */
	@Column
	private long orgId;

	/**
	 * 名称
	 */
	@Column
	private String name;

	/**
	 * 挂接功能
	 */
	@Column
	private int reffunc;

	/**
	 * 顺序号设置
	 */
	@Column
	private int orderflg;

	/**
	 * 参考 {@link CmpNav#SHOWFLG_IMG} ,{@link CmpNav#SHOWFLG_TITLE} ,
	 */
	@Column
	private byte showflg;

	public long getNavId() {
		return navId;
	}

	public void setNavId(long navId) {
		this.navId = navId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public int getReffunc() {
		return reffunc;
	}

	public void setReffunc(int reffunc) {
		this.reffunc = reffunc;
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPORGNAV_NAME_ERROR;
		}
		return Err.SUCCESS;
	}

	public byte getShowflg() {
		return showflg;
	}

	public void setShowflg(byte showflg) {
		this.showflg = showflg;
	}

	public boolean isArticleList() {
		if (this.reffunc == CmpNav.REFFUNC_LISTCONTENT) {
			return true;
		}
		return false;
	}

	public boolean isArticleSingle() {
		if (this.reffunc == CmpNav.REFFUNC_SINGLECONTENT) {
			return true;
		}
		return false;
	}

	/**
	 * 文章是否是以图片列表方式展示
	 * 
	 * @return
	 *         2010-5-19
	 */
	public boolean isArticleListWithImgShow() {
		if (this.showflg == CmpNav.SHOWFLG_IMG) {
			return true;
		}
		return false;
	}
}