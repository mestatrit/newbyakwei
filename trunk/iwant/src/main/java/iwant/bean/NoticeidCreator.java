package iwant.bean;

import cactus.dao.annotation.Id;
import cactus.dao.annotation.Table;

@Table(name = "noticeidcreator")
public class NoticeidCreator {

	@Id
	private long noticeid;

	public long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(long noticeid) {
		this.noticeid = noticeid;
	}
}
