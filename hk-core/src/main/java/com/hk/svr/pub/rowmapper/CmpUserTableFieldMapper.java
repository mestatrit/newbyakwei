package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUserTableField;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUserTableFieldMapper extends HkRowMapper<CmpUserTableField> {

	@Override
	public Class<CmpUserTableField> getMapperClass() {
		return CmpUserTableField.class;
	}

	public CmpUserTableField mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpUserTableField o = new CmpUserTableField();
		o.setFieldId(rs.getLong("fieldid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setDataId(rs.getLong("dataid"));
		o.setName(rs.getString("name"));
		o.setField_type(rs.getByte("field_type"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}