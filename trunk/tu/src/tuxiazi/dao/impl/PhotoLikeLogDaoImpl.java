package tuxiazi.dao.impl;

import halo.dao.query.BaseDao;
import halo.dao.query.PartitionTableInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tuxiazi.bean.PhotoLikeLog;
import tuxiazi.dao.PhotoLikeLogDao;
import tuxiazi.dao.PhotoLikeReport;

@Component("photoLikeLogDao")
public class PhotoLikeLogDaoImpl extends BaseDao<PhotoLikeLog> implements
		PhotoLikeLogDao {

	@Override
	public Class<PhotoLikeLog> getClazz() {
		return PhotoLikeLog.class;
	}

	@Override
	public List<PhotoLikeReport> buildPhotoLikeReportListByTime(Date begin,
			Date end) {
		String sql = "select photoid,count(photoid) as like_num from photolikelog where createtime>=? and createtime<=? group by photoid order by like_num desc";
		PartitionTableInfo partitionTableInfo = this.hkObjQuery.parse(
				PhotoLikeLog.class, null);
		List<PhotoLikeReport> list = this.hkObjQuery.getListBySQL(
				partitionTableInfo.getDsKey(), sql,
				new Object[] { begin, end }, 0, 100,
				new RowMapper<PhotoLikeReport>() {

					@Override
					public PhotoLikeReport mapRow(ResultSet rs, int arg1)
							throws SQLException {
						PhotoLikeReport o = new PhotoLikeReport(rs
								.getLong("photoid"), rs.getInt("like_num"));
						return o;
					}
				});
		return list;
	}
}
