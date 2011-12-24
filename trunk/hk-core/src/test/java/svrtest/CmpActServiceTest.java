package svrtest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import com.hk.bean.CmdData;
import com.hk.bean.CmpAct;
import com.hk.bean.CmpActCost;
import com.hk.bean.CmpActStepCost;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmdDataService;
import com.hk.svr.CmpActService;
import com.hk.svr.pub.Err;

public class CmpActServiceTest extends HkServiceTest {
	private CmpActService cmpActService;

	private CmdDataService cmdDataService;

	public void setCmdDataService(CmdDataService cmdDataService) {
		this.cmdDataService = cmdDataService;
	}

	public void setCmpActService(CmpActService cmpActService) {
		this.cmpActService = cmpActService;
	}

	public void testCreate() {
		long companyId = 1;
		long uid = 1;
		String name = "活动1";
		String intro = "活动介绍";
		String spintro = "活动注意事项";
		String addr = "活动地址";
		String actKey = "活动关键字";
		byte userNeedCheckflg = CmpAct.USERNEEDCHECKFLG_Y;
		double actCost = 90;
		int pcityId = 1;
		int userLimitCount = 100;
		long kindId = 1;
		String bd = "2010-02-09";
		String bt = "8:00";
		String ed = "2010-02-09";
		String et = "20:00";
		Date beginTime = DataUtil.parseTime(bd + " " + bt, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(ed + " " + et, "yyyy-MM-dd HH:mm");
		CmpAct cmpAct = new CmpAct();
		cmpAct.setCompanyId(companyId);
		cmpAct.setUid(uid);
		cmpAct.setActCost(actCost);
		cmpAct.setName(DataUtil.toHtmlRow(name));
		cmpAct.setIntro(DataUtil.toHtml(intro));
		cmpAct.setSpintro(DataUtil.toHtml(spintro));
		cmpAct.setActKey(DataUtil.toHtmlRow(actKey));
		cmpAct.setAddr(DataUtil.toHtml(addr));
		cmpAct.setUserNeedCheckflg(userNeedCheckflg);
		cmpAct.setMemberLimitflg(CmpAct.MEMBERLIMITFLG_N);
		cmpAct.setPcityId(pcityId);
		cmpAct.setKindId(kindId);
		cmpAct.setBeginTime(beginTime);
		cmpAct.setEndTime(endTime);
		cmpAct.setUserLimitCount(userLimitCount);
		String[] cmpActCost_name = { "第一期", "第二期" };
		Number[] cmpActCost_cost = { 40, 50 };
		String[] cmpActCost_intro = { "第一期说明", "第二期说明" };
		Number[] cmpActSetp_userCount = { 100, 200 };
		Number[] cmpActSetp_cost = { 1000, 700 };
		List<CmpActCost> cmpActCostList = new ArrayList<CmpActCost>();
		for (int i = 0; i < cmpActCost_name.length; i++) {
			CmpActCost cost = new CmpActCost();
			cost.setActCost(cmpActCost_cost[i].doubleValue());
			cost.setName(DataUtil.toHtmlRow(cmpActCost_name[i]));
			cost.setIntro(DataUtil.toHtml(cmpActCost_intro[i]));
			cmpActCostList.add(cost);
		}
		List<CmpActStepCost> cmpActStepCostList = new ArrayList<CmpActStepCost>();
		for (int i = 0; i < cmpActSetp_userCount.length; i++) {
			CmpActStepCost cost = new CmpActStepCost();
			cost.setActCost(cmpActSetp_cost[i].doubleValue());
			cost.setUserCount(cmpActSetp_userCount[i].intValue());
			cmpActStepCostList.add(cost);
		}
		int code = cmpAct.validate();
		if (code != Err.SUCCESS) {
			Assert.fail(String.valueOf(code));
		}
		CmdData cmdData = new CmdData();
		cmdData.setEndTime(endTime);
		cmdData.setName(DataUtil.toHtmlRow(actKey));
		cmdData.setEndflg(CmdData.ENDFLG_Y);
		cmdData.setOtype(CmdData.OTYPE_CMPACT);
		cmdData.setOid(cmpAct.getActId());
		code = cmdData.validate();
		if (code != Err.SUCCESS) {
			Assert.fail(String.valueOf(code));
		}
		if (!this.cmdDataService.createCmdData(cmdData)) {
			Assert.fail(String.valueOf(Err.CMDDATA_NAME_DUPLICATE));
		}
		this.cmpActService.createCmpAct(cmpAct, cmpActCostList,
				cmpActStepCostList);
		this.commit();
	}
}