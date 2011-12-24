package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.processor.CompanyPhotoProcessor;

@Component("/epp/web/svr")
public class SvrAction extends EppBaseAction {

	@Autowired
	private CompanyPhotoProcessor companyPhotoProcessor;

	@Autowired
	private CompanyPhotoService companyPhotoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String photoajax(HkRequest req, HkResponse resp) throws Exception {
		long setId = req.getLongAndSetAttr("setId");
		long companyId = req.getLong("companyId");
		List<CmpPhotoSetRef> list = this.companyPhotoProcessor
				.getCmpPhotoSetRefListByCompanyIdAndSetId(companyId, setId,
						true, 0, 300);
		if (list.size() == 0) {
			resp.sendHtml(0);
			return null;
		}
		req.setAttribute("list", list);
		return this.getWebPath("mod/3/0/actor/actorsvrajax.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-28
	 */
	public String loadphotoset(HkRequest req, HkResponse resp) throws Exception {
		long setId = req.getLongAndSetAttr("setId");
		long companyId = req.getLong("companyId");
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, setId);
		req.setAttribute("cmpPhotoSet", cmpPhotoSet);
		return this.getWebPath("mod/3/0/actor/reserve_photoset.jsp");
	}
}