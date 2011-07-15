package iwant.util;

import halo.util.HaloUtil;


public class PicUtil {

	/**
	 * 70*70
	 */
	public static final String SLIDE_PIC1_NAME = "p1.jpg";

	/**
	 * 640*960
	 */
	public static final String SLIDE_PIC2_NAME = "p2.jpg";

	public static FileCnf slideFileCnf;

	public static void initSlideFileCnf() {
		if (slideFileCnf == null) {
			slideFileCnf = (FileCnf) HaloUtil.getBean("slideFileCnf");
		}
	}

	public static String getSlidePic1Url(String dbPath) {
		initSlideFileCnf();
		return slideFileCnf.getFileUrlPart(dbPath) + SLIDE_PIC1_NAME;
	}

	public static String getSlidePic2Url(String dbPath) {
		initSlideFileCnf();
		return slideFileCnf.getFileUrlPart(dbPath) + SLIDE_PIC2_NAME;
	}
}