package iwant.bean.validate;

import halo.util.HaloValidate;
import halo.util.HaloValidate2;
import iwant.bean.Project;
import iwant.web.admin.util.Err;

import java.util.ArrayList;
import java.util.List;

public class ProjectValidate {

	public static List<String> validate(Project o) {
		List<String> list = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(o.getName(), false, 20)) {
			list.add(Err.PROJECT_NAME_ERR);
		}
		if (!HaloValidate.validateEmptyAndLength(o.getDescr(), false, 3000)) {
			list.add(Err.PROJECT_DESCR_ERR);
		}
		if (!HaloValidate.validateEmptyAndLength(o.getAddr(), false, 100)) {
			list.add(Err.PROJECT_ADDR_ERR);
		}
		if (!HaloValidate.validateLength(o.getTel(), false, 100)) {
			list.add(Err.PROJECT_TEL_ERR);
		}
		if (o.getDid() <= 0) {
			list.add(Err.PROJECT_DID_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getRongjilv(), 20)) {
			list.add(Err.PROJECT_RONGJILV_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getLvhualv(), 20)) {
			list.add(Err.PROJECT_LVHUALV_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getMrate(), 20)) {
			list.add(Err.PROJECT_MRATE_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getCarspace(), 50)) {
			list.add(Err.PROJECT_CARSPACE_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getBuildtime(), 20)) {
			list.add(Err.PROJECT_BUILDTIME_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getBuildtype(), 20)) {
			list.add(Err.PROJECT_BUILDTYPE_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getMtype(), 20)) {
			list.add(Err.PROJECT_MTYPE_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getTraffic(), 300)) {
			list.add(Err.PROJECT_TRAFFIC_ERR);
		}
		if (!HaloValidate2.validateEmptyAndLength(o.getNeardescr(), 1000)) {
			list.add(Err.PROJECT_NEARDESCR_ERR);
		}
		return list;
	}
}