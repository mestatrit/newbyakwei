package svr;

import iwant.bean.PptTimeline;
import iwant.bean.enumtype.ReadFlagType;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dev3g.cactus.util.DateUtil;

public class PptTimelineSvrTest extends BaseSvrTest {

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

	@Test
	public void createPptTimeline() throws PptNotFoundException,
			UserNotFoundException {
		// Assert.fail();
		PptTimeline dbobj = this.pptTimelineSvr.getPptTimelineByUseridAndPptid(
				this.user0.getUserid(), this.ppt0.getPptid());
		PptTimeline pptTimeline = new PptTimeline();
		pptTimeline.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		pptTimeline.setPptid(this.ppt0.getPptid());
		pptTimeline.setRead_flag(ReadFlagType.NOTREAD.getValue());
		pptTimeline.setReadtime(DateUtil.createNoMillisecondTime(new Date()));
		pptTimeline.setUserid(this.user0.getUserid());
		boolean result = this.pptTimelineSvr.createPptTimeline(pptTimeline);
		if (dbobj == null) {
			Assert.assertEquals(true, result);
		}
		else {
			Assert.assertEquals(false, result);
		}
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
	// @Test
	// public void getPptTimelineListByUserid() {
	// List<PptTimeline> list = this.pptTimelineSvr
	// .getPptTimelineListByUserid(this.pptTimeline0.getUserid(), 0,
	// -1, false, false);
	// Assert.assertEquals(2, list.size());
	// list = this.pptTimelineSvr.getPptTimelineListByUserid(this.pptTimeline0
	// .getUserid(), 0, 1, false, false);
	// Assert.assertEquals(1, list.size());
	// list = this.pptTimelineSvr.getPptTimelineListByUserid(this.pptTimeline0
	// .getUserid(), 0, -1, false, false);
	// Assert.assertEquals(2, list.size());
	// this.assertData(this.pptTimeline1, list.get(0));
	// this.assertData(this.pptTimeline0, list.get(1));
	// }
}
