package iwant.web.admin;

import iwant.bean.Ppt;
import iwant.bean.Slide;
import iwant.bean.validate.SlideValidator;
import iwant.svr.OptStatus;
import iwant.svr.PptSvr;
import iwant.web.BaseAction;
import iwant.web.admin.util.Err;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/mgr/slide")
public class SlideAction extends BaseAction {

	@Autowired
	private PptSvr pptSvr;

	public String create(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLongAndSetAttr("pptid");
		Ppt ppt = this.pptSvr.getPpt(pptid);
		if (this.isForwardPage(req)) {
			req.setAttribute("ppt", ppt);
			req.setAttribute("op_project", true);
			return this.getAdminPath("slide/create.jsp");
		}
		Slide slide = new Slide();
		slide.setTitle(req.getStringRow("title"));
		slide.setSubtitle(req.getStringRow("subtitle"));
		slide.setDescr(req.getString("descr", ""));
		slide.setPptid(req.getLong("pptid"));
		slide.setSubtitle(req.getStringRow("subtitle", ""));
		File imgFile = req.getFile("f");
		List<String> errlist = SlideValidator.validate(slide, imgFile, true);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "createerr");
		}
		OptStatus optStatus = this.pptSvr.createSlide(slide, imgFile);
		if (!optStatus.isSuccess()) {
			errlist.add(Err.PROCESS_IMAGEFILE_ERR);
		}
		this.opCreateSuccess(req);
		return this.onSuccess(req, "createok", pptid);
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		Slide slide = this.pptSvr.getSlide(req.getLongAndSetAttr("slideid"));
		if (slide == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("slide", slide);
			Ppt ppt = this.pptSvr.getPpt(slide.getPptid());
			req.setAttribute("ppt", ppt);
			req.setAttribute("op_project", true);
			return this.getAdminPath("slide/update.jsp");
		}
		slide.setTitle(req.getStringRow("title"));
		slide.setSubtitle(req.getStringRow("subtitle"));
		slide.setDescr(req.getString("descr", ""));
		slide.setSubtitle(req.getStringRow("subtitle", ""));
		File imgFile = req.getFile("f");
		List<String> errlist = SlideValidator.validate(slide, imgFile, false);
		if (!errlist.isEmpty()) {
			return this.onErrorList(req, errlist, "updateerr");
		}
		OptStatus optStatus = this.pptSvr.updateSlide(slide, imgFile);
		if (!optStatus.isSuccess()) {
			errlist.add(Err.PROCESS_IMAGEFILE_ERR);
		}
		this.opCreateSuccess(req);
		return this.onSuccess(req, "updateok", null);
	}

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

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		Slide slide = this.pptSvr.getSlide(req.getLong("slideid"));
		if (slide == null) {
			return null;
		}
		this.pptSvr.deleteSlide(slide);
		this.opDeleteSuccess(req);
		return null;
	}
}