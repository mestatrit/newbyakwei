package com.hk.web.pub.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/back")
public class BackAction extends BaseAction {
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String from = req.getString("from");
		int page = req.getInt("repage");
		if (from == null || from.equals("index")) {
			return "r:/square.do" + "?page=" + page;
		}
		else if (from.equals("reuserlist")) {
			return "r:/laba/reuserlist.do" + "?page=" + page;
		}
		else if (from.equals("userlabalist")) {
			return "r:/laba/userlabalist.do" + "?userId="
					+ req.getLong("ouserId") + "&page=" + page;
		}
		else if (from.equals("square")) {
			return "r:/square.do" + "?w=" + req.getString("w") + "&page="
					+ page;
		}
		return "r:/square.do" + "?page=" + page;
	}
}