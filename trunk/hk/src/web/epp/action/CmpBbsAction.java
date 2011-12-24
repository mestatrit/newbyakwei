package web.epp.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsContent;
import com.hk.bean.CmpBbsKind;
import com.hk.bean.CmpBbsReply;
import com.hk.bean.CmpBomber;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBbsService;
import com.hk.svr.CmpBomberService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpBbsProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/cmpbbs")
public class CmpBbsAction extends EppBaseAction {

	@Autowired
	private CmpBbsService cmpBbsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpBomberService cmpBomberService;

	@Autowired
	private CmpBbsProcessor cmpBbsProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		long companyId = req.getLong("companyId");
		List<CmpBbsKind> list = this.cmpBbsService
				.getCmpBbsKindListByCompanyId(companyId);
		List<CmpBbsKindVo> volist = new ArrayList<CmpBbsKindVo>();
		for (CmpBbsKind kind : list) {
			List<CmpBbs> bbslist = this.cmpBbsProcessor.getCmpBbsListByKindId(
					kind.getKindId(), true, 0, 7);
			CmpBbsKindVo vo = new CmpBbsKindVo(kind, bbslist);
			if (bbslist.size() == 7) {
				bbslist.remove(6);
				vo.setHasMore(true);
			}
			volist.add(vo);
		}
		req.setAttribute("volist", volist);
		req.setReturnUrl("/epp/web/cmpbbs.do?companyId=" + companyId);
		return this.getWebPath("cmpbbs/kindlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		List<CmpBbsKind> list = this.cmpBbsService
				.getCmpBbsKindListByCompanyId(companyId);
		req.setAttribute("list", list);
		return this.getWapPath("cmpbbs/kindlist.jsp");
	}

