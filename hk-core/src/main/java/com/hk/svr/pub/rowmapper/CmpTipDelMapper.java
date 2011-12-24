package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTipDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTipDelMapper extends HkRowMapper<CmpTipDel> {

	@Override
	public Class<CmpTipDel> getMapperClass() {
		return CmpTipDel.class;
	}

	public CmpTipDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTipDel o = new CmpTipDel();
		o.setTipId(rs.getLong("tipid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setShowflg(rs.getByte("showflg"));
		o.setDoneflg(rs.getByte("doneflg"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}