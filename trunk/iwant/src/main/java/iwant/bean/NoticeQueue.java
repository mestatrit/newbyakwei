package iwant.bean;

import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

@Table(name = "noticequeue")
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