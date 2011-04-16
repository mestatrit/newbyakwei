package iwant.dao;

import iwant.bean.Notice;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface NoticeDao extends IDao<Notice> {

	List<Notice> getList(int begin, int size);
}
