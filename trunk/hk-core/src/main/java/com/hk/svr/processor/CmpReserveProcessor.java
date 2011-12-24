package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorReport;
import com.hk.bean.CmpReserve;
import com.hk.bean.CmpSvr;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpReserveService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CompanyService;

public class CmpReserveProcessor {

	@Autowired
	private CmpReserveService cmpReserveService;

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpSvrService cmpSvrService;

	@Autowired
	private CompanyService companyService;

	public List<CmpReserve> getCmpReserveListByCompanyIdEx(long companyId,
			long actorId, byte reserveStatus, String username, String mobile,
			Date beginTime, Date endTime, boolean buildActor, boolean buildSvr,
			int begin, int size) {
		List<CmpReserve> list = this.cmpReserveService
				.getCmpReserveListByCompanyIdEx(companyId, actorId,
						reserveStatus, username, mobile, beginTime, endTime,
						begin, size);
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpReserve o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpReserve o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		if (buildSvr) {
			this.buildSvr(list);
		}
		return list;
	}

	public List<CmpReserve> getCmpReserveListByCompanyIdAndUserId(
			long companyId, long userId, boolean buildActor, boolean buildSvr,
			int begin, int size) {
		List<CmpReserve> list = this.cmpReserveService
				.getCmpReserveListByCompanyIdAndUserId(companyId, userId,
						begin, size);
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpReserve o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpReserve o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		if (buildSvr) {
			this.buildSvr(list);
		}
		return list;
	}

	private void buildSvr(List<CmpReserve> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpReserve o : list) {
			if (!DataUtil.isEmpty(o.getSvrdata())) {
				String[] ids = o.getSvrdata().split(",");
				for (String id : ids) {
					idList.add(Long.valueOf(id));
				}
			}
		}
		Map<Long, CmpSvr> map = this.cmpSvrService.getCmpSvrMapInId(idList);
		for (CmpReserve o : list) {
			if (!DataUtil.isEmpty(o.getSvrdata())) {
				String[] ids = o.getSvrdata().split(",");
				List<CmpSvr> svrlist = new ArrayList<CmpSvr>();
				for (String id : ids) {
					CmpSvr svr = map.get(Long.valueOf(id));
					if (svr != null) {
						svrlist.add(svr);
					}
				}
				o.setCmpSvrList(svrlist);
			}
		}
	}

	/**
	 * @param userId
	 * @param reserveStatus
	 * @param unuse 是否是作废预约,true:作废,false:不作废。当此参数为ture时，忽略reserveStatus参数
	 * @param buildCompany
	 * @param buildActor
	 * @param buildSvr
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-19
	 */
	public List<CmpReserve> getCmpReserveListByUserIdAndReserveStatus(
			long userId, byte reserveStatus, boolean unuse,
			boolean buildCompany, boolean buildActor, boolean buildSvr,
			int begin, int size) {
		List<CmpReserve> list = null;
		if (unuse) {
			list = this.cmpReserveService.getCmpReserveListByUserIdForUnuse(
					userId, begin, size);
		}
		else {
			list = this.cmpReserveService
					.getCmpReserveListByUserIdAndReserveStatus(userId,
							reserveStatus, begin, size);
		}
		if (buildCompany) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpReserve o : list) {
				idList.add(o.getCompanyId());
			}
			Map<Long, Company> map = this.companyService
					.getCompanyMapInId(idList);
			for (CmpReserve o : list) {
				o.setCompany(map.get(o.getCompanyId()));
			}
		}
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpReserve o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpReserve o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		if (buildSvr) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpReserve o : list) {
				if (!DataUtil.isEmpty(o.getSvrdata())) {
					String[] ids = o.getSvrdata().split(",");
					for (String id : ids) {
						idList.add(Long.valueOf(id));
					}
				}
			}
			Map<Long, CmpSvr> map = this.cmpSvrService.getCmpSvrMapInId(idList);
			for (CmpReserve o : list) {
				if (!DataUtil.isEmpty(o.getSvrdata())) {
					String[] ids = o.getSvrdata().split(",");
					List<CmpSvr> svrlist = new ArrayList<CmpSvr>();
					for (String id : ids) {
						CmpSvr svr = map.get(Long.valueOf(id));
						if (svr != null) {
							svrlist.add(svr);
						}
					}
					o.setCmpSvrList(svrlist);
				}
			}
		}
		return list;
	}

	public List<CmpActorReport> getCmpActorReportListByCompanyId(
			long companyId, Date beginTime, Date endTime, boolean buildActor) {
		List<CmpActorReport> list = this.cmpReserveService
				.getCmpActorReportListByCompanyId(companyId, beginTime, endTime);
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorReport o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpActorReport o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		return list;
	}
}