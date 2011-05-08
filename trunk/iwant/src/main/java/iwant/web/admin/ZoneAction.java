package iwant.web.admin;

import iwant.web.BaseAction;
import iwant.web.admin.util.AdminUtil;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;

@Lazy
@Component("/mgr/zone")
public class ZoneAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getAdminPath("zone/zone.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String changecity(HkRequest req, HkResponse resp) throws Exception {
		AdminUtil.setLoginCity(resp, req.getInt("cityid"));
		return "r:/mgr/project.do";
	}
}