package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpActorSvrRef;
import com.hk.bean.CmpSvr;
import com.hk.bean.CmpSvrKind;

/**
 * 企业提供的服务，以及人员与服务的配置
 * 
 * @author akwei
 */
public interface CmpSvrService {

	void createCmpSvr(CmpSvr cmpSvr);

	void updateCmpSvr(CmpSvr cmpSvr);

	/**
	 * 相应关系数据也要删除
	 * 
	 * @param companyId
	 * @param svrId
	 *            2010-7-23
	 */
	void deleteCmpSvr(long companyId, long svrId);

	CmpSvr getCmpSvr(long companyId, long svrId);

	/**
	 * @param companyId
	 * @param name 为空时，不参与查询,不为空时，模糊搜索关键字
	 * @param begin
	 * @param size
	 * @return
	 *         2010-7-23
	 */
	List<CmpSvr> getCmpSvrListByCompanyId(long companyId, String name,
			int begin, int size);

	/**
	 * 如果已经存在关系数据，将不会再次创建
	 * 
	 * @param cmpActorSvrRef
	 *            2010-7-23
	 */
	void createCmpActorSvrRef(CmpActorSvrRef cmpActorSvrRef);

	void deleteCmpActorSvrRef(long companyId, long oid);

	CmpActorSvrRef getCmpActorSvrRef(long companyId, long oid);

	List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndActorId(
			long companyId, long actorId);

	Map<Long, CmpSvr> getCmpSvrMapInId(List<Long> idList);

	List<CmpSvr> getCmpSvrListInId(List<Long> idList);

	void createCmpSvrKind(CmpSvrKind cmpSvrKind);

	void updateCmpSvrKind(CmpSvrKind cmpSvrKind);

	void deleteCmpSvrKind(long companyId, long kindId);

	CmpSvrKind getCmpSvrKind(long companyId, long kindId);

	List<CmpSvrKind> getCmpSvrKindListByCompanyId(long companyId);

	List<CmpActorSvrRef> getCmpActorSvrRefListByCompanyIdAndSvrId(
			long companyId, long svrId, int begin, int size);
}