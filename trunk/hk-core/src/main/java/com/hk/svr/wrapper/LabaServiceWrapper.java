package com.hk.svr.wrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.hk.bean.CompanyRefLaba;
import com.hk.bean.FavLaba;
import com.hk.bean.Follow;
import com.hk.bean.IndexLaba;
import com.hk.bean.IpCityLaba;
import com.hk.bean.IpCityRangeLaba;
import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.LabaDel;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.PinkLaba;
import com.hk.bean.RefLaba;
import com.hk.bean.RespLaba;
import com.hk.bean.Tag;
import com.hk.bean.TagLaba;
import com.hk.bean.UserLabaReply;
import com.hk.bean.UserRecentLaba;
import com.hk.listener.laba.LabaEventListener;
import com.hk.svr.FollowService;
import com.hk.svr.LabaService;
import com.hk.svr.impl.LabaIndxField;
import com.hk.svr.laba.parser.LabaInfo;

public class LabaServiceWrapper implements LabaService {

	private static Comparator<Long> comparator = new LabaIdComparator();

	private List<LabaEventListener> labaEventListenerList;

	private LabaService labaService;

	private FollowService followService;

	public void setLabaEventListenerList(
			List<LabaEventListener> labaEventListenerList) {
		this.labaEventListenerList = labaEventListenerList;
	}

	public void addLabaEventListener(LabaEventListener listener) {
		if (labaEventListenerList == null) {
			labaEventListenerList = new ArrayList<LabaEventListener>();
		}
		labaEventListenerList.add(listener);
	}

	public void setLabaService(LabaService labaService) {
		this.labaService = labaService;
	}

	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public long createLaba(LabaInfo labaInfo) {
		long labaId = this.labaService.createLaba(labaInfo);
		Laba laba = this.labaService.getLaba(labaId);
		for (LabaEventListener listener : this.labaEventListenerList) {
			listener.labaCreated(laba, labaInfo);
		}
		return labaId;
	}

	public Laba getLastUserLaba(long userId) {
		UserRecentLaba recentLaba = labaService.getUserRecentLaba(userId);
		Laba laba = null;
		if (recentLaba != null) {
			List<Long> idList = recentLaba.getLabaIdList();
			if (idList.size() > 0) {
				laba = labaService.getLaba(idList.iterator().next());
			}
		}
		return laba;
	}

	public List<Laba> getLabaListForFollowByUserId(long userId, int begin,
			int size) {
		List<Long> labaIdlist = this.getLabaIdListForFollowByUserId(userId,
				begin, size);
		List<Laba> list = labaService.getLabaListInId(labaIdlist);
		return list;
	}

	private List<Long> getLabaIdListForFollowByUserId(long userId, int begin,
			int size) {
		List<Follow> followList = followService.getFollowList(userId);
		List<Long> userIdList = new ArrayList<Long>();
		for (Follow o : followList) {
			userIdList.add(o.getFriendId());
		}
		userIdList.add(userId);
		Map<Long, UserRecentLaba> labamap = labaService
				.getUserRecentLabaMapInUser(userIdList);
		List<Long> labaIdlist = new ArrayList<Long>();
		for (UserRecentLaba recentLaba : labamap.values()) {
			if (recentLaba != null && recentLaba.getLabaData() != null
					&& !recentLaba.getLabaData().equals("")) {
				String[] t = recentLaba.getLabaData().split(",");
				for (int i = 0; i < t.length; i++) {
					labaIdlist.add(Long.parseLong(t[i]));
				}
			}
		}
		Collections.sort(labaIdlist, comparator);
		int end = 0;
		if (begin > labaIdlist.size() - 1) {
			return new ArrayList<Long>();
		}
		end = begin + size;
		if (end > labaIdlist.size() - 1) {
			end = labaIdlist.size();
		}
		labaIdlist = labaIdlist.subList(begin, end);
		return labaIdlist;
	}

