package com.hk.web.cmpunion.valuecreater.req;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnionReq;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.web.cmpunion.valuecreater.ValueCreater;

public class JoinInCmpUnionReqValueCreater implements ValueCreater {
	public String getValue(HttpServletRequest request, Object obj) {
		CmpUnionReq req = (CmpUnionReq) obj;
		Map<String, String> map = DataUtil.getMapFromJson(req.getData());
		return ResourceConfig.getText("view.cmpunionreq.reqflg1", map
				.get("name"));
	}
}