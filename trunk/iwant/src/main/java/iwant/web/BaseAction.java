package iwant.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.action.Action;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

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
		req.setAttribute("error_msg", req.getText(String.valueOf(code)));
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
			map.put(String.valueOf("error_" + i), req
					.getText(String.valueOf(i)));
			sb.append(i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String json = DataUtil.toJson(map);
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
		req.setSessionText("op.create.success");
	}

	protected void opUpdateSuccess(HkRequest req) {
		req.setSessionText("op.update.success");
	}

	protected void opDeleteSuccess(HkRequest req) {
		req.setSessionText("op.delete.success");
	}

	protected void processListForPage(SimplePage page, List<?> list) {
		if (list.size() == page.getSize() + 1) {
			page.setHasNext(true);
			list.remove(page.getSize());
		}
	}
}