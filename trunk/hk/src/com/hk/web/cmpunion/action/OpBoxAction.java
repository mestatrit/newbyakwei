package com.hk.web.cmpunion.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.processor.BoxProccessorOpenResult;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;

@Component("/union/op/box")
public class OpBoxAction extends CmpUnionBaseAction {

	@Autowired
	private BoxService boxService;

	@Autowired
	private BoxProcessor boxProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String open(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		long uid = req.getLong("uid");
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		String path = "r:/union/box.do?uid=" + uid + "&boxId=" + boxId;
		BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
				.openBox2(userId, loginUser.getPcityId(), box, req
						.getRemoteAddr());
		int result = boxProccessorOpenResult.getErrorCode();
		if (result != Err.SUCCESS) {
			req.setSessionText(result + "", ZoneUtil.getCityName(box
					.getCityId()));
			return path;
		}
		if (boxProccessorOpenResult.isNoEnoughPoints()) {
			req.setSessionText("view2.notenoughpointsforopenbox");
			return path;
		}
		BoxOpenResult boxOpenResult = boxProccessorOpenResult
				.getBoxOpenResult();
		if (boxOpenResult == null) {
			return path;
		}
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
			return path;
		}
		if (boxOpenResult.getUserBoxPrize() != null) {
			BoxPrize prize = this.boxService.getBoxPrize(boxOpenResult
					.getUserBoxPrize().getPrizeId());
			req.setAttribute("prize", prize);
		}
		return this.getUnionWapJsp("box/boxresult.jsp");
	}
}