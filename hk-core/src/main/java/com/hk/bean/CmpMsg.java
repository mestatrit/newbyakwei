package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业留言板
 * 
 * @author akwei
 */
@Table(name = "cmpmsg")
public class CmpMsg {

	@Id
	private long oid;

	@Column
	private long companyId;

	/**
	 * 姓名
	 */
	@Column
	private String name;

	/**
	 * 联系电话
	 */
	@Column
	private String tel;

	/**
	 * 留言内容
	 */
	@Column
	private String content;

	/**
	 * 留言时间
	 */
	@Column
	private Date createTime;

	@Column
	private String ip;

	@Column
	private byte cmppink;

	@Column
	private Date cmppinkTime;

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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPMSG_NAME_ERROR;
		}
		s = DataUtil.toText(this.tel);
		if (DataUtil.isEmpty(s) || s.length() > 20) {
			return Err.CMPMSG_TEL_ERROR;
		}
		s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 300) {
			return Err.CMPMSG_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}

	public byte getCmppink() {
		return cmppink;
	}

	public void setCmppink(byte cmppink) {
		this.cmppink = cmppink;
	}

	public Date getCmppinkTime() {
		return cmppinkTime;
	}

	public void setCmppinkTime(Date cmppinkTime) {
		this.cmppinkTime = cmppinkTime;
	}
}