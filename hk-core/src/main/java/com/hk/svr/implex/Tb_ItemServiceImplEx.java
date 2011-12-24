package com.hk.svr.implex;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Score;
import com.hk.bean.taobao.Tb_News;
import com.hk.frame.util.JsonUtil;
import com.hk.jms.HkMsgProducer;
import com.hk.jms.JmsMsg;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_Item_User_RefService;
import com.hk.svr.Tb_NewsService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.impl.Tb_ItemServiceImpl;

public class Tb_ItemServiceImplEx extends Tb_ItemServiceImpl {

	private Tb_ItemService tbItemService;

	public void setTbItemService(Tb_ItemService tbItemService) {
		this.tbItemService = tbItemService;
	}

	@Autowired
	private Tb_Item_CmtService tb_Item_CmtService;

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	@Autowired
	private Tb_NewsService tbNewsService;

	@Autowired
	private Tb_Item_User_RefService tbItemUserRefService;

	/**
	 * 保存用户打分，并更新商品总分与打分总人数
	 * 
	 * @param tbItemScore
	 *            2010-9-1
	 */
	@Override
	public void saveTb_Item_Score(Tb_Item_Score tbItemScore) {
		this.tbItemService.saveTb_Item_Score(tbItemScore);
		// 总分统计
		int sum_score = this.tbItemService
				.sumScoreFromTb_Item_ScoreByItemId(tbItemScore.getItemid());
		// 总人数统计
		int num_count = this.tbItemService
				.countTb_Item_ScoreByItemid(tbItemScore.getItemid());
		Tb_Item tbItem = this.tbItemService.getTb_Item(tbItemScore.getItemid());
		if (tbItem != null) {
			tbItem.setHkscore(sum_score);
			tbItem.setHkscore_num(num_count);
			this.tbItemService.updateTb_Item(tbItem);
		}
		this.tb_Item_CmtService.updateScoreByItemidAndUserid(tbItemScore
				.getItemid(), tbItemScore.getUserid(), tbItemScore.getScore());
	}

	@Override
	public void createTb_Item(Tb_Item tbItem) {
		this.tbItemService.createTb_Item(tbItem);
		tb_UserService.addItem_count(tbItem.getUserid(), 1);
		Map<String, String> map = new HashMap<String, String>();
		map.put(JsonKey.ITEMID, String.valueOf(tbItem.getItemid()));
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_NEWS_CREATE_ITEM);
		jmsMsg.setBody(JsonUtil.toJson(map));
		this.hkMsgProducer.send(jmsMsg.toMessage());
		map = new HashMap<String, String>();
		map.put(JsonKey.USERID, String.valueOf(tbItem.getUserid()));
		jmsMsg = new JmsMsg(JmsMsg.HEAD_USER_REPORT_ADDITEM, JsonUtil
				.toJson(map));
		this.hkMsgProducer.send(jmsMsg.toMessage());
	}

	@Override
	public void deleteTb_Item(long itemid) {
		Tb_Item tbItem = this.tbItemService.getTb_Item(itemid);
		this.tb_Item_CmtService.deleteTb_Item_CmtByItemid(itemid);
		this.tb_Item_CmtService.deleteTb_Item_Cmt_ReplyByItemid(itemid);
		this.tbItemService.deleteTb_Item_ImgByNum_iid(tbItem.getNum_iid());
		this.tbNewsService.deleteTb_NewsByNtypeAndOid(Tb_News.NTYPE_CREATEITEM,
				itemid);
		this.tbItemService.deleteTb_Item_ScoreByItemid(itemid);
		this.tbItemUserRefService.deleteTb_Item_User_RefByItemid(itemid);
		this.tbItemService.deleteTb_Item(itemid);
	}
}