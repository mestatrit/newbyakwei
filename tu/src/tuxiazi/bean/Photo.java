package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.DataUtil;
import halo.util.FileUtil;
import halo.util.HaloUtil;
import halo.util.JsonUtil;
import halo.util.image.ImageException;
import halo.util.image.ImageParam;
import halo.util.image.ImageShaper;
import halo.util.image.ImageShaperFactory;
import halo.util.image.ImageSize;
import halo.util.image.ImageSizeMaker;
import halo.util.image.OriginInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tuxiazi.dao.PhotoDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;
import tuxiazi.util.FileCnf;
import tuxiazi.util.PhotoUtil;

/**
 * 图片
 * 
 * @author akwei
 */
@Table(name = "photo", partitionClass = TuxiaziDbPartitionHelper.class)
public class Photo {

	private final Log log = LogFactory.getLog(Photo.class);

	public static final String p1_houzhui = "p_1.jpg";

	public static final String p2_houzhui = "p_2.jpg";

	public static final String p4_houzhui = "p_4.jpg";

	public static final String p6_houzhui = "p_6.jpg";

	/**
	 * 图片id
	 */
	@Id
	private long photoid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 图片目录
	 */
	@Column
	private String path;

	/**
	 * 图片创建时间
	 */
	@Column
	private Date create_time;

	/**
	 * 图片名称
	 */
	@Column
	private String name;

	/**
	 * 图片描述
	 */
	@Column
	private String intro;

	/**
	 * 图片评论数量
	 */
	@Column
	private int cmt_num;

	/**
	 * 喜欢这张图片的人的数量
	 */
	@Column
	private int like_num;

	/**
	 * 最新的喜欢这张图片的10个人的nick
	 */
	@Column
	private String like_user;

	private List<PhotoCmt> photoCmtList;

	private boolean opliked;

	public void setPhotoCmtList(List<PhotoCmt> photoCmtList) {
		this.photoCmtList = photoCmtList;
	}

	public List<PhotoCmt> getPhotoCmtList() {
		return photoCmtList;
	}

	public void setOpliked(boolean opliked) {
		this.opliked = opliked;
	}

	public boolean isOpliked() {
		return opliked;
	}

	private List<LikeUser> likeUserList;

	public int getLike_num() {
		return like_num;
	}

	public void setLike_num(int likeNum) {
		like_num = likeNum;
	}

	public String getLike_user() {
		return like_user;
	}

	public void setLike_user(String likeUser) {
		like_user = likeUser;
	}

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return
	 */
	public String getP1url() {
		return PhotoUtil.getP1url(this.path);
	}

	public String getP2url() {
		return PhotoUtil.getP2url(this.path);
	}

	public String getP3url() {
		return PhotoUtil.getP3url(this.path);
	}

	public String getP4url() {
		return PhotoUtil.getP4url(this.path);
	}

	public String getP5url() {
		return PhotoUtil.getP5url(this.path);
	}

	public String getP6url() {
		return PhotoUtil.getP6url(this.path);
	}

	public int getCmt_num() {
		return cmt_num;
	}

	public void setCmt_num(int cmtNum) {
		cmt_num = cmtNum;
	}

	public List<LikeUser> getLikeUserList() {
		if (this.likeUserList == null) {
			this.initPhotoLikeUserList();
		}
		return likeUserList;
	}

	public void setLikeUserList(List<LikeUser> likeUserList) {
		this.likeUserList = likeUserList;
	}

	public void initLikeUserlist(List<LikeUser> likeUserList) {
		this.setLikeUserList(likeUserList);
		this.createLike_user();
	}

	public void addLikeUser(long userid, String nick) {
		this.initPhotoLikeUserList();
		this.likeUserList.add(new LikeUser(userid, nick));
		this.likeUserList = DataUtil.subList(this.likeUserList, 0, 10);
		this.createLike_user();
	}

	public void removeLikeUser(long userid) {
		this.initPhotoLikeUserList();
		for (LikeUser o : this.likeUserList) {
			if (o.getUserid() == userid) {
				this.likeUserList.remove(o);
				break;
			}
		}
		this.createLike_user();
	}

	private void createLike_user() {
		if (this.likeUserList.isEmpty()) {
			this.like_user = null;
			return;
		}
		List<String> list = new ArrayList<String>();
		for (LikeUser o : this.likeUserList) {
			list.add(o.getUserid() + ";" + o.getNick());
		}
		this.like_user = JsonUtil.toJson(list);
	}

