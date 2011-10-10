package iwant.svr;

import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.District;
import iwant.bean.Province;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.ProvinceNotFoundException;

import java.util.List;

/**
 * 地区相关逻辑
 * 
 * @author akwei
 */
public interface ZoneSvr {

	Country getCountry(int countryid);

	void createCountry(Country country);

	void updateCountry(Country country);

	void createProvince(Province province)
			throws DuplicateProvinceNameException;

	void updateProvince(Province province)
			throws DuplicateProvinceNameException;

	/**
	 * 删除省，并删除省下面的市
	 * 
	 * @param provinceid
	 */
	void deleteProvince(int provinceid);

	/**
	 * 获得省
	 * 
	 * @param provinceid
	 * @return
	 */
	Province getProvince(int provinceid);

	/**
	 * 获得指定国家的省数据集合，按照字母正序排列
	 * 
	 * @param countryid
	 * @return
	 */
	List<Province> getProvinceListByCountryid(int countryid);

	List<Province> getProvinceListByCountryidForShow(int countryid);

	void createCity(City city) throws DuplicateCityNameException,
			ProvinceNotFoundException;

	void updateCity(City city) throws DuplicateCityNameException,
			ProvinceNotFoundException;

	/**
	 * 删除城市
	 * 
	 * @param cityid
	 */
	void deleteCity(int cityid);

	/**
	 * 获得城市
	 * 
	 * @param cityid
	 * @return
	 */
	City getCity(int cityid);

	/**
	 * 获得省下的城市集合，按照字母正序排列
	 * 
	 * @param provinceid
	 * @return
	 */
	List<City> getCityListByProvinceid(int provinceid);

	List<City> getCityListByProvinceidForShow(int provinceid);

	/**
	 * 获得所有城市集合，按照字母正序排列
	 * 
	 * @return
	 */
	List<City> getCityList();

	City getCityByNameLike(String name);

	void createDistrict(District district)
			throws DuplicateDistrictNameException;

	void updateDistrict(District district)
			throws DuplicateDistrictNameException;

	void deleteDistrict(int did);

	District getDistrict(int did);

	District getDistrictByCityidAndNameLike(int cityid, String name);

	List<District> getDistrictListByCityid(int cityid);

	List<District> getDistrictListByCityidForShow(int cityid);
}
