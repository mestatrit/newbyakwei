package com.hk.svr.implex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Follow;
import com.hk.bean.taobao.Tb_Followed;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.JsonUtil;
import com.hk.jms.HkMsgProducer;
import com.hk.jms.JmsMsg;
import com.hk.svr.Tb_FollowService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.impl.Tb_FollowServiceImpl;

public class Tb_FollowServiceImplEx extends Tb_FollowServiceImpl {

	private Tb_FollowService tbFollowService;

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	public void setTbFollowService(Tb_FollowService tbFollowService) {
		this.tbFollowService = tbFollowService;
	}

	@Override
	public void createTb_Follow(Tb_Follow tbFollow, boolean follow_to_sina) {
		this.tbFollowService.createTb_Follow(tbFollow, follow_to_sina);
		Tb_Followed tbFollowed = new Tb_Followed();
		tbFollowed.setUserid(tbFollow.getFriendid());
		tbFollowed.setFansid(tbFollow.getUserid());
		this.tbFollowService.createTb_Followed(tbFollowed);
		this.processFriendNumAndFansNum(tbFollow);
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_NEWS_CREATE_FOLLOW);
		Map<String, String> map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbFollow.getUserid()));
		map.put(JsonKey.FRIENDID, String.valueOf(tbFollow.getFriendid()));
		jmsMsg.setBody(JsonUtil.toJson(map));
		this.hkMsgProducer.send(jmsMsg.toMessage());
		if (follow_to_sina) {
			// 如果双方都有新浪微博账号，就关注对方
			map = new HashMap<String, String>();
			map.put(JsonKey.USERID, String.valueOf(tbFollow.getUserid()));
			map.put(JsonKey.FRIENDID, String.valueOf(tbFollow.getFriendid()));
			jmsMsg = new JmsMsg(JmsMsg.HEAD_OTHER_API_SINAFOLLOW_CREATE_FOLLOW,
					JsonUtil.toJson(map));
			this.hkMsgProducer.send(jmsMsg.toMessage());
		}
		if (tbFollow.getUserid() != tbFollow.getFriendid()) {
			map = new HashMap<String, String>();
			map.put(JsonKey.USERID, String.valueOf(tbFollow.getFriendid()));
			map.put(JsonKey.FANSID, String.valueOf(tbFollow.getUserid()));
			jmsMsg = new JmsMsg(JmsMsg.HEAD_NOTICE_FOLLOW_USER, JsonUtil
					.toJson(map));
			this.hkMsgProducer.send(jmsMsg.toMessage());
		}
	}

	@Override
	public void deleteTb_Follow(Tb_Follow tbFollow) {
		this.tbFollowService.deleteTb_Follow(tbFollow);
		Tb_Followed tbFollowed = this.tbFollowService
				.getTb_FollowedByUseridAndFriendid(tbFollow.getFriendid(),
						tbFollow.getUserid());
		if (tbFollowed != null) {
			this.tbFollowService.deleteTb_Followed(tbFollowed);
		}
		this.processFriendNumAndFansNum(tbFollow);
	}

	/**
	 * @param userid
	 * @param buildUser
	 * @param begin
	 * @param size <0时，取所有数据
	 * @return
	 *         2010-8-31
	 */
	@Override
	public List<Tb_Follow> getTb_FollowListByUserid(long userid,
			boolean buildUser, int begin, int size) {
		List<Tb_Follow> list = this.tbFollowService.getTb_FollowListByUserid(
				userid, false, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Follow o : list) {
				idList.add(o.getFriendid());
			}
			Map<Long, Tb_User> map = tb_UserService.getTb_UserMapInId(idList);
			for (Tb_Follow o : list) {
				o.setFriend(map.get(o.getFriendid()));
			}
		}
		return list;
	}

	/**
	 * @param userid
	 * @param buildUser
	 * @param begin
	 * @param size <0时，取所有数据
	 * @return
	 *         2010-8-31
	 */
	@Override
	public List<Tb_Followed> getTb_FollowedListByUserid(long userid,
			boolean buildUser, int begin, int size) {
		List<Tb_Followed> list = this.tbFollowService
				.getTb_FollowedListByUserid(userid, false, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Followed o : list) {
				idList.add(o.getFansid());
			}
			Map<Long, Tb_User> map = tb_UserService.getTb_UserMapInId(idList);
			for (Tb_Followed o : list) {
				o.setFans(map.get(o.getFansid()));
			}
		}
		return list;
	}

	private void processFriendNumAndFansNum(Tb_Follow tbFollow) {
		Tb_User tbUser = this.tb_UserService.getTb_User(tbFollow.getUserid());
		Tb_User friend = this.tb_UserService.getTb_User(tbFollow.getFriendid());
		tbUser.setFriend_count(this.tbFollowService
				.countTb_FollowByUserid(tbFollow.getUserid()));
		friend.setFans_count(this.tbFollowService
				.countTb_FollowedByUserid(tbFollow.getFriendid()));
		this.tb_UserService.updateTb_User(tbUser);
		this.tb_UserService.updateTb_User(friend);
	}
}