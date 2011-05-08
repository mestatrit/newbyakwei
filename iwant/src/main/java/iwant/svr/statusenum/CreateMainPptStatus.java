package iwant.svr.statusenum;

public enum CreateMainPptStatus {
	/**
	 * 成功
	 */
	SUCCESS(0),
	/**
	 * 项目不存在
	 */
	NOPROJECT(1);

	private int value;

	private CreateMainPptStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
