package web.epp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hk.frame.util.P;

/**
 * 页面进行时间显示排列的辅助类
 * 
 * @author akwei
 */
public class ReserveTimeVo {

	/**
	 * 显示的时间刻度(刻度30分钟间隔)
	 */
	private Date time;

	/**
	 * 小刻度的时间集合
	 */
	private List<GraduationTimeVo> list;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<GraduationTimeVo> getList() {
		return list;
	}

	public void setList(List<GraduationTimeVo> list) {
		this.list = list;
	}


	/**
	 * 初始化时间刻度以及是否可以预约，已近过期的时间不可以预约
	 * 
	 * @param beginTime 营业开始时间
	 * @param endTime 营业结束时间
	 * @return
	 *         2010-8-6
	 */
	public static List<ReserveTimeVo> initList(Date beginTime, Date endTime) {
		long beginlong = 0;
		long endlong = 0;
		if (beginTime != null && endTime != null) {
			beginlong = beginTime.getTime();
			endlong = endTime.getTime();
		}
		Calendar t = Calendar.getInstance();
		t.setTime(beginTime);
		t.set(Calendar.SECOND, 0);
		t.set(Calendar.MILLISECOND, 0);
		List<ReserveTimeVo> volist = new ArrayList<ReserveTimeVo>();
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		long endtime = end.getTimeInMillis();
		while (t.getTimeInMillis() < endtime) {
			ReserveTimeVo vo = new ReserveTimeVo();
			vo.setTime(t.getTime());
			List<GraduationTimeVo> graduationTimeVos = new ArrayList<GraduationTimeVo>();
			Calendar cur = Calendar.getInstance();
			cur.setTime(vo.getTime());
			for (int i = 0; i < 6; i++) {
				GraduationTimeVo graduationTimeVo = new GraduationTimeVo();
				graduationTimeVo.setBeginTime(cur.getTime());
				graduationTimeVo.setAmount(5);
				graduationTimeVo.makeEndTime();
				if (graduationTimeVo.isExceedCurrentTime()) {
					graduationTimeVo.setCanReserve(false);
				}
				else {
					graduationTimeVo.setCanReserve(true);
				}
				if (beginlong > 0 && endlong > 0) {
					// 与营业时间进行对比，刻度开始时间与结束时间都小于营业开始时间，不能进行预约
					if (graduationTimeVo.getBeginTime().getTime() < beginlong
							|| graduationTimeVo.getEndTime().getTime() < beginlong) {
						graduationTimeVo.setCanReserve(false);
					}
					// 与营业时间进行对比，刻度开始时间与结束时间大于营业结束时间，不能进行预约
					if (graduationTimeVo.getBeginTime().getTime() > endlong
							|| graduationTimeVo.getEndTime().getTime() > endlong) {
						graduationTimeVo.setCanReserve(false);
					}
				}
				graduationTimeVos.add(graduationTimeVo);
				cur.add(Calendar.MINUTE, 5);
			}
			vo.setList(graduationTimeVos);
			volist.add(vo);
			t.add(Calendar.MINUTE, 30);
		}
		return volist;
	}

	public static void main(String[] args) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 9);
		begin.set(Calendar.MINUTE, 30);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 22);
		end.set(Calendar.MINUTE, 30);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		List<ReserveTimeVo> list = ReserveTimeVo.initList(begin.getTime(), end
				.getTime());
		for (ReserveTimeVo vo : list) {
			P.println(vo.getTime());
			for (GraduationTimeVo graduationTimeVo : vo.getList()) {
				P.println("    " + graduationTimeVo.getBeginTime() + " | "
						+ graduationTimeVo.getEndTime() + " | "
						+ graduationTimeVo.isCanReserve());
			}
		}
	}
}