	private void initPhotoLikeUserList() {
		if (this.likeUserList == null) {
			this.likeUserList = new ArrayList<LikeUser>();
			if (DataUtil.isNotEmpty(this.like_user)) {
				List<String> list = JsonUtil.getListFromJson(this.like_user);
				LikeUser likeUser = null;
				for (String s : list) {
					String[] tmp = s.split(";", 2);
					likeUser = new LikeUser();
					likeUser.setUserid(Long.valueOf(tmp[0]));
					likeUser.setNick(tmp[1]);
					likeUserList.add(likeUser);
				}
			}
		}
	}

	public String getFmtTime() {
		return PhotoUtil.getFmtTime(this.create_time);
	}

	public void upload(UploadPhoto uploadPhoto, FileCnf fileCnf, User user)
			throws IOException, ImageException, ImageSizeOutOfLimitException {
		PhotoDao dao = (PhotoDao) HaloUtil.getBean("photoDao");
		String name = FileCnf.createFileName();
		String dbPath = fileCnf.getFileSaveToDbPath(name);
		String filePath = fileCnf.getFilePath(dbPath);
		if (FileUtil.isBigger(uploadPhoto.getFile(), 1024 * 1024)) {
			throw new ImageSizeOutOfLimitException("imageSize : [ "
					+ uploadPhoto.getFile().length() + " bytes ]");
		}
		try {
			this.processUploadImage(uploadPhoto.getFile(), filePath);
			this.setUserid(user.getUserid());
			this.setName(uploadPhoto.getName());
			this.setCreate_time(uploadPhoto.getCreate_time());
			this.setPath(dbPath);
			dao.save(this);
			User_photo userPhoto = new User_photo();
			userPhoto.setUserid(this.getUserid());
			userPhoto.setPhotoid(this.getPhotoid());
			userPhoto.save();
		}
		catch (IOException e) {
			log.error("process image io error [ " + e.getMessage() + " ]");
			throw e;
		}
		catch (ImageException e) {
			log.error("process image error [ " + e.getMessage() + " ]");
			throw e;
		}
	}

	private void processUploadImage(File file, String filePath)
			throws IOException, ImageException {
		ImageShaper imageShaper = ImageShaperFactory
				.getImageShaper(ImageShaperFactory.SHAPER_JMAGICK);
		ImageParam imageParam = new ImageParam(file, 90, 0, 0, true);
		OriginInfo originInfo = imageParam.getOriginInfo();
		ImageSize scaleImageSize = ImageSizeMaker.makeSize(
				originInfo.getWidth(), originInfo.getHeight(), 60);
		imageShaper.scale(imageParam, scaleImageSize, filePath,
				Photo.p1_houzhui);
//		System.out.println(f.getAbsolutePath());
		scaleImageSize = ImageSizeMaker.makeSize(originInfo.getWidth(),
				originInfo.getHeight(), 120);
		imageShaper.scale(imageParam, scaleImageSize, filePath,
				Photo.p2_houzhui);
		scaleImageSize = ImageSizeMaker.makeSize(originInfo.getWidth(),
				originInfo.getHeight(), 480);
		imageShaper.scale(imageParam, scaleImageSize, filePath,
				Photo.p4_houzhui);
		imageParam.setQuality(95);
		scaleImageSize = ImageSizeMaker.makeSize(originInfo.getWidth(),
				originInfo.getHeight(), 640);
		imageShaper.scale(imageParam, scaleImageSize, filePath,
				Photo.p6_houzhui);
	}

	public void update() {
		PhotoDao dao = (PhotoDao) HaloUtil.getBean("photoDao");
		dao.update(this);
	}
	public static void main(String[] args) throws IOException, ImageException {
		ImageShaper imageShaper = ImageShaperFactory
		.getImageShaper(ImageShaperFactory.SHAPER_JMAGICK);
		ImageParam imageParam = new ImageParam(new File("d:/test/test0.jpg"), 90, 0, 0, true);
		OriginInfo originInfo = imageParam.getOriginInfo();
		ImageSize scaleImageSize = ImageSizeMaker.makeSize(
				originInfo.getWidth(), originInfo.getHeight(), 60);
		File f= imageShaper.scale(imageParam, scaleImageSize, "d:/home/tuxiazi/pub/pic/a/2011/9/18/18/1316341059640x6nt9k/",
				Photo.p1_houzhui);
		System.out.println(f.getAbsolutePath());
	}
}