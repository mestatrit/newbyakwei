package com.hk.svr;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hk.bean.AuthCompany;
import com.hk.bean.BizCircle;
import com.hk.bean.CmdCmp;
import com.hk.bean.CmpAdminHkbLog;
import com.hk.bean.CmpHkbLog;
import com.hk.bean.CmpOtherInfo;
import com.hk.bean.CmpRecruit;
import com.hk.bean.CmpTemplate;
import com.hk.bean.CmpWatch;
import com.hk.bean.CmpZoneInfo;
import com.hk.bean.Company;
import com.hk.bean.CompanyAward;
import com.hk.bean.CompanyBizCircle;
import com.hk.bean.CompanyFeed;
import com.hk.bean.CompanyMoney;
import com.hk.bean.CompanyReview;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.HkObj;
import com.hk.bean.UserCmpReview;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.pub.HkDataCompositor;

/**
 * 涉及前台获取足迹集合的方法都是获取审核通过的数据
 * 
 * @author yuanwei
 */
public interface CompanyService {

	/**
	 * 创建企业信息
	 * 
	 * @param company
	 */
	void createCompany(Company company, String ip);

	/**
	 * 请使用 {@link CompanyProcessor#updateCompany(Company, int)
	 * ,CompanyProcessor#updateCompanyMap(long, double, double)}
	 * 
	 * @param company
	 *            2010-7-20
	 */
	void updateCompany(Company company);

	/**
	 * 获得企业对象
	 * 
	 * @param companyId
	 * @return
	 */
	Company getCompany(long companyId);

	Company getCompanyByName(String name);

	/**
	 * 创建企业点评，可以包含打分
	 * 
	 * @param companyReview
	 */
	void createCompanyReview(CompanyReview companyReview);

	void updateCompanyReview(CompanyReview companyReview);

	void updateCompanyReviewCheckflg(long labaId, byte checkflg);

	CompanyReview getCompanyReview(long labaId);

	void deleteCompanyReview(long labaId);

	/**
	 * 企业打分
	 * 
	 * @param userId
	 * @param score
	 */
	void gradeCompany(long userId, long companyId, int score);

	List<CompanyReview> getCompanyReviewListByCompanyId(long companyId,
			int begin, int size);

	List<CompanyReview> getCompanyReviewListByCompanyId(long companyId,
			byte checkflg, int begin, int size);

	List<CompanyReview> getCompanyReviewListByCompanyIdNoUser(long companyId,
			long noUserId, int begin, int size);

	List<CompanyFeed> getCompanyFeedListByCity(int cityId, int begin, int size);

	List<CompanyFeed> getCompanyFeedList(int begin, int size);

	Map<Long, Company> getCompanyMapInId(List<Long> idList);

	/**
	 * 按照报到数量排序
	 * 
	 * @param cityId
	 *            为0可忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyListByPcityId(int pcityId, int begin, int size);

	List<Company> getCompanyListByPcityIdAndKindId(int pcityId, long kindId,
			int begin, int size);

	/**
	 * 查看不在这个地区的足迹
	 * 
	 * @param pcityId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-4-9
	 */
	List<Company> getCompanyListByNoPcityId(int pcityId, int begin, int size);

	List<Company> getCompanyListByCircleId(int circleId, int begin, int size);

	List<CompanyBizCircle> getCompanyBizCircleByCompanyId(long companyId);

	/**
	 * join 操作，获取数据
	 * 
	 * @param companyId
	 * @return
	 */
	List<BizCircle> getBizCircleByCompanyId(long companyId);

	/**
	 * 创建或保存用户足迹状态
	 * 
	 * @param companyId
	 * @param userId
	 * @param status
	 *            2010-4-13
	 */
	void createCompanyUserStatus(long companyId, long userId, byte status);

	// List<Company> getCompanyListByCompanyUserStatusRef(long companyId,
	// byte status, int begin, int size);
	void updateHeadPath(long companyId, String headPath);

	List<Company> getCompanyListByCreaterId(long createrId, int begin, int size);

	int countCompanyByCreaterId(long createrId);

	List<Company> getCompanyListByUserId(long userId, int begin, int size);

	List<Company> getCompanyListByUserId(long userId);

	/**
	 * 根据企业名字和申请状态模糊查询申请数据，查询条件可以不写，默认为查询所有
	 * 
	 * @param name
	 * @param checkStatus
	 * @param begin
	 * @param size
	 * @return
	 */
	List<AuthCompany> getAuthCompanyList(String name, byte mainStatus,
			int begin, int size);

	List<AuthCompany> getAuthCompanyListByMainStatus(byte mainStatus,
			int begin, int size);

	void setCompanyUser(long companyId, long userId);

