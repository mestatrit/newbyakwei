package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.P;
import com.hk.frame.util.i18n.HkI18n;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Table(name = "cmpinfo", id = "companyid")
public class CmpInfo {

	public static final byte STYLEFLG_N = 0;

	public static final byte STYLEFLG_Y = 1;

	@Id
	private long companyId;

	@Column
	private String domain;

	/**
	 * 存储数据为用户自定义颜色值(json格式)
	 */
	@Column
	private String styleData;

	/**
	 * 是否启用自定义颜色
	 */
	@Column
	private byte styleflg;

	/**
	 * @see {@link HkI18n}
	 */
	@Column
	private int language;

	/**
	 * 模板类型
	 */
	@Column
	private int tmlflg;

	/**
	 * 文章页广告代码
	 */
	@Column
	private String articlead;

	/**
	 * 栏目页面广告代码
	 */
	@Column
	private String columnad;

	@Column
	private String bgPicPath;

	/**
	 * 版权信息
	 */
	@Column
	private String cpinfo;

	public void setStyleData(String styleData) {
		this.styleData = styleData;
	}

	public String getStyleData() {
		return styleData;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public byte getStyleflg() {
		return styleflg;
	}

	public void setStyleflg(byte styleflg) {
		this.styleflg = styleflg;
	}

	public boolean isEnableUserStyle() {
		if (this.styleflg == STYLEFLG_Y) {
			return true;
		}
		return false;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getTmlflg() {
		return tmlflg;
	}

	public void setTmlflg(int tmlflg) {
		this.tmlflg = tmlflg;
	}

	public String getArticlead() {
		return articlead;
	}

	public void setArticlead(String articlead) {
		this.articlead = articlead;
	}

	public String getColumnad() {
		return columnad;
	}

	public void setColumnad(String columnad) {
		this.columnad = columnad;
	}

	public int validate() {
		if (!HkValidate.validateLength(this.articlead, 1000)) {
			return Err.CMPINFO_ARTICLEAD_ERROR;
		}
		if (!HkValidate.validateLength(this.columnad, 1000)) {
			return Err.CMPINFO_COLUMNAD_ERROR;
		}
		if (!HkValidate.validateLength(this.cpinfo, true, 300)) {
			return Err.CMPINFO_CPINFO_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getDomainName() {
		int idx = this.domain.indexOf(".com");
		if (idx == -1) {
			idx = this.domain.indexOf(".cn");
		}
		if (idx != -1) {
			String v = this.domain.substring(0, idx);
			if (v.length() > 1) {
				idx = v.indexOf('.');
				if (idx != -1) {
					v = v.substring(idx, v.length());
				}
			}
			return v;
		}
		return this.domain;
	}

	public String getBgPicPath() {
		return bgPicPath;
	}

	public void setBgPicPath(String bgPicPath) {
		this.bgPicPath = bgPicPath;
	}

	public String getBgPicUrl() {
		return ImageConfig.getCompanyBgPicUrl(this.bgPicPath);
	}

	public String getCpinfo() {
		return cpinfo;
	}

	public void setCpinfo(String cpinfo) {
		this.cpinfo = cpinfo;
	}

	public static void main(String[] args) {
		String domain = "www.p.com.cn";
		int idx = domain.indexOf(".com");
		if (idx == -1) {
			idx = domain.indexOf(".cn");
		}
		if (idx != -1) {
			String v = domain.substring(0, idx);
			if (v.length() > 1) {
				idx = v.indexOf('.');
				if (idx != -1) {
					v = v.substring(idx + 1, v.length());
				}
			}
			P.println(v);
		}
		P.println(domain);
	}
}