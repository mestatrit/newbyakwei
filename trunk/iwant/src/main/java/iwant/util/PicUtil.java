package iwant.util;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;

public class PicUtil {

	/**
	 * 70*70
	 */
	public static final String PROJECT_PIC1_NAME = "p1.jpg";

	/**
	 * 640*960
	 */
	public static final String PROJECT_PIC2_NAME = "p12.jpg";

	/**
	 * 70*70
	 */
	public static final String PPT_PIC1_NAME = "p1.jpg";

	/**
	 * 640*960
	 */
	public static final String PPT_PIC2_NAME = "p12.jpg";

	public static void main(String[] args) {
		DataUtil.deleteAllFile("f:/macpro/");
		P.println("ok");
	}
}