package com.hk.svr.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Impression;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.ImpressionService;

public class ImpressionServiceImpl implements ImpressionService {
	@Autowired
	private QueryManager manager;

	public boolean createImpression(Impression impression) {
		Query query = this.manager.createQuery();
		if (query.count(Impression.class, "prouserid=? and senderid=?",
				new Object[] { impression.getProuserId(),
						impression.getSenderId() }) > 0) {
			return false;
		}
		impression.setCreateTime(new Date());
		query.addField("prouserid", impression.getProuserId());
		query.addField("senderid", impression.getSenderId());
		query.addField("content", impression.getContent());
		query.addField("createtime", impression.getCreateTime());
		query.insert(Impression.class);
		return true;
	}

	public void deleteImpression(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(Impression.class, oid);
	}

	public List<Impression> getImpressionListByProuserId(long prouserId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Impression.class, "prouserid=?",
				new Object[] { prouserId }, "oid desc", begin, size);
	}

	public void updateImpression(Impression impression) {
		Query query = this.manager.createQuery();
		query.addField("prouserid", impression.getProuserId());
		query.addField("senderid", impression.getSenderId());
		query.addField("content", impression.getContent());
		query.addField("createtime", impression.getCreateTime());
		query.update(Impression.class, "oid=?", new Object[] { impression
				.getOid() });
	}

	public Impression getImpression(long senderId, long prouserId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Impression.class,
				"senderid=? and prouserid=?", new Object[] { senderId,
						prouserId });
	}

	public Impression getImpression(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Impression.class, oid);
	}
}