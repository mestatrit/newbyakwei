package com.hk.svr;

import java.util.List;
import com.hk.bean.BuildingTag;

public interface BuildingTagService {
	/**
	 * 如果有同名，就不创建
	 * 
	 * @param name
	 */
	boolean createBuildingTag(BuildingTag buildingTag);

	void updateBuildingTag(BuildingTag buildingTag);

	void deleteBuildingTag(int tagId);

	List<BuildingTag> getBuildingTagList(String name, int cityId, int provinceId);

//	List<BuildingTag> getBuildingTagList(String name, int cityId,
//			int provinceId, int begin, int size);

	BuildingTag getBuildingTag(int tagId);
}