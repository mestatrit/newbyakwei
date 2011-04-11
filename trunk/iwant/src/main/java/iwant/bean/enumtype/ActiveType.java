package iwant.bean.enumtype;

/**
 * 项目的是否激活信息描述
 * 
 * @author akwei
 */
public enum ActiveType {
	/**
	 * 未设置
	 */
	ALL(0),
	/**
	 * 未激活
	 */
	NOTACTIVE(1),
	/**
	 * 已激活
	 */
	ACTIVE(2);

	private int value;

	public int getValue() {
		return value;
	}

	private ActiveType(int value) {
		this.value = value;
	}
}
