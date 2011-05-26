package cactus.util.httputil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HttpData {

	private final Map<String, String> parameterMap = new HashMap<String, String>();

	private final Map<String, List<String>> batchParameterMap = new HashMap<String, List<String>>(
			0);

	private HttpHeader httpHeader;

	private List<HttpFile> httpFiles = new ArrayList<HttpFile>(0);

	public int getPatameterCount() {
		Set<Entry<String, List<String>>> set = this.batchParameterMap
				.entrySet();
		int sum = this.parameterMap.size() + this.httpFiles.size();
		for (Entry<String, List<String>> e : set) {
			sum = sum + e.getValue().size();
		}
		return sum;
	}

	public void addFile(HttpFile httpFile) {
		this.httpFiles.add(httpFile);
	}

	public void setHttpHeader(HttpHeader httpHeader) {
		this.httpHeader = httpHeader;
	}

	public HttpHeader getHttpHeader() {
		return httpHeader;
	}

	public void add(String name, String value) {
		this.parameterMap.put(name, value);
	}

	public void add(String name, List<String> value) {
		batchParameterMap.put(name, value);
	}

	public Map<String, List<String>> getBatchParameterMap() {
		return batchParameterMap;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}
}