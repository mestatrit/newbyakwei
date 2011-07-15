package iwant.web.admin;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Slide;
import iwant.bean.validate.SlideValidator;
import iwant.svr.PptSvr;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.statusenum.UpdateSldePic0Result;
import iwant.util.BackUrl;
import iwant.util.BackUrlUtil;
import iwant.util.PicPoint;
import iwant.web.BaseAction;
import iwant.web.admin.util.Err;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/mgr/slide")
public class SlideAction extends BaseAction {

	@Autowired
	private PptSvr pptSvr;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) {
		long pptid = req.getLongAndSetAttr("pptid");
		if (this.isForwardPage(req)) {
			Ppt ppt = this.pptSvr.getPpt(pptid);
			req.setAttribute("ppt", ppt);
			if (ppt == null) {
				MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
				req.setAttribute("ppt", mainPpt);
			}
			req.setAttribute("op_project", true);
			req.reSetAttribute("from");
			return this.getAdminPath("slide/create.jsp");
		}
		Slide slide = new Slide();
		slide.setTitle(req.getStringRow("title"));
		slide.setDescr(req.getString("descr", ""));
		slide.setPptid(req.getLong("pptid"));
		File imgFile = req.getFile("f");
		if (!this.pptSvr.isCanAddSlide(pptid)) {
			return this.onError(req, Err.SLIDE_CREATE_LIMIT_ERR, "createerr2",
					null);
		}
		List<String> errlist = SlideValidator.validate(slide, imgFile, true);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "createerr");
		}
		try {
			this.pptSvr.createSlide(slide, imgFile);
			this.opCreateSuccess(req);
			return this.onSuccess(req, "createok", slide.getSlideid());
		}
		catch (PptNotFoundException e) {
			return this.onError(req, Err.PPT_NOT_EXIST, "noppterr", null);
		}
		catch (ImageProcessException e) {
			errlist.add(Err.PROCESS_IMAGEFILE_ERR);
			return this.onErrorList(req, errlist, "createerr");
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String update(HkRequest req, HkResponse resp) {
		Slide slide = this.pptSvr.getSlide(req.getLongAndSetAttr("slideid"));
		if (slide == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("slide", slide);
			Ppt ppt = this.pptSvr.getPpt(slide.getPptid());
			req.setAttribute("ppt", ppt);
			if (ppt == null) {
				MainPpt mainPpt = this.pptSvr.getMainPpt(slide.getPptid());
				req.setAttribute("ppt", mainPpt);
				req.setAttribute("pptid", mainPpt.getPptid());
			}
			else {
				req.setAttribute("pptid", ppt.getPptid());
			}
			req.setAttribute("op_project", true);
			BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
			backUrl.push(req.getString("back_url"));
			req.setAttribute("backUrl", backUrl);
			return this.getAdminPath("slide/update.jsp");
		}
		slide.setTitle(req.getStringRow("title"));
		slide.setDescr(req.getString("descr", ""));
		File imgFile = req.getFile("f");
		List<String> errlist = SlideValidator.validate(slide, imgFile, false);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		try {
			this.pptSvr.updateSlide(slide, imgFile);
			this.opCreateSuccess(req);
			return this.onSuccess(req, "updateok", null);
		}
		catch (PptNotFoundException e) {
			return this.onError(req, Err.PPT_NOT_EXIST, "noppterr", null);
		}
		catch (ImageProcessException e) {
			errlist.add(Err.PROCESS_IMAGEFILE_ERR);
			return this.onErrorList(req, errlist, "updateerr");
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setpic1(HkRequest req, HkResponse resp) throws Exception {
		Slide slide = this.pptSvr.getSlide(req.getLongAndSetAttr("slideid"));
		if (slide == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("slide", slide);
			Ppt ppt = this.pptSvr.getPpt(slide.getPptid());
			req.setAttribute("ppt", ppt);
			if (ppt == null) {
				MainPpt mainPpt = this.pptSvr.getMainPpt(slide.getPptid());
				req.setAttribute("ppt", mainPpt);
				req.setAttribute("pptid", mainPpt.getPptid());
			}
			else {
				req.setAttribute("pptid", ppt.getPptid());
			}
			req.setAttribute("op_project", true);
			BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
			backUrl.push(req.getString("back_url"));
			req.setAttribute("backUrl", backUrl);
			return this.getAdminPath("slide/setpic1.jsp");
		}
		PicPoint picRect = new PicPoint(req.getInt("x0"), req.getInt("y0"),
				req.getInt("x1"), req.getInt("y1"));
		UpdateSldePic0Result updateSlidePic0Result = this.pptSvr
				.updateSldePic1(slide.getSlideid(), picRect);
		if (updateSlidePic0Result == UpdateSldePic0Result.SUCCESS) {
			this.opCreateSuccess(req);
			return this.onSuccess(req, "updateok", null);
		}
		return this.onError(req, Err.PROCESS_IMAGEFILE_ERR, "updateerr", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String chgpos(HkRequest req, HkResponse resp) throws Exception {
		long slideid = req.getLong("slideid");
		long pos_slideid = req.getLong("pos_slideid");
		Slide slide = this.pptSvr.getSlide(slideid);
		Slide pos_slide = this.pptSvr.getSlide(pos_slideid);
		if (slide == null || pos_slide == null) {
			return null;
		}
		int slide_order_flag = slide.getOrder_flag();
		slide.setOrder_flag(pos_slide.getOrder_flag());
		pos_slide.setOrder_flag(slide_order_flag);
		this.pptSvr.updateSlide(slide, null);
		this.pptSvr.updateSlide(pos_slide, null);
		req.setSessionValue("myslideid", slideid);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delete(HkRequest req, HkResponse resp) throws Exception {
		Slide slide = this.pptSvr.getSlide(req.getLong("slideid"));
		if (slide == null) {
			return null;
		}
		this.pptSvr.deleteSlide(slide);
		this.opDeleteSuccess(req);
		return null;
	}

	public String back(HkRequest req, HkResponse resp) throws Exception {
		BackUrl backUrl = BackUrlUtil.getBackUrl(req, resp);
		String url = backUrl.getLastUrl();
		if (DataUtil.isNotEmpty(url)) {
			return "r:" + url;
		}
		return "r:/mgr/ppt_view.do?pptid=" + req.getLong("pptid");
	}
}