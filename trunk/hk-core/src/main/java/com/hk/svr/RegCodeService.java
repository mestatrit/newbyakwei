package com.hk.svr;

import java.util.List;
import com.hk.bean.RegCode;
import com.hk.svr.user.exception.RegCodeNameDuplicateException;

public interface RegCodeService {
	void createRegCode(RegCode regCode) throws RegCodeNameDuplicateException;

	RegCode getRegCode(long codeId);

	RegCode getRegCodeByUserId(long userId);

	List<RegCode> getRegCodeList(int begin, int size);

	int countNoUseRegCode();

	RegCode getRegCodeByName(String name);
}