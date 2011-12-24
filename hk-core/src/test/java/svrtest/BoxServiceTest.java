package svrtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.TmpBox;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.BoxService;
import com.hk.svr.box.validate.BoxValidate;

public class BoxServiceTest extends HkServiceTest {

	private BoxService boxService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

	public void setBoxService(BoxService boxService) {
		this.boxService = boxService;
	}

	public void testCountOpenBox() {
		int boxcount = this.boxService.countCanOpenBox();
		P.println(boxcount);
	}

	public void ttestEditbox() throws Exception {
		long boxId = 19;
		long userId = 1;
		String name = "box11update";
		String intro = null;
		int totalCount = 2;
		String boxKey = null;
		String begint = "20090709174000";
		String endt = "20090809174000";
		int precount = 0;
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = 0;
		byte opentype = 0;
		Date beginTime = null;
		Date endTime = null;
		beginTime = sdf.parse(begint);
		endTime = sdf.parse(endt);
		Box box = this.boxService.getBox(boxId);
		box.setName(DataUtil.toHtmlRow(name));
		box.setTotalCount(totalCount);
		box.setBoxKey(DataUtil.toHtmlRow(boxKey));
		box.setBeginTime(beginTime);
		box.setEndTime(endTime);
		box.setUserId(userId);
		box.setPrecount(precount);
		box.setPretype(pretype);
		box.setIntro(DataUtil.toHtml(intro));
		box.setOpentype(opentype);
		BoxValidate.validateEditBox(box);
		this.boxService.updateBox(box);
	}

	public void ttestCreateTmpBox() throws Exception {
		String name = "akwei宝箱";
		int totalCount = 1000;
		String boxKey = "想开就开";
		String begint = "200907080900";
		String endt = "200907081000";
		Date beginTime = null;
		Date endTime = null;
		long userId = 1;
		try {
			beginTime = sdf.parse(begint);
		}
		catch (ParseException e) {//
		}
		try {
			endTime = sdf.parse(endt);
		}
		catch (ParseException e) {//
		}
		TmpBox box = new TmpBox();
		box.setName(name);
		box.setTotalCount(totalCount);
		box.setBoxKey(boxKey);
		box.setBeginTime(beginTime);
		box.setEndTime(endTime);
		box.setUserId(userId);
		BoxValidate.validateCreateTmpBox(box);
		box.setName(DataUtil.toHtmlRow(box.getName()));
		this.boxService.createTmpBox(box);
		// this.commit();
	}

	public void ttestCreateTmpBoxPrize() throws Exception {
		String name = "手机";
		String tip = "诺基亚";
		int pcount = 0;
		byte signal = BoxPrize.SIGNAL_N;
		long boxId = 6;
		BoxPrize prize = new BoxPrize();
		prize.setBoxId(boxId);
		prize.setName(name);
		prize.setTip(tip);
		prize.setPcount(pcount);
		prize.setSignal(signal);
		this.boxService.createBoxPrize(prize);
	}
}