	void clearCompanyUser(long companyId, long userId);

	void checkAuthCompany(AuthCompany authCompany);

	void createAuthCompany(AuthCompany authCompany);

	void updateAuthCompany(AuthCompany authCompany);

	void deleteAuthCompany(long sysId);

	/**
	 * 足迹高级查询(后台查询使用，前台查询都为审核通过的数据)
	 * 
	 * @param name
	 * @param companyStatus
	 *            如果状态为审核通过，则包括所有审核通过的足迹
	 * @param cityId
	 *            地区选线都为0则不查询地区
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyListByCdn(String name, byte companyStatus,
			byte freezeflg, int pcityId, int begin, int size);

	/**
	 * 修改企业状态
	 * 
	 * @param companyId
	 * @param companyStatus
	 */
	void updateCompanyStatus(long companyId, byte companyStatus);

	AuthCompany getAuthCompany(long sysId);

	// void authCompany(AuthCompany authCompany);
	/**
	 * 查询企业提交奖励
	 * 
	 * @param status
	 *            -1:查询所有 0:未奖励 1:已奖励
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CompanyAward> getCompanyAwardList(byte status, int begin, int size);

	void updateCompanyAward(CompanyAward companyAward);

	CompanyAward getCompanyAward(long companyId);

	CompanyMoney getFirstCompanyMoney(long companyId);

	CompanyMoney getCompanyMoney(long sysId);

	List<CompanyMoney> getCompanyMoneyList(long companyId, int begin, int size);

	void createCompanyMoney(CompanyMoney companyMoney);

	CompanyUserScore getCompanyUserScore(long companyId, long userId);

	List<CompanyReview> getUserCompanyReviewList(long companyId, long userId,
			int begin, int size);

	// void tempUpdateScore();
	/**
	 * 高级搜索()以后用lucene替代，只显示审核通过的
	 * 
	 * @param kindId
	 *            0为忽略条件
	 * @param cityId
	 *            0为忽略条件
	 * @param name
	 *            null为忽略条件
	 * @param noIdList
	 *            null为忽略条件
	 * @param compositor
	 *            null为默认排序
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyListEx(int kindId, int cityId, String name,
			Set<Long> noIdSet, HkDataCompositor compositor, int begin, int size);

	List<Company> getCompanyListByMapInfo(int cityId, long noCompanyId,
			int minMarkerX, int maxMarderX, int minMarkerY, int maxMarderY,
			int begin, int size);

	void createCmpWatch(long companyId, long userId);

	void deleteCmpWatch(long companyId, long userId);

	CmpWatch getCmpWatch(long companyId, long userId);

	void setCmpWatchDuty(long companyId, long userId);

	List<CmpWatch> getCmpWatchList(long companyId, int begin, int size);

	void chgFreezeflgCompany(long companyId, byte freezeflg);

	void createCmpRecruit(CmpRecruit cmpRecruit);

	void deleteCmpRecruit(long companyId);

	CmpRecruit getCmpRecruit(long companyId);

	/**
	 * @param companyId
	 * @param file
	 * @param file2 第二个logo的文件
	 * @throws IOException
	 *             2010-8-4
	 */
	void updateLogo(long companyId, File file, File file2) throws IOException;

	CmpTemplate getCmpTemplate(long companyId);

	CompanyUserStatus getCompanyUserStatus(long companyId, long userId);

	// HkObj getHkObj(long objId);
	Company getLastCreateCompany(long createrId);

	// void initCmpZoneInfo();
	List<CmpZoneInfo> getCmpZoneInfoList();

	void addHkb(CmpHkbLog cmpHkbLog);

	void createCmpAdminHkbLog(CmpAdminHkbLog cmpAdminHkbLog);

	List<Company> getCompanyListInId(List<Long> idList, String orderSql);

	List<Company> getCompanyListNearBy(long companyId, int parentKindId,
			int cityId, double markerX, double markerY, int begin, int size);

	List<HkObj> getHkObjListInId(List<Long> idList);

	Map<Long, HkObj> getHkObjMapInId(List<Long> idList);

	boolean addMoney(long companyId, int money);

	/**
	 * 分组查询足迹分类在某地区中的足迹数量，如果是全国，则查询所有
	 * 
	 * @param cityId
	 *            如果为0，就忽略此条件
	 * @return
	 */
	Map<Integer, Integer> getCompanyKindDataInfo(int cityId);

