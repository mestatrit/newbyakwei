package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUserTableDataOption;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUserTableDataOptionMapper extends
		HkRowMapper<CmpUserTableDataOption> {

	@Override
	public Class<CmpUserTableDataOption> getMapperClass() {
		return CmpUserTableDataOption.class;
	}

	public CmpUserTableDataOption mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpUserTableDataOption o = new CmpUserTableDataOption();
		o.setOptionId(rs.getLong("optionid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setDataId(rs.getLong("dataid"));
		o.setFieldId(rs.getLong("fieldid"));
		o.setData(rs.getString("data"));
		o.setOrderflg(rs.getInt("orderflg"));
		return o;
	}
}