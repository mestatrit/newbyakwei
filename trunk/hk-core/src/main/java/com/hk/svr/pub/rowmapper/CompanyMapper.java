package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.Company;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CompanyMapper extends HkRowMapper<Company> {

	@Override
	public Class<Company> getMapperClass() {
		return Company.class;
	}

	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company o = new Company();
		o.setCompanyId(rs.getLong("companyid"));
		o.setName(rs.getString("name"));
		o.setAddr(rs.getString("addr"));
		o.setHeadPath(rs.getString("headpath"));
		o.setKindId(rs.getInt("kindid"));
		o.setTel(rs.getString("tel"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setUserId(rs.getLong("userid"));
		o.setCreaterId(rs.getLong("createrid"));
		o.setTotalScore(rs.getInt("totalscore"));
		o.setTotalVote(rs.getInt("totalvote"));
		o.setReviewCount(rs.getInt("reviewcount"));
		o.setCompanyStatus(rs.getByte("companystatus"));
		o.setTotalMoney(rs.getInt("totalmoney"));
		o.setIntro(rs.getString("intro"));
		o.setMarkerX(rs.getDouble("markerx"));
		o.setMarkerY(rs.getDouble("markery"));
		o.setTraffic(rs.getString("traffic"));
		o.setFreezeflg(rs.getByte("freezeflg"));
		o.setStopflg(rs.getByte("stopflg"));
		o.setLogopath(rs.getString("logopath"));
		o.setPrice(rs.getDouble("price"));
		o.setHkb(rs.getInt("hkb"));
		o.setRebate(rs.getDouble("rebate"));
		o.setParentKindId(rs.getInt("parentkindid"));
		o.setMoney(rs.getInt("money"));
		o.setPcityId(rs.getInt("pcityid"));
		o.setPsearchType(rs.getByte("psearchtype"));
		o.setUid(rs.getLong("uid"));
		o.setUnionKindId(rs.getLong("unionkindid"));
		o.setMayorUserId(rs.getLong("mayoruserid"));
		o.setCheckInCount(rs.getInt("checkincount"));
		o.setCmpflg(rs.getByte("cmpflg"));
		o.setProductattrflg(rs.getByte("productattrflg"));
		o.setLogo2path(rs.getString("logo2path"));
		o.setSname(rs.getString("sname"));
		o.setWorkCount(rs.getInt("workcount"));
		return o;
	}
}