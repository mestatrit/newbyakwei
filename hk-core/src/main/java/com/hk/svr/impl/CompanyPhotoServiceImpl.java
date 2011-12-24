package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.bean.CmpPhotoVote;
import com.hk.bean.CompanyPhoto;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CompanyPhotoService;

public class CompanyPhotoServiceImpl implements CompanyPhotoService {

	@Autowired
	private QueryManager manager;

	public void createPhoto(CompanyPhoto photo) {
		Query query = manager.createQuery();
		query.insertObject(photo);
	}

	public CompanyPhoto deleteCompanhPhoto(long photoId) {
		CompanyPhoto o = this.getCompanyPhoto(photoId);
		Query query = manager.createQuery();
		query.deleteById(CompanyPhoto.class, photoId);
		return o;
	}

	public List<CompanyPhoto> getPhotoListByCompanyId(long companyId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyPhoto.class, "companyid=?",
				new Object[] { companyId }, "photoid desc", begin, size);
	}

	public CompanyPhoto getCompanyPhoto(long photoId) {
		Query query = manager.createQuery();
		return query.getObjectById(CompanyPhoto.class, photoId);
	}

	public int countPhotoByCompanyIdNoLogo(long companyId, String logoPath) {
		Query query = manager.createQuery();
		return query.count(CompanyPhoto.class, "companyid=? and path!=?",
				new Object[] { companyId, logoPath });
	}

	public List<CompanyPhoto> getPhotoListByCompanyIdNoLogo(long companyId,
			String logoPath, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyPhoto.class, "companyid=? and path!=?",
				new Object[] { companyId, logoPath }, "photoid desc", begin,
				size);
	}

	public CompanyPhoto getFirstCompanyPhoto(long companyId) {
		Query query = manager.createQuery();
		return query.getObject(CompanyPhoto.class, "companyid=?",
				new Object[] { companyId }, "votecount desc,photoid desc");
	}

	public void updateName(long photoId, String name) {
		Query query = manager.createQuery();
		query.addField("name", name);
		query.updateById(CompanyPhoto.class, photoId);
	}

	public void updatePinkflg(long photoId, byte pinkflg) {
		Query query = manager.createQuery();
		query.addField("pinkflg", pinkflg);
		query.updateById(CompanyPhoto.class, photoId);
	}

	public List<CompanyPhoto> getPhotoListByCompanyIdVoteStyle(long companyId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyPhoto.class, "companyid=?",
				new Object[] { companyId }, "votecount desc, photoid desc",
				begin, size);
	}

	public void updateVoteCount(long photoId, int add) {
		CompanyPhoto companyPhoto = this.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return;
		}
		companyPhoto.setVoteCount(companyPhoto.getVoteCount() + add);
		Query query = manager.createQuery();
		query.updateObject(companyPhoto);
	}

	public List<CompanyPhoto> getNextCompanyPhotoListByCompanyId(
			long companyId, long photoId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyPhoto.class, "companyid=? and photoid>?",
				new Object[] { companyId, photoId },
				"votecount desc, photoid desc", begin, size);
	}

	public List<CompanyPhoto> getPreCompanyPhotoListByCompanyId(long companyId,
			long photoId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CompanyPhoto.class, "companyid=? and photoid>?",
				new Object[] { companyId, photoId },
				"votecount desc, photoid desc", begin, size);
	}

	public List<Long> getPhotoIdListByCompanyIdVoteStyle(long companyId) {
		Query query = manager.createQuery();
		String sql = "select photoid from companyphoto where companyid=? order by votecount desc, photoid desc";
		return query.listBySqlEx("ds1", sql, Long.class, companyId);
	}

	public boolean createCmpPhotoVote(CmpPhotoVote cmpPhotoVote) {
		Query query = manager.createQuery();
		if (query.count(CmpPhotoVote.class, "photoid=? and userid=?",
				new Object[] { cmpPhotoVote.getPhotoId(),
						cmpPhotoVote.getUserId() }) > 0) {
			return false;
		}
		query.insertObject(cmpPhotoVote);
		this.updateVoteCount(cmpPhotoVote.getPhotoId(), 1);
		return true;
	}

	public int countCmpPhotoVoteByPhotoId(long photoId) {
		Query query = manager.createQuery();
		return query.count(CmpPhotoVote.class, "photoid=?",
				new Object[] { photoId });
	}

	public CmpPhotoVote getCmpPhotoVote(long photoId, long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpPhotoVote.class, "photoid=? and userid=?",
				new Object[] { photoId, userId });
	}

	public void deleteCmpPhotoVote(long photoId, long userId) {
		Query query = manager.createQuery();
		query.delete(CmpPhotoVote.class, "photoid=? and userid=?",
				new Object[] { photoId, userId });
		this.updateVoteCount(photoId, -1);
	}

	public int countCompanyPhotoByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.count(CompanyPhoto.class, "companyid=?",
				new Object[] { companyId });
	}

	public void createCmpPhotoSet(CmpPhotoSet cmpPhotoSet) {
		Query query = manager.createQuery();
		long setId = query.insertObject(cmpPhotoSet).longValue();
		cmpPhotoSet.setSetId(setId);
	}

	public void createCmpPhotoSetRef(CmpPhotoSetRef cmpPhotoSetRef) {
		Query query = manager.createQuery();
		if (query
				.count(CmpPhotoSetRef.class,
						"companyid=? and setid=? and photoid=?", new Object[] {
								cmpPhotoSetRef.getCompanyId(),
								cmpPhotoSetRef.getSetId(),
								cmpPhotoSetRef.getPhotoId() }) > 0) {
			return;
		}
		long oid = query.insertObject(cmpPhotoSetRef).longValue();
		cmpPhotoSetRef.setOid(oid);
	}

	public void deleteCmpPhotoSet(long companyId, long setId) {
		Query query = manager.createQuery();
		query.delete(CmpPhotoSetRef.class, "companyid=? and setid=?",
				new Object[] { companyId, setId });
		query.delete(CmpPhotoSet.class, "companyid=? and setid=?",
				new Object[] { companyId, setId });
	}

	public void deleteCmpPhotosetRef(long companyId, long oid) {
		Query query = manager.createQuery();
		query.delete(CmpPhotoSetRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public CmpPhotoSet getCmpPhotoSet(long companyId, long setId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpPhotoSet.class, "companyid=? and setid=?",
				new Object[] { companyId, setId });
	}

	public CmpPhotoSetRef getCmpPhotoSetRef(long companyId, long oid) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpPhotoSetRef.class, "companyid=? and oid=?",
				new Object[] { companyId, oid });
	}

	public List<CmpPhotoSetRef> getCmpPhotoSetRefListByCompanyIdAndSetId(
			long companyId, long setId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpPhotoSetRef.class, "companyid=? and setid=?",
				new Object[] { companyId, setId }, "oid desc", begin, size);
	}

	public void updateCmpPhotoSet(CmpPhotoSet cmpPhotoSet) {
		Query query = manager.createQuery();
		query.updateObject(cmpPhotoSet);
	}

	public void setCmpPhotoSetPicPath(long companyId, long setId, String path) {
		Query query = manager.createQuery();
		query.addField("picpath", path);
		query.update(CmpPhotoSet.class, "companyid=? and setid=?",
				new Object[] { companyId, setId });
	}

	public List<CmpPhotoSet> getCmpPhotoSetListByCompanyId(long companyId) {
		Query query = manager.createQuery();
		return query.listEx(CmpPhotoSet.class, "companyid=?",
				new Object[] { companyId }, "setid desc");
	}

	public Map<Long, CompanyPhoto> getCompanyPhotoMapInId(long companyId,
			List<Long> idList) {
		Query query = manager.createQuery();
		List<CompanyPhoto> list = query.listInField(CompanyPhoto.class,
				"companyid=?", new Object[] { companyId }, "photoid", idList,
				null);
		Map<Long, CompanyPhoto> map = new HashMap<Long, CompanyPhoto>();
		for (CompanyPhoto o : list) {
			map.put(o.getPhotoId(), o);
		}
		return map;
	}

	public List<CmpPhotoSetRef> getCmpPhotoSetRefListByCompanyIdAndPhotoId(
			long companyId, long photoId) {
		Query query = manager.createQuery();
		return query.listEx(CmpPhotoSetRef.class, "companyid=? and photoid=?",
				new Object[] { companyId, photoId }, "oid desc");
	}

	public Map<Long, CmpPhotoSet> getCmpPhotoSetMapByCompanyIdAndInId(
			long companyId, List<Long> idList) {
		Query query = manager.createQuery();
		List<CmpPhotoSet> list = query.listInField(CmpPhotoSet.class,
				"companyid=?", new Object[] { companyId }, "setid", idList,
				null);
		Map<Long, CmpPhotoSet> map = new HashMap<Long, CmpPhotoSet>();
		for (CmpPhotoSet o : list) {
			map.put(o.getSetId(), o);
		}
		return map;
	}
}