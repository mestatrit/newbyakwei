package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.CmpGroupQuestion;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpGroupQuestionMapper extends HkRowMapper<CmpGroupQuestion> {
	@Override
	public Class<CmpGroupQuestion> getMapperClass() {
		return CmpGroupQuestion.class;
	}

	public CmpGroupQuestion mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CmpGroupQuestion o = new CmpGroupQuestion();
		o.setOid(rs.getLong("oid"));
		o.setCmpgroupId(rs.getLong("cmpgroupid"));
		o.setQuest(rs.getString("quest"));
		o.setAnswer(rs.getString("answer"));
		return o;
	}
}