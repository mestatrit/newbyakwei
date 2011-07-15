package iwant.bean.validate;

import halo.util.HaloUtil;
import halo.util.HaloValidate;
import iwant.bean.Slide;
import iwant.svr.PptSvr;
import iwant.web.admin.util.Err;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dev3g.cactus.util.jmagick.ImgFileInfo;

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
		PptSvr pptSvr = (PptSvr) HaloUtil.getBean("pptSvr");
		if (pptSvr.getPpt(slide.getPptid()) == null
				&& pptSvr.getMainPpt(slide.getPptid()) == null) {
			list.add(Err.SLIDE_PPTID_ERR);
		}
		return list;
	}
}