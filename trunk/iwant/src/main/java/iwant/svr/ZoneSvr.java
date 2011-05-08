package iwant.svr;

import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.Province;

import java.util.List;

/**
 * 地区相关逻辑
 * 
 * @author akwei
 */
public interface ZoneSvr {

	Country getCountry(int countryid);

	/**
	 * 创建省
	 * 
	 * @param province
	 * @return true:创建成功;false:有重名，创建失败
	 */
	boolean createProvince(Province province);

	/**
	 * 更新省
	 * 
	 * @param province
	 * @return true:更新成功;false:有重名，更新失败
	 */
	boolean updateProvince(Province province);

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

	/**
	 * 创建城市
	 * 
	 * @param city
	 * @return true:创建成功;false:在同一个省下面存在相同城市名称，创建失败
	 */
	boolean createCity(City city);

	/**
	 * @param city
	 * @return true:更新成功;false:在同一个省下面存在相同城市名称，更新失败
	 */
	boolean updateCity(City city);

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

	/**
	 * 获得所有城市集合，按照字母正序排列
	 * 
	 * @return
	 */
	List<City> getCityList();
}
