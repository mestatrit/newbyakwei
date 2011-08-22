package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

import java.util.Date;

import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

@Table(name = "invitelog", partitionClass = TuxiaziDbPartitionHelper.class)
public class Invitelog {

	@Id
	private long logid;

	@Column
	private long userid;

	@Column
	private int api_type;

	@Column
	private String otherid;

	@Column
	private Date createtime;

	public long getLogid() {
		return logid;
	}

	public void setLogid(long logid) {
		this.logid = logid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getApi_type() {
		return api_type;
	}

	public void setApi_type(int apiType) {
		api_type = apiType;
	}

	public String getOtherid() {
		return otherid;
	}

	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}