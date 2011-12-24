package com.hk.svr;

import java.util.List;

import com.hk.bean.City;
import com.hk.bean.CityCode;
import com.hk.bean.Country;
import com.hk.bean.Pcity;
import com.hk.bean.Province;
import com.hk.bean.ZoneInfo;

public interface ZoneService {

	List<City> getCityList();

	List<City> getCityList(int provinceId);

	List<Province> getProvinceList();

	/**
	 * 模糊查询
	 * 
	 * @param province
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Province> getProvinceList(String province, int begin, int size);

	List<Province> getProvinceList(String province);

	List<Province> getProvinceList(int countryId, String province);

	/**
	 * 模糊查询
	 * 
	 * @param province
	 * @return
	 */
	Province getProvinceLike(String province);

	Province getProvince(int provinceId);

	Country getCountry(int countryId);

	City getCity(int cityId);

	List<Province> getProvinceList(int countryId);

	List<Country> getCountryList();

	/**
	 * 模糊查询
	 * 
	 * @param city
	 * @param begin
	 * @param size
	 * @return
	 */
	List<City> getCityList(String city, int begin, int size);

	List<City> getCityList(String city);

	List<City> getCityList(int countryId, int provinceId, String city);

	/**
	 * 模糊查询
	 * 
	 * @param city
	 * @return
	 */
	City getCityLike(String city);

	void tmpcreateCityCode(String name, String code);

	CityCode getCityCodeByCode(String code);

	ZoneInfo getZoneInfoByZoneName(String zoneName);

	List<Pcity> getPcityList();

	// Pcity getPcityByName(String name);
	List<Pcity> getPcityListByCountryId(int countryId);

	boolean createCountry(Country country);

	boolean createProvince(Province province);

	boolean createCity(City city);

	boolean updateCountry(Country country);

	boolean updateProvince(Province province);

	boolean updateCity(City city);

	void deleteCountry(int countryId);

	void deleteProvince(int provinceId);

	void deleteCity(int cityId);

	void updateCityData();

	/**
	 * 模糊查询
	 * 
	 * @param country
	 * @return
	 */
	Country getCountryLike(String country);
}