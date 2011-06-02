package iwant.svr.impl;

import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.District;
import iwant.bean.Province;
import iwant.bean.enumtype.ZoneHideType;
import iwant.dao.CityDao;
import iwant.dao.CountryDao;
import iwant.dao.DistrictDao;
import iwant.dao.ProvinceDao;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.CityNotFoundException;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.ProvinceNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dev3g.cactus.util.NumberUtil;

@Component("zoneSvr")
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
	public void createCountry(Country country) {
		country.setCountryid(NumberUtil.getInt(this.countryDao.save(country)));
	}

	@Override
	public void updateCountry(Country country) {
		this.countryDao.update(country);
	}

	@Override
	public void createCity(City city) throws DuplicateCityNameException,
			ProvinceNotFoundException {
		if (this.cityDao.isExistByProvinceidAndName(city.getProvinceid(), city
				.getName())) {
			throw new DuplicateCityNameException();
		}
		Province province = this.getProvince(city.getProvinceid());
		if (province == null) {
			throw new ProvinceNotFoundException();
		}
		city.setCountryid(province.getCountryid());
		city.setCityid(NumberUtil.getInt(this.cityDao.save(city)));
		city.setOrder_flg(city.getCityid());
		this.cityDao.update(city);
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
		province.setOrder_flg(province.getProvinceid());
		this.provinceDao.update(province);
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
	public void updateCity(City city) throws DuplicateCityNameException,
			ProvinceNotFoundException {
		if (this.cityDao.isExistByProvinceidAndNameAndNotCityid(city
				.getProvinceid(), city.getName(), city.getCityid())) {
			throw new DuplicateCityNameException();
		}
		Province province = this.getProvince(city.getProvinceid());
		if (province == null) {
			throw new ProvinceNotFoundException();
		}
		city.setCountryid(province.getCountryid());
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
		return this.cityDao.getByNameLike(this.formatZoneName(name));
	}

	private String formatZoneName(String name) {
		return name.replaceAll("市", "").replaceAll("省", "").replaceAll("区", "");
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
			throw new CityNotFoundException("cityid : [ "
					+ district.getCityid() + " ]");
		}
		district.setCountryid(city.getCountryid());
		district.setProvinceid(city.getProvinceid());
		district.setDid(NumberUtil.getInt(this.districtDao.save(district)));
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
			throw new CityNotFoundException("cityid : [ "
					+ district.getCityid() + " ]");
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
		return this.districtDao.getByCityidAndNameLike(cityid, this
				.formatZoneName(name));
	}

	@Override
	public List<City> getCityListByProvinceidForShow(int provinceid) {
		return this.cityDao.getList("provinceid=? and hide_flg=?",
				new Object[] { provinceid, ZoneHideType.SHOW.getValue() },
				"order_flg asc", 0, -1);
	}

	@Override
	public List<District> getDistrictListByCityidForShow(int cityid) {
		return this.districtDao.getList("cityid=? and hide_flg=?",
				new Object[] { cityid, ZoneHideType.SHOW.getValue() }, null, 0,
				-1);
	}

	@Override
	public List<Province> getProvinceListByCountryidForShow(int countryid) {
		return this.provinceDao.getList("countryid=? and hide_flg=?",
				new Object[] { countryid, ZoneHideType.SHOW.getValue() },
				"order_flg asc", 0, -1);
	}
}