package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Notice;

public interface Tb_NoticeService {

	void createTb_Notice(Tb_Notice tbNotice);

	void deleteTb_Notice(long noticeid);

	void updateTb_NoticeReadflg(long noticeid, byte readflg);

	Tb_Notice getTb_Notice(long noticeid);

	List<Tb_Notice> getTb_NoticeListByuserid(long userid, int begin, int size);

	int countNoReadNoticeByUserid(long userid);
}