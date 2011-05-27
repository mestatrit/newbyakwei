package iwant.dao;

import iwant.bean.District;

import java.util.List;

import com.dev3g.cactus.dao.query.IDao;

public interface DistrictDao extends IDao<District> {

	List<District> getListByCityid(int cityid);

	void deleteByProvinceid(int provinceid);

	void deleteByCityid(int cityid);

	boolean isExistByCityidAndName(int cityid, String name);

	boolean isExistByCityidAndNameAndNotDid(int cityid, String name, int did);

	District getByCityidAndNameLike(int cityid, String name);
}
