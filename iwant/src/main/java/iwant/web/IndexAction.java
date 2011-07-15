package iwant.web;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component("/index")
public class IndexAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		HttpSession session = req.getSession();
		session.setAttribute("test", "ok");
		return "/WEB-INF/page/hello.jsp";
	}
}