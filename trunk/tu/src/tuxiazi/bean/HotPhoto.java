package tuxiazi.bean;

import tuxiazi.util.PhotoUtil;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 热门图片，即图片的喜欢数量按照从多到少的排列，最多100条数据
 * 
 * @author akwei
 */
@Table(name = "hotphoto")
public class HotPhoto {

	@Id
	private long oid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	@Column
	private long photoid;

	@Column
	private String path;

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 60 x 60
	 * 
	 * @param path
	 * @return
	 */
	public String getP1url() {
		return PhotoUtil.getP1url(path);
	}

	/**
	 * 480 x 480
	 * 
	 * @param path
	 * @return
	 */
	public String getP4url() {
		return PhotoUtil.getP4url(path);
	}
}