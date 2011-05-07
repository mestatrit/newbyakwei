package iwant.bean;

import cactus.dao.annotation.Column;
import cactus.dao.annotation.Id;
import cactus.dao.annotation.Table;

@Table(name = "country")
public class Country {

	@Id
	private int countryid;

	@Column
	private String name;

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}