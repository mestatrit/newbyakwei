package com.hk.web.box.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.BoxPrize;
import com.hk.bean.UserBoxPrize;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxService;
import com.hk.svr.processor.BoxProcessor;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/box")
public class OpBox2Action extends BaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private BoxProcessor boxProcessor;

	public String execute(HkRequest req, HkResponse resp) {
		return null;
	}

	/**
	 * 兑奖
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-18
	 */
	public String userprize(HkRequest req, HkResponse resp) {
		if (!hasOpBoxPower(req)) {
			return null;
		}
		int ch = req.getIntAndSetAttr("ch");
		long boxId = req.getLongAndSetAttr("boxId");
		byte drawflg = req.getByteAndSetAttr("drawflg");
		SimplePage page = req.getSimplePage(10);
		List<UserBoxPrize> list = this.boxProcessor
				.getUserBoxPrizeListByBoxIdAndDrawflg(boxId, drawflg, true,
						false, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		if (ch == 1) {
			String num = req.getString("num");
			String pwd = req.getString("pwd");
			UserBoxPrize userBoxPrize = this.boxService
					.getUserBoxPrizeByBoxIdAndNumAndPwd(boxId, num, pwd);
			if (userBoxPrize != null) {
				BoxPrize boxPrize = this.boxService.getBoxPrize(userBoxPrize
						.getPrizeId());
				req.setAttribute("boxPrize", boxPrize);
				req.setAttribute("userBoxPrize", userBoxPrize);
			}
		}
		return this.getWeb4Jsp("box/op/userprize.jsp");
	}

	/**
	 * 设置兑奖成功
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-18
	 */
	public String setuserprizeok(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		long sysId = req.getLong("sysId");
		if (!hasOpBoxPower(req)) {
			return null;
		}
		this.boxService.setUserBoxPrizeDrawed(sysId);
		this.setOpFuncSuccessMsg(req);
		return "r:/h4/op/box_userprize.do?boxId=" + boxId;
	}
}