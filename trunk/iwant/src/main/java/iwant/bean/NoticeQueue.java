package iwant.bean;

import com.hk.frame.dao.annotation.Id;

public class NoticeQueue {

	@Id
	private long noticeid;

	public long getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(long noticeid) {
		this.noticeid = noticeid;
	}
}