package tuxiazi.bean;

import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

/**
 * 图片id
 * 
 * @author Administrator
 */
@Table(name = "photoid")
public class Photoid {

	/**
	 * 图片id
	 */
	@Id
	private long photoid;

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}
}