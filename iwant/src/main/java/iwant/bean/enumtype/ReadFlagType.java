package iwant.bean.enumtype;

public enum ReadFlagType {
	/**
	 * 未读
	 */
	NOTREAD(0),
	/**
	 * 已读
	 */
	READED(1);

	private int value;

	public int getValue() {
		return value;
	}

	private ReadFlagType(int value) {
		this.value = value;
	}
}