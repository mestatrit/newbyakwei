package svrtest;

import com.hk.svr.CmpSmsPortService;

public class CmpSmsPortServiceTest extends HkServiceTest {
	private CmpSmsPortService cmpSmsPortService;

	public void setCmpSmsPortService(CmpSmsPortService cmpSmsPortService) {
		this.cmpSmsPortService = cmpSmsPortService;
	}

	public void testCraateCmpSmsPort() {
		int portLen = 4;
		int size = 5000;
		this.cmpSmsPortService.batchCreateCmpSmsPort(portLen, size);
		this.commit();
	}
}