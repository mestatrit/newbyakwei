package tuxiazi.bean.benum;

public enum NoticeReadEnum {
	/**
	 * 未读消息
	 */
	UNREAD(0),
	/**
	 * 已读消息
	 */
	READED(1);

	private int value;

	NoticeReadEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
