package tuxiazi.bean;

import java.io.File;
import java.util.Date;

public class UploadPhoto {

	private long userid;

	private File file;

	private String name;

	private Date create_time;

	private byte privacy_flg;

	public void setPrivacy_flg(byte privacyFlg) {
		privacy_flg = privacyFlg;
	}

	public byte getPrivacy_flg() {
		return privacy_flg;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}
}