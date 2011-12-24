package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorRole;
import com.hk.bean.CmpActorSvrRef;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpActorService;
import com.hk.svr.processor.CmpSvrProcessor;

@Component("/epp/web/actor")
public class ActorAction extends EppBaseAction {

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpSvrProcessor cmpSvrProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		long roleId = req.getLongAndSetAttr("roleId");
		SimplePage page = req.getSimplePage(20);
		List<CmpActor> list = this.cmpActorService.getCmpActorListByCompanyId(
				companyId, roleId, null, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("mod/3/0/actor/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		this.setCmpNavInfo(req);
		long actorId = req.getLongAndSetAttr("actorId");
		CmpActor cmpActor = this.cmpActorService.getCmpActor(actorId);
		if (cmpActor == null) {
			return null;
		}
		if (cmpActor.getRoleId() > 0) {
			CmpActorRole cmpActorRole = this.cmpActorService
					.getCmpActorRole(cmpActor.getRoleId());
			req.setAttribute("cmpActorRole", cmpActorRole);
		}
		List<CmpActorSvrRef> svrreflist = this.cmpSvrProcessor
				.getCmpActorSvrRefListByCompanyIdAndActorId(companyId, actorId,
						true);
		req.setAttribute("cmpActor", cmpActor);
		req.setAttribute("svrreflist", svrreflist);
		return this.getWebPath("mod/3/0/actor/view.jsp");
	}
}