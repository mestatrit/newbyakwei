package iwant.bean;

import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

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
