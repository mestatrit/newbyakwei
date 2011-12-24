package svrtest;

import com.hk.bean.HkObjKeyTagOrder;
import com.hk.bean.KeyTag;
import com.hk.svr.HkObjKeyTagOrderService;
import com.hk.svr.KeyTagService;
import com.hk.svr.company.exception.NoEnoughMoneyException;
import com.hk.svr.company.exception.SmallerThanMinMoneyException;
import com.hk.svr.pub.Err;

public class KeyTagServiceTest extends HkServiceTest {
	KeyTagService keyTagService;

	HkObjKeyTagOrderService hkObjKeyTagOrderService;

	public void setHkObjKeyTagOrderService(
			HkObjKeyTagOrderService hkObjKeyTagOrderService) {
		this.hkObjKeyTagOrderService = hkObjKeyTagOrderService;
	}

	public void setKeyTagService(KeyTagService keyTagService) {
		this.keyTagService = keyTagService;
	}

	public void ttest_searchKeyTag() {
		String name = "川菜";
		KeyTag o = this.keyTagService.getKeyTagByName(name);
		long tagId = 0;
		if (o == null) {
			tagId = this.keyTagService.createKeyTag(name);
		}
		else {
			tagId = o.getTagId();
		}
		this.hkObjKeyTagOrderService.setKeyTagSearchCountInfo(tagId);
		this.commit();
	}

	public void ttest_updateHkObjKeyTagOrder() {
		HkObjKeyTagOrder hkObjKeyTagOrder = this.hkObjKeyTagOrderService
				.getHkObjKeyTagOrder(2);
		hkObjKeyTagOrder.setMoney(120);
		hkObjKeyTagOrder.setPday(40);
		int code = hkObjKeyTagOrder.validate();
		if (code != Err.SUCCESS) {
			fail("errorcode [ " + code + " ]");
		}
		try {
			this.hkObjKeyTagOrderService
					.updateHkObjKeyTagOrder(hkObjKeyTagOrder);
		}
		catch (NoEnoughMoneyException e) {
			fail(e.getMessage());
		}
		catch (SmallerThanMinMoneyException e) {
			fail(e.getMessage());
		}
	}

	public void ttest_createHkObjKeyTagOrder() {
		String name = "java";
		long tagId = this.keyTagService.createKeyTag(name);
		HkObjKeyTagOrder hkObjKeyTagOrder = new HkObjKeyTagOrder();
		hkObjKeyTagOrder.setCityId(0);
		hkObjKeyTagOrder.setMoney(100);
		hkObjKeyTagOrder.setHkObjId(1);
		hkObjKeyTagOrder.setPday(70);
		hkObjKeyTagOrder.setStopflg(HkObjKeyTagOrder.STOPFLG_N);
		hkObjKeyTagOrder.setTagId(tagId);
		int code = hkObjKeyTagOrder.validate();
		if (code != Err.SUCCESS) {
			fail("errorcode [ " + code + " ]");
		}
		try {
			this.hkObjKeyTagOrderService
					.createHkObjKeyTagOrder(hkObjKeyTagOrder);
		}
		catch (NoEnoughMoneyException e) {
			fail(e.getMessage());
		}
		catch (SmallerThanMinMoneyException e) {
			fail(e.getMessage());
		}
	}

	public void ttestCreateKeyTag() {
		String name = "java";
		long id = this.keyTagService.createKeyTag(name);
		long id2 = this.keyTagService.createKeyTag(name);
		if (id != id2) {
			fail("id [ " + id + " ] ::  id2 [ " + id2 + " ]");
		}
	}
}