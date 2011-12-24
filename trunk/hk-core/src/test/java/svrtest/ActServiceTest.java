package svrtest;

import java.util.Calendar;
import java.util.Date;
import junit.framework.Assert;
import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ActUser;
import com.hk.bean.InfoSmsPort;
import com.hk.frame.util.DataUtil;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.act.exception.DuplicateActNameException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.NoAvailableActSysNumException;
import com.hk.svr.user.exception.NoSmsPortException;

public class ActServiceTest extends HkServiceTest {
	private ActService actService;

	private InfoSmsPortService infoSmsPortService;

	private ActSysNumService actSysNumService;

	public void setActSysNumService(ActSysNumService actSysNumService) {
		this.actSysNumService = actSysNumService;
	}

	public void setInfoSmsPortService(InfoSmsPortService infoSmsPortService) {
		this.infoSmsPortService = infoSmsPortService;
	}

	public void setActService(ActService actService) {
		this.actService = actService;
	}

	public void testCreateAct() {
		String name = "akweiwei1111111";
		long userId = 1;
		String addr = "ssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdga";
		String intro = "ssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdgsssssintro_dfsdgfsdga";
		Date beginTime = DataUtil.parseTime("200909241700", "yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime("200909271700", "yyyyMMddHHmm");
		byte needCheck = 0;
		Act o = new Act();
		o.setName(name);
		o.setUserId(userId);
		o.setAddr(addr);
		o.setIntro(intro);
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setNeedCheck(needCheck);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			Assert.fail(code + "");
		}
		try {
			this.actService.createAct(o);
			Assert.assertNotSame(0, o.getActId());
			ActUser actUser = this.actService.getActUser(o.getActId(), userId);
			Assert.assertEquals(o.getUserId(), actUser.getUserId());
			Assert.assertEquals(o.getActId(), actUser.getActId());
			Assert.assertNotSame(0, o.getPortId());
			InfoSmsPort infoSmsPort = this.infoSmsPortService.getInfoSmsPort(o
					.getPortId());
			ActSysNum actSysNum = this.actSysNumService.getActSysNum(o
					.getActSysNumId());
			Assert.assertEquals(o.getActSysNumId(), actSysNum.getSysId());
			Assert.assertEquals(infoSmsPort.getUsetype(),
					InfoSmsPort.USETYPE_ACT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(o.getEndTime());
			cal.add(Calendar.MONTH, 1);
			Assert.assertEquals(infoSmsPort.getOverTime().getTime(), cal
					.getTime().getTime());
		}
		catch (NoSmsPortException e) {
			Assert.fail(e.getMessage());
		}
		catch (DuplicateActNameException e) {
			Assert.fail(e.getMessage());
		}
		catch (NoAvailableActSysNumException e) {
			Assert.fail(e.getMessage());
		}
	}

	public void ttestCreateActUser() {
		long userId = 2;
		long actId = 12;
		this.actService.createActUser(actId, userId, ActUser.CHECKFLG_N);
		ActUser actUser = this.actService.getActUser(actId, userId);
		Assert.assertEquals(userId, actUser.getUserId());
		Assert.assertEquals(actId, actUser.getActId());
	}
}