package com.hk.api.action.cmp;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.bean.CmpComment;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpCommentService;
import com.hk.web.company.action.CmpCommentVo;

// @Component("/pubapi/cmt")
public class CmpCmtActin extends BaseApiAction {

	@Autowired
	private CmpCommentService cmpCommentService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String list(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<CmpComment> list = this.cmpCommentService.getCmpCommentList(
				companyId, page.getBegin(), size);
		List<CmpCommentVo> volist = CmpCommentVo.createVoList(list, null, true);
		VelocityContext context = new VelocityContext();
		context.put("cmtlist", volist);
		this.write(resp, "vm/e/cmtlist.vm", context);
		return null;
	}
}