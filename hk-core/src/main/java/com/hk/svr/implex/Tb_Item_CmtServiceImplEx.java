package com.hk.svr.implex;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Cmt_Reply;
import com.hk.bean.taobao.Tb_Item_Score;
import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.jms.HkMsgProducer;
import com.hk.jms.JmsMsg;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_Item_User_RefService;
import com.hk.svr.Tb_NewsService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.impl.Tb_Item_CmtServiceImpl;

public class Tb_Item_CmtServiceImplEx extends Tb_Item_CmtServiceImpl {

	private Tb_Item_CmtService tb_Item_CmtService;

	public void setTb_Item_CmtService(Tb_Item_CmtService tbItemCmtService) {
		tb_Item_CmtService = tbItemCmtService;
	}

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private Tb_ItemService tb_ItemService;

	@Autowired
	private Tb_Item_User_RefService tb_Item_User_RefService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	@Autowired
	private Tb_NewsService tbNewsService;

	/**
	 * 创建商品点评
	 * 
	 * @param tbItemCmt
	 * @param commend 是否也推荐按此商品。true:推荐,false:不推荐
	 * @param holdItem 是否设置拥有此商品 ture:设为拥有，false:不设为拥有
	 * @param create_to_sina_weibo 是否把评论信息发送到新浪微博
	 * @param serverName 当前访问的服务的域名
	 * @param contextPath 当前服务的contextPath
	 *            2010-9-3
	 */
	@Override
	public void createTb_Item_Cmt(Tb_Item_Cmt tbItemCmt, boolean commend,
			boolean holdItem, boolean create_to_sina_weibo, String serverName,
			String contextPath) {
		if (tbItemCmt.getScore() <= 0) {
			Tb_Item_Score tbItemScore = this.tb_ItemService
					.getTb_Item_ScoreByItemidAndUserid(tbItemCmt.getItemid(),
							tbItemCmt.getUserid());
			int score = 0;
			if (tbItemScore != null && tbItemScore.getScore() > 0) {
				score = tbItemScore.getScore();
			}
			tbItemCmt.setScore(score);
		}
		else {// 保存用户打分
			Tb_Item_Score tbItemScore = this.tb_ItemService
					.getTb_Item_ScoreByItemidAndUserid(tbItemCmt.getItemid(),
							tbItemCmt.getUserid());
			if (tbItemScore == null) {
				tbItemScore = new Tb_Item_Score();
			}
			tbItemScore.setUserid(tbItemCmt.getUserid());
			tbItemScore.setItemid(tbItemCmt.getItemid());
			tbItemScore.setScore(tbItemCmt.getScore());
			this.tb_ItemService.saveTb_Item_Score(tbItemScore);
		}
		this.tb_Item_CmtService.createTb_Item_Cmt(tbItemCmt, commend, holdItem,
				create_to_sina_weibo, serverName, contextPath);
		List<Tb_Item_User_Ref> reflist = this.tb_Item_User_RefService
				.getTb_Item_User_RefListByUseridAndItemid(
						tbItemCmt.getUserid(), tbItemCmt.getItemid());
		for (Tb_Item_User_Ref o : reflist) {
			if (o.isItemOwnner()) {
				this.tb_Item_User_RefService.updateCmtid(o.getOid(), tbItemCmt
						.getCmtid());
			}
			else if (o.isItemCmt()) {
				this.tb_Item_User_RefService.updateCmtid(o.getOid(), tbItemCmt
						.getCmtid());
			}
		}
		this.createTb_Item_User_Ref(Tb_Item_User_Ref.FLG_CMT, tbItemCmt);
		if (commend) {
			this.createTb_Item_User_Ref(Tb_Item_User_Ref.FLG_CMD, tbItemCmt);
		}
		if (holdItem) {
			this.createTb_Item_User_Ref(Tb_Item_User_Ref.FLG_HOLD, tbItemCmt);
			Tb_Item_User_Ref want_ItemUserRef = this.tb_Item_User_RefService
					.getTb_Item_User_RefByUseridAndItemidAndFlg(tbItemCmt
							.getUserid(), tbItemCmt.getItemid(),
							Tb_Item_User_Ref.FLG_WANT);
			if (want_ItemUserRef != null) {// 删除想买
				this.tb_Item_User_RefService
						.deleteTb_Item_User_Ref(want_ItemUserRef.getOid());
			}
		}
		this.processCmt_num(tbItemCmt, true);
		// 动态
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_NEWS_CREATE_ITEM_CMT);
		Map<String, String> map = new HashMap<String, String>();
		map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
		jmsMsg.setBody(JsonUtil.toJson(map));
		hkMsgProducer.send(jmsMsg.toMessage());
		if (create_to_sina_weibo) {
			// 同步评论信息到新浪微博
			map = new HashMap<String, String>();
			map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
			map.put(JsonKey.SERVER_NAME, serverName);
			map.put(JsonKey.CONTEXTPATH, contextPath);
			jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_OTHER_API_CREATE_ITEM_CMT_TO_STATUS);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
		map = new HashMap<String, String>();
		map.put(JsonKey.ITEM_CMTID, String.valueOf(tbItemCmt.getCmtid()));
		jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_NOTICE_CREATE_ITEM_CMT);
		jmsMsg.setBody(JsonUtil.toJson(map));
		hkMsgProducer.send(jmsMsg.toMessage());
	}

	private void processCmt_num(Tb_Item_Cmt tbItemCmt, boolean add) {
		if (add) {
			this.tb_ItemService.addCmt_num(tbItemCmt.getItemid(), 1);
		}
		else {
			this.tb_ItemService.addCmt_num(tbItemCmt.getItemid(), -1);
		}
	}

	private void createTb_Item_User_Ref(byte flg, Tb_Item_Cmt tbItemCmt) {
		Tb_Item_User_Ref tbItemUserRef_cmt = new Tb_Item_User_Ref();
		tbItemUserRef_cmt.setItemid(tbItemCmt.getItemid());
		tbItemUserRef_cmt.setFlg(flg);
		tbItemUserRef_cmt.setUserid(tbItemCmt.getUserid());
		if (tbItemUserRef_cmt.getFlg() == Tb_Item_User_Ref.FLG_CMT
				|| tbItemUserRef_cmt.getFlg() == Tb_Item_User_Ref.FLG_HOLD) {
			tbItemUserRef_cmt.setCmtid(tbItemCmt.getCmtid());
		}
		this.tb_Item_User_RefService.createTb_Item_User_Ref(tbItemUserRef_cmt);
	}

	@Override
	public void createTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply) {
		Tb_Item_Cmt tbItemCmt = this.tb_Item_CmtService
				.getTb_Item_Cmt(tbItemCmtReply.getCmtid());
		if (tbItemCmt != null) {
			tbItemCmtReply.setItemid(tbItemCmt.getItemid());
			this.tb_Item_CmtService.createTb_Item_Cmt_Reply(tbItemCmtReply);
			tbItemCmt.setReply_count(tbItemCmt.getReply_count() + 1);
			this.tb_Item_CmtService.updateTb_Item_Cmt(tbItemCmt);
			// 如果评论内容多于10字 就当做新的商品点评发出
			if (DataUtil.toTextRow(tbItemCmtReply.getContent()).length() >= 10) {
				Tb_Item_Cmt tbItemCmt2 = new Tb_Item_Cmt();
				tbItemCmt2.setItemid(tbItemCmt.getItemid());
				tbItemCmt2.setContent(tbItemCmtReply.getContent());
				tbItemCmt2.setCreate_time(new Date());
				tbItemCmt2.setUserid(tbItemCmtReply.getUserid());
				tbItemCmt2.setSid(tbItemCmt.getItemid());
				this.createTb_Item_Cmt(tbItemCmt2, false, false, false, null,
						null);
			}
			if (tbItemCmt.getUserid() != tbItemCmtReply.getUserid()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put(JsonKey.ITEM_CMT_REPLYID, String.valueOf(tbItemCmtReply
						.getReplyid()));
				JmsMsg jmsMsg = new JmsMsg(
						JmsMsg.HEAD_NOTICE_CREATE_ITEM_CMT_REPLY, JsonUtil
								.toJson(map));
				this.hkMsgProducer.send(jmsMsg.toMessage());
			}
		}
	}

	@Override
	public void deleteTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply) {
		this.tb_Item_CmtService.deleteTb_Item_Cmt_Reply(tbItemCmtReply);
		Tb_Item_Cmt tbItemCmt = this.tb_Item_CmtService
				.getTb_Item_Cmt(tbItemCmtReply.getCmtid());
		if (tbItemCmt != null) {
			tbItemCmt.setReply_count(tbItemCmt.getReply_count() - 1);
			if (tbItemCmt.getReply_count() < 0) {
				tbItemCmt.setReply_count(0);
			}
			this.tb_Item_CmtService.updateTb_Item_Cmt(tbItemCmt);
		}
	}

	@Override
	public List<Tb_Item_Cmt> getTb_Item_CmtListByItemid(long itemid,
			boolean buildUser, int begin, int size) {
		List<Tb_Item_Cmt> list = this.tb_Item_CmtService
				.getTb_Item_CmtListByItemid(itemid, buildUser, begin, size);
		if (buildUser) {
			this.buildUser(list);
		}
		return list;
	}

	@Override
	public List<Tb_Item_Cmt_Reply> getTb_Item_Cmt_ReplyListByCmtid(long cmtid,
			boolean buildUser, int begin, int size) {
		List<Tb_Item_Cmt_Reply> list = this.tb_Item_CmtService
				.getTb_Item_Cmt_ReplyListByCmtid(cmtid, buildUser, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Item_Cmt_Reply o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tb_UserService
					.getTb_UserMapInId(idList);
			for (Tb_Item_Cmt_Reply o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}

	/**
	 * 删除点评,删除点评的回复,更新用户商品关系表中相应的cmtid,把cmtid换成删除此评论之外的最新一条
	 * 
	 * @param tbItemCmt
	 *            2010-9-3
	 */
	@Override
	public void deleteTb_Item_Cmt(Tb_Item_Cmt tbItemCmt) {
		this.tb_Item_CmtService.deleteTb_Item_Cmt_ReplyByCmtid(tbItemCmt
				.getCmtid());
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefListByUseridAndItemidAndCmtid(tbItemCmt
						.getUserid(), tbItemCmt.getItemid(), tbItemCmt
						.getCmtid());
		long cmtid = 0;
		if (list.size() > 0) {
			List<Tb_Item_Cmt> cmtlist = this.tb_Item_CmtService
					.getTb_Item_CmtListByItemidAndUserid(tbItemCmt.getItemid(),
							tbItemCmt.getUserid(), 0, 2);
			for (Tb_Item_Cmt o : cmtlist) {
				if (o.getCmtid() != tbItemCmt.getCmtid()) {
					cmtid = o.getCmtid();
					break;
				}
			}
		}
		for (Tb_Item_User_Ref o : list) {
			this.tb_Item_User_RefService.updateCmtid(o.getOid(), cmtid);
		}
		this.tbNewsService.deleteTb_NewsByNtypeAndOid(
				Tb_News.NTYPE_CREATEITEM_CMT, cmtid);
		this.tb_Item_CmtService.deleteTb_Item_Cmt(tbItemCmt);
		this.processCmt_num(tbItemCmt, false);
	}

	@Override
	public List<Tb_Item_Cmt> getTb_Item_CmtListForNew(boolean buildUser,
			boolean buildItem, int begin, int size) {
		List<Tb_Item_Cmt> list = this.tb_Item_CmtService
				.getTb_Item_CmtListForNew(buildUser, buildItem, begin, size);
		if (buildUser) {
			this.buildUser(list);
		}
		if (buildItem) {
			this.buildItem(list);
		}
		return list;
	}

	private void buildItem(List<Tb_Item_Cmt> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Item_Cmt o : list) {
			idList.add(o.getItemid());
		}
		Map<Long, Tb_Item> map = this.tb_ItemService.getTb_ItemMapInId(idList);
		for (Tb_Item_Cmt o : list) {
			o.setTbItem(map.get(o.getItemid()));
		}
	}

	private void buildUser(List<Tb_Item_Cmt> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Item_Cmt o : list) {
			idList.add(o.getUserid());
		}
		Map<Long, Tb_User> map = this.tb_UserService.getTb_UserMapInId(idList);
		for (Tb_Item_Cmt o : list) {
			o.setTbUser(map.get(o.getUserid()));
		}
	}

	@Override
	public List<Tb_Item_Cmt> getTb_Item_CmtListBySid(long sid,
			boolean buildUser, boolean buildItem, int begin, int size) {
		List<Tb_Item_Cmt> list = this.tb_Item_CmtService
				.getTb_Item_CmtListBySid(sid, buildUser, buildItem, begin, size);
		if (buildUser) {
			this.buildUser(list);
		}
		if (buildItem) {
			this.buildItem(list);
		}
		return list;
	}
}