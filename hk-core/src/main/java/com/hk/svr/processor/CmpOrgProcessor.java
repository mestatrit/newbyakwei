package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrg;
import com.hk.bean.CmpOrgNav;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpOrgService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpOrgProcessor {

	@Autowired
	private CmpOrgService cmpOrgService;

	/**
	 * 更新机构标题的背景图片
	 * 
	 * @param cmpOrg
	 * @param file
	 * @return
	 * @throws IOException
	 *             2010-7-13
	 */
	public int updateCmpOrgPath(CmpOrg cmpOrg, File file) throws IOException {
		if (file != null) {
			if (DataUtil.isBigger(file, 100)) {
				return Err.IMG_OUTOFSIZE_ERROR;
			}
			String dbPath = ImageConfig.getCmpOrgBgDbPath(
					cmpOrg.getCompanyId(), cmpOrg.getOrgId());
			String filePath = ImageConfig.getCmpOrgBgFilePath(dbPath);
			DataUtil.copyFile(file, filePath, "h.jpg");
			cmpOrg.setPath(dbPath);
			this.cmpOrgService.updateCmpOrg(cmpOrg);
		}
		return Err.SUCCESS;
	}

	public void moveUpCmpOrgOrder(long companyId, long orgId, long navId) {
		List<CmpOrgNav> cmporgnavlist = cmpOrgService
				.getCmpOrgNavListByCompanyIdAndOrgId(companyId, orgId);
		CmpOrgNav[] arr = cmporgnavlist.toArray(new CmpOrgNav[cmporgnavlist
				.size()]);
		// 栏目移位
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getNavId() == navId && i > 0) {
				CmpOrgNav tmp = arr[i - 1];
				arr[i - 1] = arr[i];
				arr[i] = tmp;
			}
		}
		// 初始化所有栏目顺序并保存
		for (int i = 0; i < arr.length; i++) {
			arr[i].setOrderflg(i + 1);
			this.cmpOrgService.updateCmpOrgNav(arr[i]);
		}
	}
}