package com.etbhk.web.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Notice;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.MessageUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_NoticeService;

@Component("/tb/op/notice")
public class NoticeAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_NoticeService tbNoticeService;

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		SimplePage page = req.getSimplePage(20);
		List<Tb_Notice> list = this.tbNoticeService.getTb_NoticeListByuserid(
				this.getLoginTb_User(req).getUserid(), page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		for (Tb_Notice o : list) {
			if (!o.isReaded()) {
				this.tbNoticeService.updateTb_NoticeReadflg(o.getNoticeid(),
						Tb_Notice.READFLG_Y);
			}
		}
		req.setAttribute("list", list);
		return this.getWebJsp("notice/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-9-26
	 */
	public String sysinfo(HkRequest req, HkResponse resp) {
		Tb_User loginTbUser = this.getLoginTb_User(req);
		if (loginTbUser != null) {
			req.setAttribute("noread_notice_count", this.tbNoticeService
					.countNoReadNoticeByUserid(this.getLoginTb_User(req)
							.getUserid()));
		}
		String msg = MessageUtil.getMessage(req);
		if (msg != null) {
			req.setAttribute(HkUtil.MESSAGE_NAME, msg);
		}
		return null;
	}
}