package com.hk.svr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_CmdItem;
import com.hk.bean.taobao.Tb_Domain_Item;
import com.hk.bean.taobao.Tb_Item;
import com.hk.bean.taobao.Tb_Item_Cat_Ref;
import com.hk.bean.taobao.Tb_Item_Img;
import com.hk.bean.taobao.Tb_Item_Score;

/**
 * @author akwei
 */
public interface Tb_ItemService {

	void createTb_Item(Tb_Item tbItem);

	void updateTb_Item(Tb_Item tbItem);

	void deleteTb_Item(long itemid);

	Tb_Item getTb_Item(long itemid);

	Tb_Item getTb_ItemByNum_iid(long num_iid);

	List<Tb_Item> getTb_ItemListInId(List<Long> idList);

	Map<Long, Tb_Item> getTb_ItemMapInId(List<Long> idList);

	void createTb_Item_Cat_Ref(Tb_Item_Cat_Ref tbItemCatRef);

	void deleteTb_Item_Cat_Ref(long oid);

	void createTb_Item_Img(Tb_Item_Img tbItemImg);

	void deleteTb_Item_Img(long imgid);

	void deleteTb_Item_ImgByNum_iid(long num_iid);

	List<Tb_Item_Img> getTb_Item_ImgListByItemid(long itemid);

	/**
	 * 如果没有就创建，有就更新
	 * 
	 * @param tbItemScore
	 *            2010-9-1
	 */
	void saveTb_Item_Score(Tb_Item_Score tbItemScore);

	void deleteTb_Item_Score(long oid);

	void deleteTb_Item_ScoreByItemid(long itemid);

	Tb_Item_Score getTb_Item_ScoreByItemidAndUserid(long itemid, long userid);

	int sumScoreFromTb_Item_ScoreByItemId(long itemid);

	int countTb_Item_ScoreByItemid(long itemid);

	List<Tb_Item> getTb_ItemListForNew(int begin, int size);

	void addCmt_num(long itemid, int add);

	/**
	 * 得分高到低排序
	 * 
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-6
	 */
	List<Tb_Item> getTb_ItemListForKu(int begin, int size);

	List<Tb_Item> getTb_ItemListForUpdate(Date outer_time, int begin, int size);

	/**
	 * 获得商品集合，顺序按照自然顺序
	 * 
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-20
	 */
	List<Tb_Item> getTb_ItemList(int begin, int size);

	List<Tb_Item> getTb_ItemListForHuo(int begin, int size);

	void createTb_Domain_Item(Tb_Domain_Item tbDomainItem);

	List<Tb_Domain_Item> getTb_Domain_ItemListForHuo(int begin, int size);

	List<Tb_Domain_Item> getTb_Domain_ItemListForKu(int begin, int size);

	List<Tb_Domain_Item> getTb_Domain_ItemListForNew(int begin, int size);

	void updateTb_Domain_Item(Tb_Domain_Item tbDomainItem);

	void updateTb_Domain_ItemByItemid(long itemid, int hkscore, int huo_status,
			long volume);

	void commendItem(long itemid);

	void deleteTb_CmdItem(Tb_CmdItem tbCmdItem);

	List<Tb_CmdItem> getTb_CmdItemList(boolean buildItem, int begin, int size);

	List<Tb_Item> getTb_ItemListCdn(String title, int begin, int size);

	Tb_CmdItem getTb_CmdItem(long oid);
}