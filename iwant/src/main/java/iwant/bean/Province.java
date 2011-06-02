package iwant.bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "province")
public class Province {

	@Id
	private int provinceid;

	@Column
	private String name;

	@Column
	private int countryid;

	@Column
	private int order_flg;

	/**
	 * 隐藏标记,0:不隐藏,1:隐藏
	 */
	@Column
	private int hide_flg;

	public void setOrder_flg(int orderFlg) {
		order_flg = orderFlg;
	}

	public int getOrder_flg() {
		return order_flg;
	}

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}

	public int getHide_flg() {
		return hide_flg;
	}

	public void setHide_flg(int hideFlg) {
		hide_flg = hideFlg;
	}
}
