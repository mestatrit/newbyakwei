package iwant.svr;

public class OptStatus {

	private boolean success;

	private int error_code;

	/**
	 * 操作是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 如果失败，返回>0的状态码
	 * 
	 * @return
	 */
	public int getError_code() {
		return error_code;
	}

	public void setError_code(int errorCode) {
		error_code = errorCode;
	}
}