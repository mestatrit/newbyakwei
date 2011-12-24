package com.etbhk.web.itemcat.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Item_Cat;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_Item_CatService;
import com.hk.svr.pub.TaoBaoAccessLimitException;
import com.hk.svr.pub.TaoBaoUtil;
import com.taobao.api.TaobaoApiException;

@Component("/tb/itemcat")
public class ItemCatAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_Item_CatService tb_Item_CatService;

	private final Log log = LogFactory.getLog(ItemCatAction.class);

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 从淘宝api中获取所有分类到火酷数据库中
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws TaobaoApiException
	 * @throws TaoBaoAccessLimitException
	 * @throws Exception
	 *             2010-8-29
	 */
	public String init(HkRequest req, HkResponse resp)
			throws TaobaoApiException {
		List<Tb_Item_Cat> list = null;
		do {
			list = this.tb_Item_CatService.getTb_Item_CatListForNoDeal(0, 1000);
			for (Tb_Item_Cat o : list) {
				try {
					List<Tb_Item_Cat> children = TaoBaoUtil.getItemCatList(o
							.getCid(), true);
					this.insertIntoDB(children);
					o.setChild_update(Tb_Item_Cat.CHILD_UPDATE_Y);
					this.tb_Item_CatService.updateTb_Item_Cat(o);
					log.info("get data success [ " + o.getCid() + "|"
							+ o.getName() + " ]");
				}
				catch (TaoBaoAccessLimitException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(1300);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		while (list.size() > 0);
		resp.sendHtml("init  cat ok");
		return null;
	}

	private void insertIntoDB(List<Tb_Item_Cat> list) {
		Tb_Item_Cat tmp = null;
		for (Tb_Item_Cat o : list) {
			tmp = this.tb_Item_CatService.getTb_Item_Cat(o.getCid());
			if (tmp == null) {
				this.tb_Item_CatService.createTb_Item_Cat(o);
			}
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-9-23
	 */
	public String initrootcat(HkRequest req, HkResponse resp) throws Exception {
		// 从淘宝取根目录数据，存入数据库
		List<Tb_Item_Cat> list = TaoBaoUtil.getItemCatList(0, true);
		Tb_Item_Cat tmp = null;
		for (Tb_Item_Cat o : list) {
			tmp = this.tb_Item_CatService.getTb_Item_Cat(o.getCid());
			if (tmp == null) {
				this.tb_Item_CatService.createTb_Item_Cat(o);
			}
		}
		resp.sendHtml("init root cat ok");
		return null;
	}
}