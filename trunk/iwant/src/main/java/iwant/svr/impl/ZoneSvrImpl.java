package iwant.svr.impl;

import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.Province;
import iwant.dao.CityDao;
import iwant.dao.CountryDao;
import iwant.dao.ProvinceDao;
import iwant.svr.ZoneSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cactus.util.NumberUtil;

public class ZoneSvrImpl implements ZoneSvr {

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private ProvinceDao provinceDao;

	@Autowired
	private CityDao cityDao;

	@Override
	public Country getCountry(int countryid) {
		return this.countryDao.getById(countryid);
	}

	@Override
	public boolean createCity(City city) {
		if (this.cityDao.isExistByProvinceidAndName(city.getProvniceid(), city
				.getName())) {
			return false;
		}
		city.setCityid(NumberUtil.getInt(this.cityDao.save(city)));
		return true;
	}

	@Override
	public boolean createProvince(Province province) {
		if (this.provinceDao.isExistByCountryidAndName(province.getCountryid(),
				province.getName())) {
			return false;
		}
		province.setProvinceid(NumberUtil.getInt(this.provinceDao
				.save(province)));
		return true;
	}

	@Override
	public void deleteCity(int cityid) {
		this.cityDao.deleteById(cityid);
	}

	@Override
	public void deleteProvince(int provinceid) {
		this.cityDao.deleteByProvinceid(provinceid);
		this.provinceDao.deleteById(provinceid);
	}

	@Override
	public City getCity(int cityid) {
		return this.cityDao.getById(cityid);
	}

	@Override
	public List<City> getCityList() {
		return this.cityDao.getList();
	}

	@Override
	public List<City> getCityListByProvinceid(int provinceid) {
		return this.cityDao.getListByProvinceid(provinceid);
	}

	@Override
	public Province getProvince(int provinceid) {
		return this.provinceDao.getById(provinceid);
	}

	@Override
	public List<Province> getProvinceListByCountryid(int countryid) {
		return this.provinceDao.getListByCountryidOrderByOrder_flg(countryid);
	}

	@Override
	public boolean updateCity(City city) {
		if (this.cityDao.isExistByProvinceidAndNameAndNotCityid(city
				.getProvniceid(), city.getName(), city.getCityid())) {
			return false;
		}
		this.cityDao.update(city);
		return true;
	}

	@Override
	public boolean updateProvince(Province province) {
		if (this.provinceDao.isExistByCountryidAndNameAndNotProvinceid(province
				.getCountryid(), province.getName(), province.getProvinceid())) {
			return false;
		}
		this.provinceDao.update(province);
		return true;
	}
}