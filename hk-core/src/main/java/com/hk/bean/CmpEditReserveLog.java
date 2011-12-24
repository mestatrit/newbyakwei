package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 后台管理人员对预约数据进行修改的日志
 * 
 * @author akwei
 */
@Table(name = "cmpeditreservelog")
public class CmpEditReserveLog {

	@Id
	private long logid;

	@Column
	private long companyId;

	@Column
	private long reserveId;

	@Column
	private Date createTime;

	@Column
	private String data;

	public long getLogid() {
		return logid;
	}

	public void setLogid(long logid) {
		this.logid = logid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getReserveId() {
		return reserveId;
	}

	public void setReserveId(long reserveId) {
		this.reserveId = reserveId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}