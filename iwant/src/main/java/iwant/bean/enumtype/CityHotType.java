package iwant.bean.enumtype;

/**
 * 城市热度
 * 
 * @author akwei
 */
public enum CityHotType {
	/**
	 * 无
	 */
	NOTHOT(0),
	/**
	 * 男
	 */
	HOT(1);

	private int value;

	public int getValue() {
		return value;
	}

	private CityHotType(int value) {
		this.value = value;
	}
}
