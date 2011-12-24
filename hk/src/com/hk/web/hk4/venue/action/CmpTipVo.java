package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.CmpTip;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.bean.UserCmpTip;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpTipService;
import com.hk.svr.UserService;

public class CmpTipVo {
	private CmpTip cmpTip;

	private boolean isAddToDo;

	private boolean isAddDone;

	public CmpTipVo(CmpTip cmpTip) {
		this.cmpTip = cmpTip;
	}

	public CmpTip getCmpTip() {
		return cmpTip;
	}

	public boolean isAddToDo() {
		return isAddToDo;
	}

	public boolean isAddDone() {
		return isAddDone;
	}

	public void setAddDone(boolean isAddDone) {
		this.isAddDone = isAddDone;
	}

	public void setAddToDo(boolean isAddToDo) {
		this.isAddToDo = isAddToDo;
	}

	public static CmpTipVo createVo(CmpTip cmpTip, long loginUserId) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		CmpTipVo vo = new CmpTipVo(cmpTip);
		cmpTip.setUser(userService.getUser(cmpTip.getUserId()));
		if (loginUserId > 0) {
			UserCmpTip userCmpTip = cmpTipService
					.getUserCmpTipByUserIdAndTipId(loginUserId, cmpTip
							.getTipId());
			if (userCmpTip != null) {
				if (userCmpTip.isDone()) {
					vo.setAddDone(true);
				}
				else if (userCmpTip.isToDo()) {
					vo.setAddToDo(true);
				}
			}
		}
		return vo;
	}

	public static List<CmpTipVo> createVoList2(List<UserCmpTip> list,
			long loginUserId, boolean showUser) {
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		List<CmpTipVo> volist = new ArrayList<CmpTipVo>();
		Map<String, String> datamap = null;
		List<Long> idList = new ArrayList<Long>();
		for (UserCmpTip userCmpTip : list) {
			idList.add(userCmpTip.getTipId());
		}
		Map<Long, CmpTip> cmpTipMap = cmpTipService.getCmpTipMapInId(idList);
		for (UserCmpTip userCmpTip : list) {
			datamap = DataUtil.getMapFromJson(userCmpTip.getData());
			CmpTip cmpTip = cmpTipMap.get(userCmpTip.getTipId());
			Company company = new Company();
			company.setCompanyId(cmpTip.getCompanyId());
			company.setName(datamap.get("cmpname"));
			cmpTip.setCompany(company);
			CmpTipVo vo = new CmpTipVo(cmpTip);
			volist.add(vo);
		}
		build(volist, loginUserId, showUser);
		return volist;
	}

	public static List<CmpTipVo> createVoList(List<CmpTip> list,
			long loginUserId, boolean showUser) {
		List<CmpTipVo> volist = new ArrayList<CmpTipVo>();
		List<Long> idList = new ArrayList<Long>();
		for (CmpTip o : list) {
			CmpTipVo vo = new CmpTipVo(o);
			volist.add(vo);
			idList.add(o.getUserId());
		}
		build(volist, loginUserId, showUser);
		return volist;
	}

	private static void build(List<CmpTipVo> volist, long loginUserId,
			boolean showUser) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		List<Long> idList = null;
		if (showUser) {
			idList = new ArrayList<Long>();
			for (CmpTipVo vo : volist) {
				idList.add(vo.getCmpTip().getUserId());
			}
			Map<Long, User> map = userService.getUserMapInId(idList);
			for (CmpTipVo vo : volist) {
				vo.getCmpTip().setUser(map.get(vo.getCmpTip().getUserId()));
			}
		}
		if (loginUserId > 0) {
			idList = new ArrayList<Long>();
			for (CmpTipVo vo : volist) {
				idList.add(vo.getCmpTip().getTipId());
			}
			Map<Long, UserCmpTip> map2 = cmpTipService
					.getUserCmpTipMapByUserIdInTipId(loginUserId, idList);
			for (CmpTipVo vo : volist) {
				UserCmpTip userCmpTip = map2.get(vo.getCmpTip().getTipId());
				if (userCmpTip != null) {
					if (userCmpTip.isDone()) {
						vo.setAddDone(true);
					}
					else if (userCmpTip.isToDo()) {
						vo.setAddToDo(true);
					}
				}
			}
		}
	}
}