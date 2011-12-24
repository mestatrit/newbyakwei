package com.hk.svr.box.validate;

import java.util.List;
import com.hk.bean.Box;
import com.hk.bean.BoxPrize;
import com.hk.bean.PreBox;
import com.hk.bean.TmpBox;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.box.exception.BeginTimeException;
import com.hk.svr.box.exception.BoxIntroOutOfLengthException;
import com.hk.svr.box.exception.BoxKeyDataException;
import com.hk.svr.box.exception.EndTimeException;
import com.hk.svr.box.exception.NameDataException;
import com.hk.svr.box.exception.PrizeCountException;
import com.hk.svr.box.exception.PrizeNameDataException;
import com.hk.svr.box.exception.PrizeTipDataException;
import com.hk.svr.box.exception.TimeException;
import com.hk.svr.box.exception.TotalCountException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.UserNotExistException;

public class BoxValidate implements Err {

	private BoxValidate() {//
	}

	public static void validateCreateTmpBox(TmpBox box)
			throws NameDataException, TotalCountException, BeginTimeException,
			EndTimeException, BoxKeyDataException, TimeException,
			UserNotExistException {
		if (box.getUserId() == 0) {
			throw new UserNotExistException("userid [ " + box.getUserId()
					+ " ] is not exist");
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (userService.getUser(box.getUserId()) == null) {
			throw new UserNotExistException("userid [ " + box.getUserId()
					+ " ] is not exist");
		}
		if (DataUtil.isEmpty(box.getName())
				|| DataUtil.toText(box.getName()).length() > 15) {
			throw new NameDataException("box name [ " + box.getName()
					+ " ] data error");
		}
		if (box.getTotalCount() <= 0 || box.getTotalCount() > 10000) {
			throw new TotalCountException("box totalCount [ "
					+ box.getTotalCount() + " ] data error");
		}
		long now = System.currentTimeMillis();
		if (box.getBeginTime() == null || box.getBeginTime().getTime() < now) {
			throw new BeginTimeException("box beginTime data error");
		}
		if (box.getEndTime() == null || box.getEndTime().getTime() < now) {
			throw new EndTimeException("box endTime data error");
		}
		if (box.getBeginTime().getTime() >= box.getEndTime().getTime()) {
			throw new TimeException("beginTime >= endTime");
		}
		if (!DataUtil.isEmpty(box.getBoxKey())) {
			if (DataUtil.toText(box.getBoxKey()).length() > 6
					|| !DataUtil.isNumberOrCharOrChinese(box.getBoxKey())) {
				throw new BoxKeyDataException("boxKey [ " + box.getBoxKey()
						+ " ] data error");
			}
		}
	}

	public static void validateUpdatePreBox(PreBox box)
			throws NameDataException, TotalCountException, BeginTimeException,
			EndTimeException, BoxKeyDataException, TimeException,
			UserNotExistException {
		if (box.getUserId() == 0) {
			throw new UserNotExistException("userid [ " + box.getUserId()
					+ " ] is not exist");
		}
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (userService.getUser(box.getUserId()) == null) {
			throw new UserNotExistException("userid [ " + box.getUserId()
					+ " ] is not exist");
		}
		if (DataUtil.isEmpty(box.getName())
				|| DataUtil.toText(box.getName()).length() > 15) {
			throw new NameDataException("box name [ " + box.getName()
					+ " ] data error");
		}
		if (box.getTotalCount() <= 0 || box.getTotalCount() > 10000) {
			throw new TotalCountException("box totalCount [ "
					+ box.getTotalCount() + " ] data error");
		}
		long now = System.currentTimeMillis();
		if (box.getBeginTime() == null || box.getBeginTime().getTime() < now) {
			throw new BeginTimeException("box beginTime data error");
		}
		if (box.getEndTime() == null || box.getEndTime().getTime() < now) {
			throw new EndTimeException("box endTime data error");
		}
		if (box.getBeginTime().getTime() >= box.getEndTime().getTime()) {
			throw new TimeException("beginTime >= endTime");
		}
		if (!DataUtil.isEmpty(box.getBoxKey())) {
			if (DataUtil.toText(box.getBoxKey()).length() > 6
					|| !DataUtil.isNumberOrCharOrChinese(box.getBoxKey())) {
				throw new BoxKeyDataException("boxKey [ " + box.getBoxKey()
						+ " ] data error");
			}
		}
	}

	public static void validateEditBox(Box box) throws NameDataException,
			TotalCountException, EndTimeException, BoxKeyDataException,
			TimeException, BoxIntroOutOfLengthException {
		if (DataUtil.isEmpty(box.getName())
				|| DataUtil.toText(box.getName()).length() > 15) {
			throw new NameDataException("box name [ " + box.getName()
					+ " ] data error");
		}
		if (box.getTotalCount() <= 0 || box.getTotalCount() > 10000) {
			throw new TotalCountException("box totalCount [ "
					+ box.getTotalCount() + " ] data error");
		}
		long now = System.currentTimeMillis();
		if (box.getEndTime() == null || box.getEndTime().getTime() < now) {
			throw new EndTimeException("box endTime data error");
		}
		if (box.getBeginTime().getTime() >= box.getEndTime().getTime()) {
			throw new TimeException("beginTime >= endTime");
		}
		if (!DataUtil.isEmpty(box.getBoxKey())) {
			if (DataUtil.toText(box.getBoxKey()).length() > 6
					|| !DataUtil.isNumberOrCharOrChinese(box.getBoxKey())) {
				throw new BoxKeyDataException("boxKey [ " + box.getBoxKey()
						+ " ] data error");
			}
		}
		if (box.getIntro() != null && box.getIntro().length() > 500) {
			throw new BoxIntroOutOfLengthException("out of 500");
		}
	}

	public static void validateUpdateBoxPrize(BoxPrize prize)
			throws PrizeCountException, PrizeNameDataException,
			PrizeTipDataException {
		if (prize.getPcount() <= 0) {
			throw new PrizeCountException("prize count data error");
		}
		if (DataUtil.isEmpty(prize.getName())
				|| DataUtil.toText(prize.getName()).length() > 15) {
			throw new PrizeNameDataException("prize name data error");
		}
		if (!DataUtil.isEmpty(prize.getTip())) {
			if (prize.getTip().length() > 50) {
				throw new PrizeTipDataException("prize tip data error");
			}
		}
	}

	// public static void validateUpdateBoxKey(String boxKey)
	// throws BoxKeyDataException {
	// if (!DataUtil.isEmpty(boxKey)) {
	// if (DataUtil.toNormalValue(boxKey).length() > 6
	// || !DataUtil.isNumberOrCharOrChinese(boxKey)) {
	// throw new BoxKeyDataException("boxKey [ " + boxKey
	// + " ] data error");
	// }
	// }
	// }
	/**
	 * @see Err.BOX_BOXKEY_DATA_ERROR
	 * @see Err.BOX_BOXKEY_IS_SYS_KEY
	 * @see Err.BOX_BOXKEY_ONLY_NUMBER
	 */
	public static int validateUpdateBoxKey(String boxKey,
			List<String> notExistList) {
		String key = DataUtil.toText(boxKey);
		if (!DataUtil.isEmpty(boxKey)) {
			if (key.length() > 6 || !DataUtil.isNumberOrCharOrChinese(boxKey)) {
				return BOX_BOXKEY_DATA_ERROR;
			}
			for (String s : notExistList) {
				if (key.equals(s)) {
					return BOX_BOXKEY_IS_SYS_KEY;
				}
			}
		}
		return 0;
	}
}