package web.epp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpUserTableData;
import com.hk.bean.CmpUserTableDataValue;
import com.hk.bean.CmpUserTableField;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpUserTableService;
import com.hk.svr.pub.Err;

@Component("/epp/web/cmpusertable")
public class CmpUserTableAction extends EppBaseAction {

	@Autowired
	private CmpUserTableService cmpUserTableService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.execute0(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String execute0(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 1) {
			return this.execute01(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String execute01(HkRequest req, HkResponse resp) {
		long navId = req.getLongAndSetAttr("navId");
		this.setCmpNavInfo(req);
		CmpUserTableData cmpUserTableData = this.cmpUserTableService
				.getCmpUserTableDataByCmpNavOid(navId);
		if (cmpUserTableData == null) {
			return null;
		}
		req.setAttribute("cmpUserTableData", cmpUserTableData);
		List<CmpUserTableField> fieldlist = this.cmpUserTableService
				.getCmpUserTableFieldListByDataId(cmpUserTableData.getDataId());
		for (CmpUserTableField f : fieldlist) {
			if (f.isHasOption()) {
				f
						.setOptionList(this.cmpUserTableService
								.getCmpUserTableDataOptionListByFieldId(f
										.getFieldId()));
			}
		}
		req.setAttribute("fieldlist", fieldlist);
		return this.getWebPath("mod/0/1/cmpusertable/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String createvalue(HkRequest req, HkResponse resp) {
		long dataId = req.getLongAndSetAttr("dataId");
		long companyId = req.getLong("companyId");
		CmpUserTableData cmpUserTableData = this.cmpUserTableService
				.getCmpUserTableData(dataId);
		if (cmpUserTableData == null) {
			return null;
		}
		List<CmpUserTableField> fieldlist = this.cmpUserTableService
				.getCmpUserTableFieldListByDataId(dataId);
		Map<String, String> map = new HashMap<String, String>();
		for (CmpUserTableField f : fieldlist) {
			UserTableFieldValue v = new UserTableFieldValue();
			v.setCmpUserTableField(f);
			if (f.isFieldText()) {
				v.setValue(req.getHtmlRow("field_" + f.getFieldId()));
			}
			if (f.isFieldTextArea()) {
				v.setValue(req.getHtml("field_" + f.getFieldId()));
			}
			if (f.isFieldRadio() || f.isFieldSelect()) {
				v.setValue(req.getLong("field_" + f.getFieldId()) + "");
			}
			if (f.isFieldCheckbox()) {
				long[] vs = req.getLongs("field_" + f.getFieldId());
				if (vs != null && vs.length > 0) {
					StringBuilder sb = new StringBuilder();
					for (long l : vs) {
						sb.append(l).append(",");
					}
					sb.deleteCharAt(sb.length() - 1);
					v.setValue(sb.toString());
				}
			}
			int code = v.validate();
			if (code != Err.SUCCESS) {
				return this.onError(req, code, "createerr", v
						.getCmpUserTableField().getFieldId());
			}
			if (v.getValue() != null) {
				map.put(f.getFieldId() + "", v.getValue());
			}
		}
		CmpUserTableDataValue cmpUserTableDataValue = new CmpUserTableDataValue();
		cmpUserTableDataValue.setCompanyId(companyId);
		cmpUserTableDataValue.setDataId(dataId);
		cmpUserTableDataValue.setCreate_time(new Date());
		cmpUserTableDataValue.setData(JsonUtil.toJson(map));
		this.cmpUserTableService
				.createCmpUserTableDataValue(cmpUserTableDataValue);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String createok(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		return this.getWebPath("mod/0/1/cmpusertable/createok.jsp");
	}
}