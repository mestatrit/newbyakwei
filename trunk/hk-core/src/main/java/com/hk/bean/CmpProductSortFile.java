package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 产品最小分类可以上传的图片
 * 
 * @author akwei
 */
@Table(name = "cmpproductsortfile")
public class CmpProductSortFile {

	@Id
	private long oid;

	@Column
	private String name;

	@Column
	private long companyId;

	/**
	 * 产品分类id
	 */
	@Column
	private int sortId;

	/**
	 * 图片路径
	 */
	@Column
	private String path;

	/**
	 * 文件指向的链接地址
	 */
	@Column
	private String url;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
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

	public String getPicUrl() {
		return ImageConfig.getCmpProductSortPicUrl(this.path);
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.name, true, 20)) {
			return Err.CMPPRODUCTSORTFILE_NAME_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.url, true, 200)) {
			return Err.CMPPRODUCTSORTFILE_URL_ERROR;
		}
		return Err.SUCCESS;
	}
}