package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.DataUtil;
import halo.util.JsonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tuxiazi.bean.benum.PhotoPrivacyEnum;
import tuxiazi.svr.impl.jms.JsonKey;
import tuxiazi.util.PhotoUtil;

/**
 * 图片
 * 
 * @author Administrator
 */
@Table(name = "photo")
public class Photo {

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
	 * 图片隐私属性
	 */
	@Column
	private byte privacy_flg;

	/**
	 * 图片评论数量
	 */
	@Column
	private int cmt_num;

	/**
	 * 最近10条评论数据
	 */
	@Column
	private String recentCmtData;

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

	private boolean opliked;

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

	public byte getPrivacy_flg() {
		return privacy_flg;
	}

	public boolean isPrivacy() {
		if (this.privacy_flg == PhotoPrivacyEnum.PRIVATE.getValue()) {
			return true;
		}
		return false;
	}

	public void setPrivacy_flg(byte privacyFlg) {
		privacy_flg = privacyFlg;
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

	/**
	 * 存储的最近的5条评论
	 * 
	 * @return 2010-12-2
	 */
	public List<PhotoCmt> getCmtList() {
		if (DataUtil.isEmpty(this.recentCmtData)) {
			return new ArrayList<PhotoCmt>(0);
		}
		List<String> cmtjsondata = JsonUtil.getListFromJson(this.recentCmtData);
		List<PhotoCmt> list = new ArrayList<PhotoCmt>(cmtjsondata.size());
		Map<String, String> map = null;
		PhotoCmt photoCmt = null;
		User user = null;
		for (String s : cmtjsondata) {
			map = JsonUtil.getMapFromJson(s);
			photoCmt = new PhotoCmt();
			photoCmt.setCmtid(Long.valueOf(map.get(JsonKey.cmtid)));
			photoCmt.setUserid(Long.valueOf(map.get(JsonKey.userid)));
			photoCmt.setContent(map.get(JsonKey.content));
			photoCmt.setCreate_time(new Date(Long.valueOf(map
					.get(JsonKey.create_time))));
			if (DataUtil.isNotEmpty(map.get(JsonKey.replyuserid))) {
				photoCmt.setReplyuserid(Long.valueOf(map
						.get(JsonKey.replyuserid)));
			}
			user = new User();
			user.setUserid(Long.valueOf(map.get(JsonKey.userid)));
			user.setNick(map.get(JsonKey.nick));
			photoCmt.setUser(user);
			list.add(photoCmt);
		}
		return list;
	}

	/**
	 * 只保存前5条
	 * 
	 * @param list
	 *            2010-12-2
	 */
	public void buildRecentCmtData(final List<PhotoCmt> list) {
		List<PhotoCmt> _list = null;
		if (list.size() > 5) {
			_list = DataUtil.subList(list, 0, 5);
		}
		else {
			_list = list;
		}
		List<String> cmtjsondata = new ArrayList<String>(_list.size());
		Map<String, String> map = null;
		for (PhotoCmt o : _list) {
			map = new HashMap<String, String>();
			map.put(JsonKey.cmtid, String.valueOf(o.getCmtid()));
			map.put(JsonKey.content, String.valueOf(o.getContent()));
			map.put(JsonKey.create_time, String.valueOf(o.getCreate_time()
					.getTime()));
			map.put(JsonKey.userid, String.valueOf(o.getUserid()));
			map.put(JsonKey.nick, o.getUser().getNick());
			cmtjsondata.add(JsonUtil.toJson(map));
		}
		this.recentCmtData = JsonUtil.toJson(cmtjsondata);
	}

	public String getRecentCmtData() {
		return recentCmtData;
	}

	public void setRecentCmtData(String recentCmtData) {
		this.recentCmtData = recentCmtData;
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
}