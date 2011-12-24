package com.hk.svr.pub;

public class TaoBaoAccessLimitException extends Exception {

	private static final long serialVersionUID = -1438751847173837674L;

	private int invoke_count;

	public TaoBaoAccessLimitException(String message, int invoke_count) {
		super(message);
		this.invoke_count = invoke_count;
	}

	public int getInvoke_count() {
		return invoke_count;
	}
}