package tuxiazi.svr.impl2;

import halo.util.DataUtil;
import halo.util.NumberUtil;
import halo.util.ResourceConfig;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.PhotoCmtid;
import tuxiazi.bean.User;
import tuxiazi.bean.helper.noticedata.PhotoCmtNoticeCreater;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.PhotoCmtidDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.util.FileCnf;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

public class PhotoCmtServiceImpl implements PhotoCmtService {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private FileCnf fileCnf;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private PhotoCmtDao photoCmtDao;

	@Autowired
	private PhotoCmtidDao photoCmtidDao;

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private UserDao userDao;

	private Log log = LogFactory.getLog(PhotoCmtServiceImpl.class);

	@Override
	public void createPhotoCmt(Photo photo, PhotoCmt photoCmt, User user) {
		if (photo == null) {
			return;
		}
		long cmtid = NumberUtil.getLong(this.photoCmtidDao
				.save(new PhotoCmtid()));
		photoCmt.setCmtid(cmtid);
		photoCmt.setUser(user);
		this.photoCmtDao.save(photoCmt);
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
			photoCmtNoticeCreater.setContent(photoCmt.getContent());
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
				User photoUser = this.userDao.getById(photo.getUserid());
				String content = null;
				if (user.getUserid() == photo.getUserid()) {
					content = "针对自己 的图片评论道：“"
							+ DataUtil.toText(photoCmt.getContent())
							+ "” "
							+ ResourceConfig.getText("photourl",
									photo.getPhotoid() + "");
				}
				else {
					content = "针对 @"
							+ photoUser.getNick()
							+ " 的图片评论道：“"
							+ DataUtil.toText(photoCmt.getContent())
							+ "” "
							+ ResourceConfig.getText("photourl",
									photo.getPhotoid() + "");
				}
				SinaUtil.updateStatus(apiUserSina.getAccess_token(),
						apiUserSina.getToken_secret(), content, imgFile);
			}
			catch (WeiboException e) {
				log.error("error while share to weibo");
				log.error(e.toString());
			}
		}
	}

	@Override
	public void deletePhotoCmt(PhotoCmt photoCmt) {
		Photo photo = this.photoDao.getById(photoCmt.getPhotoid());
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
		this.photoCmtDao.deleteById(photoCmt.getCmtid());
	}

	@Override
	public void deletePhotoCmtByPhotoid(long photoid) {
		this.photoCmtDao.deleteByPhotoid(photoid);
	}
}