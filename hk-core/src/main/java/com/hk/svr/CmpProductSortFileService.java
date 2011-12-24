package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpProductSortFile;

public interface CmpProductSortFileService {

	/**
	 * 创建图片，目前只支持2张图片
	 * 
	 * @param cmpProductSortFile
	 *            2010-6-17
	 */
	void createCmpProductSortFile(CmpProductSortFile cmpProductSortFile);

	void updateCmpProductSortFile(CmpProductSortFile cmpProductSortFile);

	void deleteCmpProductSortFile(long oid);

	List<CmpProductSortFile> getCmpProductSortFileListByCompanyIdAndSortId(
			long companyId, int sortId, int begin, int size);

	List<CmpProductSortFile> getCmpProductSortFileListByCompanyId(long companyId);

	CmpProductSortFile getCmpProductSortFile(long oid);
}