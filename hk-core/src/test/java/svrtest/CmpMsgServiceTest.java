package svrtest;

import java.util.Date;

import com.hk.bean.CmpMsg;
import com.hk.svr.CmpMsgService;

public class CmpMsgServiceTest extends HkServiceTest {

	private CmpMsgService cmpMsgService;

	public void setCmpMsgService(CmpMsgService cmpMsgService) {
		this.cmpMsgService = cmpMsgService;
	}

	public void testCreateCmpMsg() {
		long companyId = 1;
		String name = "akwei";
		String tel = "19388309494";
		String content = "sdjflsdosdlsdf本报独家报道上海拟对住房保有环节征税，达到类似物业税的调控效果。上海市住房保障和房屋管理局发言人未否认这一消息，并回应称“在房产保有阶段征税，将由国家层面定，地方应贯彻执行好";
		for (int i = 0; i < 50; i++) {
			CmpMsg cmpMsg = new CmpMsg();
			cmpMsg.setName(name);
			cmpMsg.setTel(tel);
			cmpMsg.setContent(content);
			cmpMsg.setCompanyId(companyId);
			cmpMsg.setCreateTime(new Date());
			this.cmpMsgService.createCmpMsg(cmpMsg);
		}
		this.commit();
	}
}