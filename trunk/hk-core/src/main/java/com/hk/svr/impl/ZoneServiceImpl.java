package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CityCode;
import com.hk.bean.Country;
import com.hk.bean.Pcity;
import com.hk.bean.Province;
import com.hk.bean.ZoneInfo;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.BizCircleService;
import com.hk.svr.CmpActService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.ZoneAdminService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;

public class ZoneServiceImpl implements ZoneService {

	@Autowired
	private QueryManager queryManager;

	public List<City> getCityList() {
		Query query = queryManager.createQuery();
		return query.listEx(City.class);
	}

	public List<Country> getCountryList() {
		Query query = queryManager.createQuery();
		return query.listEx(Country.class);
	}

	public List<Province> getProvinceList() {
		Query query = queryManager.createQuery();
		return query.listEx(Province.class);
	}

	public List<City> getCityList(String city, int begin, int size) {
		Query query = queryManager.createQuery();
		query.setTable(City.class);
		query.where("city like ?").setParam("%" + city + "%");
		return query.list(begin, size, City.class);
	}

	public CityCode getCityCodeByCode(String code) {
		Query query = queryManager.createQuery();
		query.setTable(CityCode.class);
		query.where("code=?").setParam(code);
		return query.getObject(CityCode.class);
	}

	public List<Province> getProvinceList(String province, int begin, int size) {
		Query query = queryManager.createQuery();
		query.setTable(Province.class);
		query.where("province like ?").setParam("%" + province + "%");
		return query.list(begin, size, Province.class);
	}

	public List<City> getCityList(int provinceId) {
		Query query = queryManager.createQuery();
		return query.listEx(City.class, "provinceid=?",
				new Object[] { provinceId });
	}

	public List<Province> getProvinceList(int countryId) {
		Query query = queryManager.createQuery();
		return query.listEx(Province.class, "countryid=?",
				new Object[] { countryId });
	}

	public void tmpcreateCityCode(String name, String code) {
		Query query = queryManager.createQuery();
		query.addField("name", name);
		query.addField("code", code);
		query.insert(CityCode.class);
	}

	public List<City> getCityList(String city) {
		Query query = queryManager.createQuery();
		return query.listEx(City.class, "city like ?", new Object[] { "%"
				+ city + "%" });
	}

	public List<City> getCityList(int countryId, int provinceId, String city) {
		Query query = queryManager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select * from city where 1=1");
		if (countryId > 0) {
			olist.add(countryId);
			sql.append(" and countryid=?");
		}
		if (provinceId > 0) {
			olist.add(countryId);
			sql.append(" and provinceid=?");
		}
		if (!DataUtil.isEmpty(city)) {
			sql.append(" and city like ?");
			olist.add("%" + city + "%");
		}
		return query.listBySqlParamList("ds1", sql.toString(), City.class,
				olist);
	}

	public List<Province> getProvinceList(String province) {
		Query query = queryManager.createQuery();
		return query.listEx(Province.class, "province like ?",
				new Object[] { "%" + province + "%" });
	}

	public List<Province> getProvinceList(int countryId, String province) {
		Query query = queryManager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select * from province where 1=1");
		if (countryId > 0) {
			olist.add(countryId);
			sql.append(" and countryid=?");
		}
		sql.append(" and province like ?");
		olist.add("%" + province + "%");
		return query.listBySqlParamList("ds1", sql.toString(), Province.class,
				olist);
	}

	public City getCityLike(String city) {
		Query query = queryManager.createQuery();
		query.setTable(City.class);
		query.where("city like ?").setParam("%" + city + "%");
		return query.getObject(City.class);
	}

	public Province getProvinceLike(String province) {
		Query query = queryManager.createQuery();
		query.setTable(Province.class);
		query.where("province like ?").setParam("%" + province + "%");
		return query.getObject(Province.class);
	}

	public ZoneInfo getZoneInfoByZoneName(String zoneName) {
		List<City> citylist = this.getCityList(zoneName);
		ZoneInfo info = null;
		if (citylist.size() > 0) {
			info = new ZoneInfo();
			info.setCityId(citylist.iterator().next().getCityId());
		}
		else {
			List<Province> plist = this.getProvinceList(zoneName);
			if (plist.size() > 0) {
				info = new ZoneInfo();
				info.setProvinceId(plist.iterator().next().getProvinceId());
			}
		}
		return info;
	}

	public List<Pcity> getPcityList() {
		Query query = queryManager.createQuery();
		return query.listEx(Pcity.class, "cityid asc");
	}

	public Pcity getPcityByName(String name) {
		Query query = queryManager.createQuery();
		return query.getObjectEx(Pcity.class, "name=?", new Object[] { name });
	}

	public Pcity getPcityByNameLike(String name) {
		Query query = queryManager.createQuery();
		List<Pcity> list = query.listEx(Pcity.class, "name like ?",
				new Object[] { "%" + name + "%" }, "cityid desc", 0, 1);
		if (list.size() > 0) {
			return list.iterator().next();
		}
		return null;
	}

