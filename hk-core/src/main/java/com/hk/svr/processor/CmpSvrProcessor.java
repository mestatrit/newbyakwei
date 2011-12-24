package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorRole;
import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpSvrService;

public class CmpSvrProcessor {

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CmpActorService cmpActorService;

	public List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndActorId(
			long companyId, long actorId, boolean buildCmpSvr) {
		List<CmpActorSvrRef> list = this.cmpSvrService
				.getCmpActorSvrRefListByCompanyIdAndActorId(companyId, actorId);
		if (buildCmpSvr) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorSvrRef o : list) {
				idList.add(o.getSvrId());
			}
			Map<Long, CmpSvr> map = this.cmpSvrService.getCmpSvrMapInId(idList);
			for (CmpActorSvrRef o : list) {
				o.setCmpSvr(map.get(o.getSvrId()));
			}
		}
		return list;
	}

	public List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndSvrId(
			long companyId, long svrId, boolean buildActor, boolean buildRole,
			int begin, int size) {
		List<CmpActorSvrRef> list = this.cmpSvrService
				.getCmpActorSvrRefListByCompanyIdAndSvrId(companyId, svrId,
						begin, size);
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorSvrRef o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpActorSvrRef o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		if (buildRole) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorSvrRef o : list) {
				if (o.getCmpActor() != null) {
					idList.add(o.getCmpActor().getRoleId());
				}
			}
			Map<Long, CmpActorRole> map = this.cmpActorService
					.getCmpActorRoleMapByCompanyIdAndInId(idList);
			for (CmpActorSvrRef o : list) {
				if (o.getCmpActor() != null) {
					o.getCmpActor().setCmpActorRole(
							map.get(o.getCmpActor().getRoleId()));
				}
			}
		}
		return list;
	}
}