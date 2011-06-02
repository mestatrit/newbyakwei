package iwant.web.admin;

import iwant.bean.Notice;
import iwant.bean.Ppt;
import iwant.bean.validate.NoticeValidator;
import iwant.svr.NoticeSvr;
import iwant.svr.PptSvr;
import iwant.web.BaseAction;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;

@Component("/mgr/notice")
public class NoticeAction extends BaseAction {

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private NoticeSvr noticeSvr;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		Ppt ppt = this.pptSvr.getPpt(pptid);
		if (ppt == null) {
			return null;
		}
		Notice notice = new Notice();
		notice.setPptid(pptid);
		notice.setProjectid(ppt.getProjectid());
		notice.setCreatetime(new Date());
		notice.setContent(req.getStringRow("content"));
		List<String> errlist = NoticeValidator.validate(notice);
		if (!errlist.isEmpty()) {
			return this.onError(req, errlist.get(0), "createnoticeerr", null);
		}
		this.noticeSvr.createNotice(notice);
		return this.onSuccess(req, "createnoticeok", null);
	}
}