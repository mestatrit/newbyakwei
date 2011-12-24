package svrtest;

import junit.framework.Assert;
import com.hk.bean.RegCode;
import com.hk.svr.RegCodeService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.RegCodeNameDuplicateException;

public class RegCodeServiceTest extends HkServiceTest {
	private RegCodeService regCodeService;

	public void setRegCodeService(RegCodeService regCodeService) {
		this.regCodeService = regCodeService;
	}

	public void testCreate() {
		String name = "asdas";
		RegCode regCode = new RegCode();
		regCode.setObjId(0);
		regCode.setObjType((byte) 0);
		regCode.setName(name);
		int code = regCode.validate();
		if (code != Err.SUCCESS) {
			Assert.fail(code + "");
		}
		try {
			this.regCodeService.createRegCode(regCode);
			RegCode o = this.regCodeService.getRegCode(regCode.getCodeId());
			Assert.assertEquals(regCode.getCodeId(), o.getCodeId());
			Assert.assertEquals(regCode.getName(), o.getName());
			Assert.assertEquals(regCode.getObjId(), o.getObjId());
			Assert.assertEquals(regCode.getObjType(), o.getObjType());
		}
		catch (RegCodeNameDuplicateException e) {
			Assert.fail(e.getMessage());
		}
	}
}