package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpVideo;

public interface CmpVideoService {

	void createCmpVideo(CmpVideo cmpVideo);

	void updateCmpVideo(CmpVideo cmpVideo);

	void deleteCmpVideo(long oid);

	CmpVideo getCmpVideo(long oid);

	List<CmpVideo> getCmpVideoListByCompanyIdAndCmpNavOid(long companyId,
			long cmpNavOid, int begin, int size);

	/**
	 * 获得某个id附近的数据
	 * 
	 * @param companyId
	 * @param cmpNavOid
	 * @param range >0时，获取id后面的数据,<0时,获取id前面的数据
	 * @param size
	 * @return
	 *         2010-6-23
	 */
	List<CmpVideo> getCmpVideoListByCompanyIdAndCmpNavOidForRange(
			long companyId, long cmpNavOid, long oid, int range, int size);
}