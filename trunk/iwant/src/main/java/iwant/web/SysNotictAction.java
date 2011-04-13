package iwant.web;

import org.springframework.stereotype.Component;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.MessageUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

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