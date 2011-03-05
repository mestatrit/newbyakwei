package tuxiazi.job;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.svr.iface.PhotoService;

public class TuxiaziJob {

	@Autowired
	private PhotoService photoService;

	public void proccessHotPhoto() {
		this.photoService.createHotPhotos();
	}
}
