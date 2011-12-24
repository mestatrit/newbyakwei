package com.hk.svr;

import java.util.List;
import com.hk.bean.CmpBulletin;

public interface CmpBulletinService {
	void cretaeCmpBulletin(CmpBulletin cmpBulletin);

	void deleteCmpBulletin(int bulletinId);

	CmpBulletin getCmpBulletin(int bulletinId);

	void updateCmpBulletin(CmpBulletin cmpBulletin);

	List<CmpBulletin> getCmpBulletinList(long companyId, int begin, int size);
}