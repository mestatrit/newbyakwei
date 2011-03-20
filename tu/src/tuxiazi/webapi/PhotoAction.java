package tuxiazi.webapi;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.HotPhoto;
import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/photo")
public class PhotoAction extends BaseApiAction {

	@Autowired
	private PhotoService photoService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private UserService userService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long favUserid = 0;
		User user = this.getUser(req);
		if (user != null) {
			favUserid = user.getUserid();
		}
		long photoid = req.getLong("photoid");
		Photo photo = this.photoService.getPhoto(photoid, favUserid, true);
		if (photo == null) {
			APIUtil.writeErr(req, resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("photo", photo);
		APIUtil.writeData(resp, map, "vm/photo.vm");
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
		User user = this.getUser(req);
		long photoid = req.getLong("photoid");
		Photo photo = this.photoService.getPhoto(photoid);
		if (photo == null) {
			APIUtil.writeErr(req, resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		if (photo.getUserid() != user.getUserid()) {
			APIUtil.writeErr(req, resp, Err.OP_NOPOWER);
			return null;
		}
		this.photoService.deletePhoto(photo);
		APIUtil.writeSuccess(req, resp);
		return null;
	}

	/**
	 * 上传图片
	 * 
	 * @param req
	 * @param resp
	 * @return 2010-11-8
	 */
	public String prvupload(HkRequest req, HkResponse resp) {
		File file = req.getFile("f");
		if (file == null) {
			APIUtil.writeErr(req, resp, Err.PHOTO_FILE_NOTEXIST);
			return null;
		}
		User user = this.getUser(req);
		UploadPhoto uploadPhoto = new UploadPhoto();
		uploadPhoto.setUserid(user.getUserid());
		uploadPhoto.setFile(file);
		uploadPhoto.setName(DataUtil.limitTextRow(req.getHtmlRow("name"), 140));
		uploadPhoto.setCreate_time(new Date());
		uploadPhoto.setPrivacy_flg(req.getByte("privacy_flg"));
		List<UploadPhoto> list = new ArrayList<UploadPhoto>();
		list.add(uploadPhoto);
		boolean withweibo = false;
		if (req.getInt("withweibo") == 1) {
			withweibo = true;
		}
		this.photoService.createPhoto(user.getUserid(), list, req.getInt("x"),
				req.getInt("y"), req.getInt("width"), req.getInt("height"),
				withweibo, this.getApiUserSina(req));
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		APIUtil.write(resp, "vm/sinaerr.vm", context);
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
			List<Lasted_photo> lastedlist = this.photoService
					.getLasted_photoList(true, false, 0, 0, 1);
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.SUCCESS);
			context.put("lastedlist", lastedlist);
			APIUtil.write(resp, "vm/lasted.vm", context);
			return null;
		}
		List<Friend_photo_feed> list = this.feedService
				.getFriend_photo_feedListByUserid(user.getUserid(), true,
						false, 0, 0, 1);
		if (list.size() > 0) {
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.SUCCESS);
			context.put("list", list);
			APIUtil.write(resp, "vm/friend_lasted.vm", context);
			return null;
		}
		List<Lasted_photo> lastedlist = this.photoService.getLasted_photoList(
				true, false, user.getUserid(), 0, 1);
		VelocityContext context = new VelocityContext();
		context.put("errcode", Err.SUCCESS);
		context.put("lastedlist", lastedlist);
		APIUtil.write(resp, "vm/lasted.vm", context);
		return null;
	}

	/**
	 * 获取好友的最新公开图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-11-8
	 */
	public String prvfriendphotos(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 1);
		try {
			SimplePage simplePage = new SimplePage(size, page);
			List<Friend_photo_feed> list = this.feedService
					.getFriend_photo_feedListByUserid(user.getUserid(), true,
							true, user.getUserid(), simplePage.getBegin(),
							simplePage.getSize());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			APIUtil.writeData(resp, map, "vm/friendphotos.vm");
		}
		catch (Exception e) {
			APIUtil.writeErr(req, resp, Err.API_SYS_ERR);
		}
		return null;
	}

	/**
	 * 用户图片,如果当前访问用户与登录用户是同一个用户，则获取所有图片
	 * 
	 * @param req
	 * @param resp
	 * @return 2010-11-30
	 */
	public String list(HkRequest req, HkResponse resp) {
		long uid = req.getLong("uid");
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 1);
		SimplePage simplePage = new SimplePage(size, page);
		boolean allPhoto = false;
		long userid = 0;
		User loginuser = this.getUser(req);
		if (loginuser != null && loginuser.getUserid() == uid) {
			allPhoto = true;
		}
		if (loginuser != null) {
			userid = loginuser.getUserid();
		}
		try {
			User user = this.userService.getUser(uid);
			List<User_photo> list = this.photoService
					.getUser_photoListByUserid(uid, allPhoto, true, userid,
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
			APIUtil.writeErr(req, resp, Err.API_SYS_ERR);
		}
		return null;
	}

	/**
	 * 用户喜欢图片的操作
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String prvlike(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		long photoid = req.getLong("photoid");
		Photo photo = this.photoService.getPhoto(photoid);
		if (photo == null) {
			APIUtil.writeErr(req, resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		this.photoService.createPhotoUserLike(user, photo);
		APIUtil.writeSuccess(req, resp);
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
		User user = this.getUser(req);
		long photoid = req.getLong("photoid");
		this.photoService.deletePhotoUserLike(user.getUserid(), photoid);
		APIUtil.writeSuccess(req, resp);
		return null;
	}

	/**
	 *更新热门图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String makehot(HkRequest req, HkResponse resp) {
		this.photoService.createHotPhotos();
		resp.sendHtml("make hot ok");
		return null;
	}

	/**
	 *最新热门图片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String hot(HkRequest req, HkResponse resp) {
		List<HotPhoto> list = this.photoService.getHotPhotoList(0, 40);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/hotphotos.vm");
		return null;
	}

	/**
	 *喜欢某图片的人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String likeuser(HkRequest req, HkResponse resp) {
		int page = req.getInt("page");
		int size = req.getInt("size");
		long photoid = req.getLong("photoid");
		User user = this.getUser(req);
		long userid = 0;
		if (user != null) {
			userid = user.getUserid();
		}
		SimplePage simplePage = new SimplePage(size, page);
		List<PhotoLikeUser> list = this.photoService
				.getPhotoLikeUserListByPhotoid(photoid, true, userid,
						simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/photolikeuser.vm");
		return null;
	}
}