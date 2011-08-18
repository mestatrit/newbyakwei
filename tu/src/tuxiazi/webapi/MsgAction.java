package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

@Component("/api/msg")
public class MsgAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(MsgAction.class);

	public String sendbug(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		Api_user_sina apiUserSina = this.userService
				.getApi_user_sinaByUserid(user.getUserid());
		String content = req.getString("content");
		content = DataUtil.limitLength(content, 300);
		try {
			SinaUtil.sendMessage(apiUserSina.getAccess_token(),
					apiUserSina.getToken_secret(), 1639525917, content);
			APIUtil.writeSuccess(resp);
		} catch (WeiboException e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.API_SINA_ERR);
		}
		return null;
	}
}