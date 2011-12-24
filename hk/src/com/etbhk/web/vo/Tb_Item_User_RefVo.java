package com.etbhk.web.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.frame.util.HkUtil;
import com.hk.svr.Tb_Item_User_RefService;

/**
 * 页面视图类
 * 
 * @author akwei
 */
public class Tb_Item_User_RefVo {

	private Tb_Item_User_Ref tbItemUserRef;

	/**
	 * 是否已经拥有
	 */
	private boolean has_hold;

	/**
	 * 是否想拥有
	 */
	private boolean has_want;

	public Tb_Item_User_Ref getTbItemUserRef() {
		return tbItemUserRef;
	}

	public void setTbItemUserRef(Tb_Item_User_Ref tbItemUserRef) {
		this.tbItemUserRef = tbItemUserRef;
	}

	public boolean isHas_hold() {
		return has_hold;
	}

	public void setHas_hold(boolean hasHold) {
		has_hold = hasHold;
	}

	public boolean isHas_want() {
		return has_want;
	}

	public void setHas_want(boolean hasWant) {
		has_want = hasWant;
	}

	public static List<Tb_Item_User_RefVo> creatVoList(
			List<Tb_Item_User_Ref> list, long current_login_userid) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Item_User_Ref o : list) {
			idList.add(o.getItemid());
		}
		Tb_Item_User_RefService tb_Item_User_RefService = (Tb_Item_User_RefService) HkUtil
				.getBean("tb_Item_User_RefService");
		List<Tb_Item_User_Ref> reflist = tb_Item_User_RefService
				.getTb_Item_User_RefListByUseridAndInItemid(
						current_login_userid, idList);
		Map<String, Tb_Item_User_Ref> map = new HashMap<String, Tb_Item_User_Ref>();
		for (Tb_Item_User_Ref o : reflist) {
			map.put(o.getItemid() + "_" + o.getFlg(), o);
		}
		List<Tb_Item_User_RefVo> volist = new ArrayList<Tb_Item_User_RefVo>();
		Tb_Item_User_RefVo vo = null;
		Tb_Item_User_Ref tmp = null;
		String key = null;
		for (Tb_Item_User_Ref o : list) {
			vo = new Tb_Item_User_RefVo();
			vo.setTbItemUserRef(o);
			key = o.getItemid() + "_" + Tb_Item_User_Ref.FLG_HOLD;
			tmp = map.get(key);
			if (tmp != null) {
				vo.setHas_hold(true);
			}
			key = o.getItemid() + "_" + Tb_Item_User_Ref.FLG_WANT;
			tmp = map.get(key);
			if (tmp != null) {
				vo.setHas_want(true);
			}
			volist.add(vo);
		}
		return volist;
	}
}