package com.hk.svr;

import java.util.List;

import com.hk.bean.CmpFile;

/**
 * 企业上传的相关文件操作
 * 
 * @author akwei
 */
public interface CmpFileService {

	void createCmpFile(CmpFile cmpFile);

	void updateCmpFile(CmpFile cmpFile);

	void deleteCmpFile(long oid);

	CmpFile getCmpFile(long oid);

	List<CmpFile> getCmpFileListByCompanyIdAndArticleOid(long companyId,
			long articleOid);
}