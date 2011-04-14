package iwant.web.admin;

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
		Slide slide = new Slide();
		slide.setTitle(req.getStringRow("title"));
		slide.setSubtitle(req.getStringRow("subTitle"));
		slide.setDescr(req.getString("descr", ""));
		slide.setPptid(req.getLong("pptid"));
		slide.setSubtitle(req.getStringRow("subTitle", ""));
		File imgFile = req.getFile("f");
		List<String> errlist = SlideValidator.validate(slide, imgFile);
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
		Slide slide = this.pptSvr.getSlide(req.getLong("slideid"));
		if (this.isForwardPage(req)) {
			req.setAttribute("slid", slide);
			return this.getAdminPath("slide/update.jsp");
		}
		slide.setTitle(req.getStringRow("title"));
		slide.setSubtitle(req.getStringRow("subTitle"));
		slide.setDescr(req.getString("descr", ""));
		slide.setPptid(req.getLong("pptid"));
		slide.setSubtitle(req.getStringRow("subTitle", ""));
		File imgFile = req.getFile("f");
		List<String> errlist = SlideValidator.validate(slide, imgFile);
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