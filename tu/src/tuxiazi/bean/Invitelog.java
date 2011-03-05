package tuxiazi.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "invitelog")
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