package com.hk.svr.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.AdminHkb;
import com.hk.bean.AdminUser;
import com.hk.bean.DefFollowUser;
import com.hk.bean.HkbLog;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeUser;
import com.hk.bean.IpCityUser;
import com.hk.bean.IpUser;
import com.hk.bean.ProUser;
import com.hk.bean.Randnum;
import com.hk.bean.RegCode;
import com.hk.bean.RegCodeUser;
import com.hk.bean.RegfromUser;
import com.hk.bean.ScoreLog;
import com.hk.bean.User;
import com.hk.bean.UserBindInfo;
import com.hk.bean.UserContactDegree;
import com.hk.bean.UserFgtMail;
import com.hk.bean.UserNoticeInfo;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserProtect;
import com.hk.bean.UserRecentUpdate;
import com.hk.bean.UserSmsPort;
import com.hk.bean.UserTool;
import com.hk.bean.UserUpdate;
import com.hk.bean.UserWebBind;
import com.hk.bean.WelProUser;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.DesUtil;
import com.hk.frame.util.MD5Util;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.pub.DataSort;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ImageConfig;
import com.hk.svr.pub.ScoreConfig;
import com.hk.svr.user.exception.CreateRandnumException;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.LoginException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.svr.user.exception.MsnDuplicateException;
import com.hk.svr.user.exception.SendOutOfLimitException;

public class UserServiceImpl implements UserService {

	private int buguserid = 4576;// 小于这个数字的用户使用pwdhash来交验密码,由于早期以为hashcode唯一导致错误,现纠正

	private String userIdxDir;

	@Autowired
	private QueryManager manager;

	private DesUtil svrDesUtil = new DesUtil("ak47flyshowhuoku");

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	private final Log log = LogFactory.getLog(UserServiceImpl.class);

	public void addScore(long userId, int add) {
		Query query = manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.setShowFields("score");
		query.where("userid=?").setParam(userId);
		int score = query.getObject(Integer.class);
		query.setTable(UserOtherInfo.class);
		if (score + add < 0) {
			query.addField("score", 0);
		}
		else {
			query.addField("score", "add", add);
		}
		query.where("userid=?");
		query.setParam(userId);
		query.update();
	}

	public void addScore(ScoreLog scoreLog) {
		this.addScore(scoreLog.getUserId(), scoreLog.getAddcount());
		scoreLog.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("userid", scoreLog.getUserId());
		query.addField("scoretype", scoreLog.getScoretype());
		query.addField("addcount", scoreLog.getAddcount());
		query.addField("createtime", scoreLog.getCreateTime());
		query.addField("objid", scoreLog.getObjId());
		query.insert(ScoreLog.class);
	}

	public long createUser(String input, String password, String ip)
			throws EmailDuplicateException, MobileDuplicateException {
		String email = null;
		String mobile = null;
		if (input.indexOf("@") != -1) {
			email = input;
		}
		else {
			mobile = input;
		}
		return this.createUser(email, mobile, password, ip);
	}

	private synchronized long createUser(String email, String mobile,
			String password, String ip) throws EmailDuplicateException,
			MobileDuplicateException {
		Query query = manager.createQuery();
		if (email != null) {
			int count = query.count(UserOtherInfo.class, "email=?",
					new Object[] { email });
			if (count > 0) {
				throw new EmailDuplicateException("email has been exist", email);
			}
		}
		if (mobile != null) {
			int count = query.count(UserOtherInfo.class, "mobile=?",
					new Object[] { mobile });
			if (count > 0) {
				throw new MobileDuplicateException("mobile has been exist",
						mobile);
			}
		}
		long userId = this.createUserData(mobile, email, password, true);
		this.createUserIpInfo(userId, ip);
		ScoreLog scoreLog = ScoreLog.create(userId, HkLog.REG, 0, ScoreConfig
				.getRegister());
		this.addScore(scoreLog);
		return userId;
	}

	public long createUserWithRegCode(String input, String password, String ip,
			RegCode regCode) throws EmailDuplicateException,
			MobileDuplicateException {
		long userId = this.createUser(input, password, ip);
		this.processRegCodeUser(regCode, userId);
		return userId;
	}

	public long createUser(String email, String mobile, String password,
			String ip, RegCode regCode) throws EmailDuplicateException,
			MobileDuplicateException {
		long userId = this.createUser(email, mobile, password, ip);
		this.processRegCodeUser(regCode, userId);
		return userId;
	}

	public void processRegCodeUser(RegCode regCode, long userId) {
		if (regCode == null) {
			return;
		}
		Query query = manager.createQuery();
		query.addField("codeid", regCode.getCodeId());
		query.addField("userid", userId);
		query.addField("createtime", new Date());
		query.insert(RegCodeUser.class);
	}

	private long createUserData(String mobile, String email, String password,
			boolean encodePwd) {
		Query query = manager.createQuery();
		User user = new User();
		user.setNickName(null);
		user.setHeadPath(null);
		user.setHeadflg(User.HEAD_SETTED_N);
		user.setDomain(null);
		user.setFansCount(0);
		user.setFriendCount(0);
		user.setCityId(0);
		user.setSex((byte) 0);
		/** **************用户表 ************************ */
		long userId = query.insertObject(user).longValue();
		user.setDomain(String.valueOf(1000 + userId));
		user.setUserId(userId);
		String nickName = String.valueOf(userId);
		user.setNickName(nickName);
		query.setTable(User.class);
		query.addField("nickname", user.getNickName());
		query.addField("domain", user.getDomain());
		query.where("userid=?").setParam(userId).update();
		UserUpdate userUpdate = new UserUpdate();
		userUpdate.setUserId(userId);
		userUpdate.setUptime(System.currentTimeMillis());
		query.insertObject(userUpdate);
		/** **************用户详细信息表 ************************ */
		UserOtherInfo info = new UserOtherInfo();
		info.setUserId(userId);
		info.setValidateEmail(UserOtherInfo.VALIDATEEMAIL_N);
		info.setScore(ScoreConfig.getRegister());
		info.setEmail(email);
		info.setMobile(mobile);
		String pwd = null;
		if (encodePwd) {
			pwd = MD5Util.md5Encode32(password);
		}
		else {
			pwd = password;
		}
		info.setPwdHash(pwd.hashCode());
		info.setPwd(pwd);
		info.setMobileBind(UserOtherInfo.MOBILE_NOT_BIND);
		info.setUserStatus(UserOtherInfo.USERSTATUS_Y);
		info.setHkb(0);
		info.setCreateTime(new Date());
		info.setBirthdayDate(0);
		info.setBirthdayMonth(0);
		query.insertObject(info);
		return userId;
	}

	public User getUser(long userId) {
		Query query = manager.createQuery();
		return query.getObjectById(User.class, userId);
	}

	public User getUserByNickName(String nickName) {
		Query query = manager.createQuery();
		query.setTable(User.class);
		query.where("nickname=?").setParam(nickName.toLowerCase());
		return query.getObject(User.class);
	}

