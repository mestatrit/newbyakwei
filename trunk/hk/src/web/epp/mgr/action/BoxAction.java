package web.epp.mgr.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.Box;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.BoxType;
import com.hk.bean.City;
import com.hk.bean.CmpUtil;
import com.hk.bean.ObjPhoto;
import com.hk.bean.User;
import com.hk.bean.UserBoxPrize;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.cmd.CmdFactory;
import com.hk.svr.BoxService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeRemainException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.processor.CreateBoxResult;
import com.hk.svr.processor.UpdateBoxResult;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.BoxTypeUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;

/**
 * 宝箱管理
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/box")
public class BoxAction extends EppBaseAction {

	@Autowired
	private BoxProcessor boxProcessor;

	@Autowired
	private BoxService boxService;

	@Autowired
	private ObjPhotoService objPhotoService;

	/**
	 * 企业宝箱列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_12", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<Box> list = this.boxService.getBoxListByCompanyId(companyId, page
				.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		return this.getWebPath("admin/box/list.jsp");
	}

	/**
	 * 创建宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_12", 1);
		int ch = req.getInt("ch");
		if (ch == 0) {
			List<BoxPretype> prelist = BoxPretypeUtil.getList();
			req.setAttribute("prelist", prelist);
			return this.getWebPath("admin/box/create.jsp");
		}
		long companyId = req.getLong("companyId");
		User loginUser = this.getLoginUser2(req);
		String begint = req.getString("bdate") + " "
				+ req.getString("bhour", "00") + ":"
				+ req.getString("bmin", "00");
		String endt = req.getString("edate") + " "
				+ req.getString("ehour", "00") + ":"
				+ req.getString("emin", "00");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		Date beginTime = DataUtil.parseTime(begint, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(endt, "yyyy-MM-dd HH:mm");
		Box o = new Box();
		o.setName(req.getHtmlRow("name"));
		o.setTotalCount(req.getInt("totalCount"));
		o.setBoxKey(req.getHtmlRow("boxKey"));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setUserId(loginUser.getUserId());
		o.setPrecount(precount);
		o.setPretype(req.getByte("pretype"));
		o.setIntro(req.getHtml("intro"));
		o.setOpentype(req.getByte("opentype"));
		o.setCompanyId(companyId);
		o.setCityId(req.getInt("pcityId"));
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			CreateBoxResult createBoxResult = this.boxProcessor.createTmpBox(o,
					true, req.getStringAndSetAttr("zoneName"), false);
			if (createBoxResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, createBoxResult.getErrorCode(),
						"createerror", null);
			}
			this.boxService.pauseBox(o.getBoxId());
		}
		catch (BoxKeyDuplicateException e) {
			return this.onError(req, Err.BOX_BOXKEY_ALREADY_EXIST,
					"createerror", null);
		}
		return this.onSuccess2(req, "createok", o.getBoxId());
	}

	/**
	 * 修改宝箱(当宝箱在正常开启的时候，不能进行修改，要在宝箱暂停之后修改，为了不出现并发上的问题,例如，当用户正在开箱子，管理员正在修改箱子，
	 * 可能会发生箱子数量出现的问题)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		req.setAttribute("active_12", 1);
		long boxId = req.getIntAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box.isExpired()) {
			return null;
		}
		req.setAttribute("box", box);
		long companyId = req.getLong("companyId");
		if (box.getCompanyId() != companyId) {
			return null;
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			this.boxService.pauseBox(boxId);
			List<BoxPretype> prelist = BoxPretypeUtil.getList();
			req.setAttribute("prelist", prelist);
			return this.getWebPath("admin/box/update.jsp");
		}
		int totalCount = req.getInt("totalCount");
		String begint = req.getString("bdate") + " "
				+ req.getString("bhour", "00") + ":"
				+ req.getString("bmin", "00");
		String endt = req.getString("edate") + " "
				+ req.getString("ehour", "00") + ":"
				+ req.getString("emin", "00");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		Date beginTime = DataUtil.parseTime(begint, "yyyy-MM-dd HH:mm");
		Date endTime = DataUtil.parseTime(endt, "yyyy-MM-dd HH:mm");
		box.setCompanyId(companyId);
		int oldTotalCount = box.getTotalCount();
		box.setCityId(req.getInt("pcityId"));
		box.setBoxType(req.getInt("boxType"));
		box.setName(req.getHtmlRow("name"));
		box.setTotalCount(totalCount);
		box.setBoxKey(req.getHtmlRow("boxKey"));
		box.setBeginTime(beginTime);
		box.setEndTime(endTime);
		box.setPrecount(precount);
		box.setPretype(req.getByte("pretype"));
		box.setIntro(req.getHtml("intro"));
		box.setOpentype(req.getByte("opentype"));
		int code = box.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			UpdateBoxResult updateBoxResult = this.boxProcessor.updateBox(box,
					null, oldTotalCount, totalCount, false);
			if (updateBoxResult.getErrorCode() != Err.SUCCESS) {
				return this.onError(req, updateBoxResult.getErrorCode(),
						"updateerror", null);
			}
		}
		catch (BoxKeyDuplicateException e) {
			return this.onError(req, Err.BOX_BOXKEY_ALREADY_EXIST,
					"updateerror", null);
		}
		catch (PrizeCountMoreThanBoxCountException e) {
			return this.onError(req,
					Err.BOXKEY_TOTALCOUNT_SMALLER_THAN_PRIZETOTALCOUNT,
					"updateerror", null);
		}
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 创建宝箱奖品（在宝箱暂停之后操作，为了避免出现并发上用户开奖品与修改奖品的冲突而导致的数据错误）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String createprize(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.setAttribute("box", box);
			return this.getWebPath("admin/box/createprize.jsp");
		}
		int pcount = req.getInt("pcount");
		if (pcount < 0) {
			pcount = 0;
		}
		BoxPrize o = new BoxPrize();
		o.setBoxId(boxId);
		o.setName(req.getHtmlRow("name"));
		o.setTip(req.getHtmlRow("tip"));
		o.setPcount(pcount);
		o.setSignal(BoxPrize.SIGNAL_Y);
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			this.boxProcessor.createBoxPrize(o);
			this.setOpFuncSuccessMsg(req);
		}
		catch (PrizeTotalCountException e) {
			return this.onError(req,
					Err.BOXPRIZE_COUNT_MORE_THAN_BOXREMAINCOUNT,
					new Object[] { String.valueOf(box.getTotalCount()
							- box.getOpenCount()) }, "createerror", null);
		}
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改宝箱奖品（在宝箱暂停之后操作，为了避免出现并发上用户开奖品与修改奖品的冲突而导致的数据错误）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String updateprize(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("navoid");
		long prizeId = req.getLongAndSetAttr("prizeId");
		long boxId = req.getLongAndSetAttr("boxId");
		long companyId = req.getLong("companyId");
		Box box = this.boxService.getBox(boxId);
		if (box.isExpired()) {
			return null;
		}
		if (box.isStop()) {
			return null;
		}
		if (!box.isPause()) {
			return null;
		}
		req.setAttribute("box", box);
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		req.setAttribute("boxPrize", boxPrize);
		if (box.getCompanyId() != companyId) {
			return null;
		}
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/box/updateprize.jsp");
		}
		int pcount = req.getInt("pcount");
		if (pcount < 0) {
			pcount = 0;
		}
		boxPrize.setSignal(BoxPrize.SIGNAL_Y);
		boxPrize.setPcount(pcount);
		boxPrize.setName(req.getHtmlRow("name"));
		boxPrize.setTip(req.getHtmlRow("tip"));
		int code = boxPrize.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			this.boxService.updateBoxPrize(boxPrize);
			this.setOpFuncSuccessMsg(req);
		}
		catch (PrizeTotalCountException e) {
			return this.onError(req,
					Err.BOXPRIZE_COUNT_MORE_THAN_BOXREMAINCOUNT,
					new Object[] { String.valueOf(box.getTotalCount()
							- box.getOpenCount()) }, "updateerror", null);
		}
		catch (PrizeRemainException e) {
			return this.onError(req, Err.BOXPRIZE_OUT_OF_REMAIN_LIMIT,
					"updateerror", null);
		}
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 停止宝箱运行，不可再运行
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String stop(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box.getCompanyId() == companyId) {
			this.boxService.stopBox(boxId);
		}
		return null;
	}

	/**
	 * 暂停宝箱运行，可以再运行
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-15
	 */
	public String pause(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box.getCompanyId() == companyId) {
			this.boxService.pauseBox(boxId);
			req.setSessionText("view2.box.pause.ok");
		}
		return null;
	}

	/**
	 * 当奖品已经被用户开启宝箱获得时，就不能删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delprize(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		long companyId = req.getLong("companyId");
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		if (box.getCompanyId() != companyId) {
			return null;
		}
		if (box.isExpired()) {
			return null;
		}
		if (box.isStop()) {
			return null;
		}
		if (!box.isPause()) {
			return null;
		}
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		if (boxPrize != null && boxPrize.getPcount() == boxPrize.getRemain()) {
			this.boxProcessor.deleteBoxPrize(box, boxPrize);
		}
		else {
			req.setSessionText("epp.boxprize.user_already_get_cannot_delete");
		}
		return null;
	}

	/**
	 * 奖品选择图片列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selprizepic(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<ObjPhoto> list = this.objPhotoService.getObjPhotoListByCompanyId(
				companyId, page.getBegin(), page.getSize() + 1);
		req.reSetAttribute("prizeId");
		req.reSetAttribute("boxId");
		req.setAttribute("list", list);
		return this.getWebPath("admin/box/prizepic.jsp");
	}

	/**
	 * 为奖品选择图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selpic(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		long photoId = req.getLong("photoId");
		long companyId = req.getLong("companyId");
		Box box = this.boxService.getBox(boxId);
		if (box.getCompanyId() == companyId) {
			this.boxProcessor.updateBoxPrizePhoto(box, prizeId, photoId);
		}
		return null;
	}

	/**
	 * 上传新的图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		Box box = this.boxService.getBox(boxId);
		long companyId = req.getLong("companyId");
		if (box.getCompanyId() != companyId) {
			return null;
		}
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		File f = req.getFile("f");
		if (f != null) {
			try {
				this.boxProcessor.updateBoxPrizePhoto(box, boxPrize, f);
				req.setSessionText("view2.setpicforboxprizeok");
			}
			catch (ImageException e) {
				return this.onError(req, Err.IMG_UPLOAD_ERROR, "uploaderror",
						null);
			}
			catch (NotPermitImageFormatException e) {
				return this
						.onError(req, Err.IMG_FMT_ERROR, "uploaderror", null);
			}
			catch (OutOfSizeException e) {
				return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
						new Object[] { "2M" }, "uploaderror", null);
			}
		}
		return this.onSuccess2(req, "uploadok", null);
	}

	/**
	 * 推荐宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setcmppink(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.updateBoxCmppink(boxId, CmpUtil.CMPPINK_Y);
		this.setPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 取消推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delcmppink(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.updateBoxCmppink(boxId, CmpUtil.CMPPINK_N);
		this.setDelPinkSuccessMsg(req);
		return null;
	}

	/**
	 * 查看宝箱详细
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-5-16
	 */
	public String view(HkRequest req, HkResponse resp) {
		req.reSetAttribute("navoid");
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		boolean stop = false;
		boolean overdue = false;
		boolean pause = false;
		boolean normal = false;
		if (box.getBoxStatus() == Box.BOX_STATUS_STOP) {
			stop = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_PAUSE) {
			pause = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			normal = true;
		}
		if (box.getEndTime().getTime() < System.currentTimeMillis()) {
			overdue = true;
		}
		BoxType boxType = BoxTypeUtil.getBoxType(box.getBoxType());
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		if (box.getCityId() > 0) {
			City city = ZoneUtil.getCity(box.getCityId());
			if (city != null) {
				req.setAttribute("zoneName", city.getCity());
			}
		}
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("boxType", boxType);
		req.setAttribute("list", list);
		req.setAttribute("normal", normal);
		req.setAttribute("pause", pause);
		req.setAttribute("stop", stop);
		req.setAttribute("overdue", overdue);
		return this.getWebPath("admin/box/view.jsp");
	}

	/**
	 * 运行宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cont(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.continueBox(boxId);
		req.setSessionText("view2.box.run.ok");
		return null;
	}

	/**
	 * 宝箱兑换管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String uplist(HkRequest req, HkResponse resp) {
		long boxId = req.getLongAndSetAttr("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		String prizeNum = req.getStringAndSetAttr("prizeNum");
		String prizePwd = req.getStringAndSetAttr("prizePwd");
		SimplePage page = req.getSimplePage(20);
		List<UserBoxPrize> list = null;
		if (!DataUtil.isEmpty(prizeNum) && !DataUtil.isEmpty(prizePwd)) {
			list = this.boxProcessor
					.getUserBoxPrizeListByBoxIdAndPrizeNumAndPrizePwd(boxId,
							prizeNum, prizePwd, true, true, page.getBegin(),
							page.getSize() + 1);
		}
		else {
			list = this.boxProcessor.getUserBoxPrizeListByBoxId(boxId, true,
					true, page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/box/uplist.jsp");
	}

	/**
	 * 设置为已兑换
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setused(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		UserBoxPrize userBoxPrize = this.boxService.getUserBoxPrize(oid);
		if (userBoxPrize == null) {
			return null;
		}
		Box box = this.boxService.getBox(userBoxPrize.getBoxId());
		if (box.getCompanyId() == companyId) {
			this.boxService.setUserBoxPrizeDrawed(oid);
			this.setOpFuncSuccessMsg(req);
		}
		return null;
	}
}