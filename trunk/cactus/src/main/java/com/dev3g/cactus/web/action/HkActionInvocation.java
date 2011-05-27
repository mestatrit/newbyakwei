package com.dev3g.cactus.web.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Action调用器,每次请求都会创建一个新的调用器，负责把ActionMapping与拦截器进行拼装并运行
 * 
 * @author akwei
 */
public class HkActionInvocation {

	private ActionMapping actionMapping;

	private final List<HkInterceptor> hkInterceptorList = new ArrayList<HkInterceptor>(
			1);

	private Iterator<HkInterceptor> it;

	private String result;

	private HkRequest request;

	private HkResponse response;

	public HkActionInvocation(ActionMapping actionMapping, HkRequest request,
			HkResponse response, HkInterceptor hkInterceptor) {
		super();
		this.actionMapping = actionMapping;
		this.request = request;
		this.response = response;
		this.setInterceptor(hkInterceptor);
	}

	/**
	 * 负责合并拦截器
	 * 
	 * @param hkInterceptor
	 *            2010-11-20
	 */
	private void setInterceptor(HkInterceptor hkInterceptor) {
		if (hkInterceptor == null) {
			return;
		}
		if (hkInterceptor instanceof HkInterceprotStack) {
			HkInterceprotStack stack = (HkInterceprotStack) hkInterceptor;
			hkInterceptorList.addAll(stack.getList());
		}
		hkInterceptorList.add(hkInterceptor);
		this.it = this.hkInterceptorList.iterator();
	}

	public String getResult() {
		return result;
	}

	public boolean haxNext() {
		if (it == null) {
			return false;
		}
		return it.hasNext();
	}

	/**
	 * 按顺序执行拦截器以及Action,先执行拦截器的doBefor方法，在执行Action方法，最后执行拦截器doAfter方法
	 * 
	 * @return
	 * @throws Exception
	 *             2010-11-20
	 */
	public String invoke() throws Exception {
		HkInterceptor hkInterceptor = null;
		if (this.haxNext()) {
			hkInterceptor = it.next();
			this.result = hkInterceptor.doBefore(this);
		}
		else {
			if (actionMapping.hasMethod()) {
				if (actionMapping.getAsmAction() != null) {
					this.result = actionMapping.getAsmAction().execute(request,
							response);
				}
				else {
					this.result = (String) actionMapping.getActionMethod()
							.invoke(actionMapping.getAction(), request,
									response);
				}
			}
			else {
				this.result = actionMapping.getAction().execute(request,
						response);
			}
		}
		if (hkInterceptor != null) {
			hkInterceptor.doAfter(this);
		}
		return this.result;
	}
}