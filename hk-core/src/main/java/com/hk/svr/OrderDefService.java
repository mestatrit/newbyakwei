package com.hk.svr;

import java.util.List;
import com.hk.bean.HkObjKeyTagOrderDef;
import com.hk.bean.HkObjOrderDef;

/**
 * 设置竞排价格的最低价
 * 
 * @author akwei
 */
public interface OrderDefService {
	/**
	 * 不存在就创建，存在就更新
	 * 
	 * @param hkObjOrderDef
	 */
	void updateHkObjOrderDef(HkObjOrderDef hkObjOrderDef);

	/**
	 * 不存在就创建，存在就更新
	 * 
	 * @param hkObjKeyTagOrderDef
	 * @return
	 */
	void updateHkObjKeyTagOrderDef(HkObjKeyTagOrderDef hkObjKeyTagOrderDef);

	void deleteHkObjOrderDef(int oid);

	void deleteHkObjKeyTagOrderDef(long oid);

	/**
	 * 获取导航中竞排价格最低价数据
	 * 
	 * @param kind 0为忽略此条件
	 * @param kindId 0为忽略此条件
	 * @param cityId 0为忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkObjOrderDef> getHkObjOrderDefList(int kind, int kindId, int cityId,
			int begin, int size);

	/**
	 * 获取关键词的竞排价格数据
	 * 
	 * @param tagId 0为忽略此条件
	 * @param cityId 0为忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HkObjKeyTagOrderDef> getHkObjKeyTagOrderDefList(long tagId,
			int cityId, int begin, int size);

	HkObjKeyTagOrderDef getHkObjKeyTagOrderDef(long tagId, int cityId);

	HkObjOrderDef getHkObjOrderDef(byte kind, int kindId, int cityId);
}