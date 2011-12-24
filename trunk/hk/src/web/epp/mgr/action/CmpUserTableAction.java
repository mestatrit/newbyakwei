package web.epp.mgr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.epp.action.UserTableFieldValue;
import web.pub.action.EppBaseAction;

import com.hk.bean.CmpNav;
import com.hk.bean.CmpUserTableData;
import com.hk.bean.CmpUserTableDataOption;
import com.hk.bean.CmpUserTableDataValue;
import com.hk.bean.CmpUserTableField;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpUserTableService;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmpusertable")
public class CmpUserTableAction extends EppBaseAction {

	@Autowired
	private CmpUserTableService cmpUserTableService;

	@Autowired
	private CmpNavService cmpNavService;

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		long navoid = req.getLongAndSetAttr("navoid");
		CmpUserTableData cmpUserTableData = this.cmpUserTableService
				.getCmpUserTableDataByCmpNavOid(navoid);
		if (cmpUserTableData != null) {
			List<CmpUserTableField> list = this.cmpUserTableService
					.getCmpUserTableFieldListByDataId(cmpUserTableData
							.getDataId());
			req.setAttribute("list", list);
		}
		return this.getWebPath("admin/cmpusertable/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String createusertabledata(HkRequest req, HkResponse resp) {
		long navoid = req.getLongAndSetAttr("navoid");
		if (this.isForwardPage(req)) {
			CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
			req.setAttribute("cmpNav", cmpNav);
			return this
					.getWebPath("admin/cmpusertable/createusertabledata.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpUserTableData cmpUserTableData = this.cmpUserTableService
				.getCmpUserTableDataByCmpNavOid(navoid);
		if (cmpUserTableData == null) {
			cmpUserTableData = new CmpUserTableData();
		}
		cmpUserTableData.setCmpNavOid(navoid);
		cmpUserTableData.setCompanyId(companyId);
		int code = cmpUserTableData.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerr", null);
		}
		this.cmpUserTableService.saveCmpUserTableData(cmpUserTableData);
		CmpUserTableField cmpUserTableField = new CmpUserTableField();
		cmpUserTableField.setCompanyId(companyId);
		cmpUserTableField.setDataId(cmpUserTableData.getDataId());
		cmpUserTableField.setField_type(req.getByte("field_type"));
		cmpUserTableField.setName(req.getHtmlRow("name"));
		code = cmpUserTableField.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerr", null);
		}
		this.cmpUserTableService.createCmpUserTableField(cmpUserTableField);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String deleteusertablefield(HkRequest req, HkResponse resp) {
		long fieldId = req.getLong("fieldId");
		CmpUserTableField cmpUserTableField = this.cmpUserTableService
				.getCmpUserTableField(fieldId);
		if (cmpUserTableField != null) {
			this.cmpUserTableService.deleteCmpUserTableField(cmpUserTableField);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String updateusertablefield(HkRequest req, HkResponse resp) {
		long fieldId = req.getLongAndSetAttr("fieldId");
		req.reSetAttribute("navoid");
		CmpUserTableField cmpUserTableField = this.cmpUserTableService
				.getCmpUserTableField(fieldId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpUserTableField", cmpUserTableField);
			List<CmpUserTableDataOption> optionlist = this.cmpUserTableService
					.getCmpUserTableDataOptionListByFieldId(fieldId);
			req.setAttribute("optionlist", optionlist);
			return this
					.getWebPath("admin/cmpusertable/updateusertabledata.jsp");
		}
		cmpUserTableField.setField_type(req.getByte("field_type"));
		cmpUserTableField.setName(req.getHtmlRow("name"));
		int code = cmpUserTableField.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerr", null);
		}
		this.cmpUserTableService.updateCmpUserTableField(cmpUserTableField);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String createusertabledataoption(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		req.reSetAttribute("navoid");
		long fieldId = req.getLongAndSetAttr("fieldId");
		CmpUserTableField cmpUserTableField = this.cmpUserTableService
				.getCmpUserTableField(fieldId);
		if (cmpUserTableField == null) {
			return null;
		}
		CmpUserTableDataOption cmpUserTableDataOption = new CmpUserTableDataOption();
		if (this.isForwardPage(req)) {
			return this
					.getWebPath("admin/cmpusertable/updateusertabledataoption.jsp");
		}
		cmpUserTableDataOption.setDataId(cmpUserTableField.getDataId());
		cmpUserTableDataOption.setCompanyId(companyId);
		cmpUserTableDataOption.setFieldId(fieldId);
		cmpUserTableDataOption.setData(req.getHtmlRow("data"));
		int code = cmpUserTableDataOption.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createoptionerr", null);
		}
		this.cmpUserTableService
				.createCmpUserTableDataOption(cmpUserTableDataOption);
		return this.onSuccess2(req, "createoptionok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String deleteusertabledataoption(HkRequest req, HkResponse resp) {
		long optionId = req.getLongAndSetAttr("optionId");
		CmpUserTableDataOption cmpUserTableDataOption = this.cmpUserTableService
				.getCmpUserTableDataOption(optionId);
		if (cmpUserTableDataOption != null) {
			this.cmpUserTableService
					.deleteCmpUserTableDataOption(cmpUserTableDataOption);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-21
	 */
	public String updateusertabledataoption(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		long optionId = req.getLongAndSetAttr("optionId");
		CmpUserTableDataOption cmpUserTableDataOption = this.cmpUserTableService
				.getCmpUserTableDataOption(optionId);
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpUserTableDataOption", cmpUserTableDataOption);
			return this
					.getWebPath("admin/cmpusertable/usertabledataoption_form_inc.jsp");
		}
		if (cmpUserTableDataOption == null) {
			return null;
		}
		cmpUserTableDataOption.setData(req.getHtmlRow("data"));
		int code = cmpUserTableDataOption.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateoptionerr", null);
		}
		this.cmpUserTableService
				.updateCmpUserTableDataOption(cmpUserTableDataOption);
		return this.onSuccess2(req, "updateoptionok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-11-22
	 */
	public String valuelist(HkRequest req, HkResponse resp) {
		long navoid = req.getLongAndSetAttr("navoid");
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navoid);
		req.setAttribute("cmpNav", cmpNav);
		long companyId = req.getLong("companyId");
		CmpUserTableData cmpUserTableData = this.cmpUserTableService
				.getCmpUserTableDataByCmpNavOid(navoid);
		SimplePage page = req.getSimplePage(20);
		List<CmpUserTableDataValue> list = this.cmpUserTableService
				.getCmpUserTableDataValueListByDataId(companyId,
						cmpUserTableData.getDataId(), page.getBegin(), page
								.getSize() + 1);
		this.processListForPage(page, list);
		List<CmpUserTableField> fieldlist = this.cmpUserTableService
				.getCmpUserTableFieldListByDataId(cmpUserTableData.getDataId());
		Map<Long, CmpUserTableDataOption> optionmap = new HashMap<Long, CmpUserTableDataOption>();
		Map<Long, CmpUserTableField> fieldmap = new HashMap<Long, CmpUserTableField>();
		for (CmpUserTableField f : fieldlist) {
			fieldmap.put(f.getFieldId(), f);
			List<CmpUserTableDataOption> optionlist = this.cmpUserTableService
					.getCmpUserTableDataOptionListByFieldId(f.getFieldId());
			for (CmpUserTableDataOption option : optionlist) {
				optionmap.put(option.getOptionId(), option);
			}
		}
		List<CmpUserTableDataValueVo> volist = new ArrayList<CmpUserTableDataValueVo>();
		for (CmpUserTableDataValue o : list) {
			volist.add(this.build(o, fieldlist, optionmap));
		}
		req.setAttribute("volist", volist);
		return this.getWebPath("/admin/cmpusertable/valuelist.jsp");
	}

	private CmpUserTableDataValueVo build(
			CmpUserTableDataValue cmpUserTableDataValue,
			List<CmpUserTableField> fieldlist,
			Map<Long, CmpUserTableDataOption> optionmap) {
		Map<String, String> map = JsonUtil.getMapFromJson(cmpUserTableDataValue
				.getData());
		List<UserTableFieldValue> list = new ArrayList<UserTableFieldValue>();
		for (CmpUserTableField f : fieldlist) {
			UserTableFieldValue userTableFieldValue = new UserTableFieldValue();
			userTableFieldValue.setCmpUserTableField(f);
			list.add(userTableFieldValue);
			if (f.isFieldText() || f.isFieldTextArea()) {// 用户所输入的文本
				userTableFieldValue.setValue(map.get("" + f.getFieldId()));
			}
			else {// 其他选择型
				String v = map.get("" + f.getFieldId());
				if (v != null) {
					String[] vs = v.split(",");
					if (vs != null) {
						List<CmpUserTableDataOption> oplist = new ArrayList<CmpUserTableDataOption>();
						for (String s : vs) {
							CmpUserTableDataOption o = optionmap.get(Long
									.valueOf(s));
							if (o != null) {
								oplist.add(o);
							}
						}
						userTableFieldValue.setSelectedOptionList(oplist);
					}
				}
			}
		}
		return new CmpUserTableDataValueVo(list);
	}
}