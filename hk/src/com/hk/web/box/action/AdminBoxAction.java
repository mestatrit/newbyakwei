package com.hk.web.box.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxOpenType;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.BoxType;
import com.hk.bean.City;
import com.hk.bean.HkbLog;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.cmd.CmdFactory;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.svr.box.exception.BoxIntroOutOfLengthException;
import com.hk.svr.box.exception.BoxKeyDataException;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.EndTimeException;
import com.hk.svr.box.exception.NameDataException;
import com.hk.svr.box.exception.PrizeCountException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeNameDataException;
import com.hk.svr.box.exception.PrizeRemainException;
import com.hk.svr.box.exception.PrizeTipDataException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.box.exception.TimeException;
import com.hk.svr.box.exception.TotalCountException;
import com.hk.svr.box.validate.BoxValidate;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.processor.UpdateBoxResult;
import com.hk.svr.pub.BoxOpenTypeUtil;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.BoxTypeUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/box/admin/adminbox")
public class AdminBoxAction extends BaseAction {

	private final Log log = LogFactory.getLog(AdminBoxAction.class);

	private int size = 20;

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	@Autowired
	private BoxProcessor boxProcessor;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getBoxListByCdn(0, name,
				Box.CHECKFLG_UNCHECK, (byte) -1, page.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/admin/preboxlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String admin(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/box/admin/admin.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditprize(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		BoxPrize prize = this.boxService.getBoxPrize(prizeId);
		req.setAttribute("boxId", boxId);
		req.setAttribute("prizeId", prizeId);
		req.setAttribute("prize", prize);
		return "/WEB-INF/page/box/admin/editprize.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateprize(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		String name = req.getString("name");
		String tip = req.getString("tip");
		int pcount = req.getInt("pcount");
		if (pcount < 0) {
			pcount = 0;
		}
		BoxPrize prize = this.boxService.getBoxPrize(prizeId);
		prize.setBoxId(boxId);
		prize.setPrizeId(prizeId);
		prize.setPcount(pcount);
		prize.setName(DataUtil.toHtmlRow(name));
		prize.setTip(DataUtil.toHtmlRow(tip));
		req.setAttribute("prize", prize);
		req.setAttribute("boxId", boxId);
		req.setAttribute("prizeId", prizeId);
		req.setAttribute("prize", prize);
		if (DataUtil.isEmpty(tip)) {
			req.setMessage("物品描述不能为空");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		try {
			BoxValidate.validateUpdateBoxPrize(prize);
		}
		catch (PrizeCountException e) {
			req.setMessage("物品数量不能为空");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		catch (PrizeNameDataException e) {
			req.setMessage("物品名称不能超过10个字符");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		catch (PrizeTipDataException e) {
			req.setMessage("物品提示语不能超过50个字符");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		try {
			this.boxService.updateBoxPrize(prize);
		}
		catch (PrizeTotalCountException e) {
			req.setMessage("物品总数量不能超过宝箱发布数量");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		catch (PrizeRemainException e) {
			req.setMessage("物品总数量不能超过宝箱发布数量");
			return "/WEB-INF/page/box/admin/editprize.jsp";
		}
		return "r:/box/admin/adminbox_getprebox.do?boxId=" + boxId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getprebox(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLong("boxId");
		Box o = (Box) req.getAttribute("o");
		if (o == null) {
			o = this.boxService.getBox(boxId);
		}
		if (o == null) {
			return null;
		}
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
			if (zoneName == null) {
				zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
			}
		}
		if (zoneName == null && o.getBoxId() > 0) {
			City city = ZoneUtil.getCity(o.getCityId());
			if (city != null) {
				zoneName = city.getCity();
			}
		}
		req.setAttribute("zoneName", zoneName);
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("boxId", boxId);
		req.setAttribute("o", o);
		req.reSetAttribute("repage");
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		List<BoxType> typeList = BoxTypeUtil.getTypeList();
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(o.getPretype());
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("typeList", typeList);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/admin/prebox.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmdelprebox(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		req.setAttribute("boxId", boxId);
		return "/WEB-INF/page/box/admin/confirmdelprebox.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delprebox(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel != null) {
			return "r:/box/admin/adminbox_getprebox.do?boxId=" + boxId;
		}
		Box box = this.boxService.getBox(boxId);
		this.boxService.deleteBox(boxId);
		HkbLog hkbLog = HkbLog.create(box.getUserId(), HkbConfig.DELBOX, boxId,
				box.getTotalCount());
		this.userService.addHkb(hkbLog);
		return "r:/box/admin/adminbox.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String check(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		List<BoxType> typeList = BoxTypeUtil.getTypeList();
		req.setAttribute("typeList", typeList);
		req.setAttribute("list", list);
		Box o = this.boxService.getBox(boxId);
		if (o == null) {
			return "/box/admin/adminbox_tosubeditinfo.do?boxId=" + boxId
					+ "&repage=" + req.getInt("repage");
		}
		int oldTotalCount = o.getTotalCount();
		String del = req.getString("del");
		if (del != null) {
			return "/box/admin/adminbox_confirmdelprebox.do";
		}
		String ok = req.getString("ok");
		/** ******修改宝箱****** */
		String name = req.getString("name");
		int totalCount = req.getInt("totalCount");
		String intro = req.getString("intro");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		int boxType = req.getInt("boxType");
		req.reSetAttribute("begint");
		req.reSetAttribute("endt");
		Date beginTime = DataUtil.parseTime(begint, "yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime(endt, "yyyyMMddHHmm");
		byte virtualflg = req.getByte("virtualflg");
		byte otherPrizeflg = req.getByte("otherPrizeflg");
		o.setOtherPrizeflg(otherPrizeflg);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTotalCount(totalCount);
		o.setBoxKey(DataUtil.toHtmlRow(boxKey));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setPrecount(precount);
		o.setPretype(pretype);
		o.setIntro(DataUtil.toHtml(intro));
		o.setOpentype(opentype);
		o.setBoxType(boxType);
		o.setVirtualflg(virtualflg);
		req.setAttribute("o", o);
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/box/admin/adminbox_getprebox.do?boxId=" + boxId;
		}
		try {
			UpdateBoxResult updateBoxResult = this.boxProcessor.updateBox(o,
					req.getStringAndSetAttr("zoneName"), oldTotalCount,
					totalCount, true);
			if (updateBoxResult.getErrorCode() != Err.SUCCESS) {
				if (updateBoxResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
					if (updateBoxResult.getProvinceId() > 0) {// 到省下的城市中
						return "r:/index_selcityfromprovince.do?provinceId="
								+ updateBoxResult.getProvinceId()
								+ "&forsel=1"
								+ "&return_url="
								+ DataUtil
										.urlEncoder("/box/op/op_getprebox.do?boxId="
												+ boxId
												+ "&repage="
												+ req.getInt("repage"));
					}
				}
				req.setText(String.valueOf(updateBoxResult.getErrorCode()));
				return "/box/op/op_toeditbox.do";
			}
		}
		catch (BoxKeyDuplicateException e) {
			req.setText("func.boxkey_alread_exist");
			return "/box/admin/adminbox_getprebox.do?boxId=" + boxId;
		}
		catch (PrizeCountMoreThanBoxCountException e) {
			req.setText("func.boxkey_totalcount_smaller_than_prizetotalcount");
			return "/box/admin/adminbox_getprebox.do?boxId=" + boxId;
		}
		if (ok != null) {
			try {
				this.boxService.createBox(boxId);// 审核成功
				if (!o.isVirtual()) {
					List<BoxPrize> boxprizelist = this.boxService
							.getBoxPrizeListByBoxId(boxId);
					for (BoxPrize p : boxprizelist) {
						if (p.getEid() > 0) {
							p.setEid(0);
							p.setPcount(0);
							try {
								this.boxService.updateBoxPrize(p);
							}
							catch (PrizeTotalCountException e) {
							}
							catch (PrizeRemainException e) {
							}
						}
					}
				}
				req.setSessionText("op.exeok");
				return "r:/box/admin/adminbox.do?page=" + req.getInt("repage");
			}
			catch (BoxKeyDuplicateException e) {
				req.setText("func.boxkey_alread_exist");
				return "/box/admin/adminbox_getprebox.do?boxId=" + boxId;
			}
		}
		return "/box/admin/adminbox_tosubeditinfo.do?boxId=" + boxId
				+ "&repage=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosubeditinfo(HkRequest req, HkResponse resp) {
		req.reSetAttribute("repage");
		req.reSetAttribute("boxId");
		return "/WEB-INF/page/box/admin/boxeditinfo.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String subeditinfo(HkRequest req, HkResponse resp) {
		String content = req.getString("content");
		if (content != null && content.length() > 500) {
			req.setMessage("修改意见不能超过500字符");
			return "/box/admin/adminbox_tosubeditinfo.do";
		}
		long boxId = req.getLong("boxId");
		this.boxService.editForPreBox(boxId, content);
		return "r:/box/admin/adminbox.do?page=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String openboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getCanOpenBoxList(0, name, page
				.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		req.setAttribute("fromadmin", 1);
		return "/WEB-INF/page/box/admin/openboxlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cancelpink(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.updateBoxPinkFlg(boxId, Box.PINKFLG_N);
		return "r:box/admin/adminbox_openboxlist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setpink(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.updateBoxPinkFlg(boxId, Box.PINKFLG_PINK);
		return "r:box/admin/adminbox_openboxlist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String settop(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.updateBoxPinkFlgTop(boxId);
		return "r:box/admin/adminbox_openboxlist.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String unopenboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getNotBeginBoxList(0, name, page
				.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		req.setAttribute("fromadmin", 1);
		return "/WEB-INF/page/box/admin/unopenboxlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String pauseboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getBoxListByCdn(0, name,
				Box.CHECKFLG_CHECKOK, Box.BOX_STATUS_PAUSE, page.getBegin(),
				size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		req.setAttribute("fromadmin", 1);
		return "/WEB-INF/page/box/admin/pauseboxlist.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String getbox(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return "r:/more.do";
		}
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
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("boxType", boxType);
		req.setAttribute("list", list);
		req.setAttribute("normal", normal);
		req.setAttribute("pause", pause);
		req.setAttribute("stop", stop);
		req.setAttribute("overdue", overdue);
		req.setAttribute("boxId", boxId);
		req.setAttribute("box", box);
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/admin/box.jsp";
	}

	/**
	 * 作废
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String stop(HkRequest req, HkResponse resp) {
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/admin/confirmstop.jsp";
	}

	/**
	 * 确认作废
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmstop(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			this.boxService.stopBox(boxId);
			req.setSessionMessage("操作成功");
		}
		return "r:/box/admin/adminbox_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage");
	}

	/**
	 * 停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String pause(HkRequest req, HkResponse resp) {
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/admin/confirmpause.jsp";
	}

	/**
	 * 确认停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmpause(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			this.boxService.pauseBox(boxId);
			req.setSessionMessage("操作成功");
		}
		this.boxService.pauseBox(boxId);
		req.setSessionMessage("操作成功");
		return "r:/box/admin/adminbox_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cont(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.continueBox(boxId);
		req.setSessionMessage("操作成功");
		return "/box/admin/adminbox_back.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String back(HkRequest req, HkResponse resp) {
		int t = req.getInt("t");
		int repage = req.getInt("repage");
		if (t == 2) {
			return "r:/box/admin/adminbox_openboxlist.do?page=" + repage;
		}
		if (t == 4) {
			return "r:/box/admin/adminbox_unopenboxlist.do?page=" + repage;
		}
		if (t == 5) {
			return "r:/box/admin/adminbox_pauseboxlist.do?page=" + repage;
		}
		return null;
	}

	public String optbox(HkRequest req, HkResponse resp) {
		if (req.getString("stop") != null) {
			return this.stop(req, resp);
		}
		if (req.getString("pause") != null) {
			return this.pause(req, resp);
		}
		if (req.getString("cont") != null) {
			return this.cont(req, resp);
		}
		if (req.getString("editbox") != null) {
			return this.toeditbox(req, resp);
		}
		// return this.toeditkey(req, resp);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editbox(HkRequest req, HkResponse resp) {
		long boxId = req.getInt("boxId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		int totalCount = req.getInt("totalCount");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		Date beginTime = null;
		Date endTime = null;
		try {
			if (begint != null) {
				beginTime = sdf.parse(begint);
			}
		}
		catch (ParseException e) {
			log.error("error beginTime date format [ " + begint + " ]");
		}
		try {
			if (endt != null) {
				endTime = sdf.parse(endt);
			}
		}
		catch (ParseException e) {
			log.error("error endTime date format [ " + endt + " ]");
		}
		Box box = this.boxService.getBox(boxId);
		box.setName(DataUtil.toHtmlRow(name));
		box.setTotalCount(totalCount);
		box.setBoxKey(DataUtil.toHtmlRow(boxKey));
		box.setBeginTime(beginTime);
		box.setEndTime(endTime);
		box.setPrecount(precount);
		box.setPretype(pretype);
		box.setIntro(DataUtil.toHtml(intro));
		box.setOpentype(opentype);
		req.setAttribute("editbox", box);
		if (!isEmpty(boxKey)) {
			if (DataUtil.isNumber(boxKey)) {
				req.setMessage("短信开箱口令不能只是数字");
				return "/box/admin/adminbox_toeditbox.do";
			}
			if (CmdFactory.isKey(boxKey)) {
				req.setMessage("短信开箱口令不能使用yz,bd,xm开头作为关键字");
				return "/box/admin/adminbox_toeditbox.do";
			}
		}
		try {
			BoxValidate.validateEditBox(box);
		}
		catch (NameDataException e) {
			req.setMessage("宝箱名称不能为空且最多15个字符");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (TotalCountException e) {
			req.setMessage("宝箱数量不能为空且数量最多为10000");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (EndTimeException e) {
			req.setMessage("开箱结束时间不能早于当前时间");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (BoxKeyDataException e) {
			req.setMessage("短信开箱口令输入错误");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (TimeException e) {
			req.setMessage("开箱结束时间不能早于起始时间");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (BoxIntroOutOfLengthException e) {
			req.setMessage("宝箱介绍不能超过500个字符");
			return "/box/admin/adminbox_toeditbox.do";
		}
		try {
			this.boxService.updateBox(box);
		}
		catch (BoxKeyDuplicateException e) {
			req.setMessage("短信开箱口令已经存在,请重新输入");
			return "/box/admin/adminbox_toeditbox.do";
		}
		catch (PrizeCountMoreThanBoxCountException e) {
			req.setMessage("箱子总数小于物品总数");
			return "/box/admin/adminbox_toeditbox.do";
		}
		req.setSessionMessage("修改成功");
		return "r:/box/admin/adminbox_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditbox(HkRequest req, HkResponse resp) {
		long boxId = req.getInt("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("boxId");
		req.reSetAttribute("repage");
		Box box = this.boxService.getBox(boxId);
		if (box.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			return "/WEB-INF/page/box/admin/confirmpauseforedit.jsp";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String begint = sdf.format(box.getBeginTime());
		String endt = sdf.format(box.getEndTime());
		req.setAttribute("begint", begint);
		req.setAttribute("endt", endt);
		req.setAttribute("box", box);
		List<BoxType> typeList = BoxTypeUtil.getTypeList();
		List<BoxOpenType> opentypelist = BoxOpenTypeUtil.getTypeList();
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("typeList", typeList);
		req.setAttribute("opentypelist", opentypelist);
		return "/WEB-INF/page/box/admin/editbox.jsp";
	}

	/**
	 * 停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmpauseforedit(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			this.boxService.pauseBox(boxId);
			return "/box/admin/adminbox_toeditbox.do";
		}
		return "r:/box/admin/adminbox_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage");
	}
}