	/**
	 * 展示给用户，屏蔽停业的
	 * 
	 * @param parentKindId
	 * @param kindId
	 * @param cityId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyList(int parentKindId, int kindId, int cityId,
			int begin, int size);

	int countCompany(int parentKindId, int kindId, int cityId);

	List<Company> getCompanyListForCool(int kindId, int cityId, int begin,
			int size);

	/**
	 * 热门足迹
	 * 
	 * @param kindId
	 *            为0忽略此条件
	 * @param cityId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyListForHot(int kindId, int cityId, int begin,
			int size);

	/**
	 * 喜欢这个足迹的人
	 * 
	 * @param companyId
	 *            足迹id
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CompanyUserScore> getCompanyUserScoreListForLikeIt(long companyId,
			int begin, int size);

	List<UserCmpReview> getUserCmpReviewList(long userId, int begin, int size);

	int countUserCmpReview(long userId);

	List<CompanyReview> getCompanyReviewListInId(List<Long> idList);

	Map<Long, CompanyReview> getCompanyReviewMapInId(List<Long> idList);

	List<CompanyReview> getCompanyReviewListByUserId(long userId, int begin,
			int size);

//	void updateMemberCount(long companyId, int memberCount);

	void updatePsearchType(long companyId, byte psearchType);

	void updateUid(long companyId, long uid);

	List<CompanyUserStatus> getCompanyUserStatusListByUserIdAndUserStatus(
			long userId, byte status, int begin, int size);

	/**
	 * 创建推荐足迹
	 * 
	 * @param cmdCmp
	 * @return true创建成功 false已经存在，不再次创建
	 */
	boolean createCmdCmp(CmdCmp cmdCmp);

	/**
	 * 删除推荐足迹
	 * 
	 * @param oid
	 */
	void deleteCmdCmp(long oid);

	/**
	 * 根据条件查询推荐足迹
	 * 
	 * @param pcityId
	 *            为0忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmdCmp> getCmdCmpList(int pcityId, int begin, int size);

	/**
	 * @param pcityId
	 *            为0忽略此条件
	 * @return
	 */
	int countCmdCmp(int pcityId);

	/**
	 * 大家喜欢去
	 * 
	 * @param pcityId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Company> getCompanyListForUserLike(int pcityId, int begin, int size);

	CmdCmp getCmdCmp(long oid);

	List<Company> getCompanyListWithSearch(String key, int begin, int size);

	List<Long> getCompanyIdListWithSearch(int pcityId, String key, int begin,
			int size);

	List<Long> getCompanyIdListWithSearchNotPcityId(int pcityId, String key,
			int begin, int size);

	void createSearchIndex();

	/**
	 * 模糊查询含有关键字的足迹id
	 * 
	 * @param key
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Long> getCompanyIdListWithSearch(String key, int begin, int size);

	/**
	 * 删除足迹，以及相关足迹的一切数据
	 * 
	 * @param companyId
	 */
	void deleteCompany(long companyId);

	/**
	 * 更新城市id
	 */
	void updateCmpPCityIdData();

	/**
	 * 查询联盟分类的足迹
	 * 
	 * @param kindId
	 * @return
	 */
	List<Company> getCompanyListByUnionKindId(long kindId, int begin, int size);

	List<Company> getCompanyListByPcityIdAndNameLike(int pcityId, String name,
			int begin, int size);

	/**
	 * 对足迹标签进行索引
	 */
	void createCmpTagSearchIndex();

	List<Long> getCompanyIdListFromCmpTagRefWithSearchNotPcityId(int pcityId,
			String key, int begin, int size);

	void deleteCompanyUserStatus(long companyId, long userId);

	void updateCompanyUserStatus(CompanyUserStatus companyUserStatus);

	/**
	 * 更新足迹中报到数量
	 * 
	 * @param companyId
	 * @param add
	 *            2010-4-12
	 */
	void addCheckInCount(long companyId, int add);

	List<Company> getCompanyListAll();

	void initCmpZoneInfo();

	List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndUserStatus(
			long companyId, byte userStatus, int begin, int size);

	List<CompanyUserStatus> getCompanyUserStatusListByCompanyIdAndDoneStatus(
			long companyId, byte doneStatus, int begin, int size);

	void updateCompanyMap(long companyId, double markerX, double markerY);

	void updateCompanyUserId(long companyId, long userId);

	void createCmpOtherInfo(CmpOtherInfo cmpOtherInfo);

	void updateCmpOtherInfo(CmpOtherInfo cmpOtherInfo);

	CmpOtherInfo getCmpOtherInfo(long companyId);

	void updateWorkCountByCompanyId(long companyId, int workCount);

	/**
	 * 获取最新的某分类的足迹
	 * 
	 * @param kindId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-22
	 */
	List<Company> getCompanyListByKindIdForNew(int kindId, int begin, int size);

	/**
	 * 获取预约成功最多的某分类的足迹
	 * 
	 * @param kindId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-8-22
	 */
	List<Company> getCompanyListByKindIdForWorkCount(int kindId, int begin,
			int size);
}