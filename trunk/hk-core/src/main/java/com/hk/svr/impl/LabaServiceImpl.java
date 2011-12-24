package com.hk.svr.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.hk.bean.BombLaba;
import com.hk.bean.Bomber;
import com.hk.bean.CompanyRefLaba;
import com.hk.bean.CompanyRefLabaDel;
import com.hk.bean.FavLaba;
import com.hk.bean.IndexLaba;
import com.hk.bean.IpCityLaba;
import com.hk.bean.IpCityLabaDel;
import com.hk.bean.IpCityRangeLaba;
import com.hk.bean.IpCityRangeLabaDel;
import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.LabaDel;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.LabaSeq;
import com.hk.bean.LabaTag;
import com.hk.bean.LabaTagDel;
import com.hk.bean.PinkLaba;
import com.hk.bean.RefLaba;
import com.hk.bean.RespLaba;
import com.hk.bean.Tag;
import com.hk.bean.TagLaba;
import com.hk.bean.TagLabaDel;
import com.hk.bean.UserLaba;
import com.hk.bean.UserLabaDel;
import com.hk.bean.UserLabaReply;
import com.hk.bean.UserLabaReplyDel;
import com.hk.bean.UserRecentLaba;
import com.hk.bean.UserTag;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.BombService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.wrapper.LabaServiceWrapper;

/**
 * @author akwei
 */