	public List<UserLabaReply> getUserLabaReplyList(long userId, int begin,
			int size) {
		List<UserLabaReply> list = labaService.getUserLabaReplyList(userId,
				begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (UserLabaReply o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (UserLabaReply reply : list) {
			reply.setLaba(map.get(reply.getLabaId()));
		}
		return list;
	}

	public List<TagLaba> getTagLabaList(long tagId, int begin, int size) {
		List<TagLaba> list = labaService.getTagLabaList(tagId, begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (TagLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (TagLaba o : list) {
			o.setLaba(map.get(o.getLabaId()));
		}
		return list;
	}

	public List<TagLaba> getTagLabaListByUserId(long tagId, long userId,
			int begin, int size) {
		List<TagLaba> list = labaService.getTagLabaListByUserId(tagId, userId,
				begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (TagLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (TagLaba o : list) {
			o.setLaba(map.get(o.getLabaId()));
		}
		return list;
	}

	public List<FavLaba> getFavLabaListByUserId(long userId, int begin, int size) {
		List<FavLaba> list = labaService.getFavLabaListByUserId(userId, begin,
				size);
		List<Long> idList = new ArrayList<Long>();
		for (FavLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (FavLaba o : list) {
			o.setLaba(map.get(o.getLabaId()));
		}
		return list;
	}

	public List<IpCityLaba> getIpCityLabaList(int ipCityId, int begin, int size) {
		List<IpCityLaba> list = labaService.getIpCityLabaList(ipCityId, begin,
				size);
		List<Long> idList = new ArrayList<Long>();
		for (IpCityLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (IpCityLaba l : list) {
			l.setLaba(map.get(l.getLabaId()));
		}
		return list;
	}

	public List<Laba> getLabaListFromIpCity(int ipCityId, int begin, int size) {
		List<IpCityLaba> list = labaService.getIpCityLabaList(ipCityId, begin,
				size);
		List<Long> idList = new ArrayList<Long>();
		for (IpCityLaba o : list) {
			idList.add(o.getLabaId());
		}
		return labaService.getLabaListInId(idList);
	}

	public List<IpCityRangeLaba> getIpCityRangeLabaList(int rangeId, int begin,
			int size) {
		List<IpCityRangeLaba> list = labaService.getIpCityRangeLabaList(
				rangeId, begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (IpCityRangeLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (IpCityRangeLaba l : list) {
			l.setLaba(map.get(l.getLabaId()));
		}
		return list;
	}

	static final class LabaIdComparator implements Comparator<Long> {

		public int compare(Long o1, Long o2) {
			if (o1.longValue() > o2.longValue()) {
				return -1;
			}
			if (o1.longValue() == o2.longValue()) {
				return 0;
			}
			return 1;
		}
	}

	public List<UserLabaReply> getUserLabaReplyListByUserIdAndTime(long userId,
			Date beginTime, Date endTime, int begin, int size) {
		List<UserLabaReply> list = labaService
				.getUserLabaReplyListByUserIdAndTime(userId, beginTime,
						endTime, begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (UserLabaReply o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (UserLabaReply o : list) {
			o.setLaba(map.get(o.getLabaId()));
		}
		return list;
	}

	public void indexLaba(List<IndexLaba> list) {
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(labaService.getLabaIdxDir());
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(25000));
			LabaIndxField indxField = new LabaIndxField();
			for (IndexLaba o : list) {
				Document doc = new Document();
				doc.add(new Field(indxField.getLabaIdField(), o.getLabaId()
						+ "", Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field(indxField.getContentField(), o.getContent()
						+ "", Field.Store.NO, Field.Index.ANALYZED));
				iwriter.addDocument(doc);
			}
			iwriter.optimize();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			try {
				if (iwriter != null) {
					iwriter.close();
				}
			}
			catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Laba> getLabaListForSearch(String key, int begin, int size) {
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			LabaIndxField indxField = new LabaIndxField();
			File dir = new File(labaService.getLabaIdxDir());
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30, indxField
					.getContentField(), analyzer);
			Query query = parser.parse(key);
			Sort sort = new Sort();
			sort.setSort(new SortField(indxField.getLabaIdField(),
					SortField.DOC, false));
			int localSize = begin + size;
			TopFieldDocs tfd = isearcher.search(query, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			// Iterate through the results:
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long labaId = Long.parseLong(hitDoc.get(indxField
						.getLabaIdField()));
				idList.add(labaId);
			}
			if (idList.size() > size) {
				idList = idList.subList(idList.size() - size, idList.size());
			}
			return labaService.getLabaListInId(idList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Laba>();
		}
		finally {
			try {
				if (isearcher != null) {
					isearcher.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (directory != null) {
					directory.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得与企业相关的喇叭，不包括评论自动产生的喇叭
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 */
	public List<Laba> getLabaListByCompanyIdFromCompanyRefLaba(long companyId,
			int begin, int size) {
		List<CompanyRefLaba> reflist = labaService.getCompanyRefLabaList(
				companyId, begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (CompanyRefLaba companyRefLaba : reflist) {
			idList.add(companyRefLaba.getLabaId());
		}
		return labaService.getLabaListInId(idList);
	}

	public List<Laba> getInformationLabaList(long userId, long tagId,
			int begin, int size) {
		List<Long> idList = this.labaService.getInformationLabaIdList(userId,
				tagId, begin, size);
		return labaService.getLabaListInId(idList);
	}

	public List<Laba> getInformationUserLabaList(long userId, long tagId,
			int begin, int size) {
		List<Long> idList = this.labaService.getInformationLabaIdListForMe(
				userId, tagId, begin, size);
		return labaService.getLabaListInId(idList);
	}

	public List<RespLaba> getRespLabaList(int begin, int size) {
		List<RespLaba> list = this.labaService.getRespLabaList(begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (RespLaba o : list) {
			idList.add(o.getLabaId());
		}
		Map<Long, Laba> map = labaService.getLabaMapInId(idList);
		for (RespLaba o : list) {
			o.setLaba(map.get(o.getLabaId()));
		}
		return list;
	}

	public LabaDelInfo removeLaba(long userId, long labaId, boolean forBomb) {
		LabaDelInfo labaDelInfo = labaService.removeLaba(userId, labaId,
				forBomb);
		return labaDelInfo;
	}

	/****************/
	public boolean addTagForLaba(long labaId, long userId, long tagId,
			byte accessional) {
		return labaService.addTagForLaba(labaId, userId, tagId, accessional);
	}

	public void collectLaba(long userId, long labaId) {
		labaService.collectLaba(userId, labaId);
	}

	public int count() {
		return labaService.count();
	}

	public int countByUserId(long userId) {
		return labaService.countByUserId(userId);
	}

	public int countLaba(long userId, Date beginTime, Date endTime) {
		return labaService.countLaba(userId, beginTime, endTime);
	}

	public int counttUserLabaReplyListByUserIdAndTime(long userId,
			Date beginTime, Date endTime) {
		return labaService.counttUserLabaReplyListByUserIdAndTime(userId,
				beginTime, endTime);
	}

	public void delCollectLaba(long userId, long labaId) {
		labaService.delCollectLaba(userId, labaId);
	}

	public void deleteTagForLaba(long labaId, long tagId, long userId) {
		labaService.deleteTagForLaba(labaId, tagId, userId);
	}

	public void deleteTagForLaba(long labaId, long tagId) {
		labaService.deleteTagForLaba(labaId, tagId);
	}

	public List<Long> getCollectedLabaIdList(long userId, List<Long> idList) {
		return labaService.getCollectedLabaIdList(userId, idList);
	}

	public List<CompanyRefLaba> getCompanyRefLabaList(long companyId,
			int begin, int size) {
		return labaService.getCompanyRefLabaList(companyId, begin, size);
	}

	public List<Long> getInformationLabaIdList(long userId, long tagId,
			int begin, int size) {
		return labaService.getInformationLabaIdList(userId, tagId, begin, size);
	}

	public List<Long> getInformationLabaIdListForMe(long userId, long tagId,
			int begin, int size) {
		return labaService.getInformationLabaIdListForMe(userId, tagId, begin,
				size);
	}

	public List<Laba> getIpLabaList(String ip, int begin, int size) {
		return labaService.getIpLabaList(ip, begin, size);
	}

	public Laba getLaba(long labaId) {
		return labaService.getLaba(labaId);
	}

	public Map<Long, LabaDel> getLabaDelMapInId(List<Long> idList) {
		return labaService.getLabaDelMapInId(idList);
	}

	public String getLabaIdxDir() {
		return labaService.getLabaIdxDir();
	}

	public List<Laba> getLabaList(int begin, int size) {
		return labaService.getLabaList(begin, size);
	}

	public List<Laba> getLabaListByRefLabaIdAndUserId(long reflabaId,
			long userId) {
		return labaService.getLabaListByRefLabaIdAndUserId(reflabaId, userId);
	}

	public List<Laba> getLabaListByUserId(long userId, int begin, int size) {
		return labaService.getLabaListByUserId(userId, begin, size);
	}

	public List<Laba> getLabaListInId(List<Long> idList, int begin, int size) {
		return labaService.getLabaListInId(idList, begin, size);
	}

	public List<Laba> getLabaListInId(List<Long> idList) {
		return labaService.getLabaListInId(idList);
	}

	public Map<Long, Laba> getLabaMapInId(List<Long> idList) {
		return labaService.getLabaMapInId(idList);
	}

	public PinkLaba getPinkLaba(long labaId) {
		return labaService.getPinkLaba(labaId);
	}

	public RefLaba getRefLaba(long labaId, long userId) {
		return labaService.getRefLaba(labaId, userId);
	}

	public List<RefLaba> getRefLabaList(long labaId, int begin, int size) {
		return labaService.getRefLabaList(labaId, begin, size);
	}

	public Map<Long, RefLaba> getRefLabaMapInLabaIdByRefUserId(long refUserId,
			List<Long> idList) {
		return labaService.getRefLabaMapInLabaIdByRefUserId(refUserId, idList);
	}

	public List<RespLaba> getRespLabaList(Date beginTime, Date endTime) {
		return labaService.getRespLabaList(beginTime, endTime);
	}

	public List<Tag> getTagList(long labaId, byte accessional) {
		return labaService.getTagList(labaId, accessional);
	}

	public UserRecentLaba getUserRecentLaba(long userId) {
		return labaService.getUserRecentLaba(userId);
	}

	public Map<Long, UserRecentLaba> getUserRecentLabaMapInUser(
			List<Long> userIdList) {
		return labaService.getUserRecentLabaMapInUser(userIdList);
	}

	public boolean isCollected(long userId, long labaId) {
		return labaService.isCollected(userId, labaId);
	}

	public Laba reRemoveLaba(LabaDelInfo labaDelInfo) {
		return labaService.reRemoveLaba(labaDelInfo);
	}

	public void updateHotLaba(RespLaba respLaba) {
		labaService.updateHotLaba(respLaba);
	}

	public void createLabaCmt(LabaCmt labaCmt, LabaInfo labaInfo) {
		this.labaService.createLabaCmt(labaCmt, labaInfo);
		if (this.labaEventListenerList != null) {
			for (LabaEventListener listener : this.labaEventListenerList) {
				listener.labaCmtCreated(labaCmt, labaInfo);
			}
		}
	}

	public void deleteLabaCmt(long cmtId) {
		this.labaService.deleteLabaCmt(cmtId);
	}

	public List<LabaCmt> getLabaCmtListByLabaId(long labaId, int begin, int size) {
		return this.labaService.getLabaCmtListByLabaId(labaId, begin, size);
	}

	public List<Laba> getLabaList() {
		return this.labaService.getLabaList();
	}

	public void updateLaba(long labaId, String sContent, String lContent) {
		this.labaService.updateLaba(labaId, sContent, lContent);
	}

	public LabaCmt getLabaCmt(long cmtId) {
		return this.labaService.getLabaCmt(cmtId);
	}

	public List<LabaCmt> getUserLabaCmtListByLabaId(long labaId, long userId,
			int begin, int size) {
		return this.labaService.getUserLabaCmtListByLabaId(labaId, userId,
				begin, size);
	}

	public void processRespLaba(LabaInfo labaInfo) {
		this.labaService.processRespLaba(labaInfo);
	}

	public List<LabaCmt> getLabaCmtListInId(List<Long> idList) {
		return this.labaService.getLabaCmtListInId(idList);
	}

	public Map<Long, LabaCmt> getLabaCmtMapInId(List<Long> idList) {
		return this.labaService.getLabaCmtMapInId(idList);
	}

	public int countUserLabaReplyByUserId(long userId) {
		return this.labaService.countUserLabaReplyByUserId(userId);
	}
}