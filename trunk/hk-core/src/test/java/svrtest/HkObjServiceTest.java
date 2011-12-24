package svrtest;

import com.hk.bean.HkObjOrder;
import com.hk.bean.HkObjOrderDef;
import com.hk.svr.HkObjOrderService;
import com.hk.svr.HkObjService;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;
import com.hk.svr.pub.Err;

public class HkObjServiceTest extends HkServiceTest {
	HkObjService hkObjService;

	HkObjOrderService hkObjOrderService;

	public void setHkObjService(HkObjService hkObjService) {
		this.hkObjService = hkObjService;
	}

	/**
	 * 足迹宝贝充值,并设置竞排数据竞排数据的酷币不能高于充值余额
	 */
	public void ttestAddHkbFroHkObj() {
		long hkObjId = 1;
		HkObjOrder hkObjOrder = new HkObjOrder();
		hkObjOrder.setHkObjId(hkObjId);
		hkObjOrder.setCityId(0);
		hkObjOrder.setMoney(100);
		hkObjOrder.setPday(10);
		hkObjOrder.setStopflg(HkObjOrder.STOPFLG_N);
		hkObjOrder.setKind(HkObjOrderDef.KIND_HOMEPAGE);
		int code = hkObjOrder.validate();
		if (code != Err.SUCCESS) {
			fail("code : [ " + code + " ]");
		}
		try {
			this.hkObjOrderService.createHkObjOrder(hkObjOrder);
			this.commit();
		}
		catch (NoEnoughMoneyException e) {
			fail(e.getMessage());
		}
		catch (SmallerThanMinMoneyException e) {
			fail(e.getMessage());
		}
	}

	public void testUpdateHkObjOrder() {
		HkObjOrder hkObjOrder = this.hkObjOrderService.getHkObjOrder(3);
		hkObjOrder.setPday(9);
		hkObjOrder.setMoney(100);
		try {
			boolean stat = this.hkObjOrderService.updateHkObjOrder(hkObjOrder);
			if (!stat) {
				fail("already update");
			}
			this.commit();
		}
		catch (NoEnoughMoneyException e) {
			fail(e.getMessage());
		}
		catch (SmallerThanMinMoneyException e) {
			fail(e.getMessage());
		}
	}

	public void ttestCalculater() {
		long oid = 3;
		for (int i = 0; i < 5; i++) {
			HkObjOrder hkObjOrder = this.hkObjOrderService.getHkObjOrder(oid);
			this.hkObjOrderService.calculate(hkObjOrder);
		}
		this.commit();
	}
}