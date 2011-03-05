package tuxiazi.svr.iface;

import java.util.ArrayList;
import java.util.List;

import tuxiazi.bean.Photo;

public class UploadPhotoResult {

	private List<Photo> photos;

	/**
	 * 程序出错的图片数量
	 */
	private int err_num;

	/**
	 * 图片文件过大的数量
	 */
	private int err_too_big_num;

	/**
	 * 非图片的数量
	 */
	private int err_fmt_num;

	/**
	 * 是否完全上传成功
	 */
	private boolean success;

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public int getErr_num() {
		return err_num;
	}

	public int getErr_too_big_num() {
		return err_too_big_num;
	}

	public int getErr_fmt_num() {
		return err_fmt_num;
	}

	public boolean isSuccess() {
		return success;
	}

	public void addPhoto(Photo photo) {
		if (this.photos == null) {
			this.photos = new ArrayList<Photo>();
		}
		this.photos.add(photo);
	}

	public void addErr_num(int add) {
		this.err_num = this.err_num + add;
	}

	public void addErr_fmt_num(int add) {
		this.err_fmt_num = this.err_fmt_num + add;
	}

	public void addErr_too_big_num(int add) {
		this.err_too_big_num = this.err_too_big_num + add;
	}

	public void proccess() {
		if (this.err_num == 0 && this.err_fmt_num == 0
				&& this.err_too_big_num == 0) {
			this.success = true;
		}
		else {
			this.success = false;
		}
	}
}