	public void updateHeadWithCut(long userId, File headFile, int x1, int y1,
			int x2, int y2) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException {
		/** ************** 图片处理************************ */
		JMagickUtil util = new JMagickUtil(headFile, 1);
		String dbPath = ImageConfig.getHeadDbPath(userId);
		String headPath = ImageConfig.getHeadUploadPath(dbPath);
		String path = ImageConfig.getTempCutFilePath();
		String name = userId + "" + System.currentTimeMillis() + ".jpg";
		util.cutImage(path, name, x1, y1, x2, y2);
		File newf = new File(path + name);
		try {
			util = new JMagickUtil(newf, 1);
			util.makeImage(headPath, "h32.jpg", JMagickUtil.IMG_SQUARE, 32);
			util.makeImage(headPath, "h48.jpg", JMagickUtil.IMG_SQUARE, 48);
			util.makeImage(headPath, "h80.jpg", JMagickUtil.IMG_SQUARE, 80);
			/** ************** 存储图片路径************************ */
			Query query = manager.createQuery();
			query.addField("headflg", User.HEAD_SETTED_Y);
			query.addField("headpath", dbPath);
			query.updateById(User.class, userId);
		}
		finally {
			if (newf.exists()) {
				newf.delete();
			}
		}
	}

	public void updateHead(long userId, File headFile) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException {
		/** ************** 图片处理************************ */
		JMagickUtil util = new JMagickUtil(headFile, 1);
		util.setFullQuality(true);
		String dbPath = ImageConfig.getHeadDbPath(userId);
		String headPath = ImageConfig.getHeadUploadPath(dbPath);
		util.makeImage(headPath, "h32.jpg", JMagickUtil.IMG_SQUARE, 32);
		util.makeImage(headPath, "h48.jpg", JMagickUtil.IMG_SQUARE, 48);
		util.makeImage(headPath, "h80.jpg", JMagickUtil.IMG_SQUARE, 80);
		/** ************** 存储图片路径************************ */
		Query query = manager.createQuery();
		query.addField("headflg", User.HEAD_SETTED_Y);
		query.addField("headpath", dbPath);
		query.updateById(User.class, userId);
	}

	public void updateFirstAddInfo(long userId, String firstAddInfo) {
		Query query = manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("firstaddinfo", firstAddInfo);
		query.where("userid=?");
		query.setParam(userId);
		query.update();
	}

	public void updateIntro(long userId, String intro) {
		/** ************* 更新介绍 ***************** */
		Query query = manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("intro", intro);
		query.where("userid=?");
		query.setParam(userId);
		query.update();
	}

	public synchronized boolean updateNickName(long userId, String nickName) {
		if (DataUtil.isNumber(nickName)) {// 不能是纯数字，否则提示已经存在
			return false;
		}
		String lowerNickName = nickName.toLowerCase();
		User user = this.getUser(userId);
		if (nickName.equals(user.getNickName())) {
			return true;
		}
		if (lowerNickName.equalsIgnoreCase(user.getNickName())) {
			Query query = manager.createQuery();
			query.setTable(User.class);
			query.addField("nickname", nickName);
			query.where("userid=?").setParam(userId);
			query.update();
			return true;
		}
		Query query = manager.createQuery();
		int count = query.setTable(User.class).where("nickname=?").setParam(
				lowerNickName).count();
		if (count > 0) {
			return false;
		}
		/** ************* 更新昵称 ***************** */
		query.addField("nickname", nickName);
		query.updateById(User.class, userId);
		return true;
	}

	public void updateSex(long userId, byte sex) {
		Query query = manager.createQuery();
		query.addField("sex", sex);
		query.updateById(User.class, userId);
	}

	public void addScoreAndHonor(long userId, int scoreAdd, int honorAdd) {
		Query query = manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("score", "add", scoreAdd);
		query.addField("honor", "add", honorAdd);
		query.where("userid=?");
		query.setParam(userId);
		query.update();
	}

	public UserOtherInfo getUserOtherInfo(long userId) {
		Query query = manager.createQuery();
		UserOtherInfo userOtherInfo = query.getObjectById(UserOtherInfo.class,
				userId);
		return userOtherInfo;
	}

	private void createUserIpInfo(long userId, String ip) {
		if (ip == null) {
			return;
		}
		Query query = manager.createQuery();
		query.addField("ipnumber", DataUtil.parseIpNumber(ip));
		query.addField("userid", userId);
		query.insert(IpUser.class);
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		if (range != null) {
			query.setTable(IpCityRangeUser.class);
			query.where("rangeid=? and userid=?").setParam(range.getRangeId())
					.setParam(userId);
			if (query.count() == 0) {
				query.addField("rangeid", range.getRangeId());
				query.addField("userid", userId);
				query.insert(IpCityRangeUser.class);
			}
			query.setTable(IpCityUser.class);
			query.where("cityid=? and userid=?").setParam(range.getCityId())
					.setParam(userId);
			if (query.count() == 0) {
				query.addField("cityid", range.getCityId());
				query.addField("userid", userId);
				query.insert(IpCityUser.class);
			}
		}
	}

	private void updateUserIpInfo(long userId, String ip) {
		long ipnumber = DataUtil.parseIpNumber(ip);
		Query query = this.manager.createQuery();
		query.setTable(IpUser.class);
		query.where("ipnumber=? and userid=?").setParam(ipnumber).setParam(
				userId);
		IpUser ipUser = query.getObject(IpUser.class);
		if (ipUser == null) {
			this.createUserIpInfo(userId, ip);
		}
	}

	public long loginByEmail(String email, String password, String ip)
			throws LoginException {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class).where("email=?").setParam(email);
		UserOtherInfo userOtherInfo = query.getObject(UserOtherInfo.class);
		if (userOtherInfo == null) {
			throw new LoginException("no email [ " + email + " ]");
		}
		if (userOtherInfo.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {
			throw new LoginException("user stop [ " + email + " ]");
		}
		this.processLoginPassword(userOtherInfo, password, ip);
		return userOtherInfo.getUserId();
	}

	public long loginByMobile(String mobile, String password, String ip)
			throws LoginException {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class).where("mobile=?").setParam(mobile);
		UserOtherInfo userOtherInfo = query.getObject(UserOtherInfo.class);
		if (userOtherInfo == null) {
			throw new LoginException("no mobile [ " + mobile
					+ " ] or mobile not bind");
		}
		if (userOtherInfo.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {
			throw new LoginException("user stop [ " + mobile + " ]");
		}
		this.processLoginPassword(userOtherInfo, password, ip);
		return userOtherInfo.getUserId();
	}

