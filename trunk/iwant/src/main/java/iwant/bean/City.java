package iwant.bean;

import com.dev3g.cactus.dao.annotation.Column;
import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "city")
public class City {

	@Id
	private int cityid;

	@Column
	private String name;

	@Column
	private int provinceid;

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

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
	}

	public int getProvinceid() {
		return provinceid;
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
