package com.hk.web.pub.action;

public class ViewProcess {
	private int error;

	private String error_msg;

	private String functionName;

	private String op_func;

	private int view_obj_idx;

	private String jspPath;

	private Object respValue;

	public void setRespValue(Object respValue) {
		this.respValue = respValue;
	}

	public Object getRespValue() {
		return respValue;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getOp_func() {
		return op_func;
	}

	public void setOp_func(String op_func) {
		this.op_func = op_func;
	}

	public void setView_obj_idx(int view_obj_idx) {
		this.view_obj_idx = view_obj_idx;
	}

	public int getView_obj_idx() {
		return view_obj_idx;
	}
}