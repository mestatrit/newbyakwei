package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmpSellNet;
import com.hk.bean.CmpSellNetKind;

/**
 * 企业销售网络数据逻辑
 * 
 * @author akwei
 */
public interface CmpSellNetService {

	void createCmpSellNet(CmpSellNet cmpSellNet);

	void updateCmpSellNet(CmpSellNet cmpSellNet);

	void deleteCmpSellNet(long oid);

	CmpSellNet getCmpSellNet(long oid);

	List<CmpSellNet> getCmpSellNetListByCompanyId(long companyId, int begin,
			int size);

	List<CmpSellNet> getCmpSellNetListByCompanyId(long companyId, long kindId,
			int begin, int size);

	void updateCmpSellNetOrderflg(long oid, int orderflg);

	void createCmpSellNetKind(CmpSellNetKind cmpSellNetKind);

	void updateCmpSellNetKind(CmpSellNetKind cmpSellNetKind);

	void deleteCmpSellNetKind(long kindId);

	List<CmpSellNetKind> getCmpSellNetKindListByCompanyId(long companyId);

	CmpSellNetKind getCmpSellNetKind(long kindId);

	Map<Long, CmpSellNetKind> getCmpSellNetKindMapInId(List<Long> idList);

	List<CmpSellNet> getCmpSellNetListByCompanyIdEx(long companyId,
			String name, long kindId, int begin, int size);
}