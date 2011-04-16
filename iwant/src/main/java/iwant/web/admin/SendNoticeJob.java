package iwant.web.admin;

import iwant.svr.NoticeSvr;

import org.springframework.beans.factory.annotation.Autowired;

public class SendNoticeJob {

	@Autowired
	private NoticeSvr noticeSvr;

	private boolean processing;

	public void invoke() {
	}
}
