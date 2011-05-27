package iwant.web;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;
import com.dev3g.cactus.web.util.MessageUtil;

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
			req.setAttribute(MessageUtil.MESSAGE_NAME, msg);
		}
		return null;
	}
}