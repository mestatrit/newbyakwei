package iwant.bean;

import cactus.dao.annotation.Id;
import cactus.dao.annotation.Table;

@Table(name = "useridcreator")
public class UseridCreator {

	@Id
	private long userid;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}
