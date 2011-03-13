package action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hk.frame.web.action.ActionExe;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkRequestImpl;
import com.hk.frame.web.http.HkResponse;
import com.hk.frame.web.http.HkResponseImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/hkframe-web.xml" })
public class ActionTest {

	@Test
	public void testInvokeExecute() throws IOException, ServletException {
		ActionExe actionExe = new ActionExe();
		actionExe.setUrl_extension(".do");
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		actionExe.proccess(hkRequest, hkResponse);
	}

	@Test
	public void testInvokeHello() throws IOException, ServletException {
		ActionExe actionExe = new ActionExe();
		actionExe.setUrl_extension(".do");
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test_hello");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		actionExe.proccess(hkRequest, hkResponse);
	}

	@Test
	public void testInvokeHello2() throws IOException, ServletException {
		ActionExe actionExe = new ActionExe();
		actionExe.setUrl_extension(".do");
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test_hello2");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		actionExe.proccess(hkRequest, hkResponse);
	}
}