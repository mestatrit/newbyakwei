package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.BuildingTag;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BuildingTagMapper extends HkRowMapper<BuildingTag> {
	@Override
	public Class<BuildingTag> getMapperClass() {
		return BuildingTag.class;
	}

	public BuildingTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuildingTag o = new BuildingTag();
		o.setTagId(rs.getInt("tagid"));
		o.setCityId(rs.getInt("cityid"));
		o.setName(rs.getString("name"));
		o.setProvinceId(rs.getInt("provinceid"));
		return o;
	}
}