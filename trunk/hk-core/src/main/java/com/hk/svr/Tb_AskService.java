package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Answer_Status;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Ask_Index;
import com.hk.bean.taobao.Tb_User_Ask;

public interface Tb_AskService {

	void createTb_Ask(Tb_Ask tbAsk);

	/**
	 * 如果已经存在aid,userid,askflg相同的数据，就不再创建，不存在就创建
	 * 
	 * @param tbUserAsk
	 *            2010-9-10
	 */
	void createTb_User_Ask(Tb_User_Ask tbUserAsk);

	Tb_User_Ask getTb_User_Ask(long userid, long aid, byte askflg);

	List<Tb_User_Ask> getTb_User_AskList(long userid, byte askflg, int begin,
			int size);

	void deleteTb_User_Ask(long userid, long aid, byte askflg);

	void updateTb_Ask(Tb_Ask tbAsk);

	void deleteTb_Ask(Tb_Ask tbAsk);

	/**
	 * @param aid 问题id
	 * @return
	 *         2010-9-8
	 */
	Tb_Ask getTb_Ask(long aid);

	/**
	 * @param userid 用户id
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-8
	 */
	List<Tb_Ask> getTb_AskListByUserid(long userid, int begin, int size);

	List<Tb_Ask> getTb_AskListForNew(int begin, int size);

	List<Tb_Ask> getTb_AskListForResolved(int begin, int size);

	void createTb_Answer(Tb_Answer tbAnswer);

	void updateTb_Answer(Tb_Answer tbAnswer);

	void deleteTb_Answer(Tb_Answer tbAnswer);

	void deleteTb_AnswerByAid(long aid);

	/**
	 * @param ansid 答案id
	 * @return
	 *         2010-9-8
	 */
	Tb_Answer getTb_Answer(long ansid);

	/**
	 * @param qid 问题id
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-8
	 */
	List<Tb_Answer> getTb_AnswerListByAid(long aid, int begin, int size);

	void addTb_AskAnswer_num(long aid, int add);

	void createTb_Answer_Status(Tb_Answer_Status tbAnswerStatus);

	void updateTb_Answer_Status(Tb_Answer_Status tbAnswerStatus);

	Tb_Answer_Status getTb_Answer_Status(long userid, long ansid);

	void updateTb_AnswerSupportAndDiscmdInfo(long ansid, int support_num,
			int discmd_num);

	List<Tb_Ask_Index> getTb_Ask_IndexListByFlg(byte flg, int begin, int size);

	void deleteTb_Ask_Index(long oid);

	void createTb_Ask_Index(long aid, byte flg);

	Map<Long, Tb_Ask> getTb_AskMapInId(List<Long> idList);

	Map<Long, Tb_Answer> getTb_AnswerMapInId(List<Long> idList);

	List<Tb_Ask> getTb_AskListForNotResolved(int begin, int size);
}