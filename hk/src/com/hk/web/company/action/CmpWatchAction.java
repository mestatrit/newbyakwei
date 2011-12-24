package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.CmpWatch;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/cmpwatch/op")
public class CmpWatchAction extends BaseAction {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加值班人员(认领之后才能使用的功能)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addcmpwatch(HkRequest req, HkResponse resp) {
		String mobile = req.getString("mobile");
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		if (!DataUtil.isEmpty(mobile)) {
			UserOtherInfo info = this.userService
					.getUserOtherInfoByMobile(mobile);
			if (info == null) {
				req.setText("func.usernotexist2");
				return "/e/op/cmpwatch/op_cmpwatchlist.do?companyId="
						+ companyId;
			}
			this.companyService.createCmpWatch(companyId, info.getUserId());
			req.setSessionText("op.exeok");
		}
		return "r:/e/op/cmpwatch/op_cmpwatchlist.do?companyId=" + companyId;
	}

	/**
	 * 添加值班人员(认领之后才能使用的功能)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delcmpwatch(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long userId = req.getLong("userId");
		this.companyService.deleteCmpWatch(companyId, userId);
		req.setSessionText("op.exeok");
		return "r:/e/op/cmpwatch/op_cmpwatchlist.do?companyId=" + companyId;
	}

	/**
	 * 值班人员(认领之后才能使用的功能)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cmpwatchlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(size);
		List<CmpWatch> list = this.companyService.getCmpWatchList(companyId,
				page.getBegin(), size);
		page.setListSize(list.size());
		if (list.size() > 0) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpWatch o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpWatch o : list) {
				o.setUser(map.get(o.getUserId()));
			}
		}
		req.setAttribute("list", list);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/op/cmpwatchlist.jsp";
	}
}