package svrtest;

import java.util.List;

import junit.framework.Assert;

import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.frame.util.P;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.Err;

public class CompanyServiceTest extends HkServiceTest {

	private CompanyService companyService;

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void ttestiniCmpZoneInfo() {
		this.companyService.initCmpZoneInfo();
		this.commit();
	}

	public void ttestIndex() {
		this.companyService.createSearchIndex();
	}

	public void ttestsearchCmp() {
		List<Long> idList = this.companyService
				.getCompanyIdListWithSearchNotPcityId(143, "情", 0, 10);
		List<Company> list = this.companyService.getCompanyListInId(idList,
				null);
		for (Company o : list) {
			P.println(o.getCompanyId() + " | " + o.getName() + " | "
					+ o.getPcityId());
		}
	}

	public void ttestCreateBatchCompany() {
		for (int i = 0; i < 1000; i++) {
			Company o = new Company();
			o.setName("批量测试使用的足迹kkk" + i);
			o.setPcityId(575);
			o.setCreaterId(1);
			o.setKindId(1);
			int code = o.validate(true);
			if (code != Err.SUCCESS) {
				Assert.fail("validate fail error code [ " + code + " ]");
			}
			this.companyService.createCompany(o, "127.0.0.1");
		}
	}

	public void ttestdeleteCompany() {
		// this.companyService.deleteCompany(9);
	}

	// public void ttestGetListNeayBy() {
	// long companyId = 1;
	// Company company = this.companyService.getCompany(companyId);
	// List<Company> list = this.companyService.getCompanyListNearBy(
	// companyId, company.getParentKindId(), company.getCityId(),
	// company.getProvinceId(), company.getMarkerX(), company
	// .getMarkerY(), 0, 5);
	// for (Company o : list) {
	// P.println(o.getName());
	// }
	// }
	public void ttestCreateCompany() {
		Company o = new Company();
		o.setName("麦当劳2");
		o.setCreaterId(1);
		// o.setUserId(-1);
		o.setKindId(1);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		this.companyService.createCompany(o, "127.0.0.1");
	}

//	public void ttestUpdateLogo() throws IOException {
//		long companyId = 1;
//		File file = new File("d:/test/test4.png");
//		this.companyService.updateLogo(companyId, file);
//	}

	public void ttestCreateAuthCompany() {
		this.companyService.getAuthCompanyList(null, (byte) -1, 0, 5);
	}

	public void ttestUpdateCompany() {
		long companyId = 1;
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			Assert.fail("no company [ " + companyId + " ]");
			return;
		}
		o.setPcityId(2);
		// o.setName("麦当劳");
		// o.setCreaterId(1);
		// o.setKindId(1);
		// o.setUserId(-1);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		this.companyService.updateCompany(o);
	}

	public void ttestUpdateCompanyReview() {
		long reviewId = 5;
		CompanyReview o = this.companyService.getCompanyReview(reviewId);
		if (o == null) {
			Assert.fail("no CompanyReview [ " + reviewId + " ]");
			return;
		}
		o.setCompanyId(1);
		o.setUserId(1);
		o.setScore(1);
		o.setContent("啊哈哈");
		int code = o.validate();
		if (code != Err.SUCCESS) {
			Assert.fail("validate fail error code [ " + code + " ]");
		}
		this.companyService.updateCompanyReview(o);
		this.commit();
	}
}