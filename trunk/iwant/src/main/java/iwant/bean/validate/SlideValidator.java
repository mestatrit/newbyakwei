package iwant.bean.validate;

import halo.util.HaloUtil;
import halo.util.HaloValidate;
import halo.util.image.OriginInfo;
import iwant.bean.Slide;
import iwant.svr.PptSvr;
import iwant.web.admin.util.Err;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SlideValidator {

	public static List<String> validate(Slide slide, File imgFile,
			boolean forCreate) {
		List<String> list = new ArrayList<String>();
		if (!HaloValidate.validateEmptyAndLength(slide.getTitle(), false, 20)) {
			list.add(Err.SLIDE_TITLE_ERR);
		}
		if (!HaloValidate.validateLength(slide.getDescr(), false, 300)) {
			list.add(Err.SLIDE_DESCR_ERR);
		}
		if (forCreate) {
			if (imgFile == null) {
				list.add(Err.SLIDE_IMG_FORMAT_ERR);
			}
			else {
				try {
					OriginInfo originInfo = new OriginInfo(imgFile);
					if (originInfo.getImgFileSizeMB() > 3) {
						list.add(Err.SLIDE_IMG_SIZE_ERR);
					}
				}
				catch (IOException e) {
					list.add(Err.SLIDE_IMG_FORMAT_ERR);
				}
			}
		}
		PptSvr pptSvr = (PptSvr) HaloUtil.getBean("pptSvr");
		if (pptSvr.getPpt(slide.getPptid()) == null
				&& pptSvr.getMainPpt(slide.getPptid()) == null) {
			list.add(Err.SLIDE_PPTID_ERR);
		}
		return list;
	}
}