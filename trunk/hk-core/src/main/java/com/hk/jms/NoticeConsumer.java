package com.hk.jms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Cmt_Reply;
import com.hk.bean.taobao.Tb_Notice;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.Tb_AskService;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_NoticeService;
import com.hk.svr.Tb_UserService;

public class NoticeConsumer {

	@Autowired
	private Tb_ItemService tbItemService;

	@Autowired
	private Tb_UserService tbUserService;

	@Autowired
	private Tb_Item_CmtService tbItemCmtService;

	@Autowired
	private Tb_AskService tbAskService;

	@Autowired
	private Tb_NoticeService tbNoticeService;

	private final Log log = LogFactory.getLog(NoticeConsumer.class);

	public void processMessage(String value) {
		JmsMsg jmsMsg = new JmsMsg(value);
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NOTICE_CREATE_ANSWER)) {
			this.processAnswer(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NOTICE_CREATE_ITEM_CMT)) {
			this.processCreateItemCmt(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NOTICE_CREATE_ITEM_CMT_REPLY)) {
			this.processCreateItemCmtReply(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NOTICE_FOLLOW_USER)) {
			this.processFollowUser(jmsMsg.getBody());
			return;
		}
		if (jmsMsg.getHead().equals(JmsMsg.HEAD_NOTICE_SELECT_BEST_ANSWER)) {
			this.processSelectBestAnswer(jmsMsg.getBody());
			return;
		}
		log.error("unknown message type [ " + value + " ]");
	}

	private void processCreateItemCmt(String body) {
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
		if (tbItem.getUserid() == tbItemCmt.getUserid()) {
			return;
		}
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
		// itemcmt
		map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
		map.put(JsonKey.ITEM_CMT_CONTENT, DataUtil.limitHtmlRow(tbItemCmt
				.getContent(), 50));
		Tb_Notice tbNotice = new Tb_Notice();
		tbNotice.setUserid(tbItem.getUserid());
		tbNotice.setRef_oid(tbItemCmt.getCmtid());
		tbNotice.setNtype(Tb_Notice.NTYPE_CREATE_ITEMCMT);
		tbNotice.setCreate_time(new Date());
		tbNotice.setReadflg(Tb_Notice.READFLG_N);
		tbNotice.setData(JsonUtil.toJson(map));
		this.tbNoticeService.createTb_Notice(tbNotice);
	}

	private void processCreateItemCmtReply(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long replyid = Long.valueOf(map.get(JsonKey.ITEM_CMT_REPLYID));
		Tb_Item_Cmt_Reply tbItemCmtReply = this.tbItemCmtService
				.getTb_Item_Cmt_Reply(replyid);
		if (tbItemCmtReply == null) {
			return;
		}
		Tb_Item_Cmt tbItemCmt = this.tbItemCmtService
				.getTb_Item_Cmt(tbItemCmtReply.getCmtid());
		if (tbItemCmt == null) {
			return;
		}
		if (tbItemCmtReply.getUserid() == tbItemCmt.getUserid()) {
			return;
		}
		Tb_User replyUser = this.tbUserService.getTb_User(tbItemCmtReply
				.getUserid());
		if (replyUser == null) {
			return;
		}
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbItemCmtReply.getUserid()));
		map.put(JsonKey.USER_NICK, replyUser.getShow_nick());
		map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
		map.put(JsonKey.ITEM_CMT_REPLY_CONTENT, DataUtil.limitHtmlRow(
				tbItemCmtReply.getContent(), 50));
		Tb_Notice tbNotice = new Tb_Notice();
		tbNotice.setUserid(tbItemCmt.getUserid());
		tbNotice.setRef_oid(replyid);
		tbNotice.setNtype(Tb_Notice.NTYPE_CREATE_REPLYCMT);
		tbNotice.setCreate_time(new Date());
		tbNotice.setReadflg(Tb_Notice.READFLG_N);
		tbNotice.setData(JsonUtil.toJson(map));
		this.tbNoticeService.createTb_Notice(tbNotice);
	}

	private void processFollowUser(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long fansid = Long.valueOf(map.get(JsonKey.FANSID));
		long userid = Long.valueOf(map.get(JsonKey.USERID));
		Tb_User fans = this.tbUserService.getTb_User(fansid);
		if (fans == null) {
			return;
		}
		if (fansid == userid) {
			return;
		}
		map = new HashMap<String, String>();
		map.put(JsonKey.FANSID, String.valueOf(fansid));
		map.put(JsonKey.FANS_NICK, fans.getShow_nick());
		Tb_Notice tbNotice = new Tb_Notice();
		tbNotice.setUserid(userid);
		tbNotice.setRef_oid(fansid);
		tbNotice.setNtype(Tb_Notice.NTYPE_FOLLOW_YOU);
		tbNotice.setCreate_time(new Date());
		tbNotice.setReadflg(Tb_Notice.READFLG_N);
		tbNotice.setData(JsonUtil.toJson(map));
		this.tbNoticeService.createTb_Notice(tbNotice);
	}

	private void processAnswer(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long ansid = Long.valueOf(map.get(JsonKey.ANSWER_ID));
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return;
		}
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(tbAnswer.getAid());
		if (tbAsk == null) {
			return;
		}
		if (tbAnswer.getUserid() == tbAsk.getUserid()) {
			return;
		}
		map = new HashMap<String, String>();
		map.put(JsonKey.ANSWER_ID, String.valueOf(ansid));
		map.put(JsonKey.ANSWER_CONTENT, DataUtil.limitHtmlRow(tbAnswer
				.getContent(), 50));
		map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
		map.put(JsonKey.ASK_TITLE, tbAsk.getTitle());
		Tb_Notice tbNotice = new Tb_Notice();
		tbNotice.setUserid(tbAsk.getUserid());
		tbNotice.setRef_oid(tbAnswer.getAnsid());
		tbNotice.setNtype(Tb_Notice.NTYPE_CREATE_ANSWER);
		tbNotice.setCreate_time(new Date());
		tbNotice.setReadflg(Tb_Notice.READFLG_N);
		tbNotice.setData(JsonUtil.toJson(map));
		this.tbNoticeService.createTb_Notice(tbNotice);
	}

	private void processSelectBestAnswer(String body) {
		Map<String, String> map = JsonUtil.getMapFromJson(body);
		long ansid = Long.valueOf(map.get(JsonKey.ANSWER_ID));
		long aid = Long.valueOf(map.get(JsonKey.ASK_ID));
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return;
		}
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(aid);
		if (tbAsk == null) {
			return;
		}
		if (tbAnswer.getUserid() == tbAsk.getUserid()) {
			return;
		}
		Tb_User tbUser = this.tbUserService.getTb_User(tbAsk.getUserid());
		if (tbUser == null) {
			return;
		}
		map = new HashMap<String, String>();
		map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
		map.put(JsonKey.ASK_TITLE, tbAsk.getTitle());
		map.put(JsonKey.USERID, String.valueOf(tbAsk.getUserid()));
		map.put(JsonKey.USER_NICK, tbUser.getShow_nick());
		Tb_Notice tbNotice = new Tb_Notice();
		tbNotice.setUserid(tbAnswer.getUserid());
		tbNotice.setRef_oid(aid);
		tbNotice.setNtype(Tb_Notice.NTYPE_SELECT_BEST_ANSWER);
		tbNotice.setCreate_time(new Date());
		tbNotice.setReadflg(Tb_Notice.READFLG_N);
		tbNotice.setData(JsonUtil.toJson(map));
		this.tbNoticeService.createTb_Notice(tbNotice);
	}
}