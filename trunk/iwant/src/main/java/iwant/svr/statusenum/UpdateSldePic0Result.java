package iwant.svr.statusenum;

public enum UpdateSldePic0Result {
	/**
	 * 操作成功
	 */
	SUCCESS(0),
	/**
	 * 图片文件不存在
	 */
	FILE_NOT_FOUND(1),
	/**
	 * 图片处理错误
	 */
	IMAGE_PROCESS_ERR(2);

	private int value;

	public int value() {
		return this.value;
	}

	private UpdateSldePic0Result(int value) {
		this.value = value;
	}
}