package com.hk.svr;

import java.util.List;

import com.hk.bean.taobao.Tb_Ask_Cat;

public interface Tb_Ask_CatService {

	/**
	 * 创建分类,如果存在parent_cid,name相同的数据则不能创建
	 * 
	 * @param tbAskCat
	 * @return true:创建成功,false:存在相同数据，创建失败
	 *         2010-9-14
	 */
	boolean createTb_Ask_Cat(Tb_Ask_Cat tbAskCat);

	/**
	 * 修改分类,如果存在parent_cid,name相同的数据则不能修改
	 * 
	 * @param tbAskCat
	 * @return true:修改成功,false:存在相同数据，修改失败
	 *         2010-9-14
	 */
	boolean updateTb_Ask_Cat(Tb_Ask_Cat tbAskCat);

	void deleteTb_Ask_Cat(Tb_Ask_Cat tbAskCat);

	Tb_Ask_Cat getTb_Ask_Cat(long cid);

	/**
	 * 获取分类数据
	 * 
	 * @param parent_cid =0时获取大分类,>0时获取某个节点下的分类
	 * @param name 为空时不参与查询,不为空时模糊查询关键字
	 * @return
	 *         2010-9-14
	 */
	List<Tb_Ask_Cat> getTb_Ask_CatList(long parent_cid, String name);
	/**
	 * 获取
	 * @return
	 * 2010-9-15
	 */
	List<Tb_Ask_Cat> getTb_Ask_CatList();
}