package tuxiazi.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.svr.iface.InvitelogService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/invite")
public class InviteAction extends BaseApiAction {

	@Autowired
	private InvitelogService invitelogService;

	/**
	 * 邀请新浪微博好友使用图匣子
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvinvitesinafans(HkRequest req, HkResponse resp)
			throws Exception {
		long sinaUserid = req.getLong("sinauserid");
		if (sinaUserid > 0) {
			String content = "我正在使用图匣子，感觉很不错，推荐你使用 http://www.tuxiazi.com?v="
					+ System.currentTimeMillis();
			boolean result = this.invitelogService.inviteSinaFans(this
					.getApiUserSina(req), sinaUserid, content);
			// boolean result = false;
			if (result) {
				APIUtil.writeSuccess(req, resp);
			}
			else {
				APIUtil.writeErr(req, resp, Err.INVITE_ERROR);
			}
		}
		else {
			APIUtil.writeErr(req, resp, Err.INVITE_ERROR);
		}
		return null;
	}
}