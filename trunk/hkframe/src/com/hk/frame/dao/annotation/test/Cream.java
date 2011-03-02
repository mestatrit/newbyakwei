package com.hk.frame.dao.annotation.test;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Table;

@Table(name = "cream", id = "id")
public class Cream {
	@Column
	private long id;

	@Column
	private String name;

	@Column("user_id")
	private String userId;

	@Column
	private String addr;

	@Column
	private String traffic;

	@Column
	private String abc;

	@Column
	private String abc1;

	@Column
	private String abc2;

	@Column
	private String abc3;

	@Column
	private String abc4;

	@Column
	private String abc5;

	@Column
	private String abc6;

	@Column
	private String abc8;

	@Column
	private String abc7;

	@Column
	private String abc9;

	@Column
	private String abc10;

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getAbc() {
		return abc;
	}

	public void setAbc(String abc) {
		this.abc = abc;
	}

	public String getAbc1() {
		return abc1;
	}

	public void setAbc1(String abc1) {
		this.abc1 = abc1;
	}

	public String getAbc2() {
		return abc2;
	}

	public void setAbc2(String abc2) {
		this.abc2 = abc2;
	}

	public String getAbc3() {
		return abc3;
	}

	public void setAbc3(String abc3) {
		this.abc3 = abc3;
	}

	public String getAbc4() {
		return abc4;
	}

	public void setAbc4(String abc4) {
		this.abc4 = abc4;
	}

	public String getAbc5() {
		return abc5;
	}

	public void setAbc5(String abc5) {
		this.abc5 = abc5;
	}

	public String getAbc6() {
		return abc6;
	}

	public void setAbc6(String abc6) {
		this.abc6 = abc6;
	}

	public String getAbc8() {
		return abc8;
	}

	public void setAbc8(String abc8) {
		this.abc8 = abc8;
	}

	public String getAbc7() {
		return abc7;
	}

	public void setAbc7(String abc7) {
		this.abc7 = abc7;
	}

	public String getAbc9() {
		return abc9;
	}

	public void setAbc9(String abc9) {
		this.abc9 = abc9;
	}

	public String getAbc10() {
		return abc10;
	}

	public void setAbc10(String abc10) {
		this.abc10 = abc10;
	}
}