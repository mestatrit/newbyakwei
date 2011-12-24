package svrtest;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpUnionReq;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.svr.pub.Err;

public class CmpUnionServiceTest extends HkServiceTest {
	private CmpUnionService cmpUnionService;

	public void setCmpUnionService(CmpUnionService cmpUnionService) {
		this.cmpUnionService = cmpUnionService;
	}

	private CmpUnionMessageService cmpUnionMessageService;

	public void setCmpUnionMessageService(
			CmpUnionMessageService cmpUnionMessageService) {
		this.cmpUnionMessageService = cmpUnionMessageService;
	}

	public void testPrepare() {
	}

	public void ttestCreateCmpUnion() {
		CmpUnion cmpUnion = new CmpUnion();
		cmpUnion.setAddr("addr");
		cmpUnion.setDomain("huoku.com");
		cmpUnion.setWebName("huoku");
		cmpUnion.setIntro(null);
		cmpUnion.setTraffic(null);
		cmpUnion.setName("union1");
		int result = this.cmpUnionService.createCmpUnion(cmpUnion);
		Assert.assertEquals(result, 0);
		CmpUnion cmpUnion2 = new CmpUnion();
		cmpUnion2.setAddr("addr");
		cmpUnion2.setDomain("huoku1.com");
		cmpUnion2.setWebName("huoku");
		cmpUnion2.setIntro(null);
		cmpUnion2.setTraffic(null);
		cmpUnion2.setName("union1");
		result = this.cmpUnionService.createCmpUnion(cmpUnion2);
		Assert.assertEquals(result, Err.CMPUNION_WEBNAME_DUPLICATE);
		CmpUnion cmpUnion3 = new CmpUnion();
		cmpUnion3.setAddr("addr");
		cmpUnion3.setDomain("huoku.com");
		cmpUnion3.setWebName("huoku2");
		cmpUnion3.setIntro(null);
		cmpUnion3.setTraffic(null);
		cmpUnion3.setName("union1");
		result = this.cmpUnionService.createCmpUnion(cmpUnion3);
		Assert.assertEquals(result, Err.CMPUNION_DOMAIN_DUPLICATE);
	}

	public void ttestCreateAndDeleteCmpUnionKind() {
		// 创建分类11
		CmpUnionKind cmpUnionKind = new CmpUnionKind();
		cmpUnionKind.setUid(1);
		cmpUnionKind.setKindLevel(0);
		cmpUnionKind.setName("分类11");
		boolean result = this.cmpUnionService.createCmpUnionKind(cmpUnionKind);
		Assert.assertEquals(result, true);
		// 创建分类11
		CmpUnionKind cmpUnionKind2 = new CmpUnionKind();
		cmpUnionKind2.setUid(1);
		cmpUnionKind2.setKindLevel(0);
		cmpUnionKind2.setName("分类11");
		result = this.cmpUnionService.createCmpUnionKind(cmpUnionKind2);
		// 应该失败
		Assert.assertEquals(result, false);
		// 创建分类12
		CmpUnionKind cmpUnionKind3 = new CmpUnionKind();
		cmpUnionKind3.setUid(1);
		cmpUnionKind3.setKindLevel(1);
		cmpUnionKind3.setName("分类12");
		cmpUnionKind3.setParentId(cmpUnionKind.getKindId());
		result = this.cmpUnionService.createCmpUnionKind(cmpUnionKind3);
		// 应该成功
		Assert.assertEquals(result, true);
		cmpUnionKind = this.cmpUnionService.getCmpUnionKind(cmpUnionKind3
				.getParentId());
		// 父分类应该标志为有子分类
		Assert.assertEquals(cmpUnionKind.isHasChild(), true);
		// 删除父分类，应该失败，由于没有删除子分类
		result = this.cmpUnionService.deleteCmpUnionKind(cmpUnionKind
				.getKindId());
		Assert.assertEquals(result, false);
		// 删除分类12，应该成功
		result = this.cmpUnionService.deleteCmpUnionKind(cmpUnionKind3
				.getKindId());
		Assert.assertEquals(result, true);
		// 再次删除分类11，应该成功
		result = this.cmpUnionService.deleteCmpUnionKind(cmpUnionKind
				.getKindId());
		Assert.assertEquals(result, true);
	}

	public void testCreateReq() {
		CmpUnionReq cmpUnionReq = new CmpUnionReq();
		cmpUnionReq.setUid(3);
		cmpUnionReq.setReqflg(CmpUnionMessageUtil.REQ_JOIN_IN_CMPUNION);
		cmpUnionReq.setDealflg(CmpUnionReq.DEALFLG_N);
		cmpUnionReq.setObjId(1);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "情有独钟咖啡厅");
		String data = DataUtil.toJson(map);
		cmpUnionReq.setData(data);
		this.cmpUnionMessageService.createCmpUnionReq(cmpUnionReq);
		this.commit();
	}
}