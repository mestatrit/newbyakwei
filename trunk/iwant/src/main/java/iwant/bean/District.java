package iwant.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import iwant.bean.enumtype.ZoneHideType;

@Table(name = "district")
public class District {

	@Id
	private int did;

	@Column
	private int cityid;

	@Column
	private int provinceid;

	@Column
	private int countryid;

	@Column
	private String name;

	/**
	 * 隐藏标记,0:不隐藏,1:隐藏
	 */
	@Column
	private int hide_flg;

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
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

	public int getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(int provinceid) {
		this.provinceid = provinceid;
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

	public boolean isHidden() {
		if (this.hide_flg == ZoneHideType.HIDDEN.getValue()) {
			return true;
		}
		return false;
	}
}