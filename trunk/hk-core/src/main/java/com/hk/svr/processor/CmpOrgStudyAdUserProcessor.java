package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpOrgStudyAdUser;
import com.hk.svr.CmpOrgStudyAdService;
import com.hk.svr.CmpOrgStudyAdUserService;

/**
 * @author akwei
 */
public class CmpOrgStudyAdUserProcessor {

	@Autowired
	private CmpOrgStudyAdUserService cmpOrgStudyAdUserService;

	@Autowired
	private CmpOrgStudyAdService cmpOrgStudyAdService;

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgId(
			long companyId, long orgId, boolean buildStudyAd, int begin,
			int size) {
		List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserService
				.getCmpOrgStudyAdUserListByCompanyIdAndOrgId(companyId, orgId,
						begin, size);
		if (buildStudyAd) {
			this.buildStudyAd(companyId, list);
		}
		return list;
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyIdAndOrgIdAndAdid(
			long companyId, long orgId, long adid, boolean buildStudyAd,
			int begin, int size) {
		List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserService
				.getCmpOrgStudyAdUserListByCompanyIdAndOrgIdAndAdid(companyId,
						orgId, adid, begin, size);
		if (buildStudyAd) {
			this.buildStudyAd(companyId, list);
		}
		return list;
	}

	public List<CmpOrgStudyAdUser> getCmpOrgStudyAdUserListByCompanyidAndUserId(
			long companyId, long userId, boolean buildAd, int begin, int size) {
		List<CmpOrgStudyAdUser> list = this.cmpOrgStudyAdUserService
				.getCmpOrgStudyAdUserListByCompanyidAndUserId(companyId,
						userId, begin, size);
		if (buildAd) {
			this.buildStudyAd(companyId, list);
		}
		return list;
	}

	private void buildStudyAd(long companyId, List<CmpOrgStudyAdUser> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpOrgStudyAdUser o : list) {
			idList.add(o.getAdid());
		}
		Map<Long, CmpOrgStudyAd> map = this.cmpOrgStudyAdService
				.getCmpOrgStudyAdMapByCompanyIdInId(companyId, idList);
		for (CmpOrgStudyAdUser o : list) {
			o.setCmpOrgStudyAd(map.get(o.getAdid()));
		}
	}
}