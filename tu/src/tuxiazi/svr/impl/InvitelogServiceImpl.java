package tuxiazi.svr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Invitelog;
import tuxiazi.svr.iface.InvitelogService;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

import com.hk.frame.dao.query.QueryManager;

public class InvitelogServiceImpl implements InvitelogService {

	@Autowired
	private QueryManager manager;

	@Override
	public List<Invitelog> getInvitelogListByUseridAndApi_typeAndInOtherid(
			long userid, int apiType, List<String> otheridList) {
		return this.manager.createQuery().listInField(Invitelog.class,
				"userid=? and api_type=?", new Object[] { userid, apiType },
				"otherid", otheridList, null);
	}

	@Override
	public boolean inviteSinaFans(Api_user_sina apiUserSina, long sinaUserid,
			String content) {
		try {
			SinaUtil.sendMessage(apiUserSina.getAccess_token(), apiUserSina
					.getToken_secret(), sinaUserid, content);
			Invitelog invitelog = new Invitelog();
			invitelog.setUserid(apiUserSina.getUserid());
			invitelog.setApi_type(Api_user.API_TYPE_SINA);
			invitelog.setCreatetime(new Date());
			invitelog.setOtherid(String.valueOf(sinaUserid));
			invitelog.setLogid(this.manager.createQuery().insertObject(
					invitelog).longValue());
			return true;
		}
		catch (WeiboException e) {
			return false;
		}
	}
}