	public long loginByNickName(String nickName, String password, String ip)
			throws LoginException {
		String loclaNickName = nickName.toLowerCase();
		Query query = this.manager.createQuery();
		query.setTable(User.class).where("nickname=?").setParam(loclaNickName);
		User user = query.getObject(User.class);
		if (user == null) {
			log.warn("no nickName");
			throw new LoginException("no nickName [ " + loclaNickName + " ]");
		}
		UserOtherInfo userOtherInfo = query.getObjectById(UserOtherInfo.class,
				user.getUserId());
		if (userOtherInfo.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {
			throw new LoginException("user stop [ " + loclaNickName + " ]");
		}
		this.processLoginPassword(userOtherInfo, password, ip);
		return userOtherInfo.getUserId();
	}

	private void processLoginPassword(UserOtherInfo userOtherInfo,
			String password, String ip) throws LoginException {
		if (userOtherInfo.getUserId() < buguserid) {// 解决遗留密码设置问题
			if (!DataUtil.isEmpty(userOtherInfo.getPwd())) {// 如果用户修改过密码，密码字段会有值
				if (!MD5Util.md5Encode32(password).equals(
						userOtherInfo.getPwd())) {
					throw new LoginException("error password");
				}
			}
			else {
				int md5Pwdhash = MD5Util.md5Encode32(password).hashCode();
				if (md5Pwdhash != userOtherInfo.getPwdHash()) {
					throw new LoginException("error password");
				}
				// 如果密码验证通过后，而且密码字段没有值，就更新密码字段
				// Query query=this.manager.createQuery();
				// query.addField("pwd", MD5Util.md5Encode32(password));
				// query.updateById(UserOtherInfo.class,
				// userOtherInfo.getUserId());
			}
		}
		else {
			if (!MD5Util.md5Encode32(password).equals(userOtherInfo.getPwd())) {
				throw new LoginException("error password");
			}
		}
		this.updateUserIpInfo(userOtherInfo.getUserId(), ip);
	}

	public List<User> getUserList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(User.class);
		query.orderByDesc("userid");
		return query.list(begin, size, User.class);
	}

	public List<User> getUserListByUserStatus(int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select u.* from user u,userotherinfo o where u.userid=o.userid and o.userstatus=?";
		return query.listBySql("ds1", sql, begin, size, User.class,
				UserOtherInfo.USERSTATUS_Y);
	}

	public List<User> getUserListExceptUserId(long userId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(User.class).where("userid!=?").setParam(userId);
		return query.list(begin, size, User.class);
	}

	public void updateEmail(long userId, String email)
			throws EmailDuplicateException {
		UserOtherInfo userOtherInfo = this.getUserOtherInfo(userId);
		if (userOtherInfo == null) {
			return;
		}
		if (userOtherInfo.getEmail() != null
				&& userOtherInfo.getEmail().equals(email)) {
			return;
		}
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.where("email=?").setParam(email);
		if (query.count() > 0) {
			throw new EmailDuplicateException("email is already exist", email);
		}
		query.setTable(UserOtherInfo.class);
		query.addField("email", email);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public synchronized void updateMobile(long userId, String mobile)
			throws MobileDuplicateException {
		UserOtherInfo userOtherInfo = this.getUserOtherInfo(userId);
		if (userOtherInfo == null) {
			return;
		}
		if (mobile.equals(userOtherInfo.getMobile())) {
			return;
		}
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.where("mobile=?").setParam(mobile);
		if (query.count() > 0) {
			throw new MobileDuplicateException("mobile is already exist",
					mobile);
		}
		query.setTable(UserOtherInfo.class);
		query.addField("mobile", mobile);
		query.addField("mobilebind", UserOtherInfo.MOBILE_NOT_BIND);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public boolean updatePwd(long userId, String oldPwd, String newPwd) {
		UserOtherInfo userOtherInfo = this.getUserOtherInfo(userId);
		if (userOtherInfo == null) {
			return false;
		}
		String md5Old = MD5Util.md5Encode32(oldPwd);
		if (userId < buguserid) {
			if (!DataUtil.isEmpty(userOtherInfo.getPwd())) {// 如果用户修改过密码，密码字段会有值
				if (!userOtherInfo.getPwd().equals(md5Old)) {
					return false;
				}
			}
			else {
				if (userOtherInfo.getPwdHash() != md5Old.hashCode()) {
					return false;
				}
			}
		}
		else {
			if (!userOtherInfo.getPwd().equals(md5Old)) {
				return false;
			}
		}
		String pwd = MD5Util.md5Encode32(newPwd);
		Query query = this.manager.createQuery();
		query.addField("pwdhash", pwd.hashCode());
		query.addField("pwd", pwd);
		query.updateById(UserOtherInfo.class, userId);
		return true;
	}

	public void updateNewPwd(long userId, String pwd) {
		UserOtherInfo userOtherInfo = this.getUserOtherInfo(userId);
		if (userOtherInfo == null) {
			return;
		}
		String opwd = MD5Util.md5Encode32(pwd);
		int hash = opwd.hashCode();
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("pwdhash", hash);
		query.addField("pwd", opwd);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public UserProtect getUserProtect(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserProtect.class, userId);
	}

	public void updateUserProtect(long userId, int pconfig, String pvalue) {
		Query query = this.manager.createQuery();
		UserProtect userProtect = query
				.getObjectById(UserProtect.class, userId);
		if (userProtect == null) {
			query.addField("userid", userId);
			query.addField("pconfig", pconfig);
			query.addField("pvalue", pvalue);
			query.insert(UserProtect.class);
		}
		else {
			query.setTable(UserProtect.class);
			query.addField("pconfig", pconfig);
			query.addField("pvalue", pvalue);
			query.where("userid=?").setParam(userId);
			query.update();
		}
	}

	public UserOtherInfo getUserOtherInfoByeEmail(String email) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.where("email=?").setParam(email);
		return query.getObject(UserOtherInfo.class);
	}

	public String createDedValueForFgtPwd(long userId)
			throws SendOutOfLimitException {
		UserOtherInfo otherInfo = this.getUserOtherInfo(userId);
		Query query = this.manager.createQuery();
		UserFgtMail fgtMail = query.getObjectById(UserFgtMail.class, otherInfo
				.getUserId());
		String value;
		try {
			value = this.svrDesUtil.encrypt(System.currentTimeMillis() + "hk"
					+ otherInfo.getUserId());
		}
		catch (Exception e) {
			log.error("加密失败 " + e.getMessage());
			throw new RuntimeException("加密失败");
		}
		if (fgtMail == null) {
			query.addField("userid", otherInfo.getUserId());
			query.addField("sendcount", 1);
			query.addField("desvalue", value);
			query.addField("createtime", new Date());
			query.insert(UserFgtMail.class);
		}
		else {
			long fgttime = fgtMail.getCreateTime().getTime();
			long nowtime = System.currentTimeMillis();
			boolean notover24hours = (nowtime - fgttime < 86400000);
			if (fgtMail.getSencCount() >= 3 && notover24hours) {// 没有超过24小时，不能发送超过3次
				throw new SendOutOfLimitException("send mail more than 3");
			}
			query.setTable(UserFgtMail.class);
			if (notover24hours) {
				query.addField("sendcount", "add", 1);
			}
			else {
				query.addField("sendcount", 1);
			}
			query.addField("desvalue", value);
			query.addField("createtime", new Date());
			query.where("userid=?").setParam(otherInfo.getUserId());
			query.update();
		}
		return value;
	}

	public UserFgtMail getUserFgtMailByDesValue(String desValue) {
		Query query = this.manager.createQuery();
		query.setTable(UserFgtMail.class);
		query.where("desvalue=?").setParam(desValue);
		return query.getObject(UserFgtMail.class);
	}

	public void removeUsrFgtMail(long userId) {
		Query query = this.manager.createQuery();
		query.setTable(UserFgtMail.class);
		query.where("userid=?").setParam(userId);
		query.delete();
	}

	public int countUser() {
		Query query = this.manager.createQuery();
		query.setTable(User.class);
		return query.count();
	}

	public List<IpCityRangeUser> getIpCityRangeUserList(int rangeId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		query.setTable(IpCityRangeUser.class);
		query.where("rangeid=?").setParam(rangeId);
		query.orderByDesc("userid");
		return query.list(begin, size, IpCityRangeUser.class);
	}

	public List<IpCityUser> getIpCityUserList(int cityId, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(IpCityUser.class);
		query.where("cityid=?").setParam(cityId);
		query.orderByDesc("userid");
		return query.list(begin, size, IpCityUser.class);
	}

	public List<IpUser> getIpUserList(String ip, int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(IpUser.class);
		query.where("ipnumber=?").setParam(DataUtil.parseIpNumber(ip));
		query.orderByDesc("userid");
		return query.list(begin, size, IpUser.class);
	}

	public UserOtherInfo getUserOtherInfoByMobile(String mobile) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.where("mobile=?").setParam(mobile);
		return query.getObject(UserOtherInfo.class);
	}

	public void bindMobile(long userId, String mobile) {
		UserOtherInfo oi = this.getUserOtherInfo(userId);
		if (oi.getMobile() != null && oi.getMobile().equals(mobile)
				&& oi.getMobileBind() == UserOtherInfo.MOBILE_BIND) {
			return;
		}
		Query query = this.manager.createQuery();
		List<UserOtherInfo> olist = query.listEx(UserOtherInfo.class,
				"mobile=? and mobilebind=?", new Object[] { mobile,
						UserOtherInfo.MOBILE_BIND });
		for (UserOtherInfo o : olist) {
			query.setTable(UserOtherInfo.class);
			query.addField("mobilebind", UserOtherInfo.MOBILE_NOT_BIND);
			query.addField("mobile", null);
			query.addField("userstatus", UserOtherInfo.USERSTATUS_STOP);
			query.where("userid=?").setParam(o.getUserId());
			query.update();
		}
		query.setTable(UserOtherInfo.class);
		query.addField("mobilebind", UserOtherInfo.MOBILE_BIND);
		query.addField("mobile", mobile);
		query.where("userid=?").setParam(userId);
		query.update();
		// 手机绑定成功后，分配火酷号
		UserSmsPort port = this.userSmsPortService
				.getUserSmsPortByUserId(userId);
		if (port == null) {
			port = this.userSmsPortService.makeAvailableUserSmsPort(userId);
		}
	}

	public AdminUser getAdminUser(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(AdminUser.class, userId);
	}

	public void addHkb(long userId, int hkb) {
		Query query = this.manager.createQuery();
		UserOtherInfo info = this.getUserOtherInfo(userId);
		if (info.getHkb() + hkb < 0) {
			query.setTable(UserOtherInfo.class);
			query.addField("hkb", 0);
			query.where("userid=?").setParam(userId);
			query.update();
		}
		else if (info.getHkb() + hkb >= 0) {
			query.setTable(UserOtherInfo.class);
			query.addField("hkb", "add", hkb);
			query.where("userid=?").setParam(userId);
			query.update();
		}
	}

	public void addHkb(HkbLog hkbLog) {
		this.addHkb(hkbLog.getUserId(), hkbLog.getAddcount());
		this.createHkbLog(hkbLog);
	}

	public void updateHkb(long userId, int hkb) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("hkb", hkb);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public synchronized Randnum createUserRandnum(long userId)
			throws CreateRandnumException {
		Query query = this.manager.createQuery();
		List<Randnum> olist = query.listEx(Randnum.class, "userid=?",
				new Object[] { userId });
		for (Randnum o : olist) {
			query.setTable(Randnum.class);
			query.addField("userid", 0);
			query.addField("inuse", Randnum.INUSE_N);
			query.where("sysid=?").setParam(o.getSysId());
			query.update();
		}
		List<Randnum> list = query.listEx(Randnum.class, "inuse=?",
				new Object[] { Randnum.INUSE_N });
		if (list.size() == 0) {
			throw new CreateRandnumException("randnum are all in use");
		}
		Random r = new Random();
		int idx = r.nextInt(list.size());
		Randnum o = list.get(idx);
		query.setTable(Randnum.class);
		query.addField("inuse", Randnum.INUSE_Y);
		query.addField("userid", userId);
		query.addField("utime", new Date());
		query.where("sysid=?").setParam(o.getSysId());
		query.update();
		return o;
	}

	public void clearTimeoutRandnum() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		Query query = this.manager.createQuery();
		query.setTable(Randnum.class);
		query.addField("inuse", Randnum.INUSE_N);
		query.addField("userid", 0);
		query.where("inuse=? and utime<?").setParam(Randnum.INUSE_Y).setParam(
				cal.getTime());
		query.update();
	}

	public void clearTimeoutRandnum(int sysId) {
		Query query = this.manager.createQuery();
		query.setTable(Randnum.class);
		query.addField("inuse", Randnum.INUSE_N);
		query.addField("userid", 0);
		query.where("sysId=?").setParam(sysId);
		query.update();
	}

	public Randnum getRandnumByRandvalue(int randvalue) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		Query query = this.manager.createQuery();
		query.setTable(Randnum.class);
		query.where("randvalue=? and inuse=? and utime>=?").setParam(randvalue)
				.setParam(Randnum.INUSE_Y).setParam(cal.getTime());
		return query.getObject(Randnum.class);
	}

	public void cancelMobiebind(long userId) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("mobilebind", UserOtherInfo.MOBILE_NOT_BIND);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public Randnum getUserRandnum(long userId) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);
		Query query = this.manager.createQuery();
		query.setTable(Randnum.class);
		query.where("userId=? and inuse=? and utime>=?").setParam(userId)
				.setParam(Randnum.INUSE_Y).setParam(cal.getTime());
		return query.getObject(Randnum.class);
	}

	public List<User> getAllUserList() {
		Query query = this.manager.createQuery();
		return query.listEx(User.class, "userid desc");
	}

	public List<User> getUserListForSearch(String key, int begin, int size) {
		String _key = DataUtil.getSearchValue(key);
		if (DataUtil.isEmpty(_key)) {
			return new ArrayList<User>();
		}
		List<Long> idList = new ArrayList<Long>();
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			UserIndexField field = new UserIndexField();
			File dir = new File(this.userIdxDir);
			directory = FSDirectory.open(dir);
			isearcher = new IndexSearcher(directory, false); // read-only=true
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30, field
					.getNickNameField(), analyzer);
			org.apache.lucene.search.Query query = parser.parse(key);
			Sort sort = new Sort();
			sort.setSort(new SortField(field.getUserIdField(), SortField.DOC,
					false));
			int localSize = begin + size;
			int beginIdx = begin;
			int endIdx = localSize - 1;
			TopFieldDocs tfd = isearcher.search(query, null, localSize, sort);
			ScoreDoc[] hits = tfd.scoreDocs;
			if (hits.length < localSize) {
				endIdx = hits.length - 1;
			}
			// Iterate through the results:
			for (int i = beginIdx; i <= endIdx; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				long userId = Long
						.parseLong(hitDoc.get(field.getUserIdField()));
				idList.add(userId);
			}
			return this.getUserListInId(idList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
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

	public void indexUser() {
		int count = this.countUser();
		List<User> list = this.getUserList(0, count);
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			File dir = new File(this.getUserIdxDir());
			Directory directory = FSDirectory.open(dir);
			iwriter = new IndexWriter(directory, analyzer, true,
					new IndexWriter.MaxFieldLength(25000));
			UserIndexField field = new UserIndexField();
			for (User o : list) {
				Document doc = new Document();
				doc.add(new Field(field.getUserIdField(), o.getUserId() + "",
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field(field.getNickNameField(), o.getNickName()
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

	public User getUserByDomain(String domain) {
		Query query = this.manager.createQuery();
		query.setTable(User.class);
		query.where("domain=?").setParam(domain);
		return query.getObject(User.class);
	}

	public String getUserIdxDir() {
		return userIdxDir;
	}

	public void setUserIdxDir(String userIdxDir) {
		this.userIdxDir = userIdxDir;
	}

	public void updateUserStatus(long userId, byte status) {
		Query query = this.manager.createQuery();
		query.addField("userstatus", status);
		query.updateById(UserOtherInfo.class, userId);
	}

	public void setUserNormal(long userId) {
		this.updateUserStatus(userId, UserOtherInfo.USERSTATUS_Y);
	}

	public void setUserStop(long userId) {
		this.updateUserStatus(userId, UserOtherInfo.USERSTATUS_STOP);
	}

	public void updateName(long userId, String name) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("name", name);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public void updateUserOtherInfo(UserOtherInfo info) {
		Query query = this.manager.createQuery();
		query.updateObject(info);
	}

	public synchronized void updateUserBindInfo(UserBindInfo userBindInfo)
			throws MsnDuplicateException {
		Query query = this.manager.createQuery();
		query.setTable(UserBindInfo.class);
		query.where("msn=?").setParam(userBindInfo.getMsn());
		UserBindInfo o2 = query.getObject(UserBindInfo.class);
		if (o2 != null) {
			if (userBindInfo.getUserId() == o2.getUserId()) {
				return;
			}
			throw new MsnDuplicateException("msn [ " + userBindInfo.getMsn()
					+ " ] is duplicate");
		}
		if (query.getObjectById(UserBindInfo.class, userBindInfo.getUserId()) != null) {
			query.setTable(UserBindInfo.class);
			query.addField("msn", userBindInfo.getMsn());
			query.where("userid=?").setParam(userBindInfo.getUserId());
			query.update();
		}
		else {
			query.addField("userid", userBindInfo.getUserId());
			query.addField("msn", userBindInfo.getMsn());
			query.insert(UserBindInfo.class);
		}
	}

	public UserBindInfo getUserBindInfo(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserBindInfo.class, userId);
	}

	public UserBindInfo getUserBindInfoByMsn(String msn) {
		Query query = this.manager.createQuery();
		query.setTable(UserBindInfo.class);
		query.where("msn=?").setParam(msn);
		return query.getObject(UserBindInfo.class);
	}

	public UserNoticeInfo getUserNoticeInfo(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserNoticeInfo.class, userId);
	}

	public void updateUserNoticeInfo(UserNoticeInfo userNoticeInfo) {
		UserNoticeInfo o = this.getUserNoticeInfo(userNoticeInfo.getUserId());
		if (o == null) {
			Query query = this.manager.createQuery();
			query.addField("userid", userNoticeInfo.getUserId());
			query.addField("labareplynotice", userNoticeInfo
					.getLabaReplyNotice());
			query.addField("msgnotice", userNoticeInfo.getMsgNotice());
			query.addField("labareplysysnotice", userNoticeInfo
					.getLabaReplySysNotice());
			query.addField("follownotice", userNoticeInfo.getFollowNotice());
			query.addField("followsysnotice", userNoticeInfo
					.getFollowSysNotice());
			query
					.addField("followimnotice", userNoticeInfo
							.getFollowIMNotice());
			query.addField("labareplyimnotice", userNoticeInfo
					.getLabaReplyIMNotice());
			query.addField("userinlabasysnotice", userNoticeInfo
					.getUserInLabaSysNotice());
			query.insert(UserNoticeInfo.class);
		}
		else {
			Query query = this.manager.createQuery();
			query.setTable(UserNoticeInfo.class);
			query.addField("labareplynotice", userNoticeInfo
					.getLabaReplyNotice());
			query.addField("msgnotice", userNoticeInfo.getMsgNotice());
			query.addField("labareplysysnotice", userNoticeInfo
					.getLabaReplySysNotice());
			query.addField("follownotice", userNoticeInfo.getFollowNotice());
			query.addField("followsysnotice", userNoticeInfo
					.getFollowSysNotice());
			query
					.addField("followimnotice", userNoticeInfo
							.getFollowIMNotice());
			query.addField("labareplyimnotice", userNoticeInfo
					.getLabaReplyIMNotice());
			query.addField("userinlabasysnotice", userNoticeInfo
					.getUserInLabaSysNotice());
			query.where("userid=?").setParam(userNoticeInfo.getUserId());
			query.update();
		}
	}

	public void updateBirthday(long userId, int month, int date) {
		Query query = this.manager.createQuery();
		query.setTable(UserOtherInfo.class);
		query.addField("birthdaymonth", month);
		query.addField("birthdaydate", date);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public User abolishUser(long ownnerId, String input, String password) {
		long userId = 0;
		UserOtherInfo info = null;
		User user = null;
		if (input.indexOf("@") != -1) {
			info = this.getUserOtherInfoByeEmail(input);
			if (info != null) {
				user = this.getUser(info.getUserId());
			}
		}
		else if (input.length() > 10) {
			info = this.getUserOtherInfoByMobile(input);
			if (info != null) {
				user = this.getUser(info.getUserId());
			}
		}
		else {
			user = this.getUserByNickName(input);
			if (user != null) {
				info = this.getUserOtherInfo(user.getUserId());
			}
		}
		if (info != null && user != null) {
			String md5pwd = MD5Util.md5Encode32(password);
			boolean flg = false;
			if (info.getUserId() < 5000) {
				flg = md5pwd.hashCode() == info.getPwdHash();
			}
			else {
				flg = md5pwd.equals(info.getPwd());
			}
			if (flg) {
				userId = info.getUserId();
				if (userId == ownnerId) {
					return null;
				}
				info.setMobile(null);
				info.setMobileBind(UserOtherInfo.MOBILE_NOT_BIND);
				info.setEmail(null);
				info.setUserStatus(UserOtherInfo.USERSTATUS_STOP);
				this.updateUserOtherInfo(info);
				user.setNickName(userId + "");
				this.updateNickName(userId, user.getNickName());
				Query query = this.manager.createQuery();
				query.clearCacheObject(User.class, ownnerId);
				return user;
			}
			return null;
		}
		return null;
	}

	public List<User> getUserListSortFriend(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(User.class);
		query.orderByDesc("fanscount");
		return query.list(begin, size, User.class);
	}

	public List<User> getUserListInId(List<Long> idList, DataSort sort,
			int begin, int size) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList<User>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from user where userid in (" + sb.toString()
				+ ")";
		if (sort != null) {
			sql += " order by " + sort.getField();
			if (sort.isDesc()) {
				sql += " desc";
			}
		}
		Query query = this.manager.createQuery();
		return query.listBySql("ds1", sql, begin, size, User.class);
	}

	public List<User> getUserListInId(List<Long> idList) {
		return this.getUserListInId(idList, true);
	}

	public List<User> getUserListInId(List<Long> idList, boolean id_desc) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList<User>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from user where userid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		if (id_desc) {
			sql.append(" order by userid desc");
		}
		Query query = this.manager.createQuery();
		List<User> list = query.listBySqlEx("ds1", sql.toString(), User.class);
		return list;
	}

	public List<UserRecentUpdate> getUserRecentUpdateListInUserId(
			List<Long> idList, DataSort sort, int begin, int size) {
		if (idList == null || idList.size() == 0) {
			return new ArrayList<UserRecentUpdate>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from userrecentupdate where userid in ("
				+ sb.toString() + ")";
		if (sort != null) {
			sql += " order by " + sort.getField();
			if (sort.isDesc()) {
				sql += " desc";
			}
		}
		Query query = this.manager.createQuery();
		return query.listBySql("ds1", sql, begin, size, UserRecentUpdate.class);
	}

	public void updateUserRecentUpdate(UserRecentUpdate userRecentUpdate) {
		Query query = this.manager.createQuery();
		query.setTable(UserRecentUpdate.class);
		query
				.addField("last30labacount", userRecentUpdate
						.getLast30LabaCount());
		query.where("userid=?").setParam(userRecentUpdate.getUserId());
		query.update();
	}

	public void createUserRecentUpdate(UserRecentUpdate userRecentUpdate) {
		Query query = this.manager.createQuery();
		query.addField("userid", userRecentUpdate.getUserId());
		query
				.addField("last30labacount", userRecentUpdate
						.getLast30LabaCount());
		query.insert(UserRecentUpdate.class);
	}

	public UserRecentUpdate getUserRecentUpdate(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserRecentUpdate.class, userId);
	}

	public void createUserWebBind(UserWebBind userWebBind) {
		Query query = this.manager.createQuery();
		query.addField("userid", userWebBind.getUserId());
		query.addField("boseeid", userWebBind.getBoseeId());
		query.insert(UserWebBind.class);
	}

	public void updateUserWebBind(UserWebBind userWebBind) {
		Query query = this.manager.createQuery();
		query.setTable(UserWebBind.class);
		query.addField("boseeid", userWebBind.getBoseeId());
		query.where("userid=?").setParam(userWebBind.getUserId());
		query.update();
	}

	public List<User> getUserListSortUserRecentUpdate(int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select u.* from user u,userrecentupdate uru where u.userid=uru.userid order by uru.last30labacount desc";
		return query.listBySql("ds1", sql, begin, size, User.class);
	}

	public List<IpCityRangeUser> getIpCityRangeUserListSortFriend(int rangeId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select icru.* from ipcityrangeuser icru,user u where icru.rangeid=? and icru.userid=u.userid order by u.fanscount desc";
		return query.listBySql("ds1", sql, begin, size, IpCityRangeUser.class,
				rangeId);
	}

	public List<IpCityRangeUser> getIpCityRangeUserListSortUserRecentUpdate(
			int rangeId, int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select i.* from ipcityrangeuser i,userrecentupdate u where i.rangeid=? and i.userid=u.userid order by u.last30labacount desc";
		return query.listBySql("ds1", sql, begin, size, IpCityRangeUser.class,
				rangeId);
	}

	public List<IpCityUser> getIpCityUserListSortFriend(int cityId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		String sql = "select icu.* from ipcityuser icu,user u where icu.cityid=? and icu.userid=u.userid order by u.fanscount desc";
		return query.listBySql("ds1", sql, begin, size, IpCityUser.class,
				cityId);
	}

	public List<IpCityUser> getIpCityUserListSortUserRecentUpdate(int cityId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select i.* from ipcityuser i,userrecentupdate u where i.cityid=? and i.userid=u.userid order by u.last30labacount desc";
		return query.listBySql("ds1", sql, begin, size, IpCityUser.class,
				cityId);
	}

	public List<IpUser> getIpUserListSortFriend(String ip, int begin, int size) {
		long ipNumber = DataUtil.parseIpNumber(ip);
		Query query = this.manager.createQuery();
		String sql = "select iu.* from ipuser iu,user u where iu.ipnumber=? and iu.userid=u.userid order by u.fanscount desc";
		return query.listBySql("ds1", sql, begin, size, IpUser.class, ipNumber);
	}

	public List<IpUser> getIpUserListSortUserRecentUpdate(String ip, int begin,
			int size) {
		long ipnumber = DataUtil.parseIpNumber(ip);
		Query query = this.manager.createQuery();
		String sql = "select i.* from ipuser i,userrecentupdate u where i.ipnumber=? and i.userid=u.userid order by u.last30labacount desc";
		return query.listBySql("ds1", sql, begin, size, IpUser.class, ipnumber);
	}

	public boolean equalPwd(long userId, String pwd) {
		UserOtherInfo info = this.getUserOtherInfo(userId);
		String a1 = MD5Util.md5Encode32(pwd);
		if (userId < buguserid) {
			if (!DataUtil.isEmpty(info.getPwd())) {
				if (a1.equals(info.getPwd())) {
					return true;
				}
			}
			else {
				if (a1.hashCode() == info.getPwdHash()) {
					return true;
				}
			}
		}
		else {
			if (a1.equals(info.getPwd())) {
				return true;
			}
		}
		return false;
	}

	public void updateUser(User user) {
		Query query = this.manager.createQuery();
		query.addField("nickname", user.getNickName());
		query.addField("headPath", user.getHeadPath());
		query.addField("headflg", user.getHeadflg());
		query.addField("domain", user.getDomain());
		query.addField("friendCount", user.getFriendCount());
		query.addField("fansCount", user.getFansCount());
		query.addField("cityid", user.getCityId());
		query.addField("sex", user.getSex());
		query.updateById(User.class, user.getUserId());
	}

	public void updateCityId(long userId, int cityId) {
		Query query = this.manager.createQuery();
		query.addField("cityid", cityId);
		query.updateById(User.class, userId);
	}

	public void createDefFollowUser(long userId) {
		DefFollowUser o = this.getDefFollowUser(userId);
		if (o == null) {
			Query query = this.manager.createQuery();
			query.addField("userid", userId);
			query.addField("createtime", new Date());
			query.insert(DefFollowUser.class);
		}
	}

	public void delteDefFollowUser(long userId) {
		Query query = this.manager.createQuery();
		query.deleteById(DefFollowUser.class, userId);
	}

	public List<DefFollowUser> getDefFollowUserList(int begin, int size) {
		Query query = this.manager.createQuery();
		query.setTable(DefFollowUser.class);
		query.orderByDesc("createtime");
		return query.list(begin, size, DefFollowUser.class);
	}

	public DefFollowUser getDefFollowUser(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(DefFollowUser.class, userId);
	}

	public void createUserTool(UserTool userTool) {
		Query query = this.manager.createQuery();
		query.addField("userid", userTool.getUserId());
		query.addField("groundcount", userTool.getGroundCount());
		query.addField("labartflg", userTool.getLabartflg());
		query.addField("showreply", userTool.getShowReply());
		query.addField("invitecount", userTool.getInviteCount());
		query.insert(UserTool.class);
	}

	public void updateuserTool(UserTool userTool) {
		if (userTool.getGroundCount() < 0) {
			userTool.setGroundCount(0);
		}
		if (userTool.getInviteCount() < 0) {
			userTool.setInviteCount(0);
		}
		Query query = this.manager.createQuery();
		query.setTable(UserTool.class);
		query.addField("groundcount", userTool.getGroundCount());
		query.addField("labartflg", userTool.getLabartflg());
		query.addField("showreply", userTool.getShowReply());
		query.addField("invitecount", userTool.getInviteCount());
		query.where("userid=?").setParam(userTool.getUserId());
		query.update();
	}

	public UserTool getUserTool(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserTool.class, userId);
	}

	public UserTool checkUserTool(long userId) {
		UserTool o = this.getUserTool(userId);
		if (o == null) {
			o = UserTool.createDefault(userId);
			this.createUserTool(o);
		}
		return o;
	}

	public Map<Long, User> getUserMapInId(List<Long> idList) {
		List<User> list = this.getUserListInId(idList);
		Map<Long, User> map = new HashMap<Long, User>();
		for (User o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public Map<Long, User> getUserMapInId(String idStr) {
		StringBuilder sql = new StringBuilder(
				"select * from user where userid in (");
		sql.append(idStr).append(")");
		Query query = this.manager.createQuery();
		List<User> list = query.listBySqlEx("ds1", sql.toString(), User.class);
		Map<Long, User> map = new HashMap<Long, User>();
		for (User o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public boolean hasEnoughHkb(long userId, int add) {
		UserOtherInfo info = this.getUserOtherInfo(userId);
		if (info == null) {
			return false;
		}
		if (info.getHkb() + add >= 0) {
			return true;
		}
		return false;
	}

	public void createHkbLog(HkbLog hkbLog) {
		hkbLog.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("userid", hkbLog.getUserId());
		query.addField("hkbtype", hkbLog.getHkbtype());
		query.addField("addcount", hkbLog.getAddcount());
		query.addField("createtime", hkbLog.getCreateTime());
		query.addField("objid", hkbLog.getObjId());
		query.insert(HkbLog.class);
	}

	public List<AdminHkb> getAdminHkbList(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(AdminHkb.class);
		if (userId > 0) {
			query.where("userid=?").setParam(userId);
		}
		query.orderByDesc("sysid");
		return query.list(begin, size, AdminHkb.class);
	}

	public List<HkbLog> getHkbLogList(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(HkbLog.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("logid");
		return query.list(begin, size, HkbLog.class);
	}

	public List<ScoreLog> getScoreLogList(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(ScoreLog.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("logid");
		return query.list(begin, size, ScoreLog.class);
	}

	public void createAdminHkb(AdminHkb adminHkb) {
		if (adminHkb.getAddCount() <= 0) {
			return;
		}
		adminHkb.setCreateTime(new Date());
		Query query = manager.createQuery();
		query.addField("userid", adminHkb.getUserId());
		query.addField("opuserid", adminHkb.getOpuserId());
		query.addField("addcount", adminHkb.getAddCount());
		query.addField("money", adminHkb.getMoney());
		query.addField("content", adminHkb.getContent());
		query.addField("addflg", adminHkb.getAddflg());
		query.addField("createtime", adminHkb.getCreateTime());
		query.insert(AdminHkb.class);
		int b = 0;
		if (adminHkb.getAddflg() == AdminHkb.ADDFLG_MONEYBUY) {
			b = HkbConfig.MONEYBUG;
		}
		else {
			b = HkbConfig.SYSPRESENT;
		}
		HkbLog log = HkbLog.create(adminHkb.getUserId(), b, 0, adminHkb
				.getAddCount());
		log.setAddcount(adminHkb.getAddCount());
		log.setUserId(adminHkb.getUserId());
		if (adminHkb.getAddflg() == AdminHkb.ADDFLG_MONEYBUY) {
			log.setHkbtype(HkbConfig.MONEYBUG);
		}
		else {
			log.setHkbtype(HkbConfig.SYSPRESENT);
		}
		this.addHkb(log);
	}

	public boolean isMobileAlreadyBind(long userId) {
		UserOtherInfo info = this.getUserOtherInfo(userId);
		return info.isMobileAlreadyBind();
	}

	public void updateUserContactDegree(long userId, long contactUserId) {
		UserContactDegree o = this.getUserContactDegree(userId, contactUserId);
		Query query = manager.createQuery();
		if (o == null) {
			o = new UserContactDegree(userId, contactUserId);
			query.addField("userid", o.getUserId());
			query.addField("contactuserid", o.getContactUserId());
			query.addField("degree", o.getDegree());
			query.insert(UserContactDegree.class);
		}
		else {
			query.setTable(UserContactDegree.class);
			query.addField("degree", "add", 1);
			query.where("userid=? and contactuserid=?").setParam(userId)
					.setParam(contactUserId);
			query.update();
		}
	}

	private UserContactDegree getUserContactDegree(long userId,
			long contactUserId) {
		Query query = manager.createQuery();
		query.setTable(UserContactDegree.class);
		query.where("userid=? and contactuserid=?").setParam(userId).setParam(
				contactUserId);
		return query.getObject(UserContactDegree.class);
	}

	public List<UserOtherInfo> getUserOtherInfoListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserOtherInfo>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from userotherinfo where userid in("
				+ sb.toString() + ")";
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql, UserOtherInfo.class);
	}

	public Map<Long, UserOtherInfo> getUserOtherInfoMapInId(List<Long> idList) {
		List<UserOtherInfo> list = this.getUserOtherInfoListInId(idList);
		Map<Long, UserOtherInfo> map = new HashMap<Long, UserOtherInfo>();
		for (UserOtherInfo o : list) {
			map.put(o.getUserId(), o);
		}
		return map;
	}

	public void updateValidateEmail(long userId, byte validateEmail) {
		Query query = manager.createQuery();
		query.addField("validateemail", validateEmail);
		query.update(UserOtherInfo.class, "userid=?", new Object[] { userId });
	}

	public void createRegfromUser(long userId, int regfrom, long fromId) {
		Query query = manager.createQuery();
		query.addField("userid", userId);
		query.addField("regfrom", regfrom);
		query.addField("fromid", fromId);
		query.insert(RegfromUser.class);
	}

	public List<UserTool> getUserTooList(int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(UserTool.class, "groundcount desc", begin, size);
	}

	public List<UserTool> getUserToolListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<UserTool>();
		}
		StringBuilder sb = new StringBuilder();
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from usertool where userid in(" + sb.toString()
				+ ") order by groundcount desc";
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql, UserTool.class);
	}

	public boolean createProUser(ProUser proUser) {
		proUser.setUptime(new Date());
		Query query = manager.createQuery();
		if (proUser.getUserId() > 0) {
			if (query.count(ProUser.class, "userid=?", new Object[] { proUser
					.getUserId() }) > 0) {
				return false;
			}
		}
		query.addField("nickname", proUser.getNickName());
		query.addField("intro", proUser.getIntro());
		query.addField("input", proUser.getInput());
		query.addField("uptime", proUser.getUptime());
		query.addField("createrid", proUser.getCreaterId());
		query.addField("userid", proUser.getUserId());
		long id = query.insert(ProUser.class).longValue();
		proUser.setOid(id);
		return true;
	}

	public void deleteProUser(long sysId) {
		Query query = manager.createQuery();
		query.deleteById(ProUser.class, sysId);
	}

	public List<ProUser> getProUserList(String nickName, int begin, int size) {
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder("select * from prouser where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (DataUtil.isEmpty(nickName)) {
			sql.append(" and nickname=?");
			olist.add(nickName);
		}
		sql.append(" order by oid desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				ProUser.class, olist);
	}

	public boolean updateProUser(ProUser proUser) {
		Query query = manager.createQuery();
		query.addField("nickname", proUser.getNickName());
		query.addField("input", proUser.getInput());
		query.addField("intro", proUser.getIntro());
		query.addField("uptime", proUser.getUptime());
		query.addField("createrid", proUser.getCreaterId());
		query.addField("userid", proUser.getUserId());
		query.update(ProUser.class, "oid=?", new Object[] { proUser.getOid() });
		return true;
	}

	public ProUser getProUser(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(ProUser.class, oid);
	}

	public boolean createWelProUser(long userId, long prouserId) {
		Query query = manager.createQuery();
		if (query.count(WelProUser.class, "userid=? and prouserid=?",
				new Object[] { userId, prouserId }) > 0) {
			return false;
		}
		query.addField("prouserid", prouserId);
		query.addField("userId", userId);
		query.insert(WelProUser.class);
		return true;
	}

	public List<ProUser> getProUserListByOid(long oid, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(ProUser.class, "oid=?", new Object[] { oid },
				"oid desc", begin, size);
	}

	public ProUser getProUserByUserID(long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(ProUser.class, "userid=?",
				new Object[] { userId });
	}

	public List<WelProUser> getWelProuserListByProuserId(long prouserId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(WelProUser.class, "prouserid=?",
				new Object[] { prouserId }, "oid desc", begin, size);
	}

	public List<User> getUserListForBirthday(int begin, int size) {
		Calendar max = Calendar.getInstance();
		Calendar min = Calendar.getInstance();
		min.add(Calendar.DATE, -3);
		int max_month = max.get(Calendar.MONTH) + 1;
		int max_date = max.get(Calendar.DATE);
		int min_month = min.get(Calendar.MONTH) + 1;
		int min_date = min.get(Calendar.DATE);
		Query query = manager.createQuery();
		String sql = "select * from userotherinfo o,user u where o.userid=u.userid and birthdaymonth>=? and birthdaymonth<=? and birthdaydate>=? and birthdaydate<=? order by birthdaymonth asc,birthdaydate asc";
		return query.listBySql("ds1", sql, begin, size, User.class, min_month,
				max_month, min_date, max_date);
	}

	public List<User> getUserListInIdForBirthday(List<Long> idList, int begin,
			int size) {
		if (idList.size() == 0) {
			return new ArrayList<User>();
		}
		Calendar max = Calendar.getInstance();
		Calendar min = Calendar.getInstance();
		min.add(Calendar.DATE, -3);
		int max_month = max.get(Calendar.MONTH) + 1;
		int max_date = max.get(Calendar.DATE);
		int min_month = min.get(Calendar.MONTH) + 1;
		int min_date = min.get(Calendar.DATE);
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from userotherinfo o,user u where o.userid=u.userid and birthdaymonth>=? and birthdaymonth<=? and birthdaydate>=? and birthdaydate<=? and u.userid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		sql.append(" order by birthdaymonth asc,birthdaydate asc");
		return query.listBySql("ds1", sql.toString(), begin, size, User.class,
				min_month, max_month, min_date, max_date);
	}

	public void updateUserPcityId(long userId, int pcityId) {
		Query query = manager.createQuery();
		query.addField("pcityid", pcityId);
		query.updateById(User.class, userId);
	}

	public void addPoints(long userId, int add) {
		Query query = manager.createQuery();
		UserOtherInfo info = query.getObjectById(UserOtherInfo.class, userId);
		if (info != null) {
			info.setPoints(info.getPoints() + add);
			if (info.getPoints() < 0) {
				info.setPoints(0);
			}
		}
		query.updateObject(info);
	}

	public List<UserUpdate> getUserUpdateListInIdList(List<Long> idList,
			int begin, int size) {
		if (idList.size() == 0) {
			return new ArrayList<UserUpdate>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from userupdate where userid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(") order by uptime desc");
		Query query = manager.createQuery();
		return query.listBySql("ds1", sql.toString(), begin, size,
				UserUpdate.class);
	}

	public void updateUserUpdate(long userId) {
		Query query = manager.createQuery();
		query.addField("uptime", System.currentTimeMillis());
		query.updateById(UserUpdate.class, userId);
	}

	public synchronized boolean updateUserDomain(long userId, String domain) {
		User user = this.getUserByDomain(domain.toLowerCase());
		if (user != null && user.getUserId() != userId) {
			return false;
		}
		Query query = manager.createQuery();
		query.addField("domain", domain);
		query.updateById(User.class, userId);
		return true;
	}

	// @Override
	// public void createApi_user_sina(Api_user_sina apiUserSina, String nick) {
	// boolean create = false;
	// // 是否存在新浪用户
	// Query query = this.manager.createQuery();
	// Api_user_sina db_obj = query.getObjectById(Api_user_sina.class,
	// apiUserSina.getSina_userid());
	// if (db_obj == null) {
	// query.insertObject(apiUserSina);
	// create = true;
	// }
	// else {
	// apiUserSina.setUserid(db_obj.getUserid());
	// }
	// // 检查是否已经有用户信息
	// if (apiUserSina.getUserid() == 0) {
	// // 创建用户
	// long userId;
	// try {
	// userId = this.createUser(null, null, null, null);
	// // 更新api_user_sina中userid
	// User user = this.getUser(userId);
	// apiUserSina.setUserid(user.getUserId());
	// query.updateObject(apiUserSina);
	// }
	// catch (EmailDuplicateException e) {
	// }
	// catch (MobileDuplicateException e) {
	// }
	// }
	// if (query
	// .getObjectEx(Api_user.class, "userid=? and api_type=?",
	// new Object[] { apiUserSina.getUserid(),
	// Api_user.API_TYPE_SINA }) == null) {
	// Api_user apiUser = new Api_user();
	// apiUser.setUserid(apiUserSina.getUserid());
	// apiUser.setApi_type(Api_user.API_TYPE_SINA);
	// query.insertObject(apiUser);
	// }
	// }
	//
	// @Override
	// public void updateApi_user_sina(Api_user_sina apiUserSina) {
	// this.manager.createQuery().updateObject(apiUserSina);
	// }
	//
	// @Override
	// public Api_user getApi_userByUseridAndApi_type(long userid, int apiType)
	// {
	// return this.manager.createQuery().getObjectEx(Api_user.class,
	// "userid=? and api_type=?", new Object[] { userid, apiType });
	// }
	//
	// @Override
	// public Api_user_sina getApi_user_sinaBySina_userid(long sina_userid) {
	// return this.manager.createQuery().getObjectById(Api_user_sina.class,
	// sina_userid);
	// }
	//
	// @Override
	// public Api_user_sina getApi_user_sinaByUserid(long userid) {
	// return this.manager.createQuery().getObjectEx(Api_user_sina.class,
	// "userid=?", new Object[] { userid });
	// }
}