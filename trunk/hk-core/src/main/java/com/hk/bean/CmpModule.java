package com.hk.bean;

import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.TemplateUtil;

@Table(name = "cmpmodule", id = "sysId")
public class CmpModule {
	public static final byte SHOWFLG_Y = 0;

	public static final byte SHOWFLG_N = 1;

	private long sysId;

	private long companyId;

	private int templateId;

	private int moduleId;

	private String title;

	private String intro;

	private byte showflg;

	public boolean isShow() {
		if (this.showflg == SHOWFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getShowflg() {
		return showflg;
	}

	public void setShowflg(byte showflg) {
		this.showflg = showflg;
	}

	public TmlModule getTmlModule() {
		return TemplateUtil.geTmlModule(moduleId);
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int validate() {
		String s = DataUtil.toTextRow(this.title);
		if (s == null) {
			return Err.CMPMODULE_TITLE_ERROR;
		}
		if (s.length() > 20) {
			return Err.CMPMODULE_TITLE_LENGTH_TOO_LONG;
		}
		s = DataUtil.toHtml(this.intro);
		if (s != null && s.length() > 200) {
			return Err.CMPMODULE_INTRO_LENGTH_TOO_LONG;
		}
		if (!DataUtil.isInElements(showflg,
				new Object[] { SHOWFLG_N, SHOWFLG_Y })) {
			this.showflg = SHOWFLG_Y;
		}
		return Err.SUCCESS;
	}
}