	public List<Pcity> getPcityListByCountryId(int countryId) {
		Query query = queryManager.createQuery();
		return query.listEx(Pcity.class, "countryid=?",
				new Object[] { countryId }, "cityid asc");
	}

	public boolean createCity(City city) {
		Query query = queryManager.createQuery();
		if (query.count(City.class, "city=?", new Object[] { city.getCity() }) > 0) {
			return false;
		}
		query.insertObject(city);
		return true;
	}

	public boolean createCountry(Country country) {
		Query query = queryManager.createQuery();
		if (query.count(Country.class, "country=?", new Object[] { country
				.getCountry() }) > 0) {
			return false;
		}
		query.insertObject(country);
		return true;
	}

	public boolean createProvince(Province province) {
		Query query = queryManager.createQuery();
		if (query.count(Province.class, "province=?", new Object[] { province
				.getProvince() }) > 0) {
			return false;
		}
		query.insertObject(province);
		return true;
	}

	public City getCity(int cityId) {
		Query query = queryManager.createQuery();
		return query.getObjectById(City.class, cityId);
	}

	public Country getCountry(int countryId) {
		Query query = queryManager.createQuery();
		return query.getObjectById(Country.class, countryId);
	}

	public Province getProvince(int provinceId) {
		Query query = queryManager.createQuery();
		return query.getObjectById(Province.class, provinceId);
	}

	public void deleteCity(int cityId) {
		Query query = queryManager.createQuery();
		query.deleteById(City.class, cityId);
	}

	public void deleteCountry(int countryId) {
		Query query = queryManager.createQuery();
		query.delete(Province.class, "countryid=?", new Object[] { countryId });
		query.delete(City.class, "countryid=?", new Object[] { countryId });
		query.deleteById(Country.class, countryId);
	}

	public void deleteProvince(int provinceId) {
		Query query = queryManager.createQuery();
		query.delete(City.class, "provinceid=?", new Object[] { provinceId });
		query.deleteById(Province.class, provinceId);
	}

	public boolean updateCity(City city) {
		Query query = queryManager.createQuery();
		City o = query.getObjectEx(City.class, "city=?", new Object[] { city
				.getCity() });
		if (o != null && o.getCityId() != city.getCityId()) {
			return false;
		}
		query.updateObject(city);
		return true;
	}

	public boolean updateCountry(Country country) {
		Query query = queryManager.createQuery();
		Country o = query.getObjectEx(Country.class, "country=?",
				new Object[] { country.getCountry() });
		if (o != null && o.getCountryId() != country.getCountryId()) {
			return false;
		}
		query.updateObject(country);
		return true;
	}

	public boolean updateProvince(Province province) {
		Query query = queryManager.createQuery();
		Province o = query.getObjectEx(Province.class, "province=?",
				new Object[] { province.getProvince() });
		if (o != null && o.getProvinceId() != province.getProvinceId()) {
			return false;
		}
		query.updateObject(province);
		return true;
	}

	public Country getCountryLike(String country) {
		Query query = queryManager.createQuery();
		return query.getObjectEx(Country.class, "country like ?",
				new Object[] { "%" + country + "%" });
	}

	private void createbeijing() {
		City city = new City();
		city.setProvinceId(1);
		city.setCountryId(1);
		city.setCity("北京市");
		this.createCity(city);
	}

	private void createshanghai() {
		City city = new City();
		city.setProvinceId(2);
		city.setCountryId(1);
		city.setCity("上海市");
		this.createCity(city);
	}

	private void createtianjin() {
		City city = new City();
		city.setProvinceId(4);
		city.setCountryId(1);
		city.setCity("天津市");
		this.createCity(city);
	}

	private void createchongqing() {
		City city = new City();
		city.setProvinceId(3);
		city.setCountryId(1);
		city.setCity("重庆市");
		this.createCity(city);
	}

	public void updateCityData() {
		this.createbeijing();
		this.createshanghai();
		this.createtianjin();
		this.createchongqing();
		ZoneUtil.initZoneMap();
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		CmpUnionService cmpUnionService = (CmpUnionService) HkUtil
				.getBean("cmpUnionService");
		ZoneAdminService zoneAdminService = (ZoneAdminService) HkUtil
				.getBean("zoneAdminService");
		CmpActService cmpActService = (CmpActService) HkUtil
				.getBean("cmpActService");
		CmpTipService cmpTipService = (CmpTipService) HkUtil
				.getBean("cmpTipService");
		BizCircleService bizCircleService = (BizCircleService) HkUtil
				.getBean("bizCircleService");
		companyService.updateCmpPCityIdData();
		cmpUnionService.updateCmpUnionPcityIdData();
		zoneAdminService.updatePcityIdData();
		cmpActService.updatepcityiddata();
		cmpTipService.updatepcityiddata();
		bizCircleService.updatecitydata();
	}
}