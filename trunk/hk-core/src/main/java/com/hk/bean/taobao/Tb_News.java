package com.hk.bean.taobao;

import java.util.Date;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.JsonUtil;

@Table(name = "tb_news")
public class Tb_News {

	public static final int NTYPE_CREATEITEM = 1;

	public static final int NTYPE_CREATEITEM_CMT = 2;

	public static final int NTYPE_CREATEASK = 3;

	public static final int NTYPE_CREATEANSWER = 4;

	public static final int NTYPE_CREATEFOLLOW = 5;

	@Id
	private long nid;

	@Column
	private long userid;

	@Column
	private long oid;

	@Column
	private int ntype;

	@Column
	private String data;

	@Column
	private Date create_time;

	public long getNid() {
		return nid;
	}

	public void setNid(long nid) {
		this.nid = nid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public int getNtype() {
		return ntype;
	}

	public void setNtype(int ntype) {
		this.ntype = ntype;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	private Map<String, String> dataMap;

	public Map<String, String> getDataMap() {
		if (dataMap == null) {
			dataMap = JsonUtil.getMapFromJson(this.data);
		}
		return dataMap;
	}
}