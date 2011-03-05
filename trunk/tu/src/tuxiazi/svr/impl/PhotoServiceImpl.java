package tuxiazi.svr.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import tuxiazi.bean.benum.PhotoPrivacyEnum;
import tuxiazi.bean.helper.noticedata.PhotoLikeNoticeCreater;
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

import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;

public class PhotoServiceImpl implements PhotoService {

	private FileCnf fileCnf;

	private int lasted_photo_max_count;

	@Autowired
	private FriendService friendService;

	private final Log log = LogFactory.getLog(PhotoServiceImpl.class);

	public void setLasted_photo_max_count(int lastedPhotoMaxCount) {
		lasted_photo_max_count = lastedPhotoMaxCount;
	}

	public void setFileCnf(FileCnf fileCnf) {
		this.fileCnf = fileCnf;
	}

	@Autowired
	private QueryManager manager;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private FeedService feedService;

	@Autowired
	private HkMsgProducer hkMsgProducer;

	@Autowired
	private PhotoCmtService photoCmtService;

	@Autowired
	private UserService userService;

	@Override
	public UploadPhotoResult createPhoto(long userid,
			List<UploadPhoto> uploadPhotos, int x, int y, int width, int height) {
		UploadPhotoResult result = new UploadPhotoResult();
		Query query = this.manager.createQuery();
		// 处理图片创建
		this.proccessUpload(userid, result, query, uploadPhotos, x, y, width,
				height);
		// 添加图片到最新图片表中
		this.proccessLastPhoto(result, query);
		List<Friend_photo_feed> friendPhotoFeeds = new ArrayList<Friend_photo_feed>();
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
				}
				catch (WeiboException e) {
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
			Query query, List<UploadPhoto> uploadPhotos, int x, int y,
			int width, int height) {
		JMagickUtil util = null;
		Photo photo = null;
		User user = this.userService.getUser(userid);
		// int x1 = x;
		// int y1 = y;
		// int x2 = x + width;
		// int y2 = y + height;
		for (UploadPhoto o : uploadPhotos) {
			String name = FileCnf.createFileName();
			String dbPath = fileCnf.getFileSaveToDbPath(name);
			String filePath = fileCnf.getFilePath(dbPath);
			// File newf = null;
			try {
				// try {
				// DataUtil.copyFile(o.getFile(), filePath, "origion.jpg");
				// }
				// catch (IOException e) {
				// e.printStackTrace();
				// }
				util = new JMagickUtil(o.getFile(), fileCnf.getFileMaxSize());
				// if (x1 + x2 + y1 + y2 > 0) {
				// String tmpname = userid + "" + System.currentTimeMillis()
				// + System.nanoTime() + ".jpg";
				// util.cutImage(this.fileCnf.getTmpPhotoPath(), tmpname, x1,
				// y1, x2, y2);
				// newf = new File(this.fileCnf.getTmpPhotoPath() + tmpname);
				// util = new JMagickUtil(newf, fileCnf.getFileMaxSize());
				// }
				util.setQuality(80);
				util.makeImage(filePath, Photo.p1_houzhui,
						JMagickUtil.IMG_SQUARE, 60);
				util.makeImage(filePath, Photo.p2_houzhui,
						JMagickUtil.IMG_OBLONG, 120);
				util.setQuality(90);
				util.makeImage(filePath, Photo.p4_houzhui,
						JMagickUtil.IMG_OBLONG, 480);
				util.makeImage(filePath, Photo.p6_houzhui,
						JMagickUtil.IMG_OBLONG, 640);
				photo = new Photo();
				photo.setPrivacy_flg(o.getPrivacy_flg());
				photo.setPhotoid(query.insertObject(new Photoid()).longValue());
				photo.setUserid(o.getUserid());
				photo.setName(o.getName());
				photo.setCreate_time(o.getCreate_time());
				photo.setPath(dbPath);
				query.insertObject(photo);
				User_photo userPhoto = new User_photo();
				userPhoto.setUserid(photo.getUserid());
				userPhoto.setPhotoid(photo.getPhotoid());
				userPhoto.setPrivacy_flg(photo.getPrivacy_flg());
				query.insertObject(userPhoto);
				user.setPic_num(user.getPic_num() + 1);
				result.addPhoto(photo);
			}
			catch (ImageException e) {
				e.printStackTrace();
				result.addErr_num(1);
			}
			catch (NotPermitImageFormatException e) {
				result.addErr_fmt_num(1);
			}
			catch (OutOfSizeException e) {
				result.addErr_too_big_num(1);
			}
			finally {
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

	private void proccessLastPhoto(UploadPhotoResult result, Query query) {
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
			query.insertObject(lastedPhoto);
		}
		int count = query.count(Lasted_photo.class);
		if (count > this.lasted_photo_max_count) {
			List<Lasted_photo> tmplist = query.listEx(Lasted_photo.class,
					"photoid asc", this.lasted_photo_max_count - 1, count
							- this.lasted_photo_max_count);
			for (Lasted_photo o : tmplist) {
				query.deleteObject(o);
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
			sb.append(o.getPhotoid()).append(":").append(o.getUserid()).append(
					",");
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
		Query query = this.manager.createQuery();
		this.feedService.deleteFriend_photo_feedByPhotoid(photo.getPhotoid());
		query.delete(User_photo.class, "userid=? and photoid=?", new Object[] {
				photo.getUserid(), photo.getPhotoid() });
		query.deleteObject(photo);
		query.delete(HotPhoto.class, "photoid=?", new Object[] { photo
				.getPhotoid() });
		int count = query.count(User_photo.class, "userid=?",
				new Object[] { photo.getUserid() });
		User user = this.userService.getUser(photo.getUserid());
		user.setPic_num(count);
		this.userService.update(user);
	}

	private void deletePhotoUserLikeByPhotoid(long photoid) {
		Query query = this.manager.createQuery();
		query
				.delete(PhotoUserLike.class, "photoid=?",
						new Object[] { photoid });
	}

	private void deletePhotoLikeUserByPhotoid(long photoid) {
		Query query = this.manager.createQuery();
		query
				.delete(PhotoLikeUser.class, "photoid=?",
						new Object[] { photoid });
	}

	@Override
	public Photo getPhoto(long photoid) {
		return this.manager.createQuery().getObjectById(Photo.class, photoid);
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
		List<Photo> list = this.manager.createQuery().listInField(Photo.class,
				null, null, "photoid", idList, null);
		for (Photo o : list) {
			map.put(o.getPhotoid(), o);
		}
		return map;
	}

	@Override
	public List<User_photo> getUser_photoListByUserid(long userid,
			boolean allPhoto, boolean buildPhoto, long favUserid, int begin,
			int size) {
		List<User_photo> list = null;
		Query query = this.manager.createQuery();
		if (allPhoto) {
			list = query.listEx(User_photo.class, "userid=?",
					new Object[] { userid }, "photoid desc", begin, size);
		}
		else {
			list = query
					.listEx(User_photo.class, "userid=? and privacy_flg=?",
							new Object[] { userid,
									PhotoPrivacyEnum.PUBLIC.getValue() },
							"photoid desc", begin, size);
		}
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
		this.manager.createQuery().updateObject(photo);
	}

	@Override
	public List<Lasted_photo> getLasted_photoList(boolean buildPhoto,
			boolean buildPhotoUser, long favUserid, int begin, int size) {
		Query query = this.manager.createQuery();
		List<Lasted_photo> list = query.listEx(Lasted_photo.class,
				"photoid desc", begin, size);
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
		Query query = this.manager.createQuery();
		query.addField("cmt_num", "add", add);
		query.updateById(Photo.class, photoid);
	}

	@Override
	public void createPhotoUserLike(User user, Photo photo) {
		Query query = this.manager.createQuery();
		if (query.getObjectEx(PhotoLikeUser.class, "userid=? and photoid=?",
				new Object[] { user.getUserid(), photo.getPhotoid() }) != null) {
			return;
		}
		PhotoLikeUser photoLikeUser = new PhotoLikeUser();
		photoLikeUser.setUserid(user.getUserid());
		photoLikeUser.setPhotoid(photo.getPhotoid());
		query.insertObject(photoLikeUser);
		if (query.getObjectEx(PhotoUserLike.class, "userid=? and photoid=?",
				new Object[] { user.getUserid(), photo.getPhotoid() }) != null) {
			return;
		}
		PhotoUserLike photoUserLike = new PhotoUserLike();
		photoUserLike.setUserid(user.getUserid());
		photoUserLike.setPhotoid(photo.getPhotoid());
		query.insertObject(photoUserLike);
		photo.addLikeUser(user.getUserid(), user.getNick());
		photo.setLike_num(query.count(PhotoLikeUser.class, "photoid=?",
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
		Query query = this.manager.createQuery();
		Photo photo = this.getPhoto(photoid);
		if (photo == null) {
			return;
		}
		query.delete(PhotoUserLike.class, "userid=? and photoid=?",
				new Object[] { userid, photoid });
		query.delete(PhotoLikeUser.class, "userid=? and photoid=?",
				new Object[] { userid, photoid });
		List<PhotoUserLike> list = query.listEx(PhotoUserLike.class,
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
		photo.setLike_num(query.count(PhotoLikeUser.class, "photoid=?",
				new Object[] { photo.getPhotoid() }));
		this.updatePhoto(photo);
	}

	@Override
	public void createHotPhotos() {
		Query query = this.manager.createQuery();
		List<Photo> list = query.listEx(Photo.class,
				"like_num desc,photoid desc", 0, 100);
		query.delete(HotPhoto.class);
		for (Photo o : list) {
			HotPhoto hotPhoto = new HotPhoto();
			hotPhoto.setPhotoid(o.getPhotoid());
			hotPhoto.setPath(o.getPath());
			long oid = query.insertObject(hotPhoto).longValue();
			hotPhoto.setOid(oid);
		}
	}

	@Override
	public List<HotPhoto> getHotPhotoList(int begin, int size) {
		return this.manager.createQuery().listEx(HotPhoto.class, "oid asc",
				begin, size);
	}

	@Override
	public List<PhotoUserLike> getPhotoUserLikeListByUserid(long userid,
			boolean buildPhoto, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(PhotoUserLike.class, "userid=?",
				new Object[] { userid }, "oid desc", begin, size);
	}

	@Override
	public PhotoUserLike getPhotoUserLikeByUseridAndPhotoid(long userid,
			long photoid) {
		return this.manager.createQuery().getObjectEx(PhotoUserLike.class,
				"userid=? and photoid=?", new Object[] { userid, photoid });
	}

	@Override
	public List<PhotoLikeUser> getPhotoLikeUserListByPhotoid(long photoid,
			boolean buildUser, long refuserid, int begin, int size) {
		Query query = this.manager.createQuery();
		List<PhotoLikeUser> list = query.listEx(PhotoLikeUser.class,
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