public class LabaServiceImpl implements LabaService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private TagService tagService;

	@Autowired
	private BombService bombService;

	private String labaIdxDir;

	public void setLabaIdxDir(String labaIdxDir) {
		this.labaIdxDir = labaIdxDir;
	}

	public String getLabaIdxDir() {
		return labaIdxDir;
	}

	public long createLaba(LabaInfo labaInfo) {
		long labaId = this.createLabaData(labaInfo);
		if (labaInfo.getRefLabaId() > 0) {// 如果引用了喇叭，进行引用喇叭的处理
			this.createRefLaba(labaInfo);
			this.addRefCount(labaInfo.getRefLabaId(), 1);
		}
		this.createUserLaba(labaInfo, labaId);
		this.createUserRecentLaba(labaInfo);
		this.createUserLabaReply(labaInfo, labaId);
		if (labaInfo.isAddLabaTagRef()) {// 也过滤转发的企业喇叭
			this.createTagLaba(labaInfo, labaId);
			this.processCreateCompanyRefLaba(labaInfo, labaId);
		}
		this.createUserTag(labaInfo);
		this.initTagCountInfo(labaInfo);
		this.updateTagUse(labaInfo);
		return labaId;
	}

	public void processRespLaba(LabaInfo labaInfo) {
		long respLabaId = 0;
		if (labaInfo.getReplyLabaId() > 0) {
			respLabaId = labaInfo.getReplyLabaId();
		}
		else if (labaInfo.getRefLabaId() > 0) {
			respLabaId = labaInfo.getRefLabaId();
		}
		if (respLabaId > 0) {
			RespLaba o = this.getRespLaba(respLabaId);
			if (o == null) {
				this.createRespLaba(respLabaId);
			}
			else {
				o.setRespcount(o.getRespcount() + 1);
				o.setUptime(new Date());
				this.updateRespLaba(o);
			}
		}
	}

	private void createRespLaba(long labaId) {
		Query query = this.manager.createQuery();
		query.addField("labaid", labaId);
		query.addField("respcount", 1);
		query.addField("hot", 0);
		query.addField("uptime", new Date());
		query.insert(RespLaba.class);
	}

	private void updateRespLaba(RespLaba respLaba) {
		Query query = this.manager.createQuery();
		query.addField("respcount", respLaba.getRespcount());
		query.addField("hot", respLaba.getHot());
		query.addField("uptime", respLaba.getUptime());
		query.updateById(RespLaba.class, respLaba.getLabaId());
	}

	private RespLaba getRespLaba(long labaId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(RespLaba.class, labaId);
	}

	private void processCreateCompanyRefLaba(LabaInfo labaInfo, long labaId) {
		Query query = this.manager.createQuery();
		for (Long id : labaInfo.getCompanyIdList()) {
			query.setTable(CompanyRefLaba.class);
			query.where("labaid=? and companyid=?").setParam(labaId).setParam(
					id);
			if (query.count() == 0) {
				query.addField("labaid", labaId);
				query.addField("companyid", id);
				query.insert(CompanyRefLaba.class);
			}
		}
	}

	private void updateTagUse(LabaInfo labaInfo) {
		for (Long tagId : labaInfo.getTagIdList()) {
			this.tagService.updateUpdateTime(tagId, labaInfo.getCreateTime());
		}
	}

	private void addRefCount(long labaId, int add) {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class);
		query.addField("refcount", "add", add);
		if (add < 0) {
			query.where("labaid=? and refcount>?").setParam(labaId).setParam(0);
		}
		else {
			query.where("labaid=?").setParam(labaId);
		}
		query.update();
	}

	private void createRefLaba(LabaInfo labaInfo) {
		Query query = this.manager.createQuery();
		if (query.count(RefLaba.class, "labaid=? and refuserid=?",
				new Object[] { labaInfo.getRefLabaId(), labaInfo.getUserId() }) == 0) {
			RefLaba refLaba = new RefLaba();
			refLaba.setLabaId(labaInfo.getRefLabaId());
			refLaba.setRefUserId(labaInfo.getUserId());
			refLaba.setCreateTime(labaInfo.getCreateTime());
			query.insertObject(refLaba);
		}
	}

	private void initTagCountInfo(LabaInfo labaInfo) {
		for (Long tagId : labaInfo.getTagIdList()) {
			this.initTagCountInfo(tagId);
		}
	}

	private void initTagCountInfo(long tagId) {
		Query query = this.manager.createQuery();
		int labaCount = query.count(TagLaba.class, "tagid=?",
				new Object[] { tagId });
		int userCount = query.count(UserTag.class, "tagid=?",
				new Object[] { tagId });
		query.addField("labacount", labaCount);
		query.addField("usercount", userCount);
		query.updateById(Tag.class, tagId);
	}

	private void createUserTag(LabaInfo labaInfo) {
		for (Long tagId : labaInfo.getTagIdList()) {
			this.tagService.addUserTag(labaInfo.getUserId(), tagId);
		}
	}

	private void createUserLaba(LabaInfo labaInfo, long labaId) {
		Query query = this.manager.createQuery();
		UserLaba userLaba = new UserLaba();
		userLaba.setLabaId(labaId);
		userLaba.setUserId(labaInfo.getUserId());
		query.insertObject(userLaba);
	}

	private long createLabaData(LabaInfo labaInfo) {
		Date date = new Date();
		labaInfo.setCreateTime(date);
		Query query = this.manager.createQuery();
		/***************** 获得labaId **********************/
		query.addField("createtime", date);
		long labaId = query.insert(LabaSeq.class).longValue();
		/***************** 获得labaId end **********************/
		String content = labaInfo.getParsedContent();
		String longContent = labaInfo.getLongParsedContent();
		Laba laba = new Laba();
		laba.setLabaId(labaId);
		laba.setUserId(labaInfo.getUserId());
		laba.setContent(content);
		laba.setLongContent(longContent);
		laba.setCreateTime(date);
		laba.setIp(labaInfo.getIp());
		laba.setCityId(labaInfo.getCityId());
		laba.setRangeId(labaInfo.getRangeId());
		laba.setReplyCount(0);
		laba.setRefLabaId(labaInfo.getRefLabaId());
		laba.setRefcount(0);
		laba.setProductId(labaInfo.getProductId());
		laba.setSendFrom(labaInfo.getSendFrom());
		laba.setMainLabaId(labaInfo.getReplyLabaId());
		query.insertObject(laba);
		if (labaInfo.getCityId() > 0) {
			this.createIpCityLaba(labaInfo.getCityId(), labaId);
			if (labaInfo.getRangeId() > 0) {
				this.createIpCityRangeLaba(labaInfo.getRangeId(), labaId);
			}
		}
		return labaId;
	}

	private void createIpCityRangeLaba(int rangeId, long labaId) {
		Query query = this.manager.createQuery();
		IpCityRangeLaba o = new IpCityRangeLaba();
		o.setRangeId(rangeId);
		o.setLabaId(labaId);
		query.insertObject(o);
	}

	public void deleteTagForLaba(long labaId, long tagId) {
		Query query = this.manager.createQuery();
		query.delete(TagLaba.class, "labaid=? and tagid=?", new Object[] {
				labaId, tagId });
		query.delete(LabaTag.class, "labaid=? and tagid=?", new Object[] {
				labaId, tagId });
	}

	public void deleteTagForLaba(long labaId, long tagId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(TagLaba.class);
		query.where("labaid=? and tagid=? and userid=?").setParam(labaId)
				.setParam(tagId).setParam(userId);
		query.delete();
		query.setTable(LabaTag.class);
		query.where("labaid=? and tagid=? and userid=?").setParam(labaId)
				.setParam(tagId).setParam(userId);
		query.delete();
	}

	private void createTagLaba(LabaInfo labaInfo, long labaId) {
		for (Long tagId : labaInfo.getTagIdList()) {
			this.addTagForLaba(labaId, labaInfo.getUserId(), tagId,
					LabaTag.ACCESSIONAL_N);
		}
	}

	public boolean addTagForLaba(long labaId, long userId, long tagId,
			byte accessional) {
		Query query = this.manager.createQuery();
		if (query.count(TagLaba.class, " tagid=? and labaid=?", new Object[] {
				tagId, labaId }) == 0) {
			TagLaba tagLaba = new TagLaba();
			tagLaba.setLabaId(labaId);
			tagLaba.setTagId(tagId);
			tagLaba.setUserId(userId);
			query.insertObject(tagLaba);
			if (query.count(LabaTag.class, "labaid=? and tagid=?",
					new Object[] { labaId, tagId }) == 0) {
				LabaTag labaTag = new LabaTag();
				labaTag.setLabaId(labaId);
				labaTag.setUserId(userId);
				labaTag.setTagId(tagId);
				labaTag.setAccessional(accessional);
				query.insertObject(labaTag);
			}
			this.tagService.addUserTag(userId, tagId);
			this.initTagCountInfo(tagId);
			return true;
		}
		return false;
	}

	private void createIpCityLaba(int cityId, long labaId) {
		Query query = this.manager.createQuery();
		if (cityId > 0) {
			IpCityLaba o = new IpCityLaba();
			o.setCityId(cityId);
			o.setLabaId(labaId);
			query.insertObject(o);
		}
	}

	private void createUserRecentLaba(LabaInfo labaInfo) {
		this.createUserRecentLaba(labaInfo.getUserId());
	}

	private void createUserLabaReply(LabaInfo labaInfo, long labaId) {
		Query query = this.manager.createQuery();
		for (Long id : labaInfo.getUserIdList()) {
			UserLabaReply userLabaReply = new UserLabaReply();
			userLabaReply.setUserId(id);
			userLabaReply.setLabaId(labaId);
			query.insertObject(userLabaReply);
		}
	}

	public List<Laba> getLabaList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class).orderByDesc("labaid");
		List<Laba> list = query.list(begin, size, Laba.class);
		return list;
	}

	public List<Laba> getLabaListByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class);
		query.where("userid=?").setParam(userId).orderByDesc("labaid");
		return query.list(begin, size, Laba.class);
	}

	public UserRecentLaba getUserRecentLaba(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserRecentLaba.class, userId);
	}

	public Laba getLaba(long labaId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Laba.class, labaId);
	}

	private void deleteFromDel(LabaDelInfo labaDelInfo) {
		Query query = this.manager.createQuery();
		query.setTable(LabaDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除labadel
		query.setTable(UserLabaDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除UserLabaDel
		query.setTable(LabaTagDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除LabaTagDel
		query.setTable(TagLabaDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();
		query.setTable(IpCityLabaDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除IpCityLabaDel
		query.setTable(IpCityRangeLabaDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除IpCityRangeLabaDel
		query.setTable(UserLabaReplyDel.class);
		query.where("labaid=? and opuserid=? and optime=?").setParam(
				labaDelInfo.getLabaId()).setParam(labaDelInfo.getOpuserId())
				.setParam(labaDelInfo.getOptime());
		query.delete();// 删除UserLabaReplyDel
		query.setTable(BombLaba.class);
		query.where("labaid=?").setParam(labaDelInfo.getLabaId());
		query.delete();// 删除BombLaba
	}

	private void createFromDel(LabaDelInfo labaDelInfo) {
		long labaId = labaDelInfo.getLabaId();
		Object[] param = new Object[] { labaId, labaDelInfo.getOpuserId(),
				labaDelInfo.getOptime() };
		String where = "labaid=? and opuserid=? and optime=?";
		Query query = this.manager.createQuery();
		LabaDel labaDel = query.getObjectEx(LabaDel.class, where, param);
		if (labaDel != null) {
			Laba laba = new Laba();
			laba.setLabaId(labaDel.getLabaId());
			laba.setUserId(labaDel.getUserId());
			laba.setCityId(labaDel.getCityId());
			laba.setContent(labaDel.getContent());
			laba.setIp(labaDel.getIp());
			laba.setReplyCount(labaDel.getReplyCount());
			laba.setCreateTime(labaDel.getCreateTime());
			laba.setSendFrom(labaDel.getSendFrom());
			laba.setRangeId(labaDel.getRangeId());
			laba.setRefLabaId(labaDel.getRefLabaId());
			laba.setRefcount(labaDel.getRefcount());
			laba.setLongContent(labaDel.getLongContent());
			laba.setMainLabaId(labaDel.getMainLabaId());
			query.insertObject(laba);
			UserLabaDel userLabaDel = query.getObjectEx(UserLabaDel.class,
					where, param);
			if (userLabaDel != null) {
				UserLaba userLaba = new UserLaba();
				userLaba.setUserId(userLabaDel.getUserId());
				userLaba.setLabaId(userLabaDel.getLabaId());
				query.insertObject(userLaba);
			}
			List<UserLabaReplyDel> userlabareplylist = query.listEx(
					UserLabaReplyDel.class, where, param);
			for (UserLabaReplyDel reply : userlabareplylist) {
				UserLabaReply userLabaReply = new UserLabaReply();
				userLabaReply.setLabaId(reply.getLabaId());
				userLabaReply.setUserId(reply.getUserId());
				query.insertObject(userLabaReply);
			}
			List<TagLabaDel> taglabalist = query.listEx(TagLabaDel.class,
					where, param);
			for (TagLabaDel tl : taglabalist) {
				TagLaba tagLaba = new TagLaba();
				tagLaba.setLabaId(tl.getLabaId());
				tagLaba.setTagId(tl.getTagId());
				tagLaba.setUserId(tl.getUserId());
				query.insertObject(tagLaba);
			}
			List<LabaTagDel> labataglist = query.listEx(LabaTagDel.class,
					where, param);
			for (LabaTagDel tl : labataglist) {
				LabaTag labaTag = new LabaTag();
				labaTag.setLabaId(tl.getLabaId());
				labaTag.setTagId(tl.getTagId());
				labaTag.setUserId(tl.getUserId());
				labaTag.setAccessional(tl.getAccessional());
				query.insertObject(labaTag);
			}
			List<IpCityLabaDel> ipcitylabalist = query.listEx(
					IpCityLabaDel.class, where, param);
			for (IpCityLabaDel tl : ipcitylabalist) {
				IpCityLaba ipCityLaba = new IpCityLaba();
				ipCityLaba.setLabaId(tl.getLabaId());
				ipCityLaba.setCityId(tl.getCityId());
				query.insertObject(ipCityLaba);
			}
			List<IpCityRangeLabaDel> ipcityrangelabalist = query.listEx(
					IpCityRangeLabaDel.class, where, param);
			for (IpCityRangeLabaDel tl : ipcityrangelabalist) {
				IpCityRangeLaba ipCityRangeLaba = new IpCityRangeLaba();
				ipCityRangeLaba.setLabaId(tl.getLabaId());
				ipCityRangeLaba.setRangeId(tl.getRangeId());
				query.insertObject(ipCityRangeLaba);
			}
			this.createUserRecentLaba(labaDel.getUserId());
		}
	}

	public Laba reRemoveLaba(LabaDelInfo labaDelInfo) {
		this.createFromDel(labaDelInfo);
		this.processReDelCompanyRefLaba(labaDelInfo);
		this.deleteFromDel(labaDelInfo);
		this.initLabaTagCountInfo(labaDelInfo.getLabaId());
		return this.getLaba(labaDelInfo.getLabaId());
	}

	private void processReDelCompanyRefLaba(LabaDelInfo labaDelInfo) {
		String where = "opuserid=? and optime=? and labaid=?";
		Object[] param = { labaDelInfo.getOpuserId(), labaDelInfo.getOptime(),
				labaDelInfo.getLabaId() };
		Query query = this.manager.createQuery();
		List<CompanyRefLabaDel> list = query.listEx(CompanyRefLabaDel.class,
				where, param);
		// 从备份表中恢复
		for (CompanyRefLabaDel o : list) {
			query.addField("labaid", o.getLabaId());
			query.addField("companyid", o.getCompanyId());
			query.insert(CompanyRefLaba.class);
		}
		// 删除备份表中的数据
		query.delete(CompanyRefLabaDel.class, where, param);
	}

	private LabaDelInfo backToDelTable(long userId, long labaId) {
		Query query = this.manager.createQuery();
		Laba laba = this.getLaba(labaId);
		if (laba == null) {
			return null;
		}
		if (laba.getRefLabaId() > 0) {
			// 删除个人引用记录
			query.setTable(RefLaba.class);
			query.where("labaid=? and refuserid=?").setParam(
					laba.getRefLabaId()).setParam(laba.getUserId());
			query.delete();
		}
		long optime = System.currentTimeMillis();
		long opuserId = userId;
		LabaDelInfo labaDelInfo = new LabaDelInfo();
		labaDelInfo.setOptime(optime);
		labaDelInfo.setOpuserId(opuserId);
		labaDelInfo.setLabaId(labaId);
		labaDelInfo.setLabaUserId(laba.getUserId());
		query.addField("labaid", labaId);
		query.addField("userid", laba.getUserId());
		query.addField("cityid", laba.getCityId());
		query.addField("content", laba.getContent());
		query.addField("ip", laba.getIp());
		query.addField("replycount", laba.getReplyCount());
		query.addField("createtime", laba.getCreateTime());
		query.addField("sendfrom", laba.getSendFrom());
		query.addField("rangeid", laba.getRangeId());
		query.addField("reflabaid", laba.getRefLabaId());
		query.addField("refcount", laba.getRefcount());
		query.addField("longcontent", laba.getLongContent());
		query.addField("opuserid", opuserId);
		query.addField("optime", optime);
		query.addField("mainlabaid", laba.getMainLabaId());
		query.insert(LabaDel.class);
		query.setTable(UserLaba.class);
		if (laba.getRefLabaId() > 0) {
			this.addRefCount(laba.getRefLabaId(), -1);
		}
		query.where("userid=? and labaid=?").setParam(laba.getUserId())
				.setParam(labaId);
		UserLaba userLaba = query.getObject(UserLaba.class);
		if (userLaba != null) {
			query.addField("userid", userLaba.getUserId());
			query.addField("labaid", userLaba.getLabaId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.insert(UserLabaDel.class);
		}
		List<UserLabaReply> userlabareplylist = query.listEx(
				UserLabaReply.class, "labaid=?", new Object[] { labaId });
		for (UserLabaReply reply : userlabareplylist) {
			query.addField("labaid", reply.getLabaId());
			query.addField("userid", reply.getUserId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.insert(UserLabaReplyDel.class);
		}
		List<TagLaba> taglabalist = query.listEx(TagLaba.class, "labaid=?",
				new Object[] { labaId });
		for (TagLaba tl : taglabalist) {
			query.addField("labaid", tl.getLabaId());
			query.addField("tagid", tl.getTagId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.addField("userid", tl.getUserId());
			query.insert(TagLabaDel.class);
		}
		List<LabaTag> labataglist = query.listEx(LabaTag.class, "labaid=?",
				new Object[] { labaId });
		for (LabaTag tl : labataglist) {
			query.addField("labaid", tl.getLabaId());
			query.addField("tagid", tl.getTagId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.addField("userid", tl.getUserId());
			query.addField("accessional", tl.getAccessional());
			query.insert(LabaTagDel.class);
		}
		List<IpCityLaba> ipcitylabalist = query.listEx(IpCityLaba.class,
				"labaid=?", new Object[] { labaId });
		for (IpCityLaba tl : ipcitylabalist) {
			query.addField("labaid", tl.getLabaId());
			query.addField("cityid", tl.getCityId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.insert(IpCityLabaDel.class);
		}
		List<IpCityRangeLaba> ipcityrangelabalist = query.listEx(
				IpCityRangeLaba.class, "labaid=?", new Object[] { labaId });
		for (IpCityRangeLaba tl : ipcityrangelabalist) {
			query.addField("labaid", tl.getLabaId());
			query.addField("rangeid", tl.getRangeId());
			query.addField("opuserid", opuserId);
			query.addField("optime", optime);
			query.insert(IpCityRangeLabaDel.class);
		}
		return labaDelInfo;
	}

	private void deleteLaba(long labaUserId, long labaId) {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class);
		query.where("labaid=?").setParam(labaId).delete();
		query.deleteById(Laba.class, labaId);
		query.deleteById(UserLaba.class, labaId);
		query.delete(UserLabaReply.class, "labaid=?", new Object[] { labaId });
		query.delete(TagLaba.class, "labaid=?", new Object[] { labaId });
		query.delete(IpCityLaba.class, "labaid=?", new Object[] { labaId });
		query.delete(LabaTag.class, "labaid=?", new Object[] { labaId });
		query.delete(FavLaba.class, "labaid=?", new Object[] { labaId });
		query
				.delete(IpCityRangeLaba.class, "labaid=?",
						new Object[] { labaId });
		query.delete(PinkLaba.class, "labaid=?", new Object[] { labaId });
		// 删除喇叭响应表的数据
		query.deleteById(RespLaba.class, labaId);
		this.createUserRecentLaba(labaUserId);
	}

	public LabaDelInfo removeLaba(long userId, long labaId, boolean forBomb) {
		LabaDelInfo labaDelInfo = this.backToDelTable(userId, labaId);
		if (labaDelInfo == null) {
			return null;
		}
		this.processDelCompanyRefLaba(labaDelInfo);
		this.deleteLaba(labaDelInfo.getLabaUserId(), labaId);
		this.initLabaTagCountInfo(labaId);
		if (forBomb) {
			this.bombLaba(userId, labaId, labaDelInfo);
		}
		return labaDelInfo;
	}

	private LabaDelInfo bombLaba(long userId, long labaId,
			LabaDelInfo labaDelInfo) {
		if (labaDelInfo == null) {
			return null;
		}
		this.bombService.createBombLaba(userId, labaId, labaDelInfo);
		Bomber bomber = this.bombService.getBomber(userId);
		if (bomber.getUserLevel() != Bomber.USERLEVEL_SUPERADMIN) {
			this.bombService.addRemainBombCount(userId, -1);
		}
		return labaDelInfo;
	}

	private void processDelCompanyRefLaba(LabaDelInfo labaDelInfo) {
		Query query = this.manager.createQuery();
		List<CompanyRefLaba> list = query.listEx(CompanyRefLaba.class,
				"labaid=?", new Object[] { labaDelInfo.getLabaId() });
		for (CompanyRefLaba o : list) {// 把喇叭被分到删除表中
			query.addField("labaid", o.getLabaId());
			query.addField("companyid", o.getCompanyId());
			query.addField("opuserid", labaDelInfo.getOpuserId());
			query.addField("optime", labaDelInfo.getOptime());
			query.insert(CompanyRefLabaDel.class);
		}
		// 删除主表的数据
		query.delete(CompanyRefLaba.class, "labaid=?",
				new Object[] { labaDelInfo.getLabaId() });
	}

	private void initLabaTagCountInfo(long labaId) {
		Query query = this.manager.createQuery();
		List<LabaTag> list = query.listEx(LabaTag.class, "labaid=?",
				new Object[] { labaId });
		for (LabaTag o : list) {
			this.initTagCountInfo(o.getTagId());
		}
	}

	/**
	 * 交给@see LabaServiceWrapper 实现
	 */
	public List<Laba> getLabaListForFollowByUserId(long userId, int begin,
			int size) {
		return null;
	}

	public List<UserLabaReply> getUserLabaReplyList(long userId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(UserLabaReply.class);
		query.where("userid=?").setParam(userId).orderByDesc("labaid");
		return query.list(begin, size, UserLabaReply.class);
	}

	public List<UserLabaReply> getUserLabaReplyListByUserIdAndTime(long userId,
			Date beginTime, Date endTime, int begin, int size) {
		String sql = "select ulr.* from laba l,userlabareply ulr where ulr.userid=? and l.createtime>? and l.createtime<? and l.labaid=ulr.labaid order by ulr.labaid asc";
		Query query = this.manager.createQuery();
		return query.listBySql("ds1", sql, begin, size, UserLabaReply.class,
				userId, beginTime, endTime);
	}

	public int counttUserLabaReplyListByUserIdAndTime(long userId,
			Date beginTime, Date endTime) {
		String sql = "select count(*) from laba l,userlabareply ulr where ulr.userid=? and l.createtime>? and l.createtime<? and l.labaid=ulr.labaid";
		Query query = this.manager.createQuery();
		return query
				.queryForNumberBySql("ds1", sql, userId, beginTime, endTime)
				.intValue();
	}

	/**
	 * implements by LabaServiceWrapper
	 * 
	 * @see LabaServiceWrapper.
	 */
	public List<TagLaba> getTagLabaList(long tagId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(TagLaba.class);
		query.where("tagid=?").setParam(tagId).orderByDesc("labaid");
		return query.list(begin, size, TagLaba.class);
	}

	public List<TagLaba> getTagLabaListByUserId(long tagId, long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(TagLaba.class);
		query.where("tagid=? and userid=?").setParam(tagId).setParam(userId)
				.orderByDesc("labaid");
		return query.list(begin, size, TagLaba.class);
	}

	public void collectLaba(long userId, long labaId) {
		Laba laba = this.getLaba(labaId);
		if (laba == null) {
			return;
		}
		Query query = this.manager.createQuery();
		query.setTable(FavLaba.class);
		int count = query.where("userid=? and labaid=?").setParam(userId)
				.setParam(labaId).count();
		if (count > 0) {
			return;
		}
		query.addField("userid", userId);
		query.addField("labaid", labaId);
		query.insert(FavLaba.class);
	}

	public void delCollectLaba(long userId, long labaId) {
		Query query = this.manager.createQuery();
		query.setTable(FavLaba.class);
		query.where("userid=? and labaid=?").setParam(userId).setParam(labaId);
		query.delete();
	}

	public List<FavLaba> getFavLabaListByUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(FavLaba.class);
		query.where("userid=?").setParam(userId).orderByDesc("favid");
		return query.list(begin, size, FavLaba.class);
	}

	private ParameterizedRowMapper<Tag> tagMapper2 = new ParameterizedRowMapper<Tag>() {

		public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tag tag = new Tag();
			tag.setTagId(rs.getLong("tagid"));
			tag.setName(rs.getString("name"));
			tag.setUserId(rs.getLong("userid"));
			return tag;
		}
	};

	public List<Tag> getTagList(long labaId, byte accessional) {
		Query query = this.manager.createQuery();
		// query.setTable("labatag lt,tag t").setShowFields("t.*,lt.userid");
		// query.where("lt.labaid=? and lt.accessional=? and lt.tagid=t.tagid")
		// .setParam(labaId).setParam(accessional);
		// return query.list(tagMapper2);
		String sql = "select t.*,lt.userid from labatag lt,tag t where lt.labaid=? and lt.accessional=? and lt.tagid=t.tagid";
		return query.listBySql("ds1", sql, tagMapper2, labaId, accessional);
	}

	/**
	 * implements by {@link LabaServiceWrapper}
	 */
	public List<IpCityLaba> getIpCityLabaList(int ipCityId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(IpCityLaba.class);
		query.where("cityid=?").setParam(ipCityId).orderByDesc("labaid");
		return query.list(begin, size, IpCityLaba.class);
	}

	/**
	 * implements by {@link LabaServiceWrapper}
	 */
	public List<Laba> getIpLabaList(String ip, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class);
		query.where("ip=?").setParam(ip).orderByDesc("labaid");
		return query.list(begin, size, Laba.class);
	}

	public List<IpCityRangeLaba> getIpCityRangeLabaList(int rangeId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(IpCityRangeLaba.class);
		query.where("rangeid=?").setParam(rangeId).orderByDesc("labaid");
		return query.list(begin, size, IpCityRangeLaba.class);
	}

	public boolean isCollected(long userId, long labaId) {
		Query query = this.manager.createQuery();
		query.setTable(FavLaba.class);
		query.where("userid=? and labaid=?").setParam(userId).setParam(labaId);
		if (query.count() > 0) {
			return true;
		}
		return false;
	}

	private void createUserRecentLaba(long userId) {
		UserRecentLaba userRecentLaba = this.getUserRecentLaba(userId);
		Query query = this.manager.createQuery();
		List<UserLaba> ulist = query.listEx(UserLaba.class, "userid=?",
				new Object[] { userId }, "labaid desc", 0, 30);
		StringBuilder sb = new StringBuilder();
		for (UserLaba o : ulist) {
			sb.append(o.getLabaId()).append(",");
		}
		if (ulist.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		if (userRecentLaba == null) {
			userRecentLaba = new UserRecentLaba();
			userRecentLaba.setUserId(userId);
			userRecentLaba.setLabaData(sb.toString());
			query.insertObject(userRecentLaba);
		}
		else {
			userRecentLaba.setLabaData(sb.toString());
			query.updateObject(userRecentLaba);
		}
	}

	public int count() {
		Query query = this.manager.createQuery();
		query.setTable(Laba.class);
		return query.count();
	}

	public List<Laba> getLabaListForSearch(String key, int begin, int size) {
		throw new RuntimeException("implements by wrapper");
	}

	public List<Long> getCollectedLabaIdList(long userId, List<Long> idList) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList<Long>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select labaid from favlaba where userid=? and labaid in ("
				+ sb.toString() + ")";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, Long.class, userId);
	}

	private List<UserRecentLaba> getUserRecentLabaListInUser(
			List<Long> userIdList) {
		if (userIdList == null || userIdList.size() == 0) {
			return new ArrayList<UserRecentLaba>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : userIdList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from userrecentlaba where userid in ("
				+ sb.toString() + ")";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, UserRecentLaba.class);
	}

	public Map<Long, UserRecentLaba> getUserRecentLabaMapInUser(
			List<Long> userIdList) {
		Map<Long, UserRecentLaba> map = new LinkedHashMap<Long, UserRecentLaba>();
		List<UserRecentLaba> list = this
				.getUserRecentLabaListInUser(userIdList);
		for (UserRecentLaba o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public void deleteTagLaba(long labaId, long tagId) {
		Query query = this.manager.createQuery();
		query.setTable(TagLaba.class);
		query.where("labaid=? and tagid=?").setParam(labaId).setParam(tagId);
		query.delete();
	}

	public void deleteTagLaba(long labaId, long tagId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(TagLaba.class);
		query.where("labaid=? and tagid=? and userid=?").setParam(labaId)
				.setParam(tagId).setParam(userId);
		query.delete();
	}

	public int countLaba(long userId, Date beginTime, Date endTime) {
		Query query = this.manager.createQuery();
		String sql = "select count(*) from laba where userid=? and createtime>=? and createtime<=?";
		return query.countBySql("ds1", sql, userId, beginTime, endTime);
	}

	public List<Long> getInformationLabaIdList(long userId, long tagId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select ur.labaid from userlabareply ur,labatag lt where ur.labaid=lt.labaid and ur.userid=? and lt.tagid=? order by ur.labaid desc";
		return query.listBySql("ds1", sql, begin, size, Long.class, userId,
				tagId);
	}

	public List<Long> getInformationLabaIdListForMe(long userId, long tagId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select ul.labaid from userlaba ul,labatag lt where ul.labaid=lt.labaid and ul.userid=? and lt.tagid=? order by ul.labaid desc";
		return query.listBySql("ds1", sql, begin, size, Long.class, userId,
				tagId);
	}

	public List<RefLaba> getRefLabaList(long labaId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(RefLaba.class);
		query.where("labaid=?").setParam(labaId);
		query.orderByDesc("createtime");
		return query.list(begin, size, RefLaba.class);
	}

	public List<Laba> getLabaListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<Laba>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from laba where labaid in (" + sb.toString()
				+ ") order by labaid desc";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, Laba.class);
	}

	public List<Laba> getLabaListInId(List<Long> idList, int begin, int size) {
		if (idList.size() == 0) {
			return new ArrayList<Laba>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from laba where labaid in (" + sb.toString()
				+ ") order by labaid desc";
		Query query = this.manager.createQuery();
		return query.listBySql("ds1", sql, begin, size, Laba.class);
	}

	public Map<Long, Laba> getLabaMapInId(List<Long> idList) {
		List<Laba> list = this.getLabaListInId(idList);
		Map<Long, Laba> map = new HashMap<Long, Laba>();
		for (Laba o : list) {
			map.put(o.getLabaId(), o);
		}
		return map;
	}

	private List<LabaDel> getLabaListDelInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<LabaDel>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from labadel where labaid in (" + sb.toString()
				+ ") order by labaid desc";
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql, LabaDel.class);
	}

	public Map<Long, LabaDel> getLabaDelMapInId(List<Long> idList) {
		List<LabaDel> list = this.getLabaListDelInId(idList);
		Map<Long, LabaDel> map = new HashMap<Long, LabaDel>();
		for (LabaDel o : list) {
			map.put(o.getLabaId(), o);
		}
		return map;
	}

	public List<CompanyRefLaba> getCompanyRefLabaList(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(CompanyRefLaba.class);
		query.where("companyid=?").setParam(companyId);
		query.orderByDesc("labaid");
		return query.list(begin, size, CompanyRefLaba.class);
	}

	public RefLaba getRefLaba(long labaId, long userId) {
		Query query = this.manager.createQuery();
		query.setTable(RefLaba.class);
		query.where("labaid=? and refuserid=?").setParam(labaId).setParam(
				userId);
		return query.getObject(RefLaba.class);
	}

	public List<Laba> getLabaListByRefLabaIdAndUserId(long reflabaId,
			long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(Laba.class, "reflabaid=? and userid=?",
				new Object[] { reflabaId, userId });
	}

	public PinkLaba getPinkLaba(long labaId) {
		Query query = manager.createQuery();
		return query.getObjectById(PinkLaba.class, labaId);
	}

	public List<RespLaba> getRespLabaList(Date beginTime, Date endTime) {
		Query query = manager.createQuery();
		return query.listEx(RespLaba.class, "uptime>=? and uptime<=?",
				new Object[] { beginTime, endTime });
	}

	public void updateHotLaba(RespLaba respLaba) {
		Calendar b = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		b.setTime(respLaba.getUptime());
		b.set(Calendar.HOUR_OF_DAY, 0);
		b.set(Calendar.MINUTE, 0);
		b.set(Calendar.SECOND, 0);
		e.setTime(respLaba.getUptime());
		e.set(Calendar.HOUR_OF_DAY, 23);
		e.set(Calendar.MINUTE, 59);
		e.set(Calendar.SECOND, 59);
		Date begin = b.getTime();
		Date end = e.getTime();
		Query query = this.manager.createQuery();
		// 获取所有喇叭回复 需要从评论表中取
		List<LabaCmt> cmtlist = query.listEx(LabaCmt.class, "labaid=?",
				new Object[] { respLaba.getLabaId() });
		// 获取所有引用的喇叭
		String sql = "select labaid from laba where reflabaid=?";
		List<Long> labaIdlist = query.listBySqlEx("ds1", sql, Long.class,
				respLaba.getLabaId());
		// 去重复
		HashSet<Long> set = new HashSet<Long>();
		for (Long l : labaIdlist) {
			set.add(l);
		}
		if (set.size() == 0) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : set) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sql = "select l.createtime from laba l where l.labaid in ("
				+ sb.toString() + ") and l.createtime>=? and l.createtime<=?";
		List<Date> list = query.listBySql("ds1", sql,
				new ParameterizedRowMapper<Date>() {

					public Date mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getTimestamp("createtime");
					}
				}, begin, end);
		for (LabaCmt labaCmt : cmtlist) {
			list.add(labaCmt.getCreateTime());
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyMMdd");
		int arg = Integer.parseInt(sdf.format(respLaba.getUptime()));// yyMMdd*size
		int arg0 = arg * list.size() / 100;
		int sum = 0;
		sdf.applyPattern("mmss");
		for (Date d : list) {
			sum += Integer.parseInt(sdf.format(d));
		}
		int arg1 = arg + sum / 20;
		int hot = arg0 + arg1;
		respLaba.setHot(hot);
		this.updateRespLaba(respLaba);
	}

	public List<RespLaba> getRespLabaList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(RespLaba.class);
		query.orderByDesc("hot");
		return query.list(begin, size, RespLaba.class);
	}

	public void tempUpdateLaba(Laba laba) {
		Query query = this.manager.createQuery();
		query.addField("content", laba.getContent().replaceAll("针对", " RT "));
		if (laba.getLongContent() != null) {
			query.addField("longcontent", laba.getLongContent().replaceAll(
					"针对", " RT "));
		}
		query.update(Laba.class, "labaid=?", new Object[] { laba.getLabaId() });
	}

	public int countByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.count(Laba.class, "userid=?", new Object[] { userId });
	}

	public Map<Long, RefLaba> getRefLabaMapInLabaIdByRefUserId(long refUserId,
			List<Long> idList) {
		List<RefLaba> list = this.getRefLabaListInLabaIdByRefUserId(refUserId,
				idList);
		Map<Long, RefLaba> map = new HashMap<Long, RefLaba>(list.size());
		for (RefLaba o : list) {
			map.put(o.getLabaId(), o);
		}
		return map;
	}

	public List<RefLaba> getRefLabaListInLabaIdByRefUserId(long refUserId,
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<RefLaba>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from reflaba where refuserid=? and labaid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), RefLaba.class, refUserId);
	}

	public List<Laba> getLabaListByCompanyIdFromCompanyRefLaba(long companyId,
			int begin, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Laba> getLabaListFromIpCity(int ipCityId, int begin, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public Laba getLastUserLaba(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Laba> getInformationLabaList(long userId, long tagId,
			int begin, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Laba> getInformationUserLabaList(long userId, long tagId,
			int begin, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	public void indexLaba(List<IndexLaba> list) {
		// TODO Auto-generated method stub
	}

	public void createLabaCmt(LabaCmt labaCmt, LabaInfo labaInfo) {
		if (labaCmt.getLabaId() <= 0) {
			throw new RuntimeException("invalid labaId");
		}
		if (labaCmt.getUserId() <= 0) {
			throw new RuntimeException("invalid userId");
		}
		labaCmt.setCreateTime(new Date());
		Query query = this.manager.createQuery();
		long cmtId = query.insertObject(labaCmt).longValue();
		labaCmt.setCmtId(cmtId);
		this.makeLabaReplyCount(labaCmt.getLabaId());
	}

	private void makeLabaReplyCount(long labaId) {
		Query query = this.manager.createQuery();
		int count = query.count(LabaCmt.class, "labaid=?",
				new Object[] { labaId });
		query.addField("replycount", count);
		query.updateById(Laba.class, labaId);
	}

	public void deleteLabaCmt(long cmtId) {
		LabaCmt labaCmt = this.getLabaCmt(cmtId);
		if (labaCmt == null) {
			return;
		}
		Query query = this.manager.createQuery();
		query.deleteById(LabaCmt.class, cmtId);
		this.makeLabaReplyCount(labaCmt.getLabaId());
	}

	public List<LabaCmt> getLabaCmtListByLabaId(long labaId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(LabaCmt.class, "labaid=?", new Object[] { labaId },
				"cmtid desc", begin, size);
	}

	public List<LabaCmt> getUserLabaCmtListByLabaId(long labaId, long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(LabaCmt.class, "labaid=? and userid=?",
				new Object[] { labaId, userId }, "cmtid desc", begin, size);
	}

	public List<Laba> getLabaList() {
		Query query = this.manager.createQuery();
		return query.listEx(Laba.class);
	}

	public void updateLaba(long labaId, String sContent, String lContent) {
		Query query = this.manager.createQuery();
		query.addField("content", sContent);
		query.addField("longcontent", lContent);
		query.updateById(Laba.class, labaId);
	}

	public LabaCmt getLabaCmt(long cmtId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(LabaCmt.class, cmtId);
	}

	public List<LabaCmt> getLabaCmtListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<LabaCmt>();
		}
		StringBuilder sb = new StringBuilder(
				"select * from labacmt where cmtid in (");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		Query query = this.manager.createQuery();
		return query.listBySqlEx("ds1", sb.toString(), LabaCmt.class);
	}

	public Map<Long, LabaCmt> getLabaCmtMapInId(List<Long> idList) {
		List<LabaCmt> list = this.getLabaCmtListInId(idList);
		Map<Long, LabaCmt> map = new HashMap<Long, LabaCmt>();
		for (LabaCmt o : list) {
			map.put(o.getCmtId(), o);
		}
		return map;
	}

	public int countUserLabaReplyByUserId(long userId) {
		Query query = this.manager.createQuery();
		return query.count(UserLabaReply.class, "userid=?",
				new Object[] { userId });
	}
}