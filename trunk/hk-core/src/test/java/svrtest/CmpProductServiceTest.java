package svrtest;

import junit.framework.Assert;

import com.hk.bean.CmpProductReview;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductUserStatus;
import com.hk.bean.Laba;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.CmpProductService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;

public class CmpProductServiceTest extends HkServiceTest {

	private CmpProductService cmpProductService;

	private LabaService labaService;

	public void setLabaService(LabaService labaService) {
		this.labaService = labaService;
	}

	public void setCmpProductService(CmpProductService cmpProductService) {
		this.cmpProductService = cmpProductService;
	}

	public void testCreateCmpProductSort() {
		long companyId = 1;
		CmpProductSort sort_0 = new CmpProductSort();
		sort_0.setCompanyId(companyId);
		sort_0.setName("sort_0");
		this.cmpProductService.createCmpProductSort(sort_0);
		P.println(sort_0.getParentData());
		// 应该没有子分类
		assertEquals(CmpProductSort.CHILDFLG_N, sort_0.getChildflg());
		CmpProductSort sort_1 = new CmpProductSort();
		sort_1.setCompanyId(companyId);
		sort_1.setName("sort_1");
		this.cmpProductService.createCmpProductSort(sort_1);
		P.println(sort_1.getParentData());
		// 应该没有子分类
		assertEquals(CmpProductSort.CHILDFLG_N, sort_1.getChildflg());
		CmpProductSort child_0 = new CmpProductSort();
		child_0.setCompanyId(companyId);
		child_0.setName("child_0");
		child_0.setParentId(sort_0.getSortId());
		this.cmpProductService.createCmpProductSort(child_0);
		P.println(child_0.getParentData());
		// 父分类的判断
		sort_0 = this.cmpProductService.getCmpProductSort(sort_0.getSortId());
		assertEquals(CmpProductSort.CHILDFLG_Y, sort_0.getChildflg());
		assertEquals(CmpProductSort.CHILDFLG_N, child_0.getChildflg());
		CmpProductSort child_1 = new CmpProductSort();
		child_1.setCompanyId(companyId);
		child_1.setName("child_1");
		child_1.setParentId(sort_0.getSortId());
		this.cmpProductService.createCmpProductSort(child_1);
		P.println(child_1.getParentData());
		// 父分类的判断
		sort_0 = this.cmpProductService.getCmpProductSort(sort_0.getSortId());
		assertEquals(CmpProductSort.CHILDFLG_Y, sort_0.getChildflg());
		assertEquals(CmpProductSort.CHILDFLG_N, child_1.getChildflg());
	}

	public void ttestCreateCmpProductUserStatus() {
		long userId = 1;
		int productId = 3;
		byte status = CmpProductUserStatus.USERSTATUS_WANT;
		this.cmpProductService.createCmpProductUserStatus(userId, productId,
				status);
		// this.commit();
	}

	public void ttestCreateReview() {
		String content = "测试产品点评";
		long userId = 1;
		LabaInPutParser parser = new LabaInPutParser("http://u.huoku.com");
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userId);
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		content = DataUtil.toHtmlRow(content);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		long labaId = this.labaService.createLaba(labaInfo);
		int productId = 3;
		CmpProductReview o = new CmpProductReview();
		o.setUserId(userId);
		o.setContent(content);
		o.setScore(3);
		o.setProductId(productId);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setLongContent(labaInfo.getLongParsedContent());
		this.cmpProductService.createReview(o);
	}
}