package web.epp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpCheckInProcessor;
import com.hk.svr.pub.CheckInPointConfig;

@Component("/epp/web/info")
public class InfoAction extends EppBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private CmpCheckInProcessor cmpCheckInProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String map(HkRequest req, HkResponse resp) throws Exception {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.map0(req, resp);
		}
		return this.map0(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String map0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.map00(req, resp);
		}
		if (tmlflg == 1) {
			return this.map01(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String map01(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		return this.getWebPath("mod/0/1/info/map.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String map00(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		return this.getWebPath("info/map.jsp");
	}

	// /**
	// * @param req
	// * @param resp
	// * @return
	// * @throws Exception
	// * 2010-5-18
	// */
	// public String map(HkRequest req, HkResponse resp) throws Exception {
	// this.setCmpNavInfo(req);
	// return this.getWebPath("info/map.jsp");
	// }
	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-18
	 */
	public String wapmap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		return this.getWapPath("info/map.jsp");
	}

	/**
	 * 用户报到
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvcheckin(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		Company company = (Company) req.getAttribute("o");
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		int oldpoints = info.getPoints();
		CmpCheckInUserLog cmpCheckInUserLog = new CmpCheckInUserLog();
		cmpCheckInUserLog.setCompanyId(companyId);
		cmpCheckInUserLog.setUserId(loginUser.getUserId());
		cmpCheckInUserLog.setSex(loginUser.getSex());
		cmpCheckInUserLog.setKindId(company.getKindId());
		cmpCheckInUserLog.setParentId(company.getParentKindId());
		cmpCheckInUserLog.setPcityId(loginUser.getPcityId());
		this.cmpCheckInProcessor.checkIn(cmpCheckInUserLog, false, company, req
				.getRemoteAddr());
		info = this.userService.getUserOtherInfo(loginUser.getUserId());
		int newpoints = info.getPoints();
		int respoints = newpoints - oldpoints;
		List<String> msglist = new ArrayList<String>();
		if (respoints > 0) {
			if (newpoints >= CheckInPointConfig.getOpenBoxPoints()) {
				int box_open_count = newpoints
						/ CheckInPointConfig.getOpenBoxPoints();
				msglist.add(req.getText("func2.checkinokandpoints2", respoints,
						newpoints, box_open_count));
			}
			else {
				msglist.add(req.getText("func2.checkinokandpoints", respoints));
			}
		}
		else {
			msglist.add(req.getText("func2.checkinok"));
		}
		StringBuilder sb = new StringBuilder();
		for (String s : msglist) {
			sb.append("<div>").append(s).append("</div>");
		}
		req.setSessionMessage(sb.toString());
		// return "r:http://" + req.getServerName();
		return null;
	}
}