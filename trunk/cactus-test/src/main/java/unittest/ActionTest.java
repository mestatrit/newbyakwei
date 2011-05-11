package unittest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cactus.web.action.HkRequest;
import cactus.web.action.HkRequestImpl;
import cactus.web.action.HkResponse;
import cactus.web.action.HkResponseImpl;
import cactus.web.action.WebCnf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/hkframe-web.xml" })
public class ActionTest {

	@Resource
	private WebCnf webCnf;

	@Test
	public void testInvokeExecute() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		this.webCnf.getActionExe().invoke(
				this.webCnf.getMappingUriCreater().findMappingUri(hkRequest),
				hkRequest, hkResponse);
	}

	@Test
	public void testInvokeHello() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test_hello");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		this.webCnf.getActionExe().invoke(
				this.webCnf.getMappingUriCreater().findMappingUri(hkRequest),
				hkRequest, hkResponse);
	}

	@Test
	public void testInvokeHello2() throws Exception {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest(
				"get", "/test_hello2");
		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
		HkRequest hkRequest = new HkRequestImpl(mockHttpServletRequest);
		HkResponse hkResponse = new HkResponseImpl(mockHttpServletResponse);
		this.webCnf.getActionExe().invoke(
				this.webCnf.getMappingUriCreater().findMappingUri(hkRequest),
				hkRequest, hkResponse);
	}
}