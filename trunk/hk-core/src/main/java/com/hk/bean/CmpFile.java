package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业网站相关文件
 * 
 * @author akwei
 */
@Table(name = "cmpfile")
public class CmpFile {

	public static final byte FILEFLG_NONE = 0;

	public static final byte FILEFLG_IMG = 1;

	public static final byte FILEFLG_SWF = 2;

	public static final byte TOPFLG_N = 0;

	public static final byte TOPFLG_Y = 1;

	@Id
	private long oid;

	/**
	 * 关联企业文章id
	 */
	@Column
	private long articleOid;

	@Column
	private long companyId;

	/**
	 * 文件类型
	 */
	@Column
	private byte fileflg;

	@Column
	private String path;

	/**
	 * 是否是置顶文件
	 */
	@Column
	private byte topflg;

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte getFileflg() {
		return fileflg;
	}

	public void setFileflg(byte fileflg) {
		this.fileflg = fileflg;
	}

	public long getArticleOid() {
		return articleOid;
	}

	public void setArticleOid(long articleOid) {
		this.articleOid = articleOid;
	}

	public byte getTopflg() {
		return topflg;
	}

	public void setTopflg(byte topflg) {
		this.topflg = topflg;
	}

	public boolean isTopInFile() {
		if (this.topflg == TOPFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isImageShow() {
		if (this.fileflg == CmpFile.FILEFLG_IMG) {
			return true;
		}
		return false;
	}

	public boolean isFlashShow() {
		if (this.fileflg == CmpFile.FILEFLG_SWF) {
			return true;
		}
		return false;
	}

	public String getPic60() {
		return ImageConfig.getCmpFilePic60Url(path);
	}

	public String getCmpFilePic120() {
		return ImageConfig.getCmpFilePic120Url(path);
	}

	public String getCmpFilePic240() {
		return ImageConfig.getCmpFilePic240Url(path);
	}

	public String getCmpFilePic320() {
		return ImageConfig.getCmpFilePic320Url(path);
	}

	public String getCmpFilePic600() {
		return ImageConfig.getCmpFilePic600Url(path);
	}

	public String getCmpFilePic800() {
		return ImageConfig.getCmpFilePic800Url(path);
	}

	public String getCmpFileFlash() {
		return ImageConfig.getCmpFileFlashUrl(path);
	}
}