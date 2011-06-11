package com.dev3g.cactus.web.action;

import java.util.List;

public class HkInterceprotStack extends HkAbstractInterceptor {

	private boolean freeze;

	private List<HkInterceptor> list;

	public void setList(List<HkInterceptor> list) {
		if (this.freeze) {
			throw new RuntimeException("interceptor list is freeze");
		}
		this.list = list;
	}

	public List<HkInterceptor> getList() {
		return list;
	}
}