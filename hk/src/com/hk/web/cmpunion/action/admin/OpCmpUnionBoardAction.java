package com.hk.web.cmpunion.action.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpUnionBoard;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUnionService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/cmpunion/op/board")
public class OpCmpUnionBoardAction extends BaseAction {
	@Autowired
	private CmpUnionService cmpUnionService;

	public String execute(HkRequest req, HkResponse resp) {
		long uid = req.getLongAndSetAttr("uid");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionService.countCmpUnionBoardByUid(uid));
		List<CmpUnionBoard> list = this.cmpUnionService
				.getCmpUnionBoardListByUid(uid, page.getBegin(), page
						.getSize());
		req.setAttribute("list", list);
		req.setAttribute("op_func", 5);
		return this.getWeb3Jsp("unionadmin/board/boardlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String board(HkRequest req, HkResponse resp) {
		long boardId = req.getLongAndSetAttr("boardId");
		CmpUnionBoard cmpUnionBoard = this.cmpUnionService
				.getCmpUnionBoard(boardId);
		req.setAttribute("o", cmpUnionBoard);
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/board/board.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String loadboard(HkRequest req, HkResponse resp) {
		long boardId = req.getLongAndSetAttr("boardId");
		CmpUnionBoard cmpUnionBoard = this.cmpUnionService
				.getCmpUnionBoard(boardId);
		req.setAttribute("o", cmpUnionBoard);
		req.reSetAttribute("uid");
		return this.getWeb3Jsp("unionadmin/board/edit_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		String title = req.getString("title");
		String content = req.getString("content");
		CmpUnionBoard cmpUnionBoard = new CmpUnionBoard();
		cmpUnionBoard.setUid(uid);
		cmpUnionBoard.setTitle(DataUtil.toHtmlRow(title));
		cmpUnionBoard.setContent(DataUtil.toHtml(content));
		int code = cmpUnionBoard.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "boarderror", null);
		}
		this.cmpUnionService.createCmpUnionBoard(cmpUnionBoard);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "boardok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		long boardId = req.getLong("boardId");
		String title = req.getString("title");
		String content = req.getString("content");
		CmpUnionBoard cmpUnionBoard = this.cmpUnionService
				.getCmpUnionBoard(boardId);
		cmpUnionBoard.setUid(uid);
		cmpUnionBoard.setTitle(DataUtil.toHtmlRow(title));
		cmpUnionBoard.setContent(DataUtil.toHtml(content));
		int code = cmpUnionBoard.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "boarderror", null);
		}
		this.cmpUnionService.updateCmpUnionBoard(cmpUnionBoard);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "boardok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) {
		long boardId = req.getLong("boardId");
		long uid = req.getLong("uid");
		CmpUnionBoard cmpUnionBoard = this.cmpUnionService
				.getCmpUnionBoard(boardId);
		if (cmpUnionBoard == null || cmpUnionBoard.getUid() != uid) {
			return null;
		}
		this.cmpUnionService.deleteCmpUnionBoard(boardId);
		this.setOpFuncSuccessMsg(req);
		return null;
	}
}