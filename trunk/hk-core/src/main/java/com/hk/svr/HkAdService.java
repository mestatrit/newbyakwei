package com.hk.svr;

import java.util.List;

import com.hk.bean.HkAd;
import com.hk.bean.HkAdView;

/**
 * 火酷广告业务
 * 
 * @author jyy
 */
public interface HkAdService {

	/**
	 * 添加广告数据
	 * 
	 * @param hkAd
	 *            2010-5-4
	 */
	void createHkAd(HkAd hkAd);

	/**
	 * 更新广告数据
	 * 
	 * @param hkAd
	 * @return
	 *         Err.HKAD_TOTALVIEWCOUNT_LESSTHAN_VIEWCOUNT:页面总展现次数不能小于现有展现次数,Err.SUCCESS
	 *         :更新成功
	 *         2010-5-4
	 */
	int updateHkAd(HkAd hkAd);

	void deleteHkAd(long oid);

	/**
	 * 查看用户已经发布的广告
	 * 
	 * @param userId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-4
	 */
	List<HkAd> getHkAdByUserId(long userId, int begin, int size);

	/**
	 * 查看某个广告当天内是否有同一个ip的数据
	 * 
	 * @param adoid
	 * @param ip
	 * @param udate
	 * @return
	 *         2010-5-4
	 */
	HkAdView getHkAdViewByAdoidAndIpAndUdate(long adoid, String ip, int udate);

	void createHkAdView(HkAdView hkAdView);

	HkAd getHkAd(long oid);

	void updateUseflg(long oid, byte useflg);

	/**
	 * 获得不在idList的有效广告集合
	 * 
	 * @param cityId 地区id
	 * @param showflg 展示方式 @see {@link HkAd#SHOWFLG_CHAR}
	 *            {@link HkAd#SHOWFLG_IMG}
	 * @param idList
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-7
	 */
	List<HkAd> getHkAdListByCityIdForUsefulNotInId(int cityId, byte showflg,
			List<Long> idList, int begin, int size);

	/**
	 * 统计不在idList的有效广告
	 * 
	 * @param cityId 地区id
	 * @param showflg 展示方式 @see {@link HkAd#SHOWFLG_CHAR}
	 *            {@link HkAd#SHOWFLG_IMG}
	 * @param idList 广告id集合
	 * @return
	 *         2010-5-7
	 */
	int countHkAdByCityIdForUsefulNotInId(int cityId, byte showflg,
			List<Long> idList);

	/**
	 * 例如有5个
	 * 我有一个已经看了3次了
	 * 我再看，虽然随机到了这个广告，但是看我已经看了3次了，这个时候自动显示另外一个
	 * 相当于你要记录广告每天的展示人数和pv
	 * 一天有多少人看了他的广告
	 * 一天有多少人次看了他的广告，人次是按照1个人不能看一个广告三次计算的
	 * 例如一个广告，展示1000次，在火酷总共展示了3天展示完毕
	 * 第一天，我看了5次
	 * 这个时候，有效是3次
	 * 对于广告厂商，我也是仅仅看了他3次
	 * 广告展现之后，计算广告浏览人数以及pv统计
	 * 
	 * @param viewerId 用户（包括未登录）唯一识别id
	 * @param adoid 广告id
	 * @param ip 访问的ip地址
	 * @param add 增加的浏览数
	 *            2010-5-7
	 */
	void viewHkAd(String viewerId, long adoid, String ip, int add);

	List<Long> getHkAdIdListByViewerIdAndUdateForViewOk(String viewerId, int udate,int ucount);
}