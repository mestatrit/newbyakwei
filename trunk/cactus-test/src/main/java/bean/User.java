package bean;

import java.util.Date;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "user")
public class User {

	@Id
	private long userid;

	@Column
	private String nick;

	@Column
	private String addr;

	@Column
	private String intro;

	@Column
	private int sex;

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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
}