package iwant.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.ServletUtil;

public class BackUrl {

	private List<String> list = new ArrayList<String>(5);

	private int size;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String backUrl_key;

	private Cookie cookie;

	private DesUtil desUtil = new DesUtil("backurl");

	public BackUrl(String backUrl_key, int size, HttpServletRequest request,
			HttpServletResponse response) {
		this.size = size;
		this.request = request;
		this.response = response;
		this.backUrl_key = backUrl_key;
		this.build();
	}

	public void push(String url) {
		list.add(0, url);
		if (list.size() > this.size) {
			list.remove(this.size);
		}
	}

	public String pop() {
		if (list.size() > 0) {
			return list.remove(0);
		}
		return null;
	}

	public void save() {
		StringBuilder sb = new StringBuilder();
		for (String e : this.list) {
			sb.append(DataUtil.urlEncoder(e)).append(":");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		cookie.setValue(desUtil.encrypt(sb.toString()));
		this.response.addCookie(cookie);
	}

	private void build() {
		cookie = ServletUtil.getCookie(this.request, this.backUrl_key);
		if (cookie == null) {
			cookie = new Cookie(this.backUrl_key, "");
		}
		else {
			this.buildValueFromCookie();
		}
		cookie.setPath("/");
		cookie.setMaxAge(-1);
	}

	private void buildValueFromCookie() {
		String value = this.cookie.getValue();
		String[] arr = desUtil.decrypt(value).split(":");
		if (arr != null) {
			for (String s : arr) {
				this.list.add(s);
			}
		}
	}
}