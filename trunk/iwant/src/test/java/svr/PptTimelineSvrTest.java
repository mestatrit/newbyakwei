package svr;

import iwant.bean.PptTimeline;
import iwant.bean.enumtype.ReadFlagType;
import iwant.svr.PptTimelineSvr;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hk.frame.util.DataUtil;

public class PptTimelineSvrTest extends BaseSvrTest {

	@Resource
	private PptTimelineSvr pptTimelineSvr;

	private PptTimeline pptTimeline0;

	private PptTimeline pptTimeline1;

	private void assertData(PptTimeline expected, PptTimeline actual) {
		Assert.assertEquals(expected.getSysid(), actual.getSysid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getPptid(), actual.getPptid());
		Assert.assertEquals(expected.getUserid(), actual.getUserid());
		Assert.assertEquals(expected.getCatid(), actual.getCatid());
		Assert.assertEquals(expected.getRead_flag(), actual.getRead_flag());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
		Assert.assertEquals(expected.getReadtime().getTime(), actual
				.getReadtime().getTime());
		Assert.assertNull(actual.getPpt());
		Assert.assertNull(expected.getPpt());
	}

	@Before
	public void init() {
		// data 0
		this.pptTimeline0 = new PptTimeline();
		this.pptTimeline0.setCatid(5);
		this.pptTimeline0.setCreatetime(DataUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline0.setPptid(9);
		this.pptTimeline0.setProjectid(10);
		this.pptTimeline0.setRead_flag(ReadFlagType.NOTREAD.getValue());
		this.pptTimeline0.setReadtime(DataUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline0.setUserid(2);
		this.pptTimelineSvr.createPptTimeline(this.pptTimeline0);
		// data 1
		this.pptTimeline1 = new PptTimeline();
		this.pptTimeline1.setCatid(8);
		this.pptTimeline1.setCreatetime(DataUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline1.setPptid(10);
		this.pptTimeline1.setProjectid(11);
		this.pptTimeline1.setRead_flag(ReadFlagType.NOTREAD.getValue());
		this.pptTimeline1.setReadtime(DataUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline1.setUserid(2);
		this.pptTimelineSvr.createPptTimeline(this.pptTimeline1);
	}

	@Test
	public void createPptTimeline() {
		// Assert.fail();
		PptTimeline pptTimeline = new PptTimeline();
		pptTimeline.setCatid(59);
		pptTimeline.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		pptTimeline.setPptid(9);
		pptTimeline.setProjectid(1000);
		pptTimeline.setRead_flag(ReadFlagType.NOTREAD.getValue());
		pptTimeline.setReadtime(DataUtil.createNoMillisecondTime(new Date()));
		pptTimeline.setUserid(2);
		boolean result = this.pptTimelineSvr.createPptTimeline(pptTimeline);
		Assert.assertEquals(false, result);
		pptTimeline.setPptid(9876);
		result = this.pptTimelineSvr.createPptTimeline(pptTimeline);
		Assert.assertEquals(true, result);
	}

	@Test
	public void getPptTimelineByUseridAndPptid() {
		PptTimeline pptTimeline = this.pptTimelineSvr
				.getPptTimelineByUseridAndPptid(this.pptTimeline0.getUserid(),
						this.pptTimeline0.getPptid());
		this.assertData(this.pptTimeline0, pptTimeline);
	}

	@Test
	public void updateForReaded() {
		this.pptTimelineSvr.updateForReaded(this.pptTimeline0.getUserid(),
				this.pptTimeline0.getPptid());
		PptTimeline pptTimeline = this.pptTimelineSvr
				.getPptTimelineByUseridAndPptid(this.pptTimeline0.getUserid(),
						this.pptTimeline0.getPptid());
		Assert.assertEquals(ReadFlagType.READED.getValue(), pptTimeline
				.getRead_flag());
		Assert.assertNotSame(pptTimeline.getRead_flag(), this.pptTimeline0
				.getRead_flag());
	}

	@Test
	public void getPptTimelineListByUserid() {
		List<PptTimeline> list = this.pptTimelineSvr
				.getPptTimelineListByUserid(this.pptTimeline0.getUserid(), 0,
						-1, false, false);
		Assert.assertEquals(2, list.size());
		list = this.pptTimelineSvr.getPptTimelineListByUserid(this.pptTimeline0
				.getUserid(), 0, 1, false, false);
		Assert.assertEquals(1, list.size());
		list = this.pptTimelineSvr.getPptTimelineListByUserid(this.pptTimeline0
				.getUserid(), 0, -1, false, false);
		Assert.assertEquals(2, list.size());
		this.assertData(this.pptTimeline1, list.get(0));
		this.assertData(this.pptTimeline0, list.get(1));
	}
}
