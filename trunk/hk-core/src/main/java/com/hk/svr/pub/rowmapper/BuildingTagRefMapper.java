package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.BuildingTagRef;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class BuildingTagRefMapper extends HkRowMapper<BuildingTagRef> {
	@Override
	public Class<BuildingTagRef> getMapperClass() {
		return BuildingTagRef.class;
	}

	public BuildingTagRef mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuildingTagRef o = new BuildingTagRef();
		o.setCompanyId(rs.getInt("companyid"));
		o.setTagId(rs.getInt("tagid"));
		return o;
	}
}