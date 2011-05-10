package iwant.svr.impl;

import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.District;
import iwant.bean.Province;
import iwant.dao.CityDao;
import iwant.dao.CountryDao;
import iwant.dao.DistrictDao;
import iwant.dao.ProvinceDao;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.NoCityExistException;

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

	@Autowired
	private DistrictDao districtDao;

	@Override
	public Country getCountry(int countryid) {
		return this.countryDao.getById(countryid);
	}

	@Override
	public void createCity(City city) throws DuplicateCityNameException {
		if (this.cityDao.isExistByProvinceidAndName(city.getProvinceid(), city
				.getName())) {
			throw new DuplicateCityNameException();
		}
		city.setCityid(NumberUtil.getInt(this.cityDao.save(city)));
	}

	@Override
	public void createProvince(Province province)
			throws DuplicateProvinceNameException {
		if (this.provinceDao.isExistByCountryidAndName(province.getCountryid(),
				province.getName())) {
			throw new DuplicateProvinceNameException();
		}
		province.setProvinceid(NumberUtil.getInt(this.provinceDao
				.save(province)));
	}

	@Override
	public void deleteCity(int cityid) {
		this.districtDao.deleteByCityid(cityid);
		this.cityDao.deleteById(cityid);
	}

	@Override
	public void deleteProvince(int provinceid) {
		this.districtDao.deleteByProvinceid(provinceid);
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
	public void updateCity(City city) throws DuplicateCityNameException {
		if (this.cityDao.isExistByProvinceidAndNameAndNotCityid(city
				.getProvinceid(), city.getName(), city.getCityid())) {
			throw new DuplicateCityNameException();
		}
		this.cityDao.update(city);
	}

	@Override
	public void updateProvince(Province province)
			throws DuplicateProvinceNameException {
		if (this.provinceDao.isExistByCountryidAndNameAndNotProvinceid(province
				.getCountryid(), province.getName(), province.getProvinceid())) {
			throw new DuplicateProvinceNameException();
		}
		this.provinceDao.update(province);
	}

	@Override
	public City getCityByNameLike(String name) {
		return this.cityDao.getByNameLike(name);
	}

	@Override
	public List<District> getDistrictListByCityid(int cityid) {
		return this.districtDao.getListByCityid(cityid);
	}

	@Override
	public void createDistrict(District district)
			throws DuplicateDistrictNameException {
		if (this.districtDao.isExistByCityidAndName(district.getCityid(),
				district.getName())) {
			throw new DuplicateDistrictNameException();
		}
		City city = this.getCity(district.getCityid());
		if (city == null) {
			throw new NoCityExistException("cityid : [ " + district.getCityid()
					+ " ]");
		}
		district.setCountryid(city.getCountryid());
		district.setProvinceid(city.getProvinceid());
		this.districtDao.save(district);
	}

	@Override
	public District getDistrict(int did) {
		return this.districtDao.getById(did);
	}

	@Override
	public void updateDistrict(District district)
			throws DuplicateDistrictNameException {
		if (this.districtDao.isExistByCityidAndNameAndNotDid(district
				.getCityid(), district.getName(), district.getDid())) {
			throw new DuplicateDistrictNameException();
		}
		City city = this.getCity(district.getCityid());
		if (city == null) {
			throw new NoCityExistException("cityid : [ " + district.getCityid()
					+ " ]");
		}
		district.setCountryid(city.getCountryid());
		district.setProvinceid(city.getProvinceid());
		this.districtDao.update(district);
	}

	@Override
	public void deleteDistrict(int did) {
		this.districtDao.deleteById(did);
	}

	@Override
	public District getDistrictByCityidAndNameLike(int cityid, String name) {
		return this.districtDao.getByCityidAndNameLike(cityid, name);
	}
}