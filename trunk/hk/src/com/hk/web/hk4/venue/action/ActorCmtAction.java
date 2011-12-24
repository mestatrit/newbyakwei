package com.hk.web.hk4.venue.action;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/venue/actorcmt")
public class ActorCmtAction extends BaseAction {

	public String execute(HkRequest req, HkResponse resp) {
		return null;
		// long actorId = req.getLongAndSetAttr("actorId");
		// CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		// if (cmpActor == null) {
		// return null;
		// }
		// SimplePage page = req.getSimplePage(20);
		// List<CmpActorCmt> list = this.cmpActorCmtProcessor
		// .getCmpActorCmtListByActorId(actorId, true, false, page
		// .getBegin(), page.getSize() + 1);
		// this.processListForPage(page, list);
		// req.setAttribute("list", list);
		// return this.getWeb4Jsp("venue/meifa/actcmtlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-23
	 */
	public String prvcreate(HkRequest req, HkResponse resp) {
		return null;
		// long actorId = req.getLongAndSetAttr("actorId");
		// User loginUser = this.getLoginUser(req);
		// CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		// if (cmpActor == null) {
		// return null;
		// }
		// CmpActorCmt cmpActorCmt = new CmpActorCmt();
		// cmpActorCmt.setActorId(actorId);
		// cmpActorCmt.setCompanyId(cmpActor.getCompanyId());
		// cmpActorCmt.setUserId(loginUser.getUserId());
		// cmpActorCmt.setContent(req.getHtml("content"));
		// cmpActorCmt.setCreateTime(new Date());
		// CmpActorScore cmpActorScore = new CmpActorScore();
		// cmpActorScore.setActorId(actorId);
		// cmpActorScore.setCompanyId(cmpActor.getCompanyId());
		// cmpActorScore.setUserId(loginUser.getUserId());
		// cmpActorScore.setScore(req.getInt("score"));
		// if (DataUtil.isEmpty(cmpActorCmt.getContent())) {
		// this.cmpActorCmtProcessor.saveCmpActorScore(cmpActorScore);
		// }
		// else {
		// int code = cmpActorCmt.validate();
		// if (code != Err.SUCCESS) {
		// return this.onError(req, code, "createerr", null);
		// }
		// this.cmpActorCmtProcessor.createCmpActorCmt(cmpActorCmt,
		// cmpActorScore);
		// req.setSessionValue("to_cmt", true);
		// this.setOpFuncSuccessMsg(req);
		// }
		// return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-23
	 */
	public String prvupdate(HkRequest req, HkResponse resp) {
		return null;
		// long cmtId = req.getLongAndSetAttr("cmtId");
		// User loginUser = this.getLoginUser(req);
		// CmpActorCmt cmpActorCmt =
		// this.cmpActorCmtService.getCmpActorCmt(cmtId);
		// cmpActorCmt.setContent(req.getHtml("content"));
		// int code = cmpActorCmt.validate();
		// if (code != Err.SUCCESS) {
		// return this.onError(req, code, "updateerr", null);
		// }
		// CmpActorScore cmpActorScore = new CmpActorScore();
		// cmpActorScore.setActorId(cmpActorCmt.getActorId());
		// cmpActorScore.setCompanyId(cmpActorCmt.getCompanyId());
		// cmpActorScore.setUserId(loginUser.getUserId());
		// cmpActorScore.setScore(req.getInt("score"));
		// this.cmpActorCmtProcessor.updateCmpActorCmt(cmpActorCmt,
		// cmpActorScore);
		// return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-8-23
	 */
	public String prvdel(HkRequest req, HkResponse resp) {
		return null;
		// long cmtId = req.getLongAndSetAttr("cmtId");
		// User loginUser = this.getLoginUser(req);
		// CmpActorCmt cmpActorCmt =
		// this.cmpActorCmtService.getCmpActorCmt(cmtId);
		// if (cmpActorCmt == null
		// || cmpActorCmt.getUserId() != loginUser.getUserId()) {
		// return null;
		// }
		// this.cmpActorCmtProcessor.deleteCmpActorCmt(cmpActorCmt);
		// return null;
	}
}