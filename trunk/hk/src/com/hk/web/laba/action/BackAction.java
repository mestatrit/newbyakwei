package com.hk.web.laba.action;

import org.springframework.stereotype.Component;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.web.pub.action.BaseAction;

@Component("/laba/back")
public class BackAction extends BaseAction {

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String from = req.getString("from");
		int page = req.getInt("repage");
		int ref = req.getInt("ref");
		String laba_anchor = "&reflabaId=" + req.getLong("labaId") + "&ref="
				+ ref + "#laba" + req.getLong("labaId");
		if (from == null || from.equals("index")) {
			return "r:/square.do" + "?page=" + page + laba_anchor;
		}
		else if (from.equals("fav")) {
			return "r:/laba/fav.do?userId=" + req.getLong("ouserId") + "&page="
					+ page + laba_anchor;
		}
		else if (from.equals("reuser")) {
			return "r:/laba/reuserlist.do" + "?page=" + page + "&userId="
					+ req.getInt("ouserId") + laba_anchor;
		}
		else if (from.equals("relaba")) {
			return "r:/laba/relist.do" + "?labaId=" + req.getLong("relabaId")
					+ "&page=" + page + laba_anchor;
		}
		else if (from.equals("taglabalist")) {
			return "r:/laba/taglabalist.do" + "?tagId=" + req.getLong("tagId")
					+ "&page=" + page + laba_anchor;
		}
		else if (from.equals("userlabalist")) {
			return "r:/laba/userlabalist.do" + "?userId="
					+ req.getLong("ouserId") + "&page=" + page + laba_anchor;
		}
		else if (from.equals("square")) {
			return "r:/square.do" + "?w=" + req.getString("w", "")
					+ "&ipCityId=" + req.getInt("ipCityId") + "&page=" + page
					+ laba_anchor;
		}
		else if (from.equals("userlist")) {
			return "r:/user/list1.do?w=" + req.getString("w", "") + "page="
					+ req.getInt("repage") + laba_anchor;
		}
		else if (from.equals("fcuserlist")) {
			return "r:/user/list2.do?page=" + req.getInt("repage")
					+ laba_anchor;
		}
		else if (from.equals("usersearch")) {
			return "r:/user/search.do?sw=" + req.getEncodeString("sw")
					+ "&page=" + req.getInt("repage") + laba_anchor;
		}
		else if (from.equals("tore")) {
			return "r:/laba/laba.do?labaId=" + req.getLong("relabaId")
					+ laba_anchor;
		}
		else if (from.equals("labasearch")) {
			return "r:/laba/search.do?sw=" + req.getEncodeString("sw")
					+ "&page=" + req.getInt("repage") + "&sfrom="
					+ req.getString("sfrom", "") + laba_anchor;
		}
		else if (from.equals("home")) {
			return "r:/home.do?userId=" + req.getInt("ouserId") + laba_anchor;
		}
		else if (from.equals("follow")) {
			return "r:/follow/follow.do?userId=" + req.getLong("ouserId")
					+ "&nickName=" + req.getEncodeString("nickName") + "&page="
					+ req.getInt("repage");
		}
		else if (from.equals("follow_re")) {
			return "r:/follow/follow_re.do?userId=" + req.getLong("ouserId")
					+ "&nickName=" + req.getEncodeString("nickName") + "&page="
					+ req.getInt("repage");
		}
		else if (from.equals("notice")) {
			return "r:/notice/notice.do";
		}
		else if (from.equals("info")) {
			return "r:/info/info_view.do?infoId=" + req.getInt("infoId")
					+ laba_anchor;
		}
		else if (from.equals("poplaba")) {
			return "r:/laba/poplaba.do?page=" + req.getInt("repage")
					+ laba_anchor;
		}
		return "r:/square.do" + "?page=" + page + laba_anchor;
	}
}