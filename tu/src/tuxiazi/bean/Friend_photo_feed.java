package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;

import java.util.Date;

import tuxiazi.dao.Friend_photo_feedDao;
import tuxiazi.dao.dbpartitionhelper.Tuxiazi_FeedDbPartitionHelper;

/**
 * 用户关注的人的最近图片动态
 * 
 * @author akwei
 */
@Table(name = "friend_photo_feed", partitionClass = Tuxiazi_FeedDbPartitionHelper.class)
public class Friend_photo_feed {

	/**
	 * 动态id
	 */
	@Id
	private long feedid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 图片id
	 */
	@Column
	private long photoid;

	/**
	 * 动态创建时间
	 */
	@Column
	private Date create_time;

	@Column
	private long photo_userid;

	private Photo photo;

	public void setPhoto_userid(long photoUserid) {
		photo_userid = photoUserid;
	}

	public long getPhoto_userid() {
		return photo_userid;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Photo getPhoto() {
		return photo;
	}

	public long getFeedid() {
		return feedid;
	}

	public void setFeedid(long feedid) {
		this.feedid = feedid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public void save() {
		Friend_photo_feedDao dao = (Friend_photo_feedDao) HaloUtil
				.getBean("friend_photo_feedDao");
		dao.save(this);
	}

	public void delete() {
		Friend_photo_feedDao dao = (Friend_photo_feedDao) HaloUtil
				.getBean("friend_photo_feedDao");
		dao.deleteById(this.feedid);
	}

	public Friend_photo_feed() {
	}

	public Friend_photo_feed(long userid, long photoid, Date create_time,
			long photo_userid) {
		this.userid = userid;
		this.photoid = photoid;
		this.create_time = create_time;
		this.photo_userid = photo_userid;
	}
}