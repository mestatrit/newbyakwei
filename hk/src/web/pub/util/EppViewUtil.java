package web.pub.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Box;
import com.hk.bean.CmpActorRole;
import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpAdRef;
import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpArticleContent;
import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpArticleTagRef;
import com.hk.bean.CmpBbs;
import com.hk.bean.CmpBbsKind;
import com.hk.bean.CmpFrLink;
import com.hk.bean.CmpInfo;
import com.hk.bean.CmpLanguageRef;
import com.hk.bean.CmpMsg;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpSellNetKind;
import com.hk.bean.CmpSvrKind;
import com.hk.bean.CmpWebColor;
import com.hk.bean.Company;
import com.hk.bean.Coupon;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.svr.BoxService;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpAdService;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpArticleTagService;
import com.hk.svr.CmpBbsService;
import com.hk.svr.CmpFrLinkService;
import com.hk.svr.CmpModService;
import com.hk.svr.CmpMsgService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpSellNetService;
import com.hk.svr.CmpSvrService;
import com.hk.svr.CouponService;
import com.hk.svr.processor.CmpAdProcessor;
import com.hk.svr.processor.CmpArticleProcessor;
import com.hk.svr.processor.CmpInfoProcessor;
import com.hk.svr.processor.CmpModProcessor;
import com.hk.svr.processor.CmpNavProcessor;
import com.hk.svr.pub.CmpPageModUtil;

/**
 * jsp页面调用的文件
 * 
 * @author akwei
 */
public class EppViewUtil {

	/**
	 * 管理页面，左边导航
	 * 
	 * @param request
	 *            2010-5-20
	 */
	public static void loadCmpNavFunc(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpNavProcessor cmpNavProcessor = (CmpNavProcessor) HkUtil
				.getBean("cmpNavProcessor");
		List<CmpNav> mgrleft_navlist = cmpNavProcessor
				.getCmpNavListByCompanyIdForMap(companyId);
		request.setAttribute("mgrleft_navlist", mgrleft_navlist);
	}

	/**
	 * 获取企业网站所有导航，并按照父子关系组合
	 * 
	 * @param request
	 * @param removeHome 是否将首页栏目去除
	 *            2010-8-1
	 */
	public static void loadAllCmpNav(HttpServletRequest request,
			boolean removeHome) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpNavService cmpNavService = (CmpNavService) HkUtil
				.getBean("cmpNavService");
		List<CmpNav> allnavlist = cmpNavService
				.getCmpNavListByCompanyId(companyId);
		if (removeHome) {
			for (CmpNav o : allnavlist) {
				if (o.isHomeNav()) {
					allnavlist.remove(o);
					break;
				}
			}
		}
		List<CmpNav> nav_1_list = new ArrayList<CmpNav>();
		List<CmpNav> nav_2_list = new ArrayList<CmpNav>();
		List<CmpNavVo> cmpnavvolist = new ArrayList<CmpNavVo>();
		for (CmpNav o : allnavlist) {
			if (o.getNlevel() == CmpNav.NLEVEL_1) {
				nav_1_list.add(o);
			}
			else if (o.getNlevel() == CmpNav.NLEVEL_2) {
				nav_2_list.add(o);
			}
		}
		for (CmpNav o : nav_1_list) {
			List<CmpNav> children = new ArrayList<CmpNav>();
			for (CmpNav oo : nav_2_list) {
				if (oo.getParentId() == o.getOid()) {
					children.add(oo);
				}
			}
			cmpnavvolist.add(new CmpNavVo(o, children));
		}
		request.setAttribute("cmpnavvolist", cmpnavvolist);
	}

	/**
	 * 获取企业网站一级导航
	 * 
	 * @param request
	 *            2010-5-17
	 */
	@SuppressWarnings("unchecked")
	public static void loadCmpNavTop(HttpServletRequest request) {
		List<CmpNav> website_navlist = (List<CmpNav>) request
				.getAttribute("website_navlist");
		if (website_navlist == null) {
			long companyId = ServletUtil.getLong(request, "companyId");
			CmpNavService cmpNavService = (CmpNavService) HkUtil
					.getBean("cmpNavService");
			website_navlist = cmpNavService.getCmpNavListByCompanyIdAndNlevel(
					companyId, CmpNav.NLEVEL_1);
		}
		for (CmpNav o : website_navlist) {
			if (o.getReffunc() == CmpNav.REFFUNC_BBS) {
				request.setAttribute("cmpnav_cmpbbs_column", o);
				break;
			}
		}
		request.setAttribute("website_navlist", website_navlist);
	}

	public static void loadCmpArticleList(HttpServletRequest request) {
		CmpNav cmpNav = (CmpNav) request.getAttribute("right_nav");
		if (cmpNav == null) {
			return;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpArticleService cmpArticleService = (CmpArticleService) HkUtil
				.getBean("cmpArticleService");
		List<CmpArticle> cmparticlelist = cmpArticleService
				.getCmpArticleListByCompanyIdAndCmpNavOidForCmppink(companyId,
						cmpNav.getOid(), 0, 6);
		request.setAttribute("cmparticlelist", cmparticlelist);
	}

	/**
	 * 推荐的宝箱
	 * 
	 * @param request
	 *            2010-5-17
	 */
	public static void loadBoxList(HttpServletRequest request) {
		CmpNav cmpNav = (CmpNav) request.getAttribute("right_nav");
		if (cmpNav == null) {
			return;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		BoxService boxService = (BoxService) HkUtil.getBean("boxService");
		List<Box> boxlist = boxService.getCanOpenBoxListByCompanyIdForCmppink(
				companyId, 0, 6);
		request.setAttribute("boxlist", boxlist);
	}

	/**
	 * 推荐的优惠券
	 * 
	 * @param request
	 *            2010-5-17
	 */
	public static void loadCouponList(HttpServletRequest request) {
		CmpNav cmpNav = (CmpNav) request.getAttribute("right_nav");
		if (cmpNav == null) {
			return;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		CouponService couponService = (CouponService) HkUtil
				.getBean("couponService");
		List<Coupon> couponlist = couponService
				.getCouponListByCompanyIdForUsefulForCmppink(companyId, 0, 6);
		request.setAttribute("couponlist", couponlist);
	}

	/**
	 * 推荐的留言
	 * 
	 * @param request
	 *            2010-5-17
	 */
	public static void loadCmpMsgList(HttpServletRequest request) {
		CmpNav cmpNav = (CmpNav) request.getAttribute("right_nav");
		if (cmpNav == null) {
			return;
		}
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpMsgService cmpMsgService = (CmpMsgService) HkUtil
				.getBean("cmpMsgService");
		List<CmpMsg> cmpmsglist = cmpMsgService
				.getCmpMsgListByCompanyIdForCmppink(companyId, 0, 6);
		request.setAttribute("cmpmsglist", cmpmsglist);
	}

	public static String outBbsFmtTime(HttpServletRequest request, String key) {
		Date date = (Date) request.getAttribute(key);
		if (date != null) {
			return DataUtil.getFmtTime(date);
		}
		return "";
	}

	public static void loadCmpBbsKindList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpBbsService cmpBbsService = (CmpBbsService) HkUtil
				.getBean("cmpBbsService");
		List<CmpBbsKind> cmpbbskindlist = cmpBbsService
				.getCmpBbsKindListByCompanyId(companyId);
		request.setAttribute("cmpbbskindlist", cmpbbskindlist);
	}

	public static void loadCmpBbsList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpBbsService cmpBbsService = (CmpBbsService) HkUtil
				.getBean("cmpBbsService");
		List<CmpBbs> list = cmpBbsService.getCmpBbsListByCompanyId(companyId,
				0, 6);
		request.setAttribute("cmpbbslist", list);
	}

	public static void loadCmpWebColor(HttpServletRequest request) {
		CmpInfo cmpInfo = (CmpInfo) request.getAttribute("cmpInfo");
		if (cmpInfo != null) {
			CmpWebColor cmpWebColor = new CmpWebColor(cmpInfo.getStyleData());
			request.setAttribute("cmpWebColor", cmpWebColor);
		}
	}

	public static void loadCmpProductSortEachNlevel(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		List<CmpProductSort> l_1_list = cmpProductService
				.getCmpProductSortListByNlevel(companyId, 1);
		List<CmpProductSort> l_2_list = cmpProductService
				.getCmpProductSortListByNlevel(companyId, 2);
		List<CmpProductSort> l_3_list = cmpProductService
				.getCmpProductSortListByNlevel(companyId, 3);
		request.setAttribute("l_1_list", l_1_list);
		request.setAttribute("l_2_list", l_2_list);
		request.setAttribute("l_3_list", l_3_list);
	}

	/**
	 * 把分类一次加载，然后再分1,2,3级
	 * 
	 * @param request
	 *            2010-6-16
	 */
	@SuppressWarnings("unchecked")
	public static void loadCmpProductSortList(HttpServletRequest request) {
		List<CmpProductSort> list = (List<CmpProductSort>) request
				.getAttribute("epp_cmpproductsortlist");
		if (list == null) {
			long companyId = ServletUtil.getLong(request, "companyId");
			CmpProductService cmpProductService = (CmpProductService) HkUtil
					.getBean("cmpProductService");
			list = cmpProductService
					.getCmpProductSortListByCompanyId(companyId);
			request.setAttribute("epp_cmpproductsortlist", list);
		}
		List<CmpProductSort> l_1_list = new ArrayList<CmpProductSort>();
		List<CmpProductSort> l_2_list = new ArrayList<CmpProductSort>();
		List<CmpProductSort> l_3_list = new ArrayList<CmpProductSort>();
		for (CmpProductSort o : list) {
			if (o.getNlevel() == 1) {
				l_1_list.add(o);
			}
			else if (o.getNlevel() == 2) {
				l_2_list.add(o);
			}
			else if (o.getNlevel() == 3) {
				l_3_list.add(o);
			}
		}
		request.setAttribute("l_1_list", l_1_list);
		request.setAttribute("l_2_list", l_2_list);
		request.setAttribute("l_3_list", l_3_list);
	}

	public static void loadCurrentSort(HttpServletRequest request) {
		int sortId = (Integer) request.getAttribute("sortId");
		int current_sortId = (Integer) request.getAttribute("current_sortId");
		CmpProductSort cmpProductSort = (CmpProductSort) request
				.getAttribute("cmpProductSort");
		if (current_sortId > 0) {
			if (sortId == current_sortId) {
				request.setAttribute("is_current_sort", true);
			}
			else {
				if (cmpProductSort != null
						&& cmpProductSort.getParentIds().contains(
								current_sortId)) {
					request.setAttribute("is_current_sort", true);
				}
			}
		}
	}

	public static void loadCmpAd(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpAdService cmpAdService = (CmpAdService) HkUtil
				.getBean("cmpAdService");
		List<CmpAd> cmpadlist = cmpAdService.getCmpAdListByCompanyId(companyId,
				0, 0, 5);
		request.setAttribute("cmpadlist", cmpadlist);
	}

	/**
	 * 加载多语言版本
	 * 
	 * @param request
	 *            2010-6-7
	 */
	public static void loadCmpLanguageRef(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpInfoProcessor cmpInfoProcessor = (CmpInfoProcessor) HkUtil
				.getBean("cmpInfoProcessor");
		List<CmpLanguageRef> cmpLanguageRefList = cmpInfoProcessor
				.getCmpLanguageRefListByCompanyId(companyId, true);
		request.setAttribute("cmpLanguageRefList", cmpLanguageRefList);
	}

	public static void loadCmpSellNetKindList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpSellNetService cmpSellNetService = (CmpSellNetService) HkUtil
				.getBean("cmpSellNetService");
		List<CmpSellNetKind> kindlist = cmpSellNetService
				.getCmpSellNetKindListByCompanyId(companyId);
		request.setAttribute("kindlist", kindlist);
	}

	public static void loadCmpModByCompanyId(HttpServletRequest request) {
		Company o = (Company) request.getAttribute("o");
		CmpInfo cmpInfo = (CmpInfo) request.getAttribute("cmpInfo");
		List<CmpPageMod> cmpPageModList = CmpPageModUtil
				.getHomeCmpPageModListByCmpflgAndTmlflg(o.getCmpflg(), cmpInfo
						.getTmlflg());
		request.setAttribute("cmpPageModList", cmpPageModList);
	}

	public static void loadCmpAdBlockList(HttpServletRequest request, int size) {
		long blockId = (Long) request.getAttribute("data_blockId");
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpModProcessor cmpModProcessor = (CmpModProcessor) HkUtil
				.getBean("cmpModProcessor");
		List<CmpAdBlock> block_cmpadblocklist = cmpModProcessor
				.getCmpAdBlockListByCompanyIdAndBlockId(companyId, blockId,
						true, 0, size);
		request.setAttribute("block_cmpadblocklist", block_cmpadblocklist);
	}

	public static void loadCmpArticleBlockList(HttpServletRequest request,
			boolean needContent, int size) {
		loadCmpArticleBlockList(request, needContent, size, true);
	}

	@SuppressWarnings("unchecked")
	public static void loadCmpArticleBlockList(HttpServletRequest request,
			boolean needContent, int size, boolean sortDesc) {
		List<CmpArticle> block_cmparticlelist = new ArrayList<CmpArticle>();
		long blockId = (Long) request.getAttribute("data_blockId");
		long companyId = ServletUtil.getLong(request, "companyId");
		List<CmpPageBlock> cmppageblocklist = (List<CmpPageBlock>) request
				.getAttribute("cmppageblocklist");
		CmpPageBlock cmpPageBlock = null;
		for (CmpPageBlock o : cmppageblocklist) {
			if (o.getBlockId() == blockId) {
				cmpPageBlock = o;
				break;
			}
		}
		if (cmpPageBlock == null) {
			return;
		}
		if (cmpPageBlock.isAuto()) {
			CmpArticleService cmpArticleService = (CmpArticleService) HkUtil
					.getBean("cmpArticleService");
			if (cmpPageBlock.getNavId() > 0) {
				List<CmpArticle> list = cmpArticleService
						.getCmpArticleListByCompanyIdAndCmpNavOid(companyId,
								cmpPageBlock.getNavId(), null, 0, size);
				block_cmparticlelist.addAll(list);
			}
			else if (cmpPageBlock.getGroupId() > 0) {
				List<CmpArticle> list = cmpArticleService
						.getCmpArticleListByCompanyIdAndGroupId(companyId,
								cmpPageBlock.getGroupId(), 0, size);
				block_cmparticlelist.addAll(list);
			}
			else if (cmpPageBlock.getTagId() > 0) {
				CmpArticleProcessor cmpArticleProcessor = (CmpArticleProcessor) HkUtil
						.getBean("cmpArticleProcessor");
				List<CmpArticleTagRef> tagreflist = cmpArticleProcessor
						.getCmpArticleTagRefListByCompanyIdAndTagId(companyId,
								cmpPageBlock.getTagId(), true, 0, size);
				for (CmpArticleTagRef o : tagreflist) {
					block_cmparticlelist.add(o.getCmpArticle());
				}
			}
		}
		else {
			CmpModProcessor cmpModProcessor = (CmpModProcessor) HkUtil
					.getBean("cmpModProcessor");
			List<CmpArticleBlock> block_cmparticleblocklist = cmpModProcessor
					.getCmpArticleBlockListByCompanyIdAndBlockId(companyId,
							blockId, true, sortDesc, 0, size);
			for (CmpArticleBlock o : block_cmparticleblocklist) {
				block_cmparticlelist.add(o.getCmpArticle());
			}
		}
		if (needContent) {
			CmpArticleService cmpArticleService = (CmpArticleService) HkUtil
					.getBean("cmpArticleService");
			List<Long> idList = new ArrayList<Long>();
			for (CmpArticle o : block_cmparticlelist) {
				idList.add(o.getOid());
			}
			Map<Long, CmpArticleContent> map = cmpArticleService
					.getCmpArticleContentMapInId(idList);
			for (CmpArticle o : block_cmparticlelist) {
				o.setCmpArticleContent(map.get(o.getOid()));
			}
		}
		request.setAttribute("block_cmparticlelist", block_cmparticlelist);
	}

	public static void loadMainCmpArticle(HttpServletRequest request) {
		long main_articleId = (Long) request.getAttribute("main_articleId");
		CmpArticleService cmpArticleService = (CmpArticleService) HkUtil
				.getBean("cmpArticleService");
		CmpArticleContent main_cmpArticleContent = cmpArticleService
				.getCmpArticleContent(main_articleId);
		request.setAttribute("main_cmpArticleContent", main_cmpArticleContent);
	}

	public static void loadCmpArticleTagForPink(HttpServletRequest request,
			int size) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpArticleTagService cmpArticleTagService = (CmpArticleTagService) HkUtil
				.getBean("cmpArticleTagService");
		List<CmpArticleTag> pinktaglist = cmpArticleTagService
				.getCmpArticleTagListByCompanyIdForPink(companyId, 0, size);
		request.setAttribute("pinktaglist", pinktaglist);
	}

	@SuppressWarnings("unchecked")
	public static void loadCmpNavFromArticle(HttpServletRequest request) {
		List<CmpArticle> list = (List<CmpArticle>) request
				.getAttribute("block_cmparticlelist");
		if (list == null) {
			return;
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpArticle o : list) {
			idList.add(o.getCmpNavOid());
		}
		CmpNavService cmpNavService = (CmpNavService) HkUtil
				.getBean("cmpNavService");
		long companyId = ServletUtil.getLong(request, "companyId");
		List<CmpNav> tagnavlist = cmpNavService
				.getCmpNavListByCompanyIdAndInId(companyId, idList);
		request.setAttribute("tagnavlist", tagnavlist);
	}

	public static void loadP2CmpAdList(HttpServletRequest request, int size) {
		CmpAdProcessor cmpAdProcessor = (CmpAdProcessor) HkUtil
				.getBean("cmpAdProcessor");
		long companyId = ServletUtil.getLong(request, "companyId");
		List<CmpAdRef> p2cmpadreflist = cmpAdProcessor
				.getCmpAdRefListByCompanyId(companyId, true, 0, size);
		request.setAttribute("p2cmpadreflist", p2cmpadreflist);
	}

	public static void loadCmpActorRoleList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpActorService cmpActorService = (CmpActorService) HkUtil
				.getBean("cmpActorService");
		List<CmpActorRole> rolelist = cmpActorService
				.getCmpActorRoleListByCompanyId(companyId);
		request.setAttribute("rolelist", rolelist);
	}

	public static void loadCmpSvrKindList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpSvrService cmpSvrService = (CmpSvrService) HkUtil
				.getBean("cmpSvrService");
		List<CmpSvrKind> kindlist = cmpSvrService
				.getCmpSvrKindListByCompanyId(companyId);
		request.setAttribute("kindlist", kindlist);
	}

	/**
	 * 加载页面区块
	 * 
	 * @param request
	 *            2010-7-30
	 */
	public static void loadCmpPageBlock(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpModService cmpModService = (CmpModService) HkUtil
				.getBean("cmpModService");
		List<CmpPageBlock> cmppageblocklist = cmpModService
				.getCmpPageBlockListByCompanyIdAndPageflg(companyId, (byte) 1);
		request.setAttribute("cmppageblocklist", cmppageblocklist);
	}

	public static void loadCmpFrLinkList(HttpServletRequest request) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpFrLinkService cmpFrLinkService = (CmpFrLinkService) HkUtil
				.getBean("cmpFrLinkService");
		List<CmpFrLink> foot_cmpfrlist = cmpFrLinkService
				.getCmpFrLinkListByCompanyId(companyId);
		request.setAttribute("foot_cmpfrlist", foot_cmpfrlist);
	}

	public static void loadCmpNavForHome(HttpServletRequest request, String key) {
		long companyId = ServletUtil.getLong(request, "companyId");
		CmpNavService cmpNavService = (CmpNavService) HkUtil
				.getBean("cmpNavService");
		CmpNav home_cmpNav = cmpNavService.getCmpNavByCompanyIdAndReffunc(
				companyId, CmpNav.REFFUNC_HOME);
		request.setAttribute(key, home_cmpNav);
	}
}