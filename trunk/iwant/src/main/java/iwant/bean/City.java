package iwant.bean;

import cactus.dao.annotation.Column;
import cactus.dao.annotation.Id;
import cactus.dao.annotation.Table;

@Table(name = "city")
public class City {

	@Id
	private int cityid;

	@Column
	private String name;

	@Column
	private int provniceid;

	@Column
	private int countryid;

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

	public int getProvniceid() {
		return provniceid;
	}

	public void setProvniceid(int provniceid) {
		this.provniceid = provniceid;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}
}
