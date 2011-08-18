package tuxiazi.svr.impl2;

import halo.util.DataUtil;
import halo.util.NumberUtil;
import halo.util.ResourceConfig;
import halo.util.image.ImageException;
import halo.util.image.ImageShaper;
import halo.util.image.ImageShaperFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Friend_photo_feed;
import tuxiazi.bean.HotPhoto;
import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.LikeUser;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.Photoid;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
import tuxiazi.bean.helper.noticedata.PhotoLikeNoticeCreater;
import tuxiazi.dao.HotPhotoDao;
import tuxiazi.dao.Lasted_photoDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.PhotoLikeUserDao;
import tuxiazi.dao.PhotoUserLikeDao;
import tuxiazi.dao.PhotoidDao;
import tuxiazi.dao.User_photoDao;
import tuxiazi.svr.iface.FeedService;
import tuxiazi.svr.iface.FriendService;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.svr.iface.UploadPhotoResult;
import tuxiazi.svr.iface.UserService;
import tuxiazi.svr.impl.jms.HkMsgProducer;
import tuxiazi.svr.impl.jms.JmsMsg;
import tuxiazi.svr.impl.jms.JsonKey;
import tuxiazi.util.FileCnf;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

public class PhotoServiceImpl implements PhotoService {

	private FileCnf fileCnf;

	private int lasted_photo_max_count;

	private FriendService friendService;

	private PhotoidDao photoidDao;

	private PhotoDao photoDao;

	private NoticeService noticeService;

	private FeedService feedService;

	private HkMsgProducer hkMsgProducer;

	private PhotoCmtService photoCmtService;

	private UserService userService;

	private User_photoDao user_photoDao;

	private Lasted_photoDao lasted_photoDao;

	private HotPhotoDao hotPhotoDao;

	private PhotoUserLikeDao photoUserLikeDao;

	private PhotoLikeUserDao photoLikeUserDao;

	public void setPhotoLikeUserDao(PhotoLikeUserDao photoLikeUserDao) {
		this.photoLikeUserDao = photoLikeUserDao;
	}

	public void setPhotoUserLikeDao(PhotoUserLikeDao photoUserLikeDao) {
		this.photoUserLikeDao = photoUserLikeDao;
	}

	public void setHotPhotoDao(HotPhotoDao hotPhotoDao) {
		this.hotPhotoDao = hotPhotoDao;
	}

	public void setLasted_photoDao(Lasted_photoDao lastedPhotoDao) {
		lasted_photoDao = lastedPhotoDao;
	}

	public void setUser_photoDao(User_photoDao userPhotoDao) {
		user_photoDao = userPhotoDao;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public void setFeedService(FeedService feedService) {
		this.feedService = feedService;
	}

	public void setHkMsgProducer(HkMsgProducer hkMsgProducer) {
		this.hkMsgProducer = hkMsgProducer;
	}

	public void setPhotoCmtService(PhotoCmtService photoCmtService) {
		this.photoCmtService = photoCmtService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPhotoDao(PhotoDao photoDao) {
		this.photoDao = photoDao;
	}

	public void setPhotoidDao(PhotoidDao photoidDao) {
		this.photoidDao = photoidDao;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	private final Log log = LogFactory.getLog(PhotoServiceImpl.class);

	public void setLasted_photo_max_count(int lastedPhotoMaxCount) {
		lasted_photo_max_count = lastedPhotoMaxCount;
	}

	public void setFileCnf(FileCnf fileCnf) {
		this.fileCnf = fileCnf;
	}

	@Override
	public UploadPhotoResult createPhoto(long userid,
			List<UploadPhoto> uploadPhotos, int x, int y, int width, int height) {
		UploadPhotoResult result = new UploadPhotoResult();
		// 处理图片创建
		this.proccessUpload(userid, result, uploadPhotos, x, y, width, height);
		// 添加图片到最新图片表中
		this.proccessLastPhoto(result);
		List<Friend_photo_feed> friendPhotoFeeds = new ArrayList<Friend_photo_feed>();
		if (result.getPhotos() != null) {
			for (Photo o : result.getPhotos()) {
				Friend_photo_feed feed = new Friend_photo_feed();
				feed.setUserid(userid);
				feed.setPhotoid(o.getPhotoid());
				feed.setCreate_time(o.getCreate_time());
				feed.setPhoto_userid(o.getUserid());
				friendPhotoFeeds.add(feed);
			}
			this.feedService.createFriend_photo_feed(friendPhotoFeeds);
			// 图片关注动态
			this.proccessFriend_photo_feed(result);
		}
		return result;
	}

	@Override
	public UploadPhotoResult createPhoto(long userid,
			List<UploadPhoto> uploadPhotos, int x, int y, int width,
			int height, boolean withweibo, Api_user_sina apiUserSina) {
		UploadPhotoResult uploadPhotoResult = this.createPhoto(userid,
				uploadPhotos, x, y, width, height);
		if (withweibo) {
			for (Photo o : uploadPhotoResult.getPhotos()) {
				String content = "";
				if (DataUtil.isNotEmpty(o.getName())) {
					content = o.getName();
				}
				content = content + " "
						+ ResourceConfig.getText("photourl", o.getPhotoid());
				String filepath = this.fileCnf.getFilePath(o.getPath());
				File imgFile = FileCnf.getFile(filepath + Photo.p4_houzhui);
				try {
					SinaUtil.updateStatus(apiUserSina.getAccess_token(),
							apiUserSina.getToken_secret(), content, imgFile);
				} catch (WeiboException e) {
					log.error("error while share to weibo");
					log.error(e.toString());
				}
			}
		}
		return uploadPhotoResult;
	}

	/**
	 * @param userid
	 * @param result
	 * @param query
	 * @param uploadPhotos
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void proccessUpload(long userid, UploadPhotoResult result,
			List<UploadPhoto> uploadPhotos, int x, int y, int width, int height) {
		ImageShaper imageShaper = ImageShaperFactory
				.getImageShaper(ImageShaperFactory.SHAPER_JMAGICK);
		
		
		
		JMagickUtil util = null;
		Photo photo = null;
		User user = this.userService.getUser(userid);
		for (UploadPhoto o : uploadPhotos) {
			String name = FileCnf.createFileName();
			String dbPath = fileCnf.getFileSaveToDbPath(name);
			String filePath = fileCnf.getFilePath(dbPath);
			// File newf = null;
			
			try {
				util = new JMagickUtil(o.getFile(), fileCnf.getFileMaxSize());
				util.setQuality(90);
				util.makeImage(filePath, Photo.p1_houzhui,
						JMagickUtil.IMG_SQUARE, 60);
				util.makeImage(filePath, Photo.p2_houzhui,
						JMagickUtil.IMG_OBLONG, 120);
				util.setFullQuality(true);
				util.makeImage(filePath, Photo.p4_houzhui,
						JMagickUtil.IMG_OBLONG, 480);
				util.makeImage(filePath, Photo.p6_houzhui,
						JMagickUtil.IMG_OBLONG, 640);
				photo = new Photo();
				photo.setPrivacy_flg(o.getPrivacy_flg());
				photo.setPhotoid(NumberUtil.getLong(this.photoidDao.save(null,
						new Photoid())));
				photo.setUserid(o.getUserid());
				photo.setName(o.getName());
				photo.setCreate_time(o.getCreate_time());
				photo.setPath(dbPath);
				this.photoDao.save(null, photo);
				User_photo userPhoto = new User_photo();
				userPhoto.setUserid(photo.getUserid());
				userPhoto.setPhotoid(photo.getPhotoid());
				userPhoto.setPrivacy_flg(photo.getPrivacy_flg());
				this.user_photoDao.save(null, userPhoto);
				user.setPic_num(user.getPic_num() + 1);
				result.addPhoto(photo);
			} catch (ImageException e) {
				e.printStackTrace();
				result.addErr_num(1);
			} catch (NotPermitImageFormatException e) {
				result.addErr_fmt_num(1);
			} catch (OutOfSizeException e) {
				result.addErr_too_big_num(1);
			} finally {
				// if (newf != null) {
				// if (newf.exists()) {
				// newf.delete();
				// }
				// }
			}
		}
		this.userService.update(user);
		result.proccess();
	}

	private void proccessLastPhoto(UploadPhotoResult result) {
		List<Photo> list = result.getPhotos();
		if (list == null || list.isEmpty()) {
			return;
		}
		Lasted_photo lastedPhoto = null;
		for (Photo o : list) {
			if (o.isPrivacy()) {
				continue;
			}
			lastedPhoto = new Lasted_photo();
			lastedPhoto.setPhotoid(o.getPhotoid());
			this.lasted_photoDao.save(null, lastedPhoto);
		}
		int count = this.lasted_photoDao.count(null, null, null);
		if (count > this.lasted_photo_max_count) {
			List<Lasted_photo> tmplist = this.lasted_photoDao.getList(null,
					null, null, "photoid asc", this.lasted_photo_max_count - 1,
					count - this.lasted_photo_max_count);
			for (Lasted_photo o : tmplist) {
				this.lasted_photoDao.deleteById(o.getPhotoid());
			}
		}
	}

	private void proccessFriend_photo_feed(UploadPhotoResult result) {
		if (result.getPhotos() == null || result.getPhotos().isEmpty()) {
			return;
		}
		long userid = result.getPhotos().get(0).getUserid();
		JmsMsg jmsMsg = new JmsMsg();
		jmsMsg.setHead(JmsMsg.HEAD_PHOTO_CREATEPHOTO);
		jmsMsg.addData(JsonKey.userid, String.valueOf(userid));
		StringBuilder sb = new StringBuilder();
		for (Photo o : result.getPhotos()) {
			if (o.isPrivacy()) {
				continue;
			}
			sb.append(o.getPhotoid()).append(":").append(o.getUserid())
					.append(",");
		}
		jmsMsg.addData(JsonKey.photos, sb.toString());
		jmsMsg.buildBody();
		this.hkMsgProducer.send(jmsMsg.toMessage());
	}

	@Override
	public void deletePhoto(Photo photo) {
		this.photoCmtService.deletePhotoCmtByPhotoid(photo.getPhotoid());
		this.deletePhotoLikeUserByPhotoid(photo.getPhotoid());
		this.deletePhotoUserLikeByPhotoid(photo.getPhotoid());
		String filePath = this.fileCnf.getFilePath(photo.getPath());
		FileCnf.delPhotoFile(filePath);
		this.feedService.deleteFriend_photo_feedByPhotoid(photo.getPhotoid());
		this.user_photoDao.delete(null, "userid=? and photoid=?", new Object[] {
				photo.getUserid(), photo.getPhotoid() });
		this.photoDao.deleteById(photo.getPhotoid());
		this.hotPhotoDao.delete(null, "photoid=?",
				new Object[] { photo.getPhotoid() });
		int count = this.user_photoDao.count(null, "userid=?",
				new Object[] { photo.getUserid() });
		User user = this.userService.getUser(photo.getUserid());
		user.setPic_num(count);
		this.userService.update(user);
	}

	private void deletePhotoUserLikeByPhotoid(long photoid) {
		this.photoUserLikeDao.delete(null, "photoid=?",
				new Object[] { photoid });
	}

	private void deletePhotoLikeUserByPhotoid(long photoid) {
		this.photoLikeUserDao.delete(null, "photoid=?",
				new Object[] { photoid });
	}

	@Override
	public Photo getPhoto(long photoid) {
		return this.photoDao.getById(null, photoid);
	}

	@Override
	public Photo getPhoto(long photoid, long favUserid, boolean buildUser) {
		Photo photo = this.getPhoto(photoid);
		if (photo == null) {
			return null;
		}
		if (buildUser) {
			photo.setUser(this.userService.getUser(photo.getUserid()));
		}
		if (favUserid > 0) {
			PhotoUserLike photoUserLike = this
					.getPhotoUserLikeByUseridAndPhotoid(favUserid, photoid);
			if (photoUserLike != null) {
				photo.setOpliked(true);
			}
		}
		return photo;
	}

	@Override
	public Map<Long, Photo> getPhotoMapInId(List<Long> idList) {
		if (idList.isEmpty()) {
			return new HashMap<Long, Photo>();
		}
		Map<Long, Photo> map = new HashMap<Long, Photo>();
		List<Photo> list = this.photoDao
				.getListInField(null, "photoid", idList);
		for (Photo o : list) {
			map.put(o.getPhotoid(), o);
		}
		return map;
	}

	@Override
	public List<User_photo> getUser_photoListByUserid(long userid,
			boolean buildPhoto, long favUserid, int begin, int size) {
		List<User_photo> list = this.user_photoDao.getList(null, "userid=?",
				new Object[] { userid }, "photoid desc", begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (User_photo o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.getPhotoMapInId(idList);
			for (User_photo o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
		}
		if (favUserid > 0 && favUserid != userid) {
			List<PhotoUserLike> photoUserLikes = this
					.getPhotoUserLikeListByUserid(favUserid, false, begin, -1);
			Set<Long> photoidset = new HashSet<Long>();
			for (PhotoUserLike o : photoUserLikes) {
				photoidset.add(o.getPhotoid());
			}
			for (User_photo o : list) {
				if (photoidset.contains(o.getPhotoid())) {
					if (o.getPhoto() != null) {
						o.getPhoto().setOpliked(true);
					}
				}
			}
		}
		return list;
	}

	@Override
	public void updatePhoto(Photo photo) {
		this.photoDao.update(null, photo);
	}

	@Override
	public List<Lasted_photo> getLasted_photoList(boolean buildPhoto,
			boolean buildPhotoUser, long favUserid, int begin, int size) {
		List<Lasted_photo> list = this.lasted_photoDao.getList(null, null,
				null, "photoid desc", begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (Lasted_photo o : list) {
				idList.add(o.getPhotoid());
			}
			Map<Long, Photo> map = this.getPhotoMapInId(idList);
			for (Lasted_photo o : list) {
				o.setPhoto(map.get(o.getPhotoid()));
			}
			if (buildPhotoUser) {
				idList = new ArrayList<Long>();
				for (Entry<Long, Photo> e : map.entrySet()) {
					idList.add(e.getValue().getUserid());
				}
				Map<Long, User> usermap = this.userService
						.getUserMapInId(idList);
				for (Lasted_photo o : list) {
					if (o.getPhoto() != null) {
						o.getPhoto().setUser(
								usermap.get(o.getPhoto().getUserid()));
					}
				}
			}
		}
		if (favUserid > 0) {
			List<PhotoUserLike> photoUserLikes = this
					.getPhotoUserLikeListByUserid(favUserid, false, begin, -1);
			Set<Long> photoidset = new HashSet<Long>();
			for (PhotoUserLike o : photoUserLikes) {
				photoidset.add(o.getPhotoid());
			}
			for (Lasted_photo o : list) {
				if (photoidset.contains(o.getPhotoid())) {
					if (o.getPhoto() != null) {
						o.getPhoto().setOpliked(true);
					}
				}
			}
		}
		return list;
	}

	@Override
	public void addPhotoCmt_num(long photoid, int add) {
		this.photoDao.updateBySQL(null, "cmt_num=cmt_num+?", "photoid=?",
				new Object[] { add, photoid });
	}

	@Override
	public void createPhotoUserLike(User user, Photo photo) {
		if (this.photoLikeUserDao.getObject(null, "userid=? and photoid=?",
				new Object[] { user.getUserid(), photo.getPhotoid() }) != null) {
			return;
		}
		PhotoLikeUser photoLikeUser = new PhotoLikeUser();
		photoLikeUser.setUserid(user.getUserid());
		photoLikeUser.setPhotoid(photo.getPhotoid());
		this.photoLikeUserDao.save(null, photoLikeUser);
		if (this.photoUserLikeDao.getObject(null, "userid=? and photoid=?",
				new Object[] { user.getUserid(), photo.getPhotoid() }) != null) {
			return;
		}
		PhotoUserLike photoUserLike = new PhotoUserLike();
		photoUserLike.setUserid(user.getUserid());
		photoUserLike.setPhotoid(photo.getPhotoid());
		this.photoUserLikeDao.save(null, photoUserLike);
		photo.addLikeUser(user.getUserid(), user.getNick());
		photo.setLike_num(this.photoLikeUserDao.count(null, "photoid=?",
				new Object[] { photo.getPhotoid() }));
		this.updatePhoto(photo);
		// 发送通知给图片所有者
		if (user.getUserid() != photo.getUserid()) {
			PhotoLikeNoticeCreater o = new PhotoLikeNoticeCreater();
			o.setUserid(photo.getUserid());
			o.setSenderid(user.getUserid());
			o.setSender_nick(user.getNick());
			o.setSender_head(user.getHead_path());
			o.setPhotoid(photo.getPhotoid());
			this.noticeService.createNotice(o.buildNotice());
		}
	}

	@Override
	public void deletePhotoUserLike(long userid, long photoid) {
		Photo photo = this.getPhoto(photoid);
		if (photo == null) {
			return;
		}
		this.photoUserLikeDao.delete(null, "userid=? and photoid=?",
				new Object[] { userid, photoid });
		this.photoLikeUserDao.delete(null, "userid=? and photoid=?",
				new Object[] { userid, photoid });
		List<PhotoUserLike> list = this.photoUserLikeDao.getList(null,
				"photoid=?", new Object[] { photoid }, "oid desc", 0, 10);
		List<Long> idList = new ArrayList<Long>();
		for (PhotoUserLike o : list) {
			idList.add(o.getUserid());
		}
		List<User> userlist = this.userService.getUserListInId(idList);
		List<LikeUser> likeUsers = new ArrayList<LikeUser>();
		for (User o : userlist) {
			likeUsers.add(new LikeUser(o.getUserid(), o.getNick()));
		}
		photo.initLikeUserlist(likeUsers);
		photo.setLike_num(this.photoLikeUserDao.count(null, "photoid=?",
				new Object[] { photo.getPhotoid() }));
		this.updatePhoto(photo);
	}

	@Override
	public void createHotPhotos() {
		List<Photo> list = this.photoDao.getList(null, null, null,
				"like_num desc,photoid desc", 0, 100);
		this.hotPhotoDao.delete(null, null, null);
		for (Photo o : list) {
			HotPhoto hotPhoto = new HotPhoto();
			hotPhoto.setPhotoid(o.getPhotoid());
			hotPhoto.setPath(o.getPath());
			long oid = NumberUtil
					.getLong(this.hotPhotoDao.save(null, hotPhoto));
			hotPhoto.setOid(oid);
		}
	}

	@Override
	public List<HotPhoto> getHotPhotoList(int begin, int size) {
		return this.hotPhotoDao.getList(null, null, null, "oid asc", begin,
				size);
	}

	@Override
	public List<PhotoUserLike> getPhotoUserLikeListByUserid(long userid,
			boolean buildPhoto, int begin, int size) {
		return this.photoUserLikeDao.getList(null, "userid=?",
				new Object[] { userid }, "oid desc", begin, size);
	}

	@Override
	public PhotoUserLike getPhotoUserLikeByUseridAndPhotoid(long userid,
			long photoid) {
		return this.photoUserLikeDao.getObject(null, "userid=? and photoid=?",
				new Object[] { userid, photoid });
	}

	@Override
	public List<PhotoLikeUser> getPhotoLikeUserListByPhotoid(long photoid,
			boolean buildUser, long refuserid, int begin, int size) {
		List<PhotoLikeUser> list = this.photoLikeUserDao.getList(null,
				"photoid=?", new Object[] { photoid }, "oid desc", begin, size);
		List<Long> idList = new ArrayList<Long>();
		if (buildUser) {
			for (PhotoLikeUser o : list) {
				idList.add(o.getUserid());
			}
			Map<Long, User> map = this.userService.getUserMapInId(idList);
			for (PhotoLikeUser o : list) {
				o.setUser(map.get(o.getUserid()));
			}
		}
		if (refuserid > 0) {
			Set<Long> friendidset = this.friendService
					.getFriendUseridSetByUserid(refuserid);
			for (PhotoLikeUser o : list) {
				if (friendidset.contains(o.getUserid())) {
					o.setFriendRef(true);
				}
			}
		}
		return list;
	}
}