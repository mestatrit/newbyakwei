package iwant.web;

import org.springframework.stereotype.Component;

import cactus.util.HkUtil;
import cactus.web.action.HkRequest;
import cactus.web.action.HkResponse;
import cactus.web.util.MessageUtil;

/**
 * 系统信息提示，通过页面调用，不会经过webFilter
 * 
 * @author akwei
 */
@Component("/sysnotice")
public class SysNotictAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		String msg = MessageUtil.getMessage(req);
		if (msg != null) {
			req.setAttribute(HkUtil.MESSAGE_NAME, msg);
		}
		return null;
	}
}