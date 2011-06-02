package iwant.bean.enumtype;

public enum ZoneHideType {
	SHOW(0), HIDDEN(1);

	private int value;

	ZoneHideType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}