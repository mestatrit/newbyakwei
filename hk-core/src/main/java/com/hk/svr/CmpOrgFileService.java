package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpOrgFile;

/**
 * 机构上传的相关图片逻辑操作
 * 
 * @author akwei
 */
public interface CmpOrgFileService {

	void createCmpOrgFile(CmpOrgFile cmpOrgFile);

	void updateCmpOrgFile(CmpOrgFile cmpOrgFile);

	void deleteCmpOrgFile(long companyId, long oid);

	CmpOrgFile getCmpOrgFile(long companyId, long oid);

	List<CmpOrgFile> getCmpOrgFileListByCompanyIdAndArticleOid(long companyId,
			long articleOid);
}