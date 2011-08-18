package tuxiazi.bean;

import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

/**
 * 最新图片
 * 
 * @author akwei
 */
@Table(name = "lasted_photo")
public class Lasted_photo {

	/**
	 * 图片id
	 */
	@Id
	private long photoid;

	private Photo photo;

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
}