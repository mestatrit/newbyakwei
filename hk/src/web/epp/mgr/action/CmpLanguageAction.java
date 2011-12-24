package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpLanguageRef;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpInfoService;
import com.hk.svr.processor.CmpInfoProcessor;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmplanguage")
public class CmpLanguageAction extends EppBaseAction {

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpInfoProcessor cmpInfoProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_26", 1);
		long companyId = req.getLong("companyId");
		List<CmpLanguageRef> list = this.cmpInfoProcessor
				.getCmpLanguageRefListByCompanyId(companyId, true);
		req.setAttribute("list", list);
		return this.getWebPath("admin/language/list.jsp");
	}

	/**
	 * 添加不同语言版本的网站
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-6
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		String domain = req.getString("domain");
		if (DataUtil.isEmpty(domain)) {
			return this
					.onError(req, Err.CMPINFO_NOT_EXIST, "createerror", null);
		}
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfoByDomain(domain);
		if (cmpInfo == null) {
			return this
					.onError(req, Err.CMPINFO_NOT_EXIST, "createerror", null);
		}
		this.cmpInfoService.createCmpLanguageRef(companyId, cmpInfo
				.getCompanyId());
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 删除某个语言版本的网站
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-6
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpInfoService.deleteCmpLanguageRef(oid);
		this.setDelSuccessMsg(req);
		return null;
	}
}