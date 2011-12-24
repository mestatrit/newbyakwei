package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpNav;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpNavMapper extends HkRowMapper<CmpNav> {

	@Override
	public Class<CmpNav> getMapperClass() {
		return CmpNav.class;
	}

	public CmpNav mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpNav o = new CmpNav();
		o.setOid(rs.getLong("oid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setNlevel(rs.getInt("nlevel"));
		o.setReffunc(rs.getInt("reffunc"));
		o.setFilepath(rs.getString("filepath"));
		o.setShowflg(rs.getByte("showflg"));
		o.setOrderflg(rs.getInt("orderflg"));
		o.setParentId(rs.getLong("parentid"));
		o.setFileflg(rs.getByte("fileflg"));
		o.setShowInHome(rs.getByte("showinhome"));
		o.setHomepos(rs.getByte("homepos"));
		o.setApplyformflg(rs.getByte("applyformflg"));
		o.setFileShowflg(rs.getByte("fileshowflg"));
		o.setFileShowLink(rs.getString("fileshowlink"));
		o.setKindId(rs.getLong("kindid"));
		o.setTitle(rs.getString("title"));
		o.setIntro(rs.getString("intro"));
		o.setCssData(rs.getString("cssdata"));
		o.setBgPicPath(rs.getString("bgpicpath"));
		o.setBgflg(rs.getByte("bgflg"));
		o.setUrl(rs.getString("url"));
		return o;
	}
}