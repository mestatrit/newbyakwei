package com.hk.web.cmpunion.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpUnionFeed;
import com.hk.bean.CmpUnionNotice;
import com.hk.bean.CmpUnionReq;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionMessageService;
import com.hk.web.cmpunion.action.reqexe.ReqExecAble;
import com.hk.web.pub.action.BaseAction;

@Component("/cmpunion/op/message")
public class OpCmpUnionMessageAction extends BaseAction {
	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	public String execute(HkRequest req, HkResponse resp) {
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/welcome.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String req(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		byte dealflg = req.getByteAndSetAttr("dealflg");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionMessageService.countCmpUnionReqByUid(
				uid, dealflg));
		List<CmpUnionReq> list = this.cmpUnionMessageService
				.getCmpUnionReqListByUid(uid, dealflg, page.getBegin(), page
						.getSize());
		List<CmpUnionReqVo> volist = CmpUnionReqVo.createList(req, list);
		req.setAttribute("volist", volist);
		req.setAttribute("op_func", 4);
		return this.getWeb3Jsp("unionadmin/req.jsp");
	}

	/**
	 * 处理请求
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String deal(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		long reqid = req.getLong("reqid");
		CmpUnionReq cmpUnionReq = this.cmpUnionMessageService
				.getCmpUnionReq(reqid);
		if (cmpUnionReq == null || cmpUnionReq.getUid() != uid) {
			return null;
		}
		ReqExecAble.exe(cmpUnionReq);
		cmpUnionReq.setDealflg(CmpUnionReq.DEALFLG_Y);
		this.cmpUnionMessageService.updateCmpUnionReqDeaded(reqid);
		req.setSessionText("func.cmpunionreq.dealok");
		return null;
	}

	/**
	 * 拒绝请求
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String refuse(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		long reqid = req.getLong("reqid");
		CmpUnionReq cmpUnionReq = this.cmpUnionMessageService
				.getCmpUnionReq(reqid);
		if (cmpUnionReq == null || cmpUnionReq.getUid() != uid) {
			return null;
		}
		cmpUnionReq.setDealflg(CmpUnionReq.DEALFLG_Y);
		this.cmpUnionMessageService.updateCmpUnionReqDeaded(reqid);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String feed(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		SimplePage page = req.getSimplePage(20);
		List<CmpUnionFeed> list = this.cmpUnionMessageService
				.getCmpUnionFeedListByUid(uid, page.getBegin(), page.getSize());
		List<CmpUnionFeedVo> volist = CmpUnionFeedVo
				.createList(req, list, true);
		req.setAttribute("volist", volist);
		req.setAttribute("op_func", 8);
		return this.getWeb3Jsp("unionadmin/feed.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String notice(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		byte readflg = req.getByteAndSetAttr("readflg");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionMessageService
				.countCmpUnionNoticeByUid(uid, readflg));
		List<CmpUnionNotice> list = this.cmpUnionMessageService
				.getCmpUnionNoticeList(uid, readflg, page.getBegin(), page
						.getSize());
		req.setAttribute("list", list);
		return this.getWeb3Jsp("unionadmin/notice.jsp");
	}
}