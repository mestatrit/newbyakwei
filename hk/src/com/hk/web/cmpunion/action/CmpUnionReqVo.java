package com.hk.web.cmpunion.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.CmpUnionReq;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.web.cmpunion.valuecreater.ValueCreater;
import com.hk.web.cmpunion.valuecreater.req.JoinInCmpUnionReqValueCreater;

public class CmpUnionReqVo {
	private static final Map<Integer, ValueCreater> map = new HashMap<Integer, ValueCreater>();
	static {
		map.put(CmpUnionMessageUtil.REQ_JOIN_IN_CMPUNION,
				new JoinInCmpUnionReqValueCreater());
	}

	public CmpUnionReqVo(HttpServletRequest request, CmpUnionReq cmpUnionReq) {
		this.cmpUnionReq = cmpUnionReq;
		ValueCreater creater = map.get(cmpUnionReq.getReqflg());
		if (creater != null) {
			this.content = creater.getValue(request, cmpUnionReq);
		}
	}

	private CmpUnionReq cmpUnionReq;

	private String content;

	public CmpUnionReq getCmpUnionReq() {
		return cmpUnionReq;
	}

	public void setCmpUnionReq(CmpUnionReq cmpUnionReq) {
		this.cmpUnionReq = cmpUnionReq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<CmpUnionReqVo> createList(HttpServletRequest request,
			List<CmpUnionReq> list) {
		List<CmpUnionReqVo> volist = new ArrayList<CmpUnionReqVo>();
		for (CmpUnionReq cmpUnionReq : list) {
			CmpUnionReqVo vo = new CmpUnionReqVo(request, cmpUnionReq);
			volist.add(vo);
		}
		return volist;
	}
}