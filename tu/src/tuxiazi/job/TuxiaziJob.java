package tuxiazi.job;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import tuxiazi.svr.iface.PhotoService;

public class TuxiaziJob {

	@Autowired
	private PhotoService photoService;

	public void proccessHotPhoto() {
		Calendar begincal = Calendar.getInstance();
		begincal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Calendar endcal = Calendar.getInstance();
		this.photoService.createHotPhotos(begincal.getTime(), endcal.getTime());
	}
}
