package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUser;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;
import tuxiazi.web.util.SinaUtil;
import weibo4j.User;
import weibo4j.WeiboException;

@Component("/api/weibo")
public class WeiboAction extends BaseApiAction {

	@Autowired
	private UserService userService;

	@Override
	public String execute(HkRequest req, HkResponse resp) {
		return null;
	}

	public String prvsinafriend(HkRequest req, HkResponse resp) {
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		Api_user_sina apiUserSina = this.getApiUserSina(req);
		Map<String, Object> omap = new HashMap<String, Object>();
		List<SinaUser> list = this.userService.getSinaFriendList(apiUserSina,
				page, size);
		omap.put("list", list);
		APIUtil.writeData(resp, omap, "vm/sinafriendlist.vm");
		return null;
	}

	public String prvsinafans(HkRequest req, HkResponse resp) {
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		Api_user_sina apiUserSina = this.getApiUserSina(req);
		Map<String, Object> omap = new HashMap<String, Object>();
		List<SinaUser> list = this.userService.getSinaFansList(apiUserSina,
				page, size);
		omap.put("list", list);
		APIUtil.writeData(resp, omap, "vm/sinafanslist.vm");
		return null;
	}

	public String prvshowuser(HkRequest req, HkResponse resp) {
		String screenName = req.getString("screen_name");
		Api_user_sina apiUserSina = this.getApiUserSina(req);
		try {
			User user = SinaUtil.getUser(apiUserSina.getAccess_token(),
					apiUserSina.getToken_secret(), screenName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			APIUtil.writeData(resp, map, "vm/sinauser.vm");
		}
		catch (WeiboException e) {
			APIUtil.writeErr(resp, Err.API_NO_SINA_USER);
		}
		return null;
	}
}