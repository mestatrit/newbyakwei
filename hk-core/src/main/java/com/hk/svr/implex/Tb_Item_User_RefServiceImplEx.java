package com.hk.svr.implex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cmt;
import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.bean.taobao.Tb_User;
import com.hk.svr.Tb_ItemService;
import com.hk.svr.Tb_Item_CmtService;
import com.hk.svr.Tb_Item_User_RefService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.impl.Tb_Item_User_RefServiceImpl;

public class Tb_Item_User_RefServiceImplEx extends Tb_Item_User_RefServiceImpl {

	private Tb_Item_User_RefService tb_Item_User_RefService;

	@Autowired
	private Tb_ItemService tb_ItemService;

	@Autowired
	private Tb_Item_CmtService tb_Item_CmtService;

	@Autowired
	private Tb_UserService tb_UserService;

	public void setTb_Item_User_RefService(
			Tb_Item_User_RefService tbItemUserRefService) {
		tb_Item_User_RefService = tbItemUserRefService;
	}

	@Override
	public boolean createTb_Item_User_Ref(Tb_Item_User_Ref tbItemUserRef) {
		boolean result = tb_Item_User_RefService
				.createTb_Item_User_Ref(tbItemUserRef);
		if (result) {
			int count = this.tb_Item_User_RefService
					.countTb_Item_User_RefByuseridAndFlg(tbItemUserRef
							.getUserid(), tbItemUserRef.getFlg());
			if (tbItemUserRef.getFlg() == Tb_Item_User_Ref.FLG_CMT) {
				this.tb_UserService.updateItem_cmt_countByUserid(tbItemUserRef
						.getUserid(), count);
			}
			else if (tbItemUserRef.getFlg() == Tb_Item_User_Ref.FLG_HOLD) {
				this.tb_UserService.updateItem_hold_countByUserid(tbItemUserRef
						.getUserid(), count);
			}
			else if (tbItemUserRef.getFlg() == Tb_Item_User_Ref.FLG_WANT) {
				this.tb_UserService.updateItem_want_countByUserid(tbItemUserRef
						.getUserid(), count);
			}
		}
		return result;
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefByUseridAndFlg(
			long userid, byte flg, boolean buildItem, boolean buildCmt,
			int begin, int size) {
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByUseridAndFlg(userid, flg, buildItem,
						buildCmt, begin, size);
		if (buildItem) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Item_User_Ref o : list) {
				idList.add(o.getItemid());
			}
			Map<Long, Tb_Item> map = this.tb_ItemService
					.getTb_ItemMapInId(idList);
			List<Tb_Item_User_Ref> dellist = new ArrayList<Tb_Item_User_Ref>();
			for (Tb_Item_User_Ref o : list) {
				o.setTbItem(map.get(o.getItemid()));
				if (o.getTbItem() == null) {
					dellist.add(o);
				}
			}
			for (Tb_Item_User_Ref o : dellist) {
				this.tb_Item_User_RefService.deleteTb_Item_User_Ref(o.getOid());
				list.remove(o);
			}
		}
		if (buildCmt) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Item_User_Ref o : list) {
				idList.add(o.getCmtid());
			}
			Map<Long, Tb_Item_Cmt> map = this.tb_Item_CmtService
					.getTb_Item_CmtMapInId(idList);
			for (Tb_Item_User_Ref o : list) {
				o.setTbItemCmt(map.get(o.getCmtid()));
			}
		}
		return list;
	}

	@Override
	public List<Tb_Item_User_Ref> getTb_Item_User_RefByItemidAndFlg(
			long itemid, byte flg, boolean buildUser, int begin, int size) {
		List<Tb_Item_User_Ref> list = this.tb_Item_User_RefService
				.getTb_Item_User_RefByItemidAndFlg(itemid, flg, buildUser,
						begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (Tb_Item_User_Ref o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, Tb_User> map = this.tb_UserService
					.getTb_UserMapInId(idList);
			for (Tb_Item_User_Ref o : list) {
				o.setTbUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}
