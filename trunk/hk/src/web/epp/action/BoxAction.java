package web.epp.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.svr.processor.BoxProccessorOpenResult;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;

@Component("/epp/web/box")
public class BoxAction extends EppBaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	@Autowired
	private BoxProcessor boxProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Box> list = this.boxService.getCanOpenBoxListByCompanyId(
				companyId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("box/list.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-26
	 */
	public String view(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		req.reSetAttribute("navId");
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		boolean onlysmsopen = false;
		boolean smsandweb = false;
		boolean begin = false;
		boolean stop = false;
		boolean end = false;
		long nowtime = System.currentTimeMillis();
		if (box.getOpentype() == Box.BOX_OPENTYPE_SMS) {
			onlysmsopen = true;
		}
		else if (box.getOpentype() == Box.BOX_OPENTYPE_SMSANDWEB) {
			smsandweb = true;
		}
		if (box.getBeginTime().getTime() < nowtime) {
			begin = true;
		}
		if (box.getEndTime().getTime() < nowtime) {
			end = true;
		}
		if (box.getBoxStatus() != Box.BOX_STATUS_NORMAL) {
			stop = true;
		}
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		int piccount = 0;
		for (BoxPrize o : list) {
			if (o.getPath() != null) {
				piccount++;
			}
		}
		BoxPrize firstPrize = null;
		if (list.size() > 0) {
			firstPrize = list.remove(0);
			req.setAttribute("firstPrize", firstPrize);
		}
		req.setAttribute("piccount", piccount);
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("end", end);
		req.setAttribute("stop", stop);
		req.setAttribute("begin", begin);
		req.setAttribute("smsandweb", smsandweb);
		req.setAttribute("onlysmsopen", onlysmsopen);
		req.setAttribute("list", list);
		req.setAttribute("box", box);
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			if (box.getUserId() == loginUser.getUserId()
					|| this.isCmpAdminUser(req)) {
				req.setAttribute("canadminbox", true);
			}
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(loginUser.getUserId());
			int canOpenBoxCount = userOtherInfo.getPoints()
					/ CheckInPointConfig.getOpenBoxPoints();
			req.setAttribute("canOpenBoxCount", canOpenBoxCount);
		}
		return this.getWebPath("box/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-26
	 */
	public String wapview(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		req.reSetAttribute("navId");
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		boolean onlysmsopen = false;
		boolean smsandweb = false;
		boolean begin = false;
		boolean stop = false;
		boolean end = false;
		long nowtime = System.currentTimeMillis();
		if (box.getOpentype() == Box.BOX_OPENTYPE_SMS) {
			onlysmsopen = true;
		}
		else if (box.getOpentype() == Box.BOX_OPENTYPE_SMSANDWEB) {
			smsandweb = true;
		}
		if (box.getBeginTime().getTime() < nowtime) {
			begin = true;
		}
		if (box.getEndTime().getTime() < nowtime) {
			end = true;
		}
		if (box.getBoxStatus() != Box.BOX_STATUS_NORMAL) {
			stop = true;
		}
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		int piccount = 0;
		for (BoxPrize o : list) {
			if (o.getPath() != null) {
				piccount++;
			}
		}
		BoxPrize firstPrize = null;
		if (list.size() > 0) {
			firstPrize = list.remove(0);
			req.setAttribute("firstPrize", firstPrize);
		}
		req.setAttribute("piccount", piccount);
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("end", end);
		req.setAttribute("stop", stop);
		req.setAttribute("begin", begin);
		req.setAttribute("smsandweb", smsandweb);
		req.setAttribute("onlysmsopen", onlysmsopen);
		req.setAttribute("list", list);
		req.setAttribute("box", box);
		User loginUser = this.getLoginUser2(req);
		if (loginUser != null) {
			if (box.getUserId() == loginUser.getUserId()
					|| this.isCmpAdminUser(req)) {
				req.setAttribute("canadminbox", true);
			}
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(loginUser.getUserId());
			int canOpenBoxCount = userOtherInfo.getPoints()
					/ CheckInPointConfig.getOpenBoxPoints();
			req.setAttribute("canOpenBoxCount", canOpenBoxCount);
		}
		return this.getWapPath("box/view.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-21
	 */
	public String wap(HkRequest req, HkResponse resp) throws Exception {
		this.setCmpNavInfo(req);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Box> list = this.boxService.getCanOpenBoxListByCompanyId(
				companyId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapPath("box/list.jsp");
	}

	/**
	 * 开箱子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String prvopenbox(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long boxId = req.getLong("boxId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		Box box = this.boxService.getBox(boxId);
		User loginUser = this.getLoginUser2(req);
		String boxpath = "r:/epp/web/box_view.do?boxId=" + boxId
				+ "&companyId=" + companyId + "&navId=" + navId;
		BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
				.openBox2(loginUser.getUserId(), loginUser.getPcityId(), box,
						req.getRemoteAddr());
		int result = boxProccessorOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			req.setSessionText(String.valueOf(result), ZoneUtil.getCityName(box
					.getCityId()));
			return boxpath;
		}
		// 没有足够的点数开箱
		if (boxProccessorOpenResult.isNoEnoughPoints()) {
			req.setSessionText("view2.notenoughpointsforopenbox");
			return boxpath;
		}
		BoxOpenResult boxOpenResult = boxProccessorOpenResult
				.getBoxOpenResult();
		result = boxOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			if (result == Err.BOX_OUT_OF_LIMIT) {
				BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box
						.getPretype());
				req.setSessionText(String.valueOf(result),
						boxPretype.getName(), box.getPrecount());
			}
			else {
				req.setSessionText(String.valueOf(result));
			}
			return boxpath;
		}
		// 没有中奖
		if (boxOpenResult.getUserBoxPrize() == null) {
			req.setSessionText("view2.box.openbox.noprize");
			return boxpath;
		}
		// 中奖
		BoxPrize prize = this.boxService.getBoxPrize(boxOpenResult
				.getUserBoxPrize().getPrizeId());
		long prizeId = prize.getPrizeId();
		return "r:/epp/web/box_prvboxresult.do?boxId=" + boxId + "&prizeId="
				+ prizeId + "&sysId="
				+ boxOpenResult.getUserBoxPrize().getSysId() + "&companyId="
				+ companyId + "&navId=" + navId;
	}

	/**
	 * 开箱子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String wapprvopenbox(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long boxId = req.getLong("boxId");
		long companyId = req.getLong("companyId");
		long navId = req.getLong("navId");
		Box box = this.boxService.getBox(boxId);
		User loginUser = this.getLoginUser2(req);
		String boxpath = "r:/epp/web/box_wapview.do?boxId=" + boxId
				+ "&companyId=" + companyId + "&navId=" + navId;
		BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
				.openBox2(loginUser.getUserId(), loginUser.getPcityId(), box,
						req.getRemoteAddr());
		int result = boxProccessorOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			req.setSessionText(String.valueOf(result), ZoneUtil.getCityName(box
					.getCityId()));
			return boxpath;
		}
		// 没有足够的点数开箱
		if (boxProccessorOpenResult.isNoEnoughPoints()) {
			req.setSessionText("view2.notenoughpointsforopenbox");
			return boxpath;
		}
		BoxOpenResult boxOpenResult = boxProccessorOpenResult
				.getBoxOpenResult();
		result = boxOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			if (result == Err.BOX_OUT_OF_LIMIT) {
				BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box
						.getPretype());
				req.setSessionText(String.valueOf(result),
						boxPretype.getName(), box.getPrecount());
			}
			else {
				req.setSessionText(String.valueOf(result));
			}
			return boxpath;
		}
		// 没有中奖
		if (boxOpenResult.getUserBoxPrize() == null) {
			req.setSessionText("view2.box.openbox.noprize");
			return boxpath;
		}
		// 中奖
		BoxPrize prize = this.boxService.getBoxPrize(boxOpenResult
				.getUserBoxPrize().getPrizeId());
		long prizeId = prize.getPrizeId();
		return "r:/epp/web/box_wapprvboxresult.do?boxId=" + boxId + "&prizeId="
				+ prizeId + "&sysId="
				+ boxOpenResult.getUserBoxPrize().getSysId() + "&companyId="
				+ companyId + "&navId=" + navId;
	}

	/**
	 * 宝箱结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String wapprvboxresult(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long boxId = req.getLongAndSetAttr("boxId");
		long sysId = req.getLong("sysId");
		long prizeId = req.getLong("prizeId");
		User loginUser = this.getLoginUser2(req);
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		BoxPrize prize = this.boxService.getBoxPrize(prizeId);
		req.setAttribute("prize", prize);
		UserBoxPrize userBoxPrize = this.boxService.getUserBoxPrize(sysId);
		if (userBoxPrize != null
				&& userBoxPrize.getUserId() == loginUser.getUserId()) {
			if (prize.getEid() > 0 && userBoxPrize.getPrizeId() == prizeId) {
				this.boxService.deleteUserBoxPrize(sysId);
			}
			req.setAttribute("userBoxPrize", userBoxPrize);
		}
		return this.getWapPath("box/result.jsp");
	}

	/**
	 * 宝箱结果页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-4-15
	 */
	public String prvboxresult(HkRequest req, HkResponse resp) {
		this.setCmpNavInfo(req);
		long boxId = req.getLongAndSetAttr("boxId");
		long sysId = req.getLong("sysId");
		long prizeId = req.getLong("prizeId");
		User loginUser = this.getLoginUser2(req);
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		BoxPrize prize = this.boxService.getBoxPrize(prizeId);
		req.setAttribute("prize", prize);
		UserBoxPrize userBoxPrize = this.boxService.getUserBoxPrize(sysId);
		if (userBoxPrize != null
				&& userBoxPrize.getUserId() == loginUser.getUserId()) {
			if (prize.getEid() > 0 && userBoxPrize.getPrizeId() == prizeId) {
				this.boxService.deleteUserBoxPrize(sysId);
			}
			req.setAttribute("userBoxPrize", userBoxPrize);
		}
		return this.getWebPath("box/result.jsp");
	}
}