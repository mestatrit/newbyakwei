package iwant.bean.validate;

import iwant.bean.Slide;
import iwant.svr.PptSvr;
import iwant.web.admin.util.Err;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cactus.util.HkUtil;
import cactus.util.HkValidate;
import cactus.util.image.jmagick.ImgFileInfo;

public class SlideValidator {

	public static List<String> validate(Slide slide, File imgFile,
			boolean forCreate) {
		List<String> list = new ArrayList<String>();
		if (!HkValidate.validateEmptyAndLength(slide.getTitle(), false, 20)) {
			list.add(Err.SLIDE_TITLE_ERR);
		}
		if (!HkValidate.validateLength(slide.getSubtitle(), false, 2)) {
			list.add(Err.SLIDE_SUBTITLE_ERR);
		}
		if (!HkValidate.validateLength(slide.getDescr(), false, 300)) {
			list.add(Err.SLIDE_DESCR_ERR);
		}
		if (forCreate) {
			if (imgFile == null) {
				list.add(Err.SLIDE_IMG_FORMAT_ERR);
			}
			else {
				ImgFileInfo imgFileInfo = ImgFileInfo.getImageFileInfo(imgFile);
				if (imgFileInfo == null) {
					list.add(Err.SLIDE_IMG_FORMAT_ERR);
				}
				else {
					if (imgFileInfo.getImgFileSizeMB() > 3) {
						list.add(Err.SLIDE_IMG_SIZE_ERR);
					}
				}
			}
		}
		PptSvr pptSvr = (PptSvr) HkUtil.getBean("pptSvr");
		if (pptSvr.getPpt(slide.getPptid()) == null
				&& pptSvr.getMainPpt(slide.getPptid()) == null) {
			list.add(Err.SLIDE_PPTID_ERR);
		}
		return list;
	}
}