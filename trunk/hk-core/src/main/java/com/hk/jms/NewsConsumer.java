package com.hk.jms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Friend_News;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.Tb_AskService;
import com.hk.svr.Tb_FollowService;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_NewsService;
import com.hk.svr.Tb_UserService;

public class NewsConsumer {

	@Autowired
	private Tb_NewsService tbNewsService;

	@Autowired
	private Tb_FollowService tbFollowService;

	@Autowired
	private Tb_ItemService tbItemService;

	@Autowired
	private Tb_UserService tbUserService;

	@Autowired
	private Tb_Item_CmtService tbItemCmtService;

	@Autowired
	private Tb_AskService tbAskService;

	private final Log log = LogFactory.getLog(NewsConsumer.class);

	public void processMessage(String value) {
		JmsMsg jmsMsg = new JmsMsg(value);
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NEWS_CREATE_ITEM)) {
			this.processCreateItemNews(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NEWS_CREATE_ITEM_CMT)) {
			this.processCreateItemCmtNews(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NEWS_CREATE_ASK)) {
			this.processCreateAskNews(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NEWS_CREATE_ANSWER)) {
			this.processCreateAnswerNews(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NEWS_CREATE_FOLLOW)) {
			this.processCreateFollowNews(jmsMsg.getBody());
			return;
		}
		log.error("unknown message type [ " + value + " ]");
	}

	private void processCreateFollowNews(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		String userid_str = map.get(JsonKey.USERID);
		String friendid_str = map.get(JsonKey.FRIENDID);
		if (userid_str == null || friendid_str == null) {
			return;
		}
		Tb_User user = this.tbUserService.getTb_User(Long.valueOf(userid_str));
		Tb_User friend = this.tbUserService.getTb_User(Long
				.valueOf(friendid_str));
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, userid_str);
		map.put(JsonKey.USER_NICK, user.getShow_nick());
		if (user.getPic_path() != null) {
			map.put(JsonKey.USER_PIC_PATH, user.getPic_path());
		}
		map.put(JsonKey.FRIENDID, friendid_str);
		map.put(JsonKey.FRIEND_NICK, friend.getShow_nick());
		if (user.getPic_path() != null) {
			map.put(JsonKey.FRIEND_PIC_PATH, friend.getPic_path());
		}
		Tb_News tbNews = new Tb_News();
		tbNews.setUserid(user.getUserid());
		tbNews.setCreate_time(new Date());
		tbNews.setData(JsonUtil.toJson(map));
		tbNews.setNtype(Tb_News.NTYPE_CREATEFOLLOW);
		tbNews.setOid(friend.getUserid());
		this.tbNewsService.createTb_News(tbNews);
		this.createFriendNews(tbNews);
	}

	private void processCreateAskNews(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		String aid_str = map.get(JsonKey.ASK_ID);
		if (aid_str == null) {
			return;
		}
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(Long.valueOf(aid_str));
		if (tbAsk == null) {
			return;
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbAsk.getUserid());
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbUser.getUserid()));
		map.put(JsonKey.USER_NICK, tbUser.getShow_nick());
		if (tbUser.getPic_path() != null) {
			map.put(JsonKey.USER_PIC_PATH, tbUser.getPic_path());
		}
		map.put(JsonKey.ASK_ID, aid_str);
		map.put(JsonKey.ASK_TITLE, tbAsk.getTitle());
		Tb_News tbNews = new Tb_News();
		tbNews.setUserid(tbAsk.getUserid());
		tbNews.setCreate_time(tbAsk.getCreate_time());
		tbNews.setData(JsonUtil.toJson(map));
		tbNews.setNtype(Tb_News.NTYPE_CREATEASK);
		tbNews.setOid(tbAsk.getAid());
		this.tbNewsService.createTb_News(tbNews);
		this.createFriendNews(tbNews);
	}

	private void processCreateAnswerNews(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		String ansid_str = map.get(JsonKey.ANSWER_ID);
		if (ansid_str == null) {
			return;
		}
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(Long
				.valueOf(ansid_str));
		if (tbAnswer == null) {
			return;
		}
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(tbAnswer.getAid());
		if (tbAsk == null) {
			return;
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbAnswer.getUserid());
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbUser.getUserid()));
		map.put(JsonKey.USER_NICK, tbUser.getShow_nick());
		if (tbUser.getPic_path() != null) {
			map.put(JsonKey.USER_PIC_PATH, tbUser.getPic_path());
		}
		map.put(JsonKey.ANSWER_ID, ansid_str);
		map.put(JsonKey.ANSWER_CONTENT, DataUtil.limitHtmlRow(tbAnswer
				.getContent(), 50));
		map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
		map.put(JsonKey.ASK_TITLE, tbAsk.getTitle());
		Tb_News tbNews = new Tb_News();
		tbNews.setUserid(tbAnswer.getUserid());
		tbNews.setCreate_time(tbAnswer.getCreate_time());
		tbNews.setData(JsonUtil.toJson(map));
		tbNews.setNtype(Tb_News.NTYPE_CREATEANSWER);
		tbNews.setOid(tbAnswer.getAnsid());
		this.tbNewsService.createTb_News(tbNews);
		this.createFriendNews(tbNews);
	}

	private void processCreateItemCmtNews(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		String cmtid_str = map.get(JsonKey.ITEM_CMTID);
		if (cmtid_str == null) {
			return;
		}
		Tb_Item_Cmt tbItemCmt = this.tbItemCmtService.getTb_Item_Cmt(Long
				.valueOf(cmtid_str));
		if (tbItemCmt == null) {
			return;
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbItemCmt.getUserid());
		Tb_Item tbItem = this.tbItemService.getTb_Item(tbItemCmt.getItemid());
		map = new HashMap<String, String>();
		// user
		map.put(JsonKey.USERID, String.valueOf(tbUser.getUserid()));
		map.put(JsonKey.USER_NICK, tbUser.getShow_nick());
		if (tbUser.getPic_path() != null) {
			map.put(JsonKey.USER_PIC_PATH, tbUser.getPic_path());
		}
		// item
		map.put(JsonKey.ITEMID, String.valueOf(tbItem.getItemid()));
		map.put(JsonKey.ITEM_TITLE, tbItem.getTitle());
		if (tbItem.getPic_url() != null) {
			map.put(JsonKey.ITEM_IMG, tbItem.getPic_url());
		}
		// itemcmt
		map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
		map.put(JsonKey.ITEM_CMT_CONTENT, DataUtil.limitHtmlRow(tbItemCmt
				.getContent(), 50));
		Tb_News tbNews = new Tb_News();
		tbNews.setNtype(Tb_News.NTYPE_CREATEITEM_CMT);
		tbNews.setCreate_time(new Date());
		tbNews.setData(JsonUtil.toJson(map));
		tbNews.setOid(tbItemCmt.getCmtid());
		tbNews.setUserid(tbItemCmt.getUserid());
		this.tbNewsService.createTb_News(tbNews);
		this.createFriendNews(tbNews);
	}

	private void processCreateItemNews(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		String itemid_str = map.get(JsonKey.ITEMID);
		if (itemid_str == null) {
			return;
		}
		Tb_Item tbItem = this.tbItemService
				.getTb_Item(Long.valueOf(itemid_str));
		if (tbItem == null) {
			return;
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbItem.getUserid());
		Tb_News tbNews = new Tb_News();
		tbNews.setUserid(tbUser.getUserid());
		tbNews.setOid(tbItem.getItemid());
		tbNews.setCreate_time(new Date());
		tbNews.setNtype(Tb_News.NTYPE_CREATEITEM);
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbUser.getUserid()));
		map.put(JsonKey.USER_NICK, tbUser.getShow_nick());
		if (tbUser.getPic_path() != null) {
			map.put(JsonKey.USER_PIC_PATH, tbUser.getPic_path());
		}
		map.put(JsonKey.ITEMID, String.valueOf(tbItem.getItemid()));
		map.put(JsonKey.ITEM_TITLE, tbItem.getTitle());
		if (tbItem.getPic_url() != null) {
			map.put(JsonKey.ITEM_IMG, tbItem.getPic_url());
		}
		tbNews.setData(JsonUtil.toJson(map));
		this.tbNewsService.createTb_News(tbNews);
		this.createFriendNews(tbNews);
	}

	private void createFriendNews(Tb_News tbNews) {
		// 对关注人中加入自己的动态
		List<Long> fansidList = this.tbFollowService
				.getTb_FollowedFansidListByUserid(tbNews.getUserid());
		Tb_Friend_News tbFriendNews = null;
		for (Long fansid : fansidList) {
			tbFriendNews = new Tb_Friend_News();
			tbFriendNews.setNid(tbNews.getNid());
			tbFriendNews.setUserid(fansid);
			tbFriendNews.setCreate_time(tbNews.getCreate_time());
			tbFriendNews.setNews_userid(tbNews.getUserid());
			this.tbNewsService.createTb_Friend_News(tbFriendNews);
		}
	}
}