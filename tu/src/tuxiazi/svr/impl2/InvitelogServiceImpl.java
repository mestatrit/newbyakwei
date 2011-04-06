package tuxiazi.svr.impl2;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Invitelog;
import tuxiazi.dao.InvitelogDao;
import tuxiazi.svr.iface.InvitelogService;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

import com.hk.frame.util.NumberUtil;

public class InvitelogServiceImpl implements InvitelogService {

	private InvitelogDao invitelogDao;

	public void setInvitelogDao(InvitelogDao invitelogDao) {
		this.invitelogDao = invitelogDao;
	}

	private final Log log = LogFactory.getLog(InvitelogServiceImpl.class);

	@Override
	public List<Invitelog> getInvitelogListByUseridAndApi_typeAndInOtherid(
			long userid, int apiType, List<String> otheridList) {
		return this.invitelogDao.getListInField(null,
				"userid=? and api_type=?", new Object[] { userid, apiType },
				"otherid", otheridList);
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
			invitelog.setLogid(NumberUtil.getLong(this.invitelogDao.save(null,
					invitelog)));
			return true;
		}
		catch (WeiboException e) {
			log.error("send invite err : " + e.getMessage());
			return false;
		}
	}
}
