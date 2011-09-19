package tuxiazi.svr.impl2;

import halo.util.DataUtil;
import halo.util.JsonUtil;
import halo.util.ResourceConfig;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Notice;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.User;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.util.FileCnf;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

@Component("photoCmtService")
public class PhotoCmtServiceImpl implements PhotoCmtService {

	@Autowired
	private FileCnf fileCnf;

	@Autowired
	private PhotoCmtDao photoCmtDao;

	@Autowired
	private UserDao userDao;

	private Log log = LogFactory.getLog(PhotoCmtServiceImpl.class);

	@Override
	public PhotoCmt createPhotoCmt(Photo photo, String content, User sender,
			User replyedUser, boolean sendWeibo, Api_user_sina apiUserSina) {
		PhotoCmt photoCmt = new PhotoCmt();
		photoCmt.setContent(content);
		photoCmt.setUserid(sender.getUserid());
		photoCmt.setCreate_time(new Date());
		photoCmt.setPhotoid(photo.getPhotoid());
		if (replyedUser != null) {
			photoCmt.setReplyuserid(replyedUser.getUserid());
		}
		photoCmt.save();
		this.processNotice(photo, photoCmt);
		this.processWeibo(photo, photoCmt, sender, sendWeibo, apiUserSina);
		return photoCmt;
	}

	private void processNotice(Photo photo, PhotoCmt photoCmt) {
		if (photoCmt.getUserid() != photo.getUserid()) {
			// 发送通知
			Notice notice = new Notice();
			notice.setUserid(photo.getUserid());
			notice.setCreatetime(new Date());
			notice.setNotice_flg(NoticeEnum.ADD_PHOTOCMT.getValue());
			notice.setReadflg(NoticeReadEnum.UNREAD.getValue());
			notice.setRefoid(photo.getPhotoid());
			notice.setSenderid(photoCmt.getUserid());
			Map<String, String> map = new HashMap<String, String>();
			map.put("cmtid", String.valueOf(photoCmt.getCmtid()));
			notice.setData(JsonUtil.toJson(map));
			notice.save();
		}
	}

	private void processWeibo(Photo photo, PhotoCmt photoCmt, User sender,
			boolean sendWeibo, Api_user_sina apiUserSina) {
		if (!sendWeibo) {
			return;
		}
		String filepath = this.fileCnf.getFilePath(photo.getPath());
		File imgFile = FileCnf.getFile(filepath + Photo.p4_houzhui);
		try {
			User photoUser = this.userDao.getById(photo.getUserid());
			String content = null;
			if (sender.getUserid() == photo.getUserid()) {
				content = "针对自己 的图片评论道：“"
						+ DataUtil.toText(photoCmt.getContent())
						+ "” "
						+ ResourceConfig.getTextFromResource("i18n",
								"photourl", photo.getPhotoid() + "");
			}
			else {
				content = "针对 @"
						+ photoUser.getNick()
						+ " 的图片评论道：“"
						+ DataUtil.toText(photoCmt.getContent())
						+ "” "
						+ ResourceConfig.getTextFromResource("i18n",
								"photourl", photo.getPhotoid() + "");
			}
			SinaUtil.updateStatus(apiUserSina.getAccess_token(),
					apiUserSina.getToken_secret(), content, imgFile);
		}
		catch (WeiboException e) {
			log.error("error while share to weibo");
			log.error(e.toString());
		}
	}

	@Override
	public void deletePhotoCmt(Photo photo, PhotoCmt photoCmt) {
		photoCmt.delete();
		photo.setCmt_num(this.photoCmtDao.countByPhotoid(photo.getPhotoid()));
		photo.update();
	}

	@Override
	public void deletePhotoCmtByPhotoid(long photoid) {
		this.photoCmtDao.deleteByPhotoid(photoid);
	}
}