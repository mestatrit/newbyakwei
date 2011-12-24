package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsContent;
import com.hk.bean.CmpBbsDel;
import com.hk.bean.CmpBbsReply;
import com.hk.bean.CmpBbsReplyDel;
import com.hk.bean.CmpMyBbs;
import com.hk.bean.Photo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpBbsService;
import com.hk.svr.PhotoService;
import com.hk.svr.UserService;

public class CmpBbsProcessor {

	@Autowired
	private CmpBbsService cmpBbsService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private UserService userService;

	public List<CmpBbsDel> getCmpBbsDelListByCompanyId(long companyId,
			boolean buildOpuser, int begin, int size) {
		List<CmpBbsDel> list = this.cmpBbsService.getCmpBbsDelListByCompanyId(
				companyId, begin, size);
		if (buildOpuser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpBbsDel o : list) {
				idList.add(o.getOpuserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpBbsDel o : list) {
				o.setOpuser(map.get(o.getOpuserId()));
			}
		}
		return list;
	}

	public List<CmpBbsReplyDel> getCmpBbsReplyDelListByCompanyId(
			long companyId, boolean buildUser, boolean buildOpuser, int begin,
			int size) {
		List<CmpBbsReplyDel> list = this.cmpBbsService
				.getCmpBbsReplyDelListByCompanyId(companyId, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpBbsReplyDel o : list) {
				idList.add(o.getUserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpBbsReplyDel o : list) {
				o.setUser(map.get(o.getUserId()));
			}
		}
		if (buildOpuser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpBbsReplyDel o : list) {
				idList.add(o.getOpuserId());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpBbsReplyDel o : list) {
				o.setOpuser(map.get(o.getOpuserId()));
			}
		}
		return list;
	}

	public void createCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent,
			File file) throws ImageException, NotPermitImageFormatException,
			OutOfSizeException, IOException {
		Photo photo = this.saveFile(cmpBbs.getUserId(), file);
		if (photo != null) {
			cmpBbs.setPicpath(photo.getPath());
			cmpBbs.setPhotoId(photo.getPhotoId());
		}
		this.cmpBbsService.createCmpBbs(cmpBbs, cmpBbsContent);
	}

	public void updateCmpBbs(CmpBbs cmpBbs, CmpBbsContent cmpBbsContent,
			File file) throws ImageException, NotPermitImageFormatException,
			OutOfSizeException, IOException {
		Photo photo = this.saveFile(cmpBbs.getUserId(), file);
		if (photo != null) {
			cmpBbs.setPicpath(photo.getPath());
			cmpBbs.setPhotoId(photo.getPhotoId());
		}
		this.cmpBbsService.updateCmpBbs(cmpBbs, cmpBbsContent);
	}

	public void createCmpBbsReply(CmpBbsReply cmpBbsReply, CmpBbs cmpBbs,
			File file) throws ImageException, NotPermitImageFormatException,
			OutOfSizeException, IOException {
		Photo photo = this.saveFile(cmpBbsReply.getUserId(), file);
		if (photo != null) {
			cmpBbsReply.setPicpath(photo.getPath());
			cmpBbsReply.setPhotoId(photo.getPhotoId());
		}
		this.cmpBbsService.createCmpBbsReply(cmpBbsReply, cmpBbs);
	}

	public void updateCmpBbsReply(CmpBbsReply cmpBbsReply, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException, IOException {
		Photo photo = this.saveFile(cmpBbsReply.getUserId(), file);
		if (photo != null) {
			cmpBbsReply.setPicpath(photo.getPath());
			cmpBbsReply.setPhotoId(photo.getPhotoId());
		}
		this.cmpBbsService.updateCmpBbsReply(cmpBbsReply);
	}

	public List<CmpMyBbs> getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(
			long companyId, long userId, byte bbsflg, boolean buildUser,
			int begin, int size) {
		List<CmpMyBbs> mylist = this.cmpBbsService
				.getCmpMyBbsListByCompanyIdAndUserIdAndBbsflg(companyId,
						userId, bbsflg, begin, size);
		List<Long> idList = new ArrayList<Long>();
		for (CmpMyBbs o : mylist) {
			idList.add(o.getBbsId());
		}
		Map<Long, CmpBbs> map = this.cmpBbsService.getCmpBbsMapInId(idList);
		List<CmpBbs> bbslist = new ArrayList<CmpBbs>(map.values());
		if (buildUser) {
			this.buildCmpBbsUser(bbslist);
		}
		for (CmpMyBbs o : mylist) {
			o.setCmpBbs(map.get(o.getBbsId()));
		}
		return mylist;
	}

	public List<CmpBbs> getCmpBbsListByKindId(long kindId, boolean buildUser,
			int begin, int size) {
		List<CmpBbs> list = this.cmpBbsService.getCmpBbsListByKindId(kindId,
				begin, size);
		if (buildUser) {
			this.buildCmpBbsUser(list);
		}
		return list;
	}

	private void buildCmpBbsUser(List<CmpBbs> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpBbs o : list) {
			if (!idList.contains(o.getUserId())) {
				idList.add(o.getUserId());
			}
			if (o.getLastReplyUserId() > 0
					&& !idList.contains(o.getLastReplyUserId())) {
				idList.add(o.getLastReplyUserId());
			}
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (CmpBbs o : list) {
			o.setUser(map.get(o.getUserId()));
			if (o.getLastReplyUserId() > 0) {
				o.setLastReplyUser(map.get(o.getLastReplyUserId()));
			}
		}
	}

	public List<CmpBbsReply> getCmpBbsReplieListByBbsId(long bbsId,
			boolean buildUser, int begin, int size) {
		List<CmpBbsReply> list = this.cmpBbsService.getCmpBbsReplieListByBbsId(
				bbsId, begin, size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpBbsReply o : list) {
				if (!idList.contains(o.getUserId())) {
					idList.add(o.getUserId());
				}
				if (o.getReplyUserId() > 0
						&& !idList.contains(o.getReplyUserId())) {
					idList.add(o.getReplyUserId());
				}
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (CmpBbsReply o : list) {
				o.setUser(map.get(o.getUserId()));
				if (o.getReplyUserId() > 0) {
					o.setReplyUser(map.get(o.getReplyUserId()));
				}
			}
		}
		return list;
	}

	private Photo saveFile(long userId, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException, IOException {
		if (file == null) {
			return null;
		}
		if (!DataUtil.isImage(file)) {
			return null;
		}
		Photo photo = new Photo();
		photo.setUserId(userId);
		photo.setCreateTime(new Date());
		photoService.createPhoto(photo, file, 2);
		return photo;
	}
}