package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 存放临时数据的表，例如wap创建足迹与宝箱需要写入正确的城市名称时，为了便于查询城市的方便，先会把数据都以json的格式存储到这个表中，
 * 当选定好其他数据以后，会成功创建正确的数据，并把临时数据删除
 * 
 * @author akwei
 */
@Table(name = "tmpdata")
public class TmpData {

	public static final byte DATATYPE_BOX = 0;

	public static final byte DATATYPE_CMP = 1;

	public static final byte DATATYPE_COUPON = 2;

	public static final byte DATATYPE_HKAD = 3;

	@Id
	private long oid;

	@Column
	private long userId;

	@Column
	private byte datatype;

	/**
	 * 存储的是json格式的数据
	 */
	@Column
	private String data;

	@Column
	private Date createTime;

	public byte getDatatype() {
		return datatype;
	}

	public void setDatatype(byte datatype) {
		this.datatype = datatype;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}