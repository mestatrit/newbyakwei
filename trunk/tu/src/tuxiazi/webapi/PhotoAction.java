package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.util.ResourceConfig;
import halo.util.image.ImageException;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.HotPhoto;
import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.dao.Friend_photo_feedDao;
import tuxiazi.dao.HotPhotoDao;
import tuxiazi.dao.Lasted_photoDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.PhotoLikeUserDao;
import tuxiazi.dao.UserDao;
import tuxiazi.dao.User_photoDao;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;
import weibo4j.WeiboException;

@Component("/api/photo")
public class PhotoAction extends BaseApiAction {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private Friend_photo_feedDao friend_photo_feedDao;

	@Autowired
	private PhotoDao photoDao;

	@Autowired
	private User_photoDao user_photoDao;

	@Autowired
	private Lasted_photoDao lasted_photoDao;

	@Autowired
	private HotPhotoDao hotPhotoDao;

	@Autowired
	private PhotoLikeUserDao photoLikeUserDao;

	@Autowired
	private UserDao userDao;

	private final Log log = LogFactory.getLog(PhotoAction.class);

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		try {
			long favUserid = 0;
			User user = this.getUser(req);
			if (user != null) {
				favUserid = user.getUserid();
			}
			long photoid = req.getLong("photoid");
			Photo photo = this.photoDao.getById(photoid, favUserid, true);
			if (photo == null) {
				APIUtil.writeErr(resp, Err.PHOTO_NOTEXIST);
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("photo", photo);
			APIUtil.writeData(resp, map, "vm/photo.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 删除图片
	 * 
	 * @param req
	 * @param resp
	 * @return 2010-11-8
	 */
	public String prvdelete(HkRequest req, HkResponse resp) {
		try {
			User user = this.getUser(req);
			long photoid = req.getLong("photoid");
			Photo photo = this.photoDao.getById(photoid);
			if (photo == null) {
				APIUtil.writeErr(resp, Err.PHOTO_NOTEXIST);
				return null;
			}
			if (photo.getUserid() != user.getUserid()) {
				APIUtil.writeErr(resp, Err.OP_NOPOWER);
				return null;
			}
			User _u = this.userDao.getById(user.getUserid());
			this.photoService.deletePhoto(photo, _u);
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param resp
	 * @return 0:成功<br/>
	 *         14:图片超出1M限制<br/>
	 *         15:图片处理出错<br/>
	 *         4:系统出错<br/>
	 */
	public String prvupload(HkRequest req, HkResponse resp) {
		try {
			File file = req.getFile("f");
			if (file == null) {
				APIUtil.writeErr(resp, Err.PHOTO_FILE_NOTEXIST);
				return null;
			}
			User user = this.getUser(req);
			UploadPhoto uploadPhoto = new UploadPhoto();
			uploadPhoto.setFile(file);
			uploadPhoto
					.setName(DataUtil.limitLength(req.getString("name"), 140));
			uploadPhoto.setCreate_time(new Date());
			uploadPhoto.setPrivacy_flg(req.getByte("privacy_flg"));
			List<UploadPhoto> list = new ArrayList<UploadPhoto>();
			list.add(uploadPhoto);
			boolean withweibo = false;
			if (req.getInt("withweibo") == 1) {
				withweibo = true;
			}
			User _u = this.userDao.getById(user.getUserid());
			Photo photo = this.photoService.createPhoto(uploadPhoto, withweibo,
					_u, this.getApiUserSina(req));
			photo.setUser(_u);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("photo", photo);
			APIUtil.writeData(resp, map, "vm/uploadphotook.vm");
		}
		catch (ImageSizeOutOfLimitException e) {
			APIUtil.writeErr(resp, Err.API_PHOTO_OUTOF_LIMIT);
		}
		catch (ImageException e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.API_PHOTO_PROCESS_ERROR);
		}
		catch (IOException e) {
			log.error(e.getMessage());
			APIUtil.writeErr(resp, Err.API_PHOTO_PROCESS_ERROR);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 获取最新图片,如果用户已登录就获取用户关注的人的最新图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-11-8
	 */
	public String lasted(HkRequest req, HkResponse resp) throws Exception {
		User user = this.getUser(req);
		if (user == null) {
			List<Lasted_photo> lastedlist = this.lasted_photoDao.getList(true,
					false, 0, false, false, 0, 1);
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.SUCCESS);
			context.put("lastedlist", lastedlist);
			APIUtil.write(resp, "vm/lasted.vm", context);
			return null;
		}
		List<Friend_photo_feed> list = this.friend_photo_feedDao
				.getListByUserid(user.getUserid(), true, false, 0, false,
						false, 0, 1);
		if (list.size() > 0) {
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.SUCCESS);
			context.put("list", list);
			APIUtil.write(resp, "vm/friend_lasted.vm", context);
			return null;
		}
		List<Lasted_photo> lastedlist = this.lasted_photoDao.getList(true,
				false, user.getUserid(), false, false, 0, 1);
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put("lastedlist", lastedlist);
		APIUtil.write(resp, "vm/lasted.vm", context);
		return null;
	}

	/**
	 * 获取好友的最新图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-11-8
	 */
	public String prvfriendphotos(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		int page = req.getInt("page");
		int size = req.getInt("size");
		if (page <= 0) {
			page = 1;
		}
		if (size <= 0) {
			size = 10;
		}
		try {
			SimplePage simplePage = new SimplePage(size, page);
			List<Friend_photo_feed> list = this.friend_photo_feedDao
					.getListByUserid(user.getUserid(), true, true,
							user.getUserid(), true, true,
							simplePage.getBegin(), simplePage.getSize());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/friendphotos.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return 2010-11-30
	 */
	public String list(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 1);
		SimplePage simplePage = new SimplePage(size, page);
		long userid = 0;
		User loginuser = this.getUser(req);
		if (loginuser != null) {
			userid = loginuser.getUserid();
		}
		try {
			User user = this.userDao.getById(uid);
			List<User_photo> list = this.user_photoDao
					.getListByUserid(uid, true, true, userid, true, true,
							simplePage.getBegin(), size);
			for (User_photo o : list) {
				if (o.getPhoto() != null) {
					o.getPhoto().setUser(user);
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/userphotos.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 用户喜欢图片的操作
	 * 
	 * @param req
	 * @param resp
	 * @return 6:图片不存在<br/>
	 */
	public String prvlike(HkRequest req, HkResponse resp) {
		try {
			User user = this.getUser(req);
			long photoid = req.getLong("photoid");
			Photo photo = this.photoDao.getById(photoid);
			if (photo == null) {
				APIUtil.writeErr(resp, Err.PHOTO_NOTEXIST);
				return null;
			}
			this.photoService.createPhotoUserLike(user, photo);
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 取消用户喜欢图片的操作
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String prvdellike(HkRequest req, HkResponse resp) {
		try {
			User user = this.getUser(req);
			long photoid = req.getLong("photoid");
			Photo photo = this.photoDao.getById(photoid);
			User _user = this.userDao.getById(user.getUserid());
			if (photo != null) {
				this.photoService.deletePhotoUserLike(_user, photo);
			}
			APIUtil.writeSuccess(resp);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 更新热门图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String makehot(HkRequest req, HkResponse resp) {
		Calendar begincal = Calendar.getInstance();
		begincal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Calendar endcal = Calendar.getInstance();
		endcal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		this.photoService.createHotPhotos(begincal.getTime(), endcal.getTime());
		resp.sendHtml("make hot ok");
		return null;
	}

	/**
	 * 最新热门图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String hot(HkRequest req, HkResponse resp) {
		try {
			List<HotPhoto> list = this.hotPhotoDao.getList(0, 40);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/hotphotos.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 喜欢某图片的人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String likeuser(HkRequest req, HkResponse resp) {
		try {
			int page = req.getInt("page");
			int size = req.getInt("size");
			long photoid = req.getLong("photoid");
			User user = this.getUser(req);
			long userid = 0;
			if (user != null) {
				userid = user.getUserid();
			}
			SimplePage simplePage = new SimplePage(size, page);
			List<PhotoLikeUser> list = this.photoLikeUserDao.getListByPhotoid(
					photoid, true, userid, simplePage.getBegin(),
					simplePage.getSize());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/photolikeuser.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}

	/**
	 * 分享图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String prvsharephoto(HkRequest req, HkResponse resp) {
		long photoid = req.getLong("photoid");
		Photo photo = this.photoDao.getById(photoid);
		if (photo == null) {
			APIUtil.writeErr(resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		User user = this.getUser(req);
		User photoUser = this.userDao.getById(photo.getUserid());
		String content = null;
		if (user.getUserid() == photo.getUserid()) {
			content = ResourceConfig.getTextFromResource("i18n",
					"sharemyphototemplate", photoid);
		}
		else {
			content = ResourceConfig.getTextFromResource("i18n",
					"sharephototemplate", photoUser.getNick(), photoid);
		}
		Api_user_sina api_user_sina = this.getApiUserSina(req);
		try {
			this.photoService.sharePhoto(photo, api_user_sina, content);
			APIUtil.writeSuccess(resp);
		}
		catch (WeiboException e) {
			APIUtil.writeErr(resp, Err.API_SINA_ERR);
		}
		return null;
	}
}