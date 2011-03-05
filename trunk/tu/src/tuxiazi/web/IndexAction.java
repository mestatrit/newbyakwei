package tuxiazi.web;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/index")
public class IndexAction extends BaseAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return this.getWebPath("index/index.jsp");
	}
}