package iwant.bean.validate;

import iwant.bean.Notice;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import cactus.util.HkUtil;
import cactus.util.HkValidate;

public class NoticeValidator {

	public static List<String> validate(Notice notice) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(notice.getContent(), false, 100)) {
			list.add(Err.NOTICE_CONTENT_ERR);
		}
		if (notice.getPptid() <= 0) {
			list.add(Err.PPT_NOT_EXIST);
		}
		if (notice.getProjectid() <= 0) {
			list.add(Err.PROJECT_NOT_EXIST);
		}
		PptSvr pptSvr = (PptSvr) HkUtil.getBean("pptSvr");
		ProjectSvr projectSvr = (ProjectSvr) HkUtil.getBean("projectSvr");
		if (pptSvr.getPpt(notice.getPptid()) == null) {
			list.add(Err.PPT_NOT_EXIST);
		}
		if (projectSvr.getProject(notice.getProjectid()) == null) {
			list.add(Err.PROJECT_NOT_EXIST);
		}
		return list;
	}
}