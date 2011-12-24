package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOtherWebInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOtherWebInfoMapper extends HkRowMapper<CmpOtherWebInfo> {

	@Override
	public Class<CmpOtherWebInfo> getMapperClass() {
		return CmpOtherWebInfo.class;
	}

	public CmpOtherWebInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOtherWebInfo o = new CmpOtherWebInfo();
		o.setCompanyId(rs.getLong("companyid"));
		o.setTotalFileSize(rs.getLong("totalfilesize"));
		o.setUsedFileSize(rs.getLong("usedfilesize"));
		o.setOrgcheck(rs.getByte("orgcheck"));
		return o;
	}
}