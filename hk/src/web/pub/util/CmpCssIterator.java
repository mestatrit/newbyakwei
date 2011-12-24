package web.pub.util;

public class CmpCssIterator {

	private int current = 0;

	private int odd = 0;

	private int even = 1;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getOdd() {
		return odd;
	}

	public void setOdd(int odd) {
		this.odd = odd;
	}

	public int getEven() {
		return even;
	}

	public void setEven(int even) {
		this.even = even;
	}

	public int getShow() {
		if (this.current == odd) {
			this.current = this.even;
		}
		else {
			this.current = this.odd;
		}
		return this.current;
	}
}