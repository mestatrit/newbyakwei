package iwant.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/index")
public class IndexAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		HttpSession session = req.getSession();
		session.setAttribute("test", "ok");
		return "/WEB-INF/page/hello.jsp";
	}
}