	/**
	 * 分类查看
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String bbslist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		long kindId = req.getLongAndSetAttr("kindId");
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(kindId);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		SimplePage page = req.getSimplePage(20);
		List<CmpBbs> list = this.cmpBbsProcessor.getCmpBbsListByKindId(kindId,
				true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("cmpbbs/bbslist.jsp");
	}

	/**
	 * 单贴
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		long companyId = req.getLong("companyId");
		long bbsId = req.getLongAndSetAttr("bbsId");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		if (cmpBbs == null) {
			return null;
		}
		User bbsUser = this.userService.getUser(cmpBbs.getUserId());
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(cmpBbs
				.getKindId());
		CmpBbsContent cmpBbsContent = this.cmpBbsService
				.getCmpBbsContent(bbsId);
		SimplePage page = req.getSimplePage(20);
		List<CmpBbsReply> list = this.cmpBbsProcessor
				.getCmpBbsReplieListByBbsId(bbsId, true, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("bbsUser", bbsUser);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		req.setAttribute("cmpBbs", cmpBbs);
		req.setAttribute("cmpBbsContent", cmpBbsContent);
		req.setAttribute("list", list);
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			CmpBomber cmpBomber = this.cmpBomberService
					.getCmpBomberByCompanyIdAndUserId(companyId, loginUser
							.getUserId());
			req.setAttribute("cmpBomber", cmpBomber);
			req.setAttribute("userAdmin", this.isCmpAdminUser(req));
		}
		req.reSetAttribute("reply");
		req.setAttribute("lastreply_idx", list.size() - 1);
		req.reSetAttribute("replyId");
		return this.getWebPath("cmpbbs/view.jsp");
	}

	/**
	 * 单贴
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-11
	 */
	public String wapview(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		long bbsId = req.getLongAndSetAttr("bbsId");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		if (cmpBbs == null) {
			return null;
		}
		User bbsUser = this.userService.getUser(cmpBbs.getUserId());
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(cmpBbs
				.getKindId());
		CmpBbsContent cmpBbsContent = this.cmpBbsService
				.getCmpBbsContent(bbsId);
		SimplePage page = req.getSimplePage(10);
		List<CmpBbsReply> list = this.cmpBbsProcessor
				.getCmpBbsReplieListByBbsId(bbsId, true, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("bbsUser", bbsUser);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		req.setAttribute("cmpBbs", cmpBbs);
		req.setAttribute("cmpBbsContent", cmpBbsContent);
		req.setAttribute("list", list);
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			CmpBomber cmpBomber = this.cmpBomberService
					.getCmpBomberByCompanyIdAndUserId(companyId, loginUser
							.getUserId());
			req.setAttribute("cmpBomber", cmpBomber);
			req.setAttribute("userAdmin", this.isCmpAdminUser(req));
		}
		return this.getWapPath("cmpbbs/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String kind(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		long kindId = req.getLongAndSetAttr("kindId");
		SimplePage page = req.getSimplePage(20);
		List<CmpBbs> list = this.cmpBbsProcessor.getCmpBbsListByKindId(kindId,
				true, page.getBegin(), page.getSize() + 1);
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(kindId);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		req.setAttribute("list", list);
		return this.getWebPath("cmpbbs/kind.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wapkind(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long kindId = req.getLongAndSetAttr("kindId");
		SimplePage page = req.getSimplePage(20);
		List<CmpBbs> list = this.cmpBbsProcessor.getCmpBbsListByKindId(kindId,
				true, page.getBegin(), page.getSize() + 1);
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(kindId);
		req.setAttribute("cmpBbsKind", cmpBbsKind);
		req.setAttribute("list", list);
		return this.getWapPath("cmpbbs/kind.jsp");
	}

	/**
	 * 回复帖子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String lastpage(HkRequest req, HkResponse resp) {
		long bbsId = req.getLong("bbsId");
		long companyId = req.getLong("companyId");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		if (cmpBbs == null) {
			return null;
		}
		int page = (cmpBbs.getReplyCount() + 19) / 20;
		return "r:/epp/web/cmpbbs_view.do?companyId=" + companyId + "&bbsId="
				+ bbsId + "&page=" + page + "&reply=1";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) {
		req.setAttribute("active_bbs_cmpnav_column", true);
		User loginUser = this.getLoginUser2(req);
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("cmpbbs/create.jsp");
		}
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(kindId);
		if (cmpBbsKind == null) {
			return null;
		}
		CmpBbs cmpBbs = new CmpBbs();
		cmpBbs.setCompanyId(companyId);
		cmpBbs.setUserId(loginUser.getUserId());
		cmpBbs.setKindId(kindId);
		cmpBbs.setIp(req.getRemoteAddr());
		cmpBbs.setCreateTime(new Date());
		cmpBbs.setTitle(req.getHtmlRow("title"));
		CmpBbsContent cmpBbsContent = new CmpBbsContent();
		cmpBbsContent.setContent(req.getHtml("content"));
		int code = cmpBbs.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		code = cmpBbsContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		File file = req.getFile("f");
		if (cmpBbsKind.getMustpic() == CmpBbsKind.MUSTPIC_Y) {
			if (file == null) {
				return this.onError(req, Err.MUST_PIC_UPLOAD, "createerror",
						null);
			}
			try {
				if (!DataUtil.isImage(file)) {
					return this.onError(req, Err.ONLY_PIC_UPLOAD,
							"createerror", null);
				}
			}
			catch (IOException e) {// 上传出错
				return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
			}
		}
		try {
			this.cmpBbsProcessor.createCmpBbs(cmpBbs, cmpBbsContent, file);
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "createok", cmpBbs.getBbsId());
		}
		catch (IOException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
		}
		catch (ImageException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
		}
		catch (NotPermitImageFormatException e) {// 图片格式错误
			return this.onError(req, Err.IMG_FMT_ERROR, "createerror", null);
		}
		catch (OutOfSizeException e) {// 图片文件大小不符
			return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
					new Object[] { "2M" }, "createerror", null);
		}
	}

	/**
	 * 修改帖子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		User loginUser = this.getLoginUser2(req);
		long bbsId = req.getLongAndSetAttr("bbsId");
		int ch = req.getInt("ch");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		CmpBbsContent cmpBbsContent = this.cmpBbsService
				.getCmpBbsContent(bbsId);
		req.setAttribute("cmpBbs", cmpBbs);
		req.setAttribute("cmpBbsContent", cmpBbsContent);
		if (loginUser.getUserId() != cmpBbs.getUserId()) {
			return null;
		}
		if (ch == 0) {
			return this.getWebPath("cmpbbs/update.jsp");
		}
		CmpBbsKind cmpBbsKind = this.cmpBbsService.getCmpBbsKind(cmpBbs
				.getKindId());
		if (cmpBbsKind == null) {
			return null;
		}
		cmpBbs.setIp(req.getRemoteAddr());
		cmpBbs.setTitle(req.getHtmlRow("title"));
		cmpBbsContent.setContent(req.getHtml("content"));
		int code = cmpBbs.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		code = cmpBbsContent.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		File file = req.getFile("f");
		if (cmpBbsKind.getMustpic() == CmpBbsKind.MUSTPIC_Y) {
			if (file == null) {
				return this.onError(req, Err.MUST_PIC_UPLOAD, "updateerror",
						null);
			}
			try {
				if (!DataUtil.isImage(file)) {
					return this.onError(req, Err.ONLY_PIC_UPLOAD,
							"updateerror", null);
				}
			}
			catch (IOException e) {// 上传出错
				return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
			}
		}
		try {
			this.cmpBbsProcessor.updateCmpBbs(cmpBbs, cmpBbsContent, file);
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", null);
		}
		catch (IOException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
		}
		catch (ImageException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
		}
		catch (NotPermitImageFormatException e) {// 图片格式错误
			return this.onError(req, Err.IMG_FMT_ERROR, "updateerror", null);
		}
		catch (OutOfSizeException e) {// 图片文件大小不符
			return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
					new Object[] { "2M" }, "updateerror", null);
		}
	}

	/**
	 * 删除帖子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long bbsId = req.getLong("bbsId");
		long companyId = req.getLong("companyId");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		User loginUser = this.getLoginUser2(req);
		if (cmpBbs == null) {
			return null;
		}
		// 管理员或者发帖人可删除
		if (loginUser.getUserId() == cmpBbs.getUserId()
				|| this.isCmpAdminUser(req)) {
			this.cmpBbsService.deleteCmpBbs(bbsId);
			this.setDelSuccessMsg(req);
		}
		return "r:/epp/web/cmpbbs_kind.do?companyId=" + companyId + "&kindId="
				+ cmpBbs.getKindId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String clearpic(HkRequest req, HkResponse resp) throws Exception {
		long bbsId = req.getLong("bbsId");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		User loginUser = this.getLoginUser2(req);
		if (cmpBbs == null) {
			return null;
		}
		// 管理员或者发帖人可删除
		if (loginUser.getUserId() == cmpBbs.getUserId()) {
			this.cmpBbsService.clearCmpBbsPic(bbsId);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String clearreplypic(HkRequest req, HkResponse resp) {
		long replyId = req.getLong("replyId");
		CmpBbsReply cmpBbsReply = this.cmpBbsService.getCmpBbsReply(replyId);
		User loginUser = this.getLoginUser2(req);
		if (cmpBbsReply == null) {
			return null;
		}
		// 管理员或者回复人可删除
		if (loginUser.getUserId() == cmpBbsReply.getUserId()) {
			this.cmpBbsService.clearCmpBbsReplyPic(replyId);
		}
		return null;
	}

	/**
	 * 回复帖子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String createreply(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		long bbsId = req.getLong("bbsId");
		long replyUserId = req.getLong("replyUserId");
		CmpBbsReply cmpBbsReply = new CmpBbsReply();
		cmpBbsReply.setCompanyId(companyId);
		cmpBbsReply.setBbsId(bbsId);
		cmpBbsReply.setUserId(loginUser.getUserId());
		cmpBbsReply.setIp(req.getRemoteAddr());
		cmpBbsReply.setCreateTime(new Date());
		cmpBbsReply.setReplyUserId(replyUserId);
		cmpBbsReply.setContent(req.getHtml("content"));
		int code = cmpBbsReply.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		File file = req.getFile("f");
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		if (cmpBbs == null) {
			return null;
		}
		try {
			this.cmpBbsProcessor.createCmpBbsReply(cmpBbsReply, cmpBbs, file);
			return this.onSuccess2(req, "createok", cmpBbsReply.getReplyId());
		}
		catch (IOException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
		}
		catch (ImageException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "createerror", null);
		}
		catch (NotPermitImageFormatException e) {// 图片格式错误
			return this.onError(req, Err.IMG_FMT_ERROR, "createerror", null);
		}
		catch (OutOfSizeException e) {// 图片文件大小不符
			return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
					new Object[] { "2M" }, "createerror", null);
		}
	}

	/**
	 * 修改回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String updatereply(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_bbs_cmpnav_column", true);
		User loginUser = this.getLoginUser2(req);
		long replyId = req.getLongAndSetAttr("replyId");
		CmpBbsReply cmpBbsReply = this.cmpBbsService.getCmpBbsReply(replyId);
		if (cmpBbsReply == null
				|| cmpBbsReply.getUserId() != loginUser.getUserId()) {
			return null;
		}
		req.setAttribute("cmpBbsReply", cmpBbsReply);
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("page");
			return this.getWebPath("cmpbbs/updatereply.jsp");
		}
		cmpBbsReply.setIp(req.getRemoteAddr());
		cmpBbsReply.setContent(req.getHtml("content"));
		int code = cmpBbsReply.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		File file = req.getFile("f");
		try {
			this.cmpBbsProcessor.updateCmpBbsReply(cmpBbsReply, file);
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", cmpBbsReply.getReplyId());
		}
		catch (IOException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
		}
		catch (ImageException e) {// 上传出错
			return this.onError(req, Err.UPLOAD_ERROR, "updateerror", null);
		}
		catch (NotPermitImageFormatException e) {// 图片格式错误
			return this.onError(req, Err.IMG_FMT_ERROR, "updateerror", null);
		}
		catch (OutOfSizeException e) {// 图片文件大小不符
			return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
					new Object[] { "2M" }, "updateerror", null);
		}
	}

	/**
	 * 删除回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String delreply(HkRequest req, HkResponse resp) throws Exception {
		long replyId = req.getLong("replyId");
		CmpBbsReply cmpBbsReply = this.cmpBbsService.getCmpBbsReply(replyId);
		User loginUser = this.getLoginUser2(req);
		if (cmpBbsReply == null) {
			return null;
		}
		// 管理员或者回复人可删除
		if (loginUser.getUserId() == cmpBbsReply.getUserId()
				|| this.isCmpAdminUser(req)) {
			this.cmpBbsService.deleteCmpBbsReply(cmpBbsReply.getBbsId(),
					replyId);
			this.setDelSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 炸掉帖子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvbombbbs(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long bbsId = req.getLong("bbsId");
		User loginUser = this.getLoginUser2(req);
		CmpBomber cmpBomber = this.cmpBomberService
				.getCmpBomberByCompanyIdAndUserId(companyId, loginUser
						.getUserId());
		CmpBbs cmpBbs = this.cmpBbsService.getCmpBbs(bbsId);
		if (cmpBbs == null) {
			return null;
		}
		if (cmpBomber.getBombcount() > 0) {
			if (cmpBbs.getCompanyId() == companyId) {
				this.cmpBbsService.bombCmpBbs(bbsId, loginUser.getUserId());
				this.cmpBomberService.useBomb(companyId, loginUser.getUserId(),
						1);
				req.setSessionText("view2.cmpbbs.bomb.success");
			}
		}
		return "r:/epp/web/cmpbbs_kind.do?companyId=" + companyId + "&kindId="
				+ cmpBbs.getKindId();
	}

	/**
	 * 炸掉回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String prvbombreply(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long replyId = req.getLong("replyId");
		User loginUser = this.getLoginUser2(req);
		CmpBomber cmpBomber = this.cmpBomberService
				.getCmpBomberByCompanyIdAndUserId(companyId, loginUser
						.getUserId());
		if (cmpBomber.getBombcount() > 0) {
			CmpBbsReply cmpBbsReply = this.cmpBbsService
					.getCmpBbsReply(replyId);
			if (cmpBbsReply != null && cmpBbsReply.getCompanyId() == companyId) {
				this.cmpBbsService.bombCmpBbsReply(replyId, loginUser
						.getUserId());
				this.cmpBomberService.useBomb(companyId, loginUser.getUserId(),
						1);
				req.setSessionText("view2.cmpbbsreply.bomb.success");
			}
		}
		return null;
	}
}