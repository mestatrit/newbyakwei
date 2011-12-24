package com.hk.svr;

import java.util.List;
import com.hk.bean.CmpComment;

public interface CmpCommentService {
	void createCmpComment(CmpComment cmpComment);

	void deleteCmpComment(long companyId, long cmtId);

	List<CmpComment> getCmpCommentList(long companyId, int begin, int size);

	CmpComment getCmpComment(long companyId, long cmtId);

	int countCmpComment(long companyId);
}