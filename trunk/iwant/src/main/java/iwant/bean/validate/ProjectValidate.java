package iwant.bean.validate;

import halo.util.HaloValidate;
import iwant.bean.Project;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

public class ProjectValidate {

	public static List<String> validate(Project project) {
		List<String> errlist = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(project.getName(), false, 20)) {
			errlist.add(Err.PROJECT_NAME_ERR);
		}
		if (!HaloValidate.validateLength(project.getDescr(), false, 300)) {
			errlist.add(Err.PROJECT_DESCR_ERR);
		}
		if (!HaloValidate.validateLength(project.getAddr(), false, 100)) {
			errlist.add(Err.PROJECT_ADDR_ERR);
		}
		if (!HaloValidate.validateLength(project.getTel(), false, 100)) {
			errlist.add(Err.PROJECT_TEL_ERR);
		}
		if (project.getCatid() <= 0) {
			errlist.add(Err.PROJECT_CATID_ERR);
		}
		if (project.getDid() <= 0) {
			errlist.add(Err.PROJECT_DID_ERR);
		}
		return errlist;
	}
}