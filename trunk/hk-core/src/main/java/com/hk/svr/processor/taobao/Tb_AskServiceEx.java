package com.hk.svr.processor.taobao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.JsonKey;
import com.hk.bean.taobao.Tb_Answer;
import com.hk.bean.taobao.Tb_Answer_Status;
import com.hk.bean.taobao.Tb_Ask;
import com.hk.bean.taobao.Tb_Ask_Index;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Ask;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.search.AddDocProc;
import com.hk.frame.util.search.ResultMapper;
import com.hk.frame.util.search.SearchUtil;
import com.hk.jms.HkMsgProducer;
import com.hk.jms.JmsMsg;
import com.hk.svr.Tb_AskService;
import com.hk.svr.Tb_UserService;

public class Tb_AskServiceEx {

	@Autowired
	private Tb_AskService tbAskService;

	@Autowired
	private Tb_UserService tbUserService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	private String index_dir_base_path;

	private String index_allask_path;

	private String index_resolvedask_path;

	public List<Tb_Ask> getTb_AskListByUserid(long userid, boolean buildUser,
			int begin, int size) {
		List<Tb_Ask> list = this.tbAskService.getTb_AskListByUserid(userid,
				begin, size);
		if (buildUser) {
			this.buildUserForAsk(list);
		}
		return list;
	}

	public List<Tb_Ask> getTb_AskListForNew(boolean buildUser,
			boolean buildAnswer, int begin, int size) {
		List<Tb_Ask> list = this.tbAskService.getTb_AskListForNew(begin, size);
		if (buildUser) {
			this.buildUserForAsk(list);
		}
		if (buildAnswer) {
			this.buildAnswer(list);
		}
		return list;
	}

	public List<Tb_Answer> getTb_AnswerListByAid(long qid, boolean buildUser,
			int begin, int size) {
		List<Tb_Answer> list = this.tbAskService.getTb_AnswerListByAid(qid,
				begin, size);
		if (buildUser) {
			this.buildUserForAnswer(list);
		}
		return list;
	}

	private void buildUserForAnswer(List<Tb_Answer> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Answer o : list) {
			idList.add(o.getUserid());
		}
		Map<Long, Tb_User> map = this.tbUserService.getTb_UserMapInId(idList);
		for (Tb_Answer o : list) {
			o.setTbUser(map.get(o.getUserid()));
		}
	}

	private void buildUserForAsk(List<Tb_Ask> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Ask o : list) {
			idList.add(o.getUserid());
		}
		Map<Long, Tb_User> map = this.tbUserService.getTb_UserMapInId(idList);
		for (Tb_Ask o : list) {
			o.setTbUser(map.get(o.getUserid()));
		}
	}

	public void createTb_Ask(Tb_Ask tbAsk, boolean create_to_sina,
			String serverName, String contextPath) {
		this.tbAskService.createTb_Ask(tbAsk);
		Tb_User_Ask tbUserAsk = new Tb_User_Ask();
		tbUserAsk.setAid(tbAsk.getAid());
		tbUserAsk.setUserid(tbAsk.getUserid());
		tbUserAsk.setAskflg(Tb_User_Ask.ASKFLG_ASK);
		this.tbAskService.createTb_User_Ask(tbUserAsk);
		this.tbAskService.createTb_Ask_Index(tbAsk.getAid(),
				Tb_Ask_Index.FLG_ALLASK);
		Map<String, String> map = new HashMap<String, String>();
		map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_NEWS_CREATE_ASK);
		jmsMsg.setBody(JsonUtil.toJson(map));
		hkMsgProducer.send(jmsMsg.toMessage());
		if (create_to_sina) {
			map = new HashMap<String, String>();
			map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
			map.put(JsonKey.SERVER_NAME, serverName);
			map.put(JsonKey.CONTEXTPATH, contextPath);
			jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_OTHER_API_CREATE_ASK_TO_STATUS);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
	}

	public void createTb_Answer(Tb_Answer tbAnswer, Tb_Ask tbAsk,
			boolean create_to_sina, String serverName, String contextPath) {
		this.tbAskService.createTb_Answer(tbAnswer);
		// 提出问题的人与回答的人不是同一人，就需要设置为用户回答过的问题
		if (tbAsk.getUserid() != tbAnswer.getUserid()) {
			Tb_User_Ask tbUserAsk = new Tb_User_Ask();
			tbUserAsk.setAid(tbAnswer.getAid());
			tbUserAsk.setUserid(tbAnswer.getUserid());
			tbUserAsk.setAskflg(Tb_User_Ask.ASKFLG_ANSWER);
			this.tbAskService.createTb_User_Ask(tbUserAsk);
			Map<String, String> map = new HashMap<String, String>();
			map.put(JsonKey.ANSWER_ID, String.valueOf(tbAnswer.getAnsid()));
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_NEWS_CREATE_ANSWER);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
		this.tbAskService.addTb_AskAnswer_num(tbAsk.getAid(), 1);
		if (create_to_sina) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(JsonKey.ANSWER_ID, String.valueOf(tbAnswer.getAnsid()));
			map.put(JsonKey.SERVER_NAME, serverName);
			map.put(JsonKey.CONTEXTPATH, contextPath);
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_OTHER_API_CREATE_ANSWER_TO_STATUS);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
		if (tbAnswer.getUserid() != tbAsk.getUserid()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(JsonKey.ANSWER_ID, String.valueOf(tbAnswer.getAnsid()));
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_NOTICE_CREATE_ANSWER);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
	}

	public void deleteTb_Answer(Tb_Answer tbAnswer) {
		this.tbAskService.deleteTb_Answer(tbAnswer);
		Tb_Ask tbAsk = this.tbAskService.getTb_Ask(tbAnswer.getAid());
		if (tbAsk != null) {// 如果此id为用户选择的答案，就更新问题的答案id=0
			if (tbAsk.getAnsid() == tbAnswer.getAnsid()) {
				tbAsk.setAnsid(0);
			}
			tbAsk.setAnswer_num(tbAsk.getAnswer_num() - 1);
			if (tbAsk.getAnswer_num() <= 0) {
				tbAsk.setAnswer_num(0);
			}
			this.tbAskService.updateTb_Ask(tbAsk);
		}
	}

	public void deleteTb_Ask(Tb_Ask tbAsk) {
		this.tbAskService.deleteTb_User_Ask(tbAsk.getUserid(), tbAsk.getAid(),
				Tb_User_Ask.ASKFLG_ASK);
		this.tbAskService.deleteTb_AnswerByAid(tbAsk.getAid());
		this.tbAskService.deleteTb_Ask(tbAsk);
	}

	public Tb_Answer supportAnswer(long userid, long ansid) {
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return null;
		}
		Tb_Answer_Status tbAnswerStatus = this.tbAskService
				.getTb_Answer_Status(userid, ansid);
		if (tbAnswerStatus == null) {
			tbAnswer.setSupport_num(tbAnswer.getSupport_num() + 1);
			this.tbAskService.updateTb_AnswerSupportAndDiscmdInfo(ansid,
					tbAnswer.getSupport_num(), tbAnswer.getDiscmd_num());
			tbAnswerStatus = new Tb_Answer_Status();
			tbAnswerStatus.setUserid(userid);
			tbAnswerStatus.setAns_status(Tb_Answer_Status.ANS_STATUS_SUPPORT);
			tbAnswerStatus.setAnsid(ansid);
			tbAnswerStatus.setAid(tbAnswer.getAid());
			this.tbAskService.createTb_Answer_Status(tbAnswerStatus);
		}
		else {
			if (tbAnswerStatus.getAns_status() == Tb_Answer_Status.ANS_STATUS_DISCMD) {
				tbAnswer.setSupport_num(tbAnswer.getSupport_num() + 1);
				tbAnswer.setDiscmd_num(tbAnswer.getDiscmd_num() - 1);
				if (tbAnswer.getDiscmd_num() < 0) {
					tbAnswer.setDiscmd_num(0);
				}
				this.tbAskService.updateTb_AnswerSupportAndDiscmdInfo(ansid,
						tbAnswer.getSupport_num(), tbAnswer.getDiscmd_num());
				tbAnswerStatus
						.setAns_status(Tb_Answer_Status.ANS_STATUS_SUPPORT);
				this.tbAskService.updateTb_Answer_Status(tbAnswerStatus);
			}
		}
		return tbAnswer;
	}

	public Tb_Answer discmdAnswer(long userid, long ansid) {
		Tb_Answer tbAnswer = this.tbAskService.getTb_Answer(ansid);
		if (tbAnswer == null) {
			return null;
		}
		Tb_Answer_Status tbAnswerStatus = this.tbAskService
				.getTb_Answer_Status(userid, ansid);
		if (tbAnswerStatus == null) {
			tbAnswer.setDiscmd_num(tbAnswer.getDiscmd_num() + 1);
			this.tbAskService.updateTb_AnswerSupportAndDiscmdInfo(ansid,
					tbAnswer.getSupport_num(), tbAnswer.getDiscmd_num());
			tbAnswerStatus = new Tb_Answer_Status();
			tbAnswerStatus.setUserid(userid);
			tbAnswerStatus.setAns_status(Tb_Answer_Status.ANS_STATUS_DISCMD);
			tbAnswerStatus.setAnsid(ansid);
			tbAnswerStatus.setAid(tbAnswer.getAid());
			this.tbAskService.createTb_Answer_Status(tbAnswerStatus);
		}
		else {
			if (tbAnswerStatus.getAns_status() == Tb_Answer_Status.ANS_STATUS_SUPPORT) {
				tbAnswer.setSupport_num(tbAnswer.getSupport_num() - 1);
				tbAnswer.setDiscmd_num(tbAnswer.getDiscmd_num() + 1);
				if (tbAnswer.getSupport_num() < 0) {
					tbAnswer.setSupport_num(0);
				}
				this.tbAskService.updateTb_AnswerSupportAndDiscmdInfo(ansid,
						tbAnswer.getSupport_num(), tbAnswer.getDiscmd_num());
				tbAnswerStatus
						.setAns_status(Tb_Answer_Status.ANS_STATUS_DISCMD);
				this.tbAskService.updateTb_Answer_Status(tbAnswerStatus);
			}
		}
		return tbAnswer;
	}

	public void selectBestAnswer(Tb_Ask tbAsk, Tb_Answer tbAnswer) {
		tbAsk.setAnsid(tbAnswer.getAnsid());
		tbAsk.setResolve_status(Tb_Ask.RESOLVE_STATUS_Y);
		this.tbAskService.updateTb_Ask(tbAsk);
		if (tbAsk.getUserid() != tbAnswer.getUserid()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(JsonKey.ANSWER_ID, String.valueOf(tbAnswer.getAnsid()));
			map.put(JsonKey.ASK_ID, String.valueOf(tbAsk.getAid()));
			JmsMsg jmsMsg = new JmsMsg();
			jmsMsg.setHead(JmsMsg.HEAD_NOTICE_SELECT_BEST_ANSWER);
			jmsMsg.setBody(JsonUtil.toJson(map));
			hkMsgProducer.send(jmsMsg.toMessage());
		}
	}

	private final String field_aid = "0";

	private final String field_title = "1";

	private final String field_content = "2";

	private final String field_answer_content = "3";

	public void indexAllAsk() throws IOException {
		List<Tb_Ask> list = new ArrayList<Tb_Ask>();
		int begin = 0;
		int size = 1000;
		List<Tb_Ask> nlist = this.tbAskService.getTb_AskListForNew(begin, size);
		List<Long> idList = null;
		while (nlist.size() > 0) {
			idList = new ArrayList<Long>();
			for (Tb_Ask o : nlist) {
				if (o.getAnsid() > 0) {
					idList.add(o.getAnsid());
				}
			}
			Map<Long, Tb_Answer> ansmap = this.tbAskService
					.getTb_AnswerMapInId(idList);
			for (Tb_Ask o : nlist) {
				if (o.getAnsid() > 0) {
					o.setTbAnswer(ansmap.get(o.getAnsid()));
				}
			}
			list.addAll(nlist);
			begin = begin + size;
			nlist = this.tbAskService.getTb_AskListForNew(begin, size);
		}
		SearchUtil.indexDocUseIK(true, this.getIndex_dir_base_path()
				+ this.getIndex_allask_path(), list, new AddDocProc<Tb_Ask>() {

			@Override
			public Document buildDoc(Tb_Ask t) {
				Document doc = new Document();
				doc.add(new Field(field_aid, String.valueOf(t.getAid()),
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field(field_title, t.getTitle(), Field.Store.NO,
						Field.Index.ANALYZED));
				if (t.getContent() != null) {
					doc.add(new Field(field_content, t.getContent(),
							Field.Store.NO, Field.Index.ANALYZED));
				}
				if (t.getTbAnswer() != null
						&& t.getTbAnswer().getContent() != null) {
					doc
							.add(new Field(field_answer_content, t
									.getTbAnswer().getContent(),
									Field.Store.NO, Field.Index.ANALYZED));
				}
				return doc;
			}
		});
	}

	/**
	 * 增量添加问题索引
	 * 
	 * @throws IOException
	 * @throws IOException
	 *             2010-9-10
	 */
	public void addAskDocIntoIndex() throws IOException {
		List<Tb_Ask_Index> list = this.tbAskService.getTb_Ask_IndexListByFlg(
				Tb_Ask_Index.FLG_ALLASK, 0, 100);
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Ask_Index o : list) {
			idList.add(o.getAid());
		}
		Map<Long, Tb_Ask> map = this.tbAskService.getTb_AskMapInId(idList);
		List<Tb_Ask_Index> newlist = new ArrayList<Tb_Ask_Index>();
		idList = new ArrayList<Long>();
		for (Tb_Ask_Index o : list) {
			if (map.containsKey(o.getAid())) {
				o.setTbAsk(map.get(o.getAid()));
				if (o.getTbAsk().getAnsid() > 0) {
					idList.add(o.getTbAsk().getAnsid());
				}
				newlist.add(o);
			}
		}
		Map<Long, Tb_Answer> ansmap = this.tbAskService
				.getTb_AnswerMapInId(idList);
		for (Tb_Ask_Index o : list) {
			if (o.getTbAsk() != null) {
				o.getTbAsk().setTbAnswer(ansmap.get(o.getTbAsk().getAnsid()));
			}
		}
		boolean create = false;
		String path = this.getIndex_dir_base_path()
				+ this.getIndex_allask_path();
		if (!DataUtil.isFileDirectory(path)) {
			create = true;
		}
		SearchUtil.indexDocUseIK(create, this.getIndex_dir_base_path()
				+ this.getIndex_allask_path(), newlist,
				new AddDocProc<Tb_Ask_Index>() {

					@Override
					public Document buildDoc(Tb_Ask_Index t) {
						Document doc = new Document();
						doc.add(new Field(field_aid,
								String.valueOf(t.getAid()), Field.Store.YES,
								Field.Index.NOT_ANALYZED));
						doc.add(new Field(field_title, t.getTbAsk().getTitle(),
								Field.Store.NO, Field.Index.ANALYZED));
						if (t.getTbAsk().getContent() != null) {
							doc.add(new Field(field_content, t.getTbAsk()
									.getContent(), Field.Store.NO,
									Field.Index.ANALYZED));
						}
						if (t.getTbAsk().getTbAnswer() != null
								&& t.getTbAsk().getTbAnswer().getContent() != null) {
							doc.add(new Field(field_answer_content, t
									.getTbAsk().getTbAnswer().getContent(),
									Field.Store.NO, Field.Index.ANALYZED));
						}
						return doc;
					}
				});
		// 删除此集合的数据
		for (Tb_Ask_Index o : list) {
			this.tbAskService.deleteTb_Ask_Index(o.getOid());
		}
	}

	public void removeAskFromAllAskIndex(long aid) throws IOException {
		SearchUtil.removeDocFromIndex(this.getIndex_dir_base_path()
				+ this.getIndex_allask_path(), field_aid, String.valueOf(aid));
	}

	public void removeAskFromResolvedAskIndex(long aid) throws IOException {
		SearchUtil.removeDocFromIndex(this.getIndex_dir_base_path()
				+ this.getIndex_resolvedask_path(), field_aid, String
				.valueOf(aid));
	}

	public List<Tb_Ask> searchAll(String key, boolean buildAnswer,
			boolean buildAskUser, int begin, int size) throws IOException {
		Sort sort = new Sort();
		sort.setSort(new SortField(field_aid, SortField.INT, true));
		List<Long> idList;
		idList = SearchUtil.searchUseIK(key, this.getIndex_dir_base_path()
				+ this.getIndex_allask_path(), new String[] { field_title,
				field_content, field_answer_content }, sort, begin, size,
				new ResultMapper<Long>() {

					@Override
					public Long mapRow(Document doc) {
						return Long.parseLong(doc.get(field_aid));
					}
				});
		return this.buildTb_AskList(idList, buildAnswer, buildAskUser);
	}

	private List<Tb_Ask> buildTb_AskList(List<Long> aidList,
			boolean buildAnswer, boolean buildAskUser) {
		List<Tb_Ask> list = new ArrayList<Tb_Ask>();
		Map<Long, Tb_Ask> askmap = this.tbAskService.getTb_AskMapInId(aidList);
		for (Long l : aidList) {
			if (askmap.containsKey(l)) {
				list.add(askmap.get(l));
			}
		}
		if (buildAnswer) {
			this.buildAnswer(list);
		}
		if (buildAskUser) {
			this.buildUserForAsk(list);
		}
		return list;
	}

	private void buildAnswer(List<Tb_Ask> list) {
		List<Long> idList = new ArrayList<Long>();
		for (Tb_Ask o : list) {
			if (o.getAnsid() > 0) {
				idList.add(o.getAnsid());
			}
		}
		Map<Long, Tb_Answer> ansmap = this.tbAskService
				.getTb_AnswerMapInId(idList);
		for (Tb_Ask o : list) {
			o.setTbAnswer(ansmap.get(o.getAnsid()));
		}
	}

	public List<Tb_User_Ask> getTb_User_AskList(long userid, byte askflg,
			boolean buildAsk, boolean buildUser, boolean buildAnswer,
			int begin, int size) {
		List<Tb_User_Ask> list = this.tbAskService.getTb_User_AskList(userid,
				askflg, begin, size);
		List<Long> idList = null;
		if (buildAsk) {
			idList = new ArrayList<Long>();
			for (Tb_User_Ask o : list) {
				idList.add(o.getAid());
			}
			Map<Long, Tb_Ask> map = this.tbAskService.getTb_AskMapInId(idList);
			for (Tb_User_Ask o : list) {
				o.setTbAsk(map.get(o.getAid()));
			}
		}
		if (buildUser) {
			idList = new ArrayList<Long>();
			for (Tb_User_Ask o : list) {
				if (o.getTbAsk() != null) {
					idList.add(o.getTbAsk().getUserid());
				}
			}
			Map<Long, Tb_User> map = this.tbUserService
					.getTb_UserMapInId(idList);
			for (Tb_User_Ask o : list) {
				if (o.getTbAsk() != null) {
					o.getTbAsk().setTbUser(map.get(o.getTbAsk().getUserid()));
				}
			}
		}
		if (buildAnswer) {
			idList = new ArrayList<Long>();
			for (Tb_User_Ask o : list) {
				if (o.getTbAsk() != null && o.getTbAsk().getAnsid() > 0) {
					idList.add(o.getTbAsk().getAnsid());
				}
			}
			Map<Long, Tb_Answer> map = this.tbAskService
					.getTb_AnswerMapInId(idList);
			for (Tb_User_Ask o : list) {
				if (o.getTbAsk() != null && o.getTbAsk().getAnsid() > 0) {
					o.getTbAsk().setTbAnswer(map.get(o.getTbAsk().getAnsid()));
				}
			}
		}
		return list;
	}

	public String getIndex_dir_base_path() {
		return index_dir_base_path;
	}

	public void setIndex_dir_base_path(String indexDirBasePath) {
		index_dir_base_path = indexDirBasePath;
	}

	public String getIndex_allask_path() {
		return index_allask_path;
	}

	public void setIndex_allask_path(String indexAllaskPath) {
		index_allask_path = indexAllaskPath;
	}

	public String getIndex_resolvedask_path() {
		return index_resolvedask_path;
	}

	public void setIndex_resolvedask_path(String indexResolvedaskPath) {
		index_resolvedask_path = indexResolvedaskPath;
	}

	public List<Tb_Ask> getTb_AskListForNotResolved(boolean buildUser,
			int begin, int size) {
		List<Tb_Ask> list = this.tbAskService.getTb_AskListForNotResolved(
				begin, size);
		if (buildUser) {
			this.buildUserForAsk(list);
		}
		return list;
	}
}