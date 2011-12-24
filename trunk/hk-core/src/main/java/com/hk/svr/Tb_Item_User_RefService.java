package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Item_User_Ref;

public interface Tb_Item_User_RefService {

	/**
	 * 如果itemid,userid,flg有相同的数据，就不在创建
	 * 
	 * @param tbItemUserRef
	 *            2010-9-19
	 */
	boolean createTb_Item_User_Ref(Tb_Item_User_Ref tbItemUserRef);

	void updateCmtid(long oid, long cmtid);

	void deleteTb_Item_User_Ref(long oid);

	void deleteTb_Item_User_RefByItemid(long itemid);

	Tb_Item_User_Ref getTb_Item_User_Ref(long oid);

	Tb_Item_User_Ref getTb_Item_User_RefByUseridAndItemidAndFlg(long userid,
			long itemid, byte flg);

	List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndItemid(
			long userid, long itemid);

	List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndInItemid(
			long userid, List<Long> idList);

	List<Tb_Item_User_Ref> getTb_Item_User_RefListByUseridAndItemidAndCmtid(
			long userid, long itemid, long cmtid);

	List<Tb_Item_User_Ref> getTb_Item_User_RefByUseridAndFlg(long userid,
			byte flg, boolean buildItem, boolean buildCmt, int begin, int size);

	List<Tb_Item_User_Ref> getTb_Item_User_RefByItemidAndFlg(long itemid,
			byte flg, boolean buildUser, int begin, int size);

	int countTb_Item_User_RefByuseridAndFlg(long userid, byte flg);
}