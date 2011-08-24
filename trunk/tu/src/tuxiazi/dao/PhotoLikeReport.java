package tuxiazi.dao;

public class PhotoLikeReport {

	public PhotoLikeReport(long photoid, int like_num) {
		this.photoid = photoid;
		this.like_num = like_num;
	}

	private long photoid;

	private int like_num;

	public long getPhotoid() {
		return photoid;
	}

	public void setPhotoid(long photoid) {
		this.photoid = photoid;
	}

	public int getLike_num() {
		return like_num;
	}

	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}
}