package iwant.bean.enumtype;

public enum GenderType {
	/**
	 * 无
	 */
	NONE(0),
	/**
	 * 男
	 */
	MALE(1),
	/**
	 * 女
	 */
	FEMALE(2);

	private int value;

	public int getValue() {
		return value;
	}

	private GenderType(int value) {
		this.value = value;
	}
}