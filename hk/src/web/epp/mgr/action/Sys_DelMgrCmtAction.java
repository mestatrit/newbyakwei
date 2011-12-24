package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import web.pub.action.EppBaseAction;
import web.pub.util.LabaParser;
import web.pub.util.WebUtil;

import com.hk.frame.util.ServletUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCommentService;
import com.hk.web.company.action.CmpCommentVo;

@Deprecated
// @Component("/epp/mgr/cmt")
public class Sys_DelMgrCmtAction extends EppBaseAction {

	@Autowired
	private CmpCommentService cmpCommentService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		SimplePage page = ServletUtil.getSimplePage(req, 20);
		List<com.hk.bean.CmpComment> list = cmpCommentService
				.getCmpCommentList(companyId, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		for (com.hk.bean.CmpComment o : list) {
			String c = LabaParser.parseContent(WebUtil.getUrlInfo(req), o
					.getContent());
			o.setContent(c);
		}
		List<CmpCommentVo> volist = CmpCommentVo.createVoList(list, null, true);
		req.setAttribute("volist", volist);
		return this.getMgrJspPath(req, "cmt/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long cmtId = req.getLong("cmtId");
		this.cmpCommentService.deleteCmpComment(companyId, cmtId);
		req.setSessionText("func.mgrsite.cmt.delete_ok");
		return "r:/epp/mgr/cmt_list.do?companyId=" + req.getLong("companyId");
	}
}