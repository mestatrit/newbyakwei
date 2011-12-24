package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpModule;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpModuleMapper extends HkRowMapper<CmpModule> {
	@Override
	public Class<CmpModule> getMapperClass() {
		return CmpModule.class;
	}

	public CmpModule mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpModule o = new CmpModule();
		o.setCompanyId(rs.getLong("companyid"));
		o.setModuleId(rs.getInt("moduleid"));
		o.setSysId(rs.getLong("sysid"));
		o.setTitle(rs.getString("title"));
		o.setIntro(rs.getString("intro"));
		o.setTemplateId(rs.getInt("templateid"));
		o.setShowflg(rs.getByte("showflg"));
		return o;
	}
}
