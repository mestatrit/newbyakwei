package iwant.web;

import iwant.bean.AdminUser;
import iwant.web.admin.util.AdminUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev3g.cactus.util.JsonUtil;
import com.dev3g.cactus.util.ResourceConfig;
import com.dev3g.cactus.web.action.Action;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;
import com.dev3g.cactus.web.util.SimplePage;

public class BaseAction implements Action {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	protected boolean isForwardPage(HkRequest req) {
		if (req.getInt("ch") == 0) {
			return true;
		}
		return false;
	}

	protected String getAdminPath(String path) {
		return "/WEB-INF/page/admin/" + path;
	}

	protected String onError(HkRequest req, String code, String functionName,
			Object respValue) {
		req.setAttribute("error", code);
		req.setAttribute("error_msg", ResourceConfig.getText(code));
		req.setAttribute("functionName", functionName);
		if (respValue != null) {
			req.setAttribute("respValue", respValue);
		}
		return "/WEB-INF/page/inc/onerror.jsp";
	}

	protected String onErrorList(HkRequest req, Collection<String> list,
			String functionName) {
		Map<String, String> map = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		for (String i : list) {
			map.put(String.valueOf("error_" + i), ResourceConfig.getText(i));
			sb.append(i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String json = JsonUtil.toJson(map);
		req.setAttribute("json", json);
		req.setAttribute("errorlist", sb.toString());
		req.setAttribute("functionName", functionName);
		return "/WEB-INF/page/inc/onerrorlist.jsp";
	}

	protected String onSuccess(HkRequest req, String functionName,
			Object respValue) {
		return this.onError(req, "0", functionName, respValue);
	}

	protected void opCreateSuccess(HkRequest req) {
		req.setSessionMessage(ResourceConfig.getText("op.create.success"));
	}

	protected void opUpdateSuccess(HkRequest req) {
		req.setSessionMessage(ResourceConfig.getText("op.update.success"));
	}

	protected void opDeleteSuccess(HkRequest req) {
		req.setSessionMessage(ResourceConfig.getText("op.delete.success"));
	}

	protected void processListForPage(SimplePage page, List<?> list) {
		if (list.size() == page.getSize() + 1) {
			page.setHasNext(true);
			list.remove(page.getSize());
		}
	}

	protected AdminUser getAdminUser(HkRequest req) {
		return AdminUtil.getLoginAdminUser(req);
	}

	protected int getLoginCityid(HkRequest req) {
		AdminUser adminUser = this.getAdminUser(req);
		if (adminUser == null) {
			return 0;
		}
		return adminUser.getCityid();
	}
}