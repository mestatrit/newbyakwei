package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.BoxOpenResult;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.svr.processor.BoxProccessorOpenResult;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.util.HkWebConfig;

public class OpenBoxCmd extends BaseCmd {

	private final Log log = LogFactory.getLog(OpenBoxCmd.class);

	@Autowired
	private BoxService boxService;

	@Autowired
	private BoxProcessor boxProcessor;

	@Autowired
	private UserService userService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		log.info("process sms box");
		Sms o = new Sms();
		o.setMobile(receivedSms.getMobile());
		o.setLinkid(receivedSms.getLinkid());
		Box box = this.boxService.getBoxByBoxKey(receivedSms.getContent());
		if (box == null) {
			o.setContent("开箱指令错误或还没有到开箱时间");
			this.sendMsg(o);
			return null;
		}
		if (box.getOpentype() == Box.BOX_OPENTYPE_WEB) {
			o.setContent(box.getName() + "只支持网站开箱,请登录"
					+ HkWebConfig.getWebDomain() + "参与开箱");
			this.sendMsg(o);
			return null;
		}
		UserOtherInfo uinfo = this.getUserSmsMo(receivedSms).getUserOtherInfo();
		if (uinfo != null) {
			long userId = uinfo.getUserId();
			User user = this.userService.getUser(userId);
			BoxProccessorOpenResult boxProccessorOpenResult = this.boxProcessor
					.openBox2(userId, user.getPcityId(), box, null);
			int result = boxProccessorOpenResult.getErrorCode();
			if (result != Err.SUCCESS) {
				o.setContent(ResourceConfig.getText(String.valueOf(result),
						ZoneUtil.getCityName(box.getCityId())));
			}
			else {
				if (boxProccessorOpenResult.isNoEnoughPoints()) {
					o.setContent(ResourceConfig
							.getText("view2.notenoughpointsforopenbox"));
				}
				else {
					BoxOpenResult boxOpenResult = boxProccessorOpenResult
							.getBoxOpenResult();
					result = boxOpenResult.getErrorCode();
					if (result != Err.SUCCESS) {
						if (result == Err.BOX_OUT_OF_LIMIT) {
							BoxPretype boxPretype = BoxPretypeUtil
									.getBoxPretype(box.getPretype());
							o.setContent(ResourceConfig.getText(String
									.valueOf(result), boxPretype.getName(), box
									.getPrecount()));
						}
						else {
							o.setContent(ResourceConfig.getText(String
									.valueOf(result)));
						}
					}
					else {
						UserBoxPrize userBoxPrize = boxOpenResult
								.getUserBoxPrize();
						if (userBoxPrize != null) {
							BoxPrize prize = this.boxService
									.getBoxPrize(userBoxPrize.getPrizeId());
							o.setContent(prize.getTip());
						}
						else {
							o.setContent("在参与" + box.getName()
									+ "宝箱的活动中,很可惜,你什么也没有中.");
						}
					}
				}
			}
			this.sendMsg(o);
		}
		else {
			log.info("no user mobile [ " + receivedSms.getMobile() + " ]");
		}
		return null;
	}
}