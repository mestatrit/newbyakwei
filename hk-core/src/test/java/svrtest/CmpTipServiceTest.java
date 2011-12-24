package svrtest;

import com.hk.bean.CmpTip;
import com.hk.bean.CmpTipDel;
import com.hk.frame.util.P;
import com.hk.svr.CmpTipService;

public class CmpTipServiceTest extends HkServiceTest {

	private CmpTipService cmpTipService;

	public void setCmpTipService(CmpTipService cmpTipService) {
		this.cmpTipService = cmpTipService;
	}

	public void testA() {
		P.println("begin test ...");
	}

	public void testCreateCmpTip() {
		long userId = 3;
		long companyId = 1;
		for (int i = 0; i < 1000; i++) {
			CmpTip cmpTip = new CmpTip();
			cmpTip.setUserId(userId);
			cmpTip.setCompanyId(companyId);
			cmpTip.setContent("哈哈哈" + i);
			cmpTip.setDoneflg(CmpTip.DONEFLG_DONE);
			cmpTip.setPcityId(575);
			this.cmpTipService.createCmpTip(cmpTip);
		}
		this.commit();
	}

	public void ttestBombCmpTip() {
		long tipId = 124;
		CmpTip cmpTip = this.cmpTipService.getCmpTip(tipId);
		CmpTipDel cmpTipDel = new CmpTipDel(cmpTip);
		this.cmpTipService.bombCmpTip(cmpTipDel);
	}

	public void ttestRecoverCmpTip() {
		long tipId = 124;
		this.cmpTipService.recoverCmpTip(tipId);
	}
}