package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Impression;
import com.hk.bean.ProUser;
import com.hk.bean.User;
import com.hk.bean.WelProUser;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.ImpressionService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.ImpressionVo;

@Component("/prouser")
public class ProUserAction extends BaseAction {
	@Autowired
	private UserService userService;

	@Autowired
	private ImpressionService impressionService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long prouserId = req.getLong("prouserId");
		ProUser proUser = this.userService.getProUser(prouserId);
		if (proUser != null) {
			proUser
					.setCreater(this.userService
							.getUser(proUser.getCreaterId()));
		}
		SimplePage page = req.getSimplePage(size20);
		List<Impression> impressionlist = this.impressionService
				.getImpressionListByProuserId(prouserId, page.getBegin(),
						size20);
		List<ImpressionVo> impressionvolist = ImpressionVo.createVoList(
				impressionlist, this.getUrlInfo(req), false);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			Impression myImpression = this.impressionService.getImpression(
					loginUser.getUserId(), prouserId);
			if (myImpression != null) {
				req.setAttribute("reviewed", true);
			}
		}
		List<WelProUser> wellist = this.userService
				.getWelProuserListByProuserId(prouserId, 0, 30);
		List<Long> idList = new ArrayList<Long>();
		for (WelProUser o : wellist) {
			idList.add(o.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (WelProUser o : wellist) {
			o.setUser(map.get(o.getUserId()));
		}
		req.setAttribute("wellist", wellist);
		req.setAttribute("prouserId", prouserId);
		req.setAttribute("proUser", proUser);
		req.setAttribute("impressionvolist", impressionvolist);
		return this.getWapJsp("user/prouser.jsp");
	}
}