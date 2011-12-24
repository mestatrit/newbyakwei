package web.epp.mgr.action;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.hk.bean.CmpNav;

public class CmpNavUtil {

	private static Map<Integer, String> map = new HashMap<Integer, String>();

	/**
	 * 后台管理中，点击栏目时，根据栏目类型到不同的功能页面，key-value对应的为 栏目类型-栏目功能后台链接
	 */
	private static Map<Integer, String> adminfunccmap = new HashMap<Integer, String>();

	/**
	 * pc页面中，点击栏目，根据栏目到不同的功能页面,key-value对应的为栏目类型-栏目功能前台链接
	 */
	private static Map<Integer, String> webmap = new HashMap<Integer, String>();

	/**
	 * wap页面对应的key-value
	 */
	private static Map<Integer, String> wapmap = new HashMap<Integer, String>();
	static {
		map
				.put(CmpNav.REFFUNC_NONE,
						"/epp/web/op/webadmin/admincmpnav_list2.do?companyId={0}&parentId={1}");
		/****************** 后台功能跳转 *****************************/
		adminfunccmap.put(CmpNav.REFFUNC_SINGLECONTENT,
				"/epp/web/op/webadmin/cmparticle.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_LISTCONTENT,
				"/epp/web/op/webadmin/cmparticle.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_CONTACT,
				"/epp/web/op/webadmin/cmpcontact.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_BOX,
				"/epp/web/op/webadmin/box.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_COUPON,
				"/epp/web/op/webadmin/coupon.do?companyId={0}&navoid={1}");
		adminfunccmap
				.put(CmpNav.REFFUNC_BBS,
						"/epp/web/op/webadmin/admincmpbbskind.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_MSG,
				"/epp/web/op/webadmin/cmpmsg.do?companyId={0}&navoid={1}");
		adminfunccmap
				.put(CmpNav.REFFUNC_MAP,
						"/epp/web/op/webadmin/admincmpnav_setmap.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_SELNET,
				"/epp/web/op/webadmin/cmpsellnet.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_PRODUCT,
				"/epp/web/op/webadmin/cmpproduct.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_CMPDOWNFILE,
				"/epp/web/op/webadmin/cmpdownfile.do?companyId={0}&navoid={1}");
		adminfunccmap.put(CmpNav.REFFUNC_VIDEO,
				"/epp/web/op/webadmin/cmpvideo.do?companyId={0}&navoid={1}");
		adminfunccmap
				.put(CmpNav.REFFUNC_TABLE_DATA,
						"/epp/web/op/webadmin/cmpusertable.do?companyId={0}&navoid={1}");
		/****************** 前台 pc 显示使用 *****************************/
		webmap.put(CmpNav.REFFUNC_SINGLECONTENT, "/articles/{0}/{1}/");
		webmap.put(CmpNav.REFFUNC_LISTCONTENT, "/articles/{0}/{1}/");
		webmap.put(CmpNav.REFFUNC_CONTACT,
				"/epp/web/cmpcontact.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_BOX,
				"/epp/web/box.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_COUPON,
				"/epp/web/coupon.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_BBS,
				"/epp/web/cmpbbs.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_MSG,
				"/epp/web/cmpmsg.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_MAP,
				"/epp/web/info_map.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_NONE,
				"/epp/web/cmpnav_view.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_SELNET,
				"/epp/web/cmpsellnet.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_PRODUCT, "/products/{0}/{1}");
		webmap.put(CmpNav.REFFUNC_CMPDOWNFILE,
				"/epp/web/file.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_VIDEO,
				"/epp/web/video.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_RESERVE, "/actors/{0}/{1}");
		webmap.put(CmpNav.REFFUNC_LOGINANDREG,
				"/epp/login_web.do?companyId={0}");
		webmap.put(CmpNav.REFFUNC_TABLE_DATA,
				"/epp/web/cmpusertable.do?companyId={0}&navId={1}");
		webmap.put(CmpNav.REFFUNC_RETURNHOME, "/");
		/****************** 前台 wap 显示使用 *****************************/
		wapmap.put(CmpNav.REFFUNC_SINGLECONTENT,
				"/epp/web/cmparticle_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_LISTCONTENT,
				"/epp/web/cmparticle_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_CONTACT,
				"/epp/web/cmpcontact_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_BOX,
				"/epp/web/box_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_COUPON,
				"/epp/web/coupon_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_BBS,
				"/epp/web/cmpbbs_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_MSG,
				"/epp/web/cmpmsg_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_MAP,
				"/epp/web/info_wapmap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_NONE,
				"/epp/web/cmpnav_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_SELNET,
				"/epp/web/cmpsellnet_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_PRODUCT,
				"/epp/web/product_wap.do?companyId={0}&navId={1}");
		wapmap.put(CmpNav.REFFUNC_LOGINANDREG, "/epp/login.do?companyId={0}");
		wapmap.put(CmpNav.REFFUNC_RETURNHOME, "/m");
	}

	/**
	 * 前台显示时，根据oid跳转到不同的功能页面
	 * 
	 * @param cmpNav
	 * @return
	 *         2010-5-18
	 */
	public static String getWebFuncUrl(CmpNav cmpNav) {
		String url = webmap.get(cmpNav.getReffunc());
		if (url != null) {
			return MessageFormat.format(url, String.valueOf(cmpNav
					.getCompanyId()), String.valueOf(cmpNav.getOid()));
		}
		return null;
	}

	/**
	 * 前台wap显示时，根据nav跳转到不同的功能页面
	 * 
	 * @param cmpNav
	 * @return
	 *         2010-5-18
	 */
	public static String getWapFuncUrl(CmpNav cmpNav) {
		String url = wapmap.get(cmpNav.getReffunc());
		if (url != null) {
			return MessageFormat.format(url, String.valueOf(cmpNav
					.getCompanyId()), String.valueOf(cmpNav.getOid()));
		}
		return null;
	}

	/**
	 * 后台根据不同功能跳转到相应功能页面
	 * 
	 * @param cmpNav
	 * @return
	 *         2010-5-18
	 */
	public static String getAdminFuncUrl(CmpNav cmpNav) {
		String url = adminfunccmap.get(cmpNav.getReffunc());
		if (url != null) {
			return MessageFormat.format(url, String.valueOf(cmpNav
					.getCompanyId()), String.valueOf(cmpNav.getOid()));
		}
		return null;
	}

	public static String getFuncUrl(int reffunc, long companyId, long navoid) {
		String url = map.get(reffunc);
		if (url != null) {
			return MessageFormat.format(url, String.valueOf(companyId), String
					.valueOf(navoid));
		}
		return null;
	}
}