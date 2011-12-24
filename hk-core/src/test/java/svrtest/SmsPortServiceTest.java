package svrtest;

import java.text.DecimalFormat;
import java.util.Date;
import com.hk.bean.InfoSmsPort;
import com.hk.svr.InfoSmsPortService;

public class SmsPortServiceTest extends HkServiceTest {
	private InfoSmsPortService smsPortService;

	public void setSmsPortService(InfoSmsPortService smsPortService) {
		this.smsPortService = smsPortService;
	}

	public void ttestCreateSmsPort() {
		Date d = new Date();
		DecimalFormat df = new DecimalFormat("000000");
		for (int i = 1; i <= 999999; i++) {
			InfoSmsPort o = new InfoSmsPort();
			o.setPortNumber(df.format(i));
			o.setOverTime(d);
			this.smsPortService.createSmsPort(o);
		}
		// this.commit();
		System.out.println("============= end ========");
	}
}