package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import com.hk.bean.HkbLog;
import com.hk.frame.util.ResourceConfig;

public class HkbLogVo {
	private String content;

	private HkbLog hkbLog;

	public void setHkbLog(HkbLog hkbLog) {
		this.hkbLog = hkbLog;
	}

	public HkbLog getHkbLog() {
		return hkbLog;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<HkbLogVo> createVoList(List<HkbLog> list) {
		List<HkbLogVo> volist = new ArrayList<HkbLogVo>();
		for (HkbLog o : list) {
			HkbLogVo vo = new HkbLogVo();
			vo.setHkbLog(o);
			vo
					.setContent(ResourceConfig.getText("hklog.type"
							+ o.getHkbtype()));
			volist.add(vo);
		}
		return volist;
	}
}