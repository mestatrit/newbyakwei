package tuxiazi.bean;

import tuxiazi.dao.Lasted_photoDao;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;

/**
 * 最新图片
 * 
 * @author akwei
 */
@Table(name = "lasted_photo")
public class Lasted_photo {

	public Lasted_photo() {
	}

	public Lasted_photo(long photoid) {
		this.photoid = photoid;
	}

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

	public void save() {
		Lasted_photoDao dao = (Lasted_photoDao) HaloUtil
				.getBean("lasted_photoDao");
		dao.save(this);
	}

	public void delete() {
		Lasted_photoDao dao = (Lasted_photoDao) HaloUtil
				.getBean("lasted_photoDao");
		dao.deleteById(this.photoid);
	}
}