package demo.web;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.util.P;
import com.dev3g.cactus.web.action.HkRequest;
import com.dev3g.cactus.web.action.HkResponse;
import com.dev3g.cactus.web.action.WebCnf;

import demo.bean.UserInfo;
import demo.svr.UserInfoService;

@Component("/hello")
public class HelloAction extends BaseAction {

	@Autowired
	private UserInfoService userInfoService;

	public HelloAction() {
		P.println("inovke helloAction");
	}

	/**
	 * 向页面输出数据,url:http://localhost:8080/hkdemo/hello
	 */
	@Override
	public String execute(HkRequest req, HkResponse resp) {
		resp.sendHtml("你好");
		this.userInfoService.createUserInfo(new UserInfo());
		return null;
	}

	/**
	 * 向jsp页面输出用户传入的数据 url:http://localhost:8080/hkdemo/hello_method1
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String method1(HkRequest req, HkResponse resp) {
		if (req.getMethod().equals("GET")) {
			return "input.jsp";
		}
		req.setAttribute("uid", req.getInt("uid"));// 限定数字
		req.setAttribute("name", req.getString("name"));// 限定单行字符
		req.setAttribute("intro", req.getString("intro"));// 限定多行字符
		req.setAttribute("aihao", req.getInt("aihao"));// radio
		req.setAttribute("gender", req.getInt("gender"));// select
		int[] languages = req.getInts("language");
		String s = "";
		if (languages != null) {
			for (int i : languages) {
				s += i + ",";
			}
		}
		req.setAttribute("languagesvalue", s);
		return "result.jsp";
	}

	/**
	 * 重定向功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String method2(HkRequest req, HkResponse resp) {
		return "r:/2.jsp";
	}

	/**
	 * 资源文件输出
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String method3(HkRequest req, HkResponse resp) {
		req.setAttribute("key", System.currentTimeMillis());
		return "resource.jsp";
	}

	/**
	 * 页面调用action
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String method4(HkRequest req, HkResponse resp) {
		return "action.jsp";
	}

	/**
	 * 页面调用的action
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String jspinvoke(HkRequest req, HkResponse resp) {
		req.setAttribute("invoke_attr", "hello invoke");
		return null;
	}

	/**
	 * 重定向到外部链接功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String method5(HkRequest req, HkResponse resp) {
		return "r:http://www.163.com";
	}

	/**
	 * 由于设置了 {@link WebCnf#setMustCheckUpload(boolean)}
	 * ，所以只有此url才能接受文件上传，其他url都不会自定进行文件上传
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String upload(HkRequest req, HkResponse resp) {
		if (req.getInt("ch") == 0) {
			return "/upload.jsp";
		}
		File f = req.getFile("f");
		if (f != null) {
			String name = f.getAbsolutePath();
			req.setAttribute("name", name);
		}
		return "/uploadresult.jsp";
	}

	/**
	 * 由于没有设置 {@link WebCnf#setMustCheckUpload(boolean)}
	 * ，所以此url不会接受文件上传，获得的文件为null
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String upload2(HkRequest req, HkResponse resp) {
		File f = req.getFile("f");
		if (f == null) {
			req.setAttribute("uploadnull", true);
		}
		return "/uploadresult2.jsp";
	}
}