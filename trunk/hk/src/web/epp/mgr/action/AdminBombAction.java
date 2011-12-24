package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBbsDel;
import com.hk.bean.CmpBbsReply;
import com.hk.bean.CmpBbsReplyDel;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBbsService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpBbsProcessor;

@Component("/epp/web/op/webadmin/bomb")
public class AdminBombAction extends EppBaseAction {

	@Autowired
	private CmpBbsService cmpBbsService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpBbsProcessor cmpBbsProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 被炸掉的话题列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String bbslist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_27", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpBbsDel> list = this.cmpBbsProcessor
				.getCmpBbsDelListByCompanyId(companyId, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/bomb/bbslist.jsp");
	}

	/**
	 * 查看被炸掉的话题
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String bbsview(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_27", 1);
		long bbsId = req.getLongAndSetAttr("bbsId");
		CmpBbsDel cmpBbsDel = this.cmpBbsService.getCmpBbsDel(bbsId);
		if (cmpBbsDel == null) {
			return null;
		}
		User bbsUser = this.userService.getUser(cmpBbsDel.getUserId());
		SimplePage page = req.getSimplePage(20);
		List<CmpBbsReply> list = this.cmpBbsProcessor
				.getCmpBbsReplieListByBbsId(bbsId, true, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("bbsUser", bbsUser);
		req.setAttribute("cmpBbsDel", cmpBbsDel);
		req.setAttribute("list", list);
		User opuser = this.userService.getUser(cmpBbsDel.getOpuserId());
		req.setAttribute("opuser", opuser);
		return this.getWebPath("admin/bomb/bbsview.jsp");
	}

	/**
	 * 被炸掉的话题回复列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String bbsreplylist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_27", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpBbsReplyDel> list = this.cmpBbsProcessor
				.getCmpBbsReplyDelListByCompanyId(companyId, true, true, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/bomb/bbsreplylist.jsp");
	}

	/**
	 * 恢复话题
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String recoverbombbbs(HkRequest req, HkResponse resp) {
		long bbsId = req.getLong("bbsId");
		this.cmpBbsService.recoverBombCmpBbs(bbsId);
		req.setSessionText("epp.cmpbbs.recover.success");
		return null;
	}

	/**
	 * 恢复话题回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String recoverbombbbsreply(HkRequest req, HkResponse resp) {
		long replyId = req.getLong("replyId");
		this.cmpBbsService.recoverBombCmpBbsReply(replyId);
		req.setSessionText("epp.cmpbbsreply.recover.success");
		return null;
	}

	/**
	 * 删除被炸掉的话题
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String delbombbbs(HkRequest req, HkResponse resp) throws Exception {
		long bbsId = req.getLong("bbsId");
		this.cmpBbsService.deleteCmpBbsDel(bbsId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 删除被炸掉的话题回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-9
	 */
	public String delbombbbsreply(HkRequest req, HkResponse resp)
			throws Exception {
		long bbsId = req.getLong("bbsId");
		long replyId = req.getLong("replyId");
		this.cmpBbsService.deleteCmpBbsReplyDel(bbsId, replyId);
		this.setDelSuccessMsg(req);
		return null;
	}
}