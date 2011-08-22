package tuxiazi.svr.impl.jms;

import halo.util.JsonUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Friend;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

public class UserConsumer {

	private final Log log = LogFactory.getLog(UserConsumer.class);

	@Autowired
	private FriendService friendService;

	@Autowired
	private Api_user_sinaDao api_user_sinaDao;

	public void processMessage(String value) {
		JmsMsg jmsMsg = new JmsMsg(value);
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_PHOTO_CREATEUSER)) {
			this.proccessCreateUser(jmsMsg.getBody());
			return;
		}
		log.error("unknown message type [ " + value + " ]");
	}

	private void proccessCreateUser(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long userid = Long.valueOf(map.get(JsonKey.userid));
		String sina_userid = map.get(JsonKey.sina_userid);
		String access_token = map.get(JsonKey.access_token);
		String token_secret = map.get(JsonKey.token_secret);
		List<Long> frlist = null;
		List<Long> fansidlist = null;
		try {
			frlist = SinaUtil.getFriendIdList(access_token, token_secret,
					Long.valueOf(sina_userid));
			fansidlist = SinaUtil.getFansIdList(access_token, token_secret,
					Long.valueOf(sina_userid));
			if (frlist != null) {
				Set<Long> bothset = this.getBothset(frlist, fansidlist);
				// 查看在新浪微博的好友是否有注册的，如果有，就关注
				List<Api_user_sina> list = this.api_user_sinaDao
						.getListInSina_userid(frlist, true);
				Friend friend = null;
				for (Api_user_sina o : list) {
					friend = new Friend();
					friend.setUserid(userid);
					friend.setFriendid(o.getUserid());
					friend.setFlg(Friend.FLG_NOBOTH);
					try {
						boolean sendNotice = false;
						if (bothset.contains(o.getSina_userid())) {
							// 发送通知告诉对方我也来了
							sendNotice = true;
							// 让双方相互关注
							Friend friend2 = new Friend();
							friend2.setUserid(o.getUserid());
							friend2.setFriendid(userid);
							friend2.setFlg(Friend.FLG_BOTH);
							this.friendService.createFriend(friend2, false,
									false);
						}
						this.friendService.createFriend(friend, sendNotice,
								true);
					}
					catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}
		catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Set<Long> getBothset(List<Long> friendidlist, List<Long> fansidlist) {
		Set<Long> bothset = new HashSet<Long>();
		Set<Long> set = new HashSet<Long>(fansidlist);
		for (Long o : friendidlist) {
			if (set.contains(o)) {
				bothset.add(o);
			}
		}
		return bothset;
	}
}