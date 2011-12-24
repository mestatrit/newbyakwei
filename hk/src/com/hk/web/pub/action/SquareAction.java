package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.IpCityLaba;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeLaba;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.IpCityService;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;

@Component("/square")
public class SquareAction extends BaseAction {

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String w = req.getString("w", "we");
		int size = 20;
		SimplePage page = req.getSimplePage(size);
		List<LabaVo> labavolist = new ArrayList<LabaVo>();
		if (w.equals("we") && loginUser == null) {
			w = "all";
		}
		if (w.equals("we")) {
			if (loginUser != null) {
				List<Laba> list = labaService.getLabaListForFollowByUserId(
						loginUser.getUserId(), page.getBegin(), size);
				if (list.size() == 0) {
					return "r:/square.do?w=all";
				}
				LabaParserCfg cfg = this.getLabaParserCfg(req);
				labavolist = LabaVo.createVoList(list, cfg);
				page.setListSize(list.size());
			}
		}
		else if (w.equals("range")) {
			IpCityRange range = this.ipCityService.getIpCityRange(req
					.getRemoteAddr());
			if (range != null) {
				List<IpCityRangeLaba> list = labaService
						.getIpCityRangeLabaList(range.getRangeId(), page
								.getBegin(), size);
				page.setListSize(list.size());
				List<Laba> labalist = new ArrayList<Laba>();
				for (IpCityRangeLaba o : list) {
					labalist.add(o.getLaba());
				}
				labavolist = LabaVo.createVoList(labalist, this
						.getLabaParserCfg(req));
			}
		}
		else if (w.equals("city")) {
			int ipCityId = req.getInt("ipCityId");
			if (ipCityId == 0) {
				IpCityRange range = this.ipCityService.getIpCityRange(req
						.getRemoteAddr());
				if (range != null) {
					ipCityId = range.getCityId();
				}
				if (ipCityId == 0) {
					if (loginUser != null) {
						User user = this.userService.getUser(loginUser
								.getUserId());
						IpZoneInfo ipZoneInfo = new IpZoneInfo(user
								.getPcityId());
						ipCityId = ipZoneInfo.getIpCityId();
					}
				}
			}
			List<IpCityLaba> list = labaService.getIpCityLabaList(ipCityId,
					page.getBegin(), size);
			page.setListSize(list.size());
			List<Laba> labalist = new ArrayList<Laba>();
			for (IpCityLaba o : list) {
				labalist.add(o.getLaba());
			}
			labavolist = LabaVo.createVoList(labalist, this
					.getLabaParserCfg(req));
		}
		else if (w.equals("ip")) {
			List<Laba> list = labaService.getIpLabaList(req.getRemoteAddr(),
					page.getBegin(), size);
			page.setListSize(list.size());
			labavolist = LabaVo.createVoList(list, this.getLabaParserCfg(req));
		}
		else if (w.equals("all")) {
			List<Laba> list = labaService.getLabaList(page.getBegin(), size);
			page.setListSize(list.size());
			labavolist = LabaVo.createVoList(list, this.getLabaParserCfg(req));
		}
		if (loginUser != null) {
			String n = loginUser.getNickName();
			for (LabaVo vo : labavolist) {
				if (vo.getContent().indexOf(n) != -1) {
					vo.setReplyMe(true);
				}
			}
		}
		req.setAttribute("labavolist", labavolist);
		req.setAttribute("w", w);
		return "/WEB-INF/page/square.jsp";
	}
}