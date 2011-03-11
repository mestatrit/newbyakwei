package sqlupdatemapper;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "testuser")
public class TestUser {

	@Id
	private long userid;

	@Column
	private String nick;

	@Column
	private Date createtime;

	@Column
	private byte gender;

	@Column
	private double money;

	@Column
	private float purchase;

	@Column
	private char purchase1;

	public char getPurchase1() {
		return purchase1;
	}

	public void setPurchase1(char purchase1) {
		this.purchase1 = purchase1;
	}

	public long getUserid() {
		return userid;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public float getPurchase() {
		return purchase;
	}

	public void setPurchase(float purchase) {
		this.purchase = purchase;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}