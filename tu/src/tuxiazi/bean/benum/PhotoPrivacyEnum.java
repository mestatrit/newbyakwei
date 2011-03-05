package tuxiazi.bean.benum;

public enum PhotoPrivacyEnum {
	/**
	 * 公开图片
	 */
	PUBLIC(0),
	/**
	 * 私有图片
	 */
	PRIVATE(1);

	private int value;

	private PhotoPrivacyEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
