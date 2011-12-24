package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业加盟申请
 * 
 * @author akwei
 */
@Table(name = "cmpjoininapply")
public class CmpJoinInApply {

	public static final byte READED_N = 0;

	public static final byte READED_Y = 1;

	@Id
	private long oid;

	/**
	 * 申请人姓名
	 */
	@Column
	private String name;

	@Column
	private long companyId;

	/**
	 * 申请人电话
	 */
	@Column
	private String tel;

	/**
	 * 申请人手机
	 */
	@Column
	private String mobile;

	/**
	 * 申请内容
	 */
	@Column
	private String content;

	/**
	 * 申请人公司
	 */
	@Column
	private String cmpname;

	/**
	 * 申请时间
	 */
	@Column
	private Date createTime;

	/**
	 * 读取标识
	 */
	@Column
	private byte readed;

	@Column
	private String ip;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIp() {
		return ip;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCmpname() {
		return cmpname;
	}

	public void setCmpname(String cmpname) {
		this.cmpname = cmpname;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public byte getReaded() {
		return readed;
	}

	public void setReaded(byte readed) {
		this.readed = readed;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public boolean isApplyReaded() {
		if (this.readed == READED_Y) {
			return true;
		}
		return false;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPJOININAPPLY_NAME_ERROR;
		}
		s = DataUtil.toText(this.mobile);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPJOININAPPLY_MOBILE_ERROR;
		}
		s = DataUtil.toText(this.tel);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPJOININAPPLY_TEL_ERROR;
		}
		s = DataUtil.toText(this.cmpname);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPJOININAPPLY_CMPNAME_ERROR;
		}
		s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 1000) {
			return Err.CMPJOININAPPLY_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}