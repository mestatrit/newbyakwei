package tuxiazi.bean.benum;

/**
 * 通知类型
 * 
 * @author Administrator
 */
public enum NoticeEnum {
	/**
	 * 关注某人的通知
	 */
	ADD_FOLLOW(0),
	/**
	 * 有人评论的你的图片
	 */
	ADD_PHOTOCMT(1),
	/**
	 * 有人喜欢你的图片
	 */
	ADD_PHOTOLIKE(2);

	private int value;

	private NoticeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
