package iwant.bean.validate;

import iwant.bean.Project;
import iwant.svr.CategorySvr;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.HkValidate;

public class ProjectValidate {

	public static List<String> validate(Project project) {
		List<String> errlist = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(project.getName(), false, 20)) {
			errlist.add(Err.PROJECT_NAME_ERR);
		}
		if (!HkValidate.validateLength(project.getDescr(), false, 300)) {
			errlist.add(Err.PROJECT_DESCR_ERR);
		}
		if (!HkValidate.validateLength(project.getAddr(), false, 100)) {
			errlist.add(Err.PROJECT_ADDR_ERR);
		}
		if (!HkValidate.validateLength(project.getTel(), false, 100)) {
			errlist.add(Err.PROJECT_TEL_ERR);
		}
		if (project.getCatid() <= 0) {
			errlist.add(Err.PROJECT_CATID_ERR);
		}
		else {
			CategorySvr categorySvr = (CategorySvr) HkUtil
					.getBean("categorySvr");
			if (categorySvr.getCategory(project.getCatid()) == null) {
				errlist.add(Err.PROJECT_CATID_ERR);
			}
		}
		return errlist;
	}
}