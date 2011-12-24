package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_Cmt_Reply;

public interface Tb_Item_CmtService {

	void createTb_Item_Cmt(Tb_Item_Cmt tbItemCmt, boolean commend,
			boolean holdItem, boolean create_to_sina_weibo, String serverName,
			String contextPath);

	void updateTb_Item_Cmt(Tb_Item_Cmt tbItemCmt);

	void deleteTb_Item_Cmt(Tb_Item_Cmt tbItemCmt);

	void deleteTb_Item_CmtByItemid(long itemid);

	void deleteTb_Item_Cmt_ReplyByItemid(long itemid);

	Tb_Item_Cmt getTb_Item_Cmt(long cmtid);

	List<Tb_Item_Cmt> getTb_Item_CmtListByItemid(long itemid,
			boolean buildUser, int begin, int size);

	List<Tb_Item_Cmt> getTb_Item_CmtListByItemidAndUserid(long itemid,
			long userid, int begin, int size);

	void createTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply);

	void deleteTb_Item_Cmt_Reply(Tb_Item_Cmt_Reply tbItemCmtReply);

	void deleteTb_Item_Cmt_ReplyByCmtid(long cmtid);

	Tb_Item_Cmt_Reply getTb_Item_Cmt_Reply(long replyid);

	List<Tb_Item_Cmt_Reply> getTb_Item_Cmt_ReplyListByCmtid(long cmtid,
			boolean buildUser, int begin, int size);

	void updateScoreByItemidAndUserid(long itemid, long userid, int score);

	int countTb_Item_CmtByUseridAndItemid(long userid, long itemid);

	Map<Long, Tb_Item_Cmt> getTb_Item_CmtMapInId(List<Long> idList);

	List<Tb_Item_Cmt> getTb_Item_CmtListForNew(boolean buildUser,
			boolean buildItem, int begin, int size);

	List<Tb_Item_Cmt> getTb_Item_CmtListBySid(long sid, boolean buildUser,
			boolean buildItem, int begin, int size);
}