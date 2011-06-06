package demo.bean;

import java.util.Date;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "userinfo")
public class UserInfo {

	@Id
	private long userid;

	@Column
	private String nick;

	@Column
	private byte gender;

	@Column
	private Date createtime;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}