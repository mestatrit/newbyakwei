package tuxiazi.svr.impl.jms;

import halo.util.DataUtil;
import halo.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class JmsMsg {

	public static final String HEAD_PHOTO_CREATEPHOTO = "tuxiazi_photo_createphoto";

	public static final String HEAD_PHOTO_CREATEUSER = "tuxiazi_user_createuser";

	private Map<String, String> map = new HashMap<String, String>();

	public JmsMsg() {
	}

	public JmsMsg(String value) {
		String[] tmp = value.split("&");
		this.head = tmp[0];
		this.body = DataUtil.urlDecoder(tmp[1]);
	}

	public JmsMsg(String head, String body) {
		this.head = head;
		this.body = body;
	}

	private String head;

	/**
	 * json格数数据
	 */
	private String body;

	public void setHead(String head) {
		this.head = head;
	}

	public String getHead() {
		return head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setBody(Map<String, String> map) {
		this.setBody(JsonUtil.toJson(map));
	}

	public String toMessage() {
		return this.head + "&" + DataUtil.urlEncoder(this.body);
	}

	public void addData(String key, String value) {
		this.map.put(key, value);
	}

	public void buildBody() {
		this.setBody(this.map);
	}
}