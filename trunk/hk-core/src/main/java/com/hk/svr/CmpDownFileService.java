package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpDownFile;

public interface CmpDownFileService {

	void createCmpDownFile(CmpDownFile cmpDownFile);

	void updateCmpDownFile(CmpDownFile cmpDownFile);

	void deleteCmpDownFile(long oid);

	/**
	 * 最新上传在最前面，可按照id倒排
	 * 
	 * @param companyId
	 * @param cmpNavOid
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-22
	 */
	List<CmpDownFile> getCmpDownFileListByCompanyIdAndCmpNavOid(long companyId,
			long cmpNavOid, int begin, int size);

	CmpDownFile getCmpDownFile(long oid);

	void addCmpDownFileDcount(long oid, int add);
}