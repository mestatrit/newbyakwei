package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业自定义广告,可以是图片，可以是文字
 * 
 * @author akwei
 */
@Table(name = "cmpad")
public class CmpAd {

	public static final byte ADFLG_IMG = 0;

	public static final byte ADFLG_TEXT = 1;

	public static final byte ADFLG_HTML = 2;

	@Id
	private long adid;

	@Column
	private String name;

	@Column
	private String path;

	@Column
	private long companyId;

	@Column
	private String url;

	/**
	 * 广告类型,0:图片类型,1:文字类型,2:广告代码
	 */
	@Column
	private byte adflg;

	/**
	 * 如果推荐到页面模块中，就有模块id，否则，为0
	 */
	@Column
	private long page1BlockId;

	@Column
	private long groupId;

	@Column
	private String html;

	public CmpPageBlock page1Block;

	public void setPage1Block(CmpPageBlock page1Block) {
		this.page1Block = page1Block;
	}

	public CmpPageBlock getPage1Block() {
		return page1Block;
	}

	public void setPage1BlockId(long page1BlockId) {
		this.page1BlockId = page1BlockId;
	}

	public long getPage1BlockId() {
		return page1BlockId;
	}

	public long getAdid() {
		return adid;
	}

	public void setAdid(long adid) {
		this.adid = adid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getPicUrl() {
		return ImageConfig.getCmpAdPicUrl(this.path);
	}

	public byte getAdflg() {
		return adflg;
	}

	public void setAdflg(byte adflg) {
		this.adflg = adflg;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (!DataUtil.isEmpty(s) && s.length() > 50) {
			return Err.CMPAD_NAME_ERROR;
		}
		if (DataUtil.isEmpty(this.html)) {
			s = DataUtil.toText(this.url);
			if (DataUtil.isEmpty(s) || s.length() > 200) {
				return Err.CMPAD_URL_ERROR;
			}
		}
		else {
			if (!HkValidate.validateEmptyAndLength(this.html, false, 1000)) {
				return Err.CMPAD_HTML_ERROR;
			}
		}
		return Err.SUCCESS;
	}

	public boolean isImageAd() {
		if (this.adflg == ADFLG_IMG) {
			return true;
		}
		return false;
	}

	public boolean isTextAd() {
		if (this.adflg == ADFLG_TEXT) {
			return true;
		}
		return false;
	}

	public boolean isHtmlAd() {
		if (this.adflg == ADFLG_HTML) {
			return true;
		}
		return false;
	}
}