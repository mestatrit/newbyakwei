package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.dao.query.QueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Fans;
import tuxiazi.dao.FansDao;

@Component("fansDao")
public class FansDaoImpl extends BaseDao<Fans> implements FansDao {

	@Override
	public Class<Fans> getClazz() {
		return Fans.class;
	}

	public Fans getByUseridAndFansid(long userid, long fansid) {
		return this.getObject("userid=? and fansid=?", new Object[] { userid,
				fansid });
	}

	public int deleteByUseridAndFansid(long userid, long fansid) {
		return this.delete("userid=? and fansid=?", new Object[] { userid,
				fansid });
	}

	public int countByUserid(long userid) {
		return this.count("userid=?", new Object[] { userid });
	}

	public List<Long> getFansidListByUserid(long userid) {
		QueryParam queryParam = new QueryParam(this.getKey(), null);
		queryParam.addClass(Fans.class);
		queryParam.setOrder("oid desc");
		queryParam.setColumns(new String[][] { new String[] { "fansid" } });
		queryParam.setWhereAndParams("userid=?", new Object[] { userid });
		return this.hkObjQuery.getList(queryParam, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getLong("fansid");
			}
		});
	}

	public List<Fans> getListByUserid(long userid, int begin, int size) {
		return this.getList(null, "userid=?", new Object[] { userid },
				"oid desc", begin, size);
	}
}
