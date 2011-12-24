package com.hk.svr.processor;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgStudyAd;
import com.hk.bean.CmpOrgStudyAdContent;
import com.hk.bean.CmpStudyKind;
import com.hk.svr.CmpOrgStudyAdService;
import com.hk.svr.CmpStudyKindService;

public class CmpOrgStudyAdProcessor {

	@Autowired
	private CmpOrgStudyAdService cmpOrgStudyAdService;

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	public void createCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent) {
		this.buildKindInfo(cmpOrgStudyAd);
		this.cmpOrgStudyAdService.createCmpOrgStudyAd(cmpOrgStudyAd,
				cmpOrgStudyAdContent);
	}

	public void updateCmpOrgStudyAd(CmpOrgStudyAd cmpOrgStudyAd,
			CmpOrgStudyAdContent cmpOrgStudyAdContent) {
		this.buildKindInfo(cmpOrgStudyAd);
		this.cmpOrgStudyAdService.updateCmpOrgStudyAd(cmpOrgStudyAd,
				cmpOrgStudyAdContent);
	}

	private void buildKindInfo(CmpOrgStudyAd cmpOrgStudyAd) {
		long kindId = cmpOrgStudyAd.getKindId();
		long kindId1 = 0;
		long kindId2 = 0;
		long kindId3 = 0;
		CmpStudyKind cmpStudyKind = this.cmpStudyKindService.getCmpStudyKind(
				cmpOrgStudyAd.getCompanyId(), kindId);
		if (cmpStudyKind.getKlevel() == 1) {
			kindId1 = kindId;
		}
		else if (cmpStudyKind.getKlevel() == 2) {
			kindId2 = kindId;
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					cmpOrgStudyAd.getCompanyId(), cmpStudyKind.getParentId());
			if (parent != null) {
				kindId1 = parent.getKindId();
			}
		}
		else {
			kindId3 = kindId;
			CmpStudyKind parent = this.cmpStudyKindService.getCmpStudyKind(
					cmpOrgStudyAd.getCompanyId(), cmpStudyKind.getParentId());
			if (parent != null) {
				kindId2 = parent.getKindId();
				CmpStudyKind parent2 = this.cmpStudyKindService
						.getCmpStudyKind(cmpOrgStudyAd.getCompanyId(), parent
								.getParentId());
				if (parent2 != null) {
					kindId1 = parent2.getKindId();
				}
			}
		}
		cmpOrgStudyAd.setKindId(kindId1);
		cmpOrgStudyAd.setKindId2(kindId2);
		cmpOrgStudyAd.setKindId3(kindId3);
	}
}