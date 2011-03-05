package tuxiazi.svr.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.PhotoCmtid;
import tuxiazi.bean.User;
import tuxiazi.bean.helper.noticedata.PhotoCmtNoticeCreater;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.FileCnf;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;

public class PhotoCmtServiceImpl implements PhotoCmtService {

	@Autowired
	private QueryManager manager;

	@Autowired
	private UserService userService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private FileCnf fileCnf;

	@Autowired
	private NoticeService noticeService;

	private Log log = LogFactory.getLog(PhotoCmtServiceImpl.class);

	@Override
	public void createPhotoCmt(Photo photo, PhotoCmt photoCmt, User user) {
		Query query = this.manager.createQuery();
		if (photo == null) {
			return;
		}
		long cmtid = query.insertObject(new PhotoCmtid()).longValue();
		photoCmt.setCmtid(cmtid);
		photoCmt.setUser(user);
		query.insertObject(photoCmt);
		photo.setCmt_num(photo.getCmt_num() + 1);
		List<PhotoCmt> list = photo.getCmtList();
		list.add(0, photoCmt);
		photo.buildRecentCmtData(list);
		this.photoService.updatePhoto(photo);
		if (photoCmt.getUserid() != photo.getUserid()) {
			// 发送通知
			PhotoCmtNoticeCreater photoCmtNoticeCreater = new PhotoCmtNoticeCreater();
			photoCmtNoticeCreater.setUserid(photo.getUserid());
			photoCmtNoticeCreater.setCmtid(cmtid);
			photoCmtNoticeCreater.setPhotoid(photo.getPhotoid());
			photoCmtNoticeCreater.setSenderid(photoCmt.getUserid());
			photoCmtNoticeCreater.setSender_nick(user.getNick());
			photoCmtNoticeCreater.setSender_head(user.getHead_path());
			this.noticeService
					.createNotice(photoCmtNoticeCreater.buildNotice());
		}
	}

	@Override
	public void createPhotoCmt(Photo photo, PhotoCmt photoCmt, User user,
			int withweibo, Api_user_sina apiUserSina) {
		this.createPhotoCmt(photo, photoCmt, user);
		if (withweibo == 1) {
			String filepath = this.fileCnf.getFilePath(photo.getPath());
			File imgFile = FileCnf.getFile(filepath + Photo.p4_houzhui);
			try {
				SinaUtil.updateStatus(apiUserSina.getAccess_token(),
						apiUserSina.getToken_secret(), photoCmt.getContent(),
						imgFile);
			}
			catch (WeiboException e) {
				log.error("error while share to weibo");
				log.error(e.toString());
			}
		}
	}

	@Override
	public void deletePhotoCmt(PhotoCmt photoCmt) {
		Photo photo = this.photoService.getPhoto(photoCmt.getPhotoid());
		if (photo != null) {
			photo.setCmt_num(photo.getCmt_num() - 1);
			if (photo.getCmt_num() < 0) {
				photo.setCmt_num(0);
			}
			List<PhotoCmt> list = photo.getCmtList();
			for (PhotoCmt o : list) {
				if (o.getCmtid() == photoCmt.getCmtid()) {
					list.remove(o);
					break;
				}
			}
			photo.buildRecentCmtData(list);
			this.photoService.updatePhoto(photo);
		}
		this.manager.createQuery().delete(PhotoCmt.class,
				"cmtid=? and photoid=?",
				new Object[] { photoCmt.getCmtid(), photoCmt.getPhotoid() });
	}

	@Override
	public void deletePhotoCmtByPhotoid(long photoid) {
		this.manager.createQuery().delete(PhotoCmt.class, "photoid=?",
				new Object[] { photoid });
	}

	@Override
	public PhotoCmt getPhotoCmt(long photoid, long cmtid) {
		return this.manager.createQuery().getObjectEx(PhotoCmt.class,
				"photoid=? and cmtid=?", new Object[] { photoid, cmtid });
	}

	@Override
	public List<PhotoCmt> getPhotoCmtListByPhotoid(long photoid,
			boolean buildUser, int begin, int size) {
		List<PhotoCmt> list = this.manager.createQuery().listEx(PhotoCmt.class,
				"photoid=?", new Object[] { photoid }, "cmtid desc", begin,
				size);
		if (buildUser) {
			List<Long> idList = new ArrayList<Long>();
			for (PhotoCmt o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (PhotoCmt o : list) {
				o.setUser(map.get(o.getUserid()));
			}
		}
		return list;
	}
}