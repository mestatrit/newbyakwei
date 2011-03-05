package tuxiazi.webapi;

import org.springframework.stereotype.Component;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/test")
public class TestAction extends BaseApiAction {

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}
}