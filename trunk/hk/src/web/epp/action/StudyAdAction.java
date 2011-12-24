package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpStudyKind;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpOrgStudyAdService;
import com.hk.svr.CmpStudyKindService;

@Component("/epp/web/studyad")
public class StudyAdAction extends EppBaseAction {

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	@Autowired
	private CmpOrgStudyAdService cmpOrgStudyAdService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long kindId = req.getLongAndSetAttr("kindId");
		CmpStudyKind cmpStudyKind = this.cmpStudyKindService.getCmpStudyKind(
				companyId, kindId);
		if (cmpStudyKind == null) {
			return null;
		}
		PageSupport page = req.getPageSupport(20);
		List<CmpOrgStudyAd> list = null;
		if (cmpStudyKind.getKlevel() == 1) {
			page.setTotalCount(this.cmpOrgStudyAdService
					.countCmpOrgStudyAdByCompanyIdAndKindId(companyId, kindId));
			list = this.cmpOrgStudyAdService
					.getCmpOrgStudyAdListByCompanyIdAndKindId(companyId,
							kindId, page.getBegin(), page.getSize());
		}
		else if (cmpStudyKind.getKlevel() == 2) {
			page
					.setTotalCount(this.cmpOrgStudyAdService
							.countCmpOrgStudyAdByCompanyIdAndKindId2(companyId,
									kindId));
			list = this.cmpOrgStudyAdService
					.getCmpOrgStudyAdListByCompanyIdAndKindId2(companyId,
							kindId, page.getBegin(), page.getSize());
		}
		else {
			page
					.setTotalCount(this.cmpOrgStudyAdService
							.countCmpOrgStudyAdByCompanyIdAndKindId3(companyId,
									kindId));
			list = this.cmpOrgStudyAdService
					.getCmpOrgStudyAdListByCompanyIdAndKindId3(companyId,
							kindId, page.getBegin(), page.getSize());
		}
		req.setAttribute("cmpStudyKind", cmpStudyKind);
		req.setAttribute("list", list);
		return this.getWebPath("mod/2/0/studyad/list.jsp");
	}
}