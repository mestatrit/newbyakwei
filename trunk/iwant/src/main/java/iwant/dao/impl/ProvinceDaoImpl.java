package iwant.dao.impl;

import iwant.bean.Province;
import iwant.dao.ProvinceDao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dev3g.cactus.dao.query.BaseDao;

@Component("provinceDao")
public class ProvinceDaoImpl extends BaseDao<Province> implements ProvinceDao {

	@Override
	public Class<Province> getClazz() {
		return Province.class;
	}

	@Override
	public List<Province> getListByCountryidOrderByOrder_flg(int countryid) {
		return this.getList(null, "countryid=?", new Object[] { countryid },
				"order_flg asc", 0, -1);
	}

	@Override
	public boolean isExistByCountryidAndName(int countryid, String name) {
		if (this.count("countryid=? and name=?",
				new Object[] { countryid, name }) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistByCountryidAndNameAndNotProvinceid(int countryid,
			String name, int provinceid) {
		if (this.count("countryid=? and name=? and provinceid!=?",
				new Object[] { countryid, name, provinceid }) > 0) {
			return true;
		}
		return false;
	}
}