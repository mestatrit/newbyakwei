package com.hk.svr.pub;

import java.util.List;

import org.junit.Test;

import com.hk.frame.util.P;
import com.taobao.api.TaobaoApiException;
import com.taobao.api.taobaoke.model.Item;
import com.taobao.api.taobaoke.model.TaobaokeItemDetail;

public class TaobaoUtilTest {

	@Test
	public void testGetTaobaokeItemDetail() throws TaobaoApiException,
			TaoBaoAccessLimitException {
		String num_iid = "";
		TaobaokeItemDetail taobaokeItemDetail = TaoBaoUtil
				.getTaobaokeItemDetailByNum_iidForTaobaoKe(num_iid, null, false);
		if (taobaokeItemDetail != null) {
			Item item = taobaokeItemDetail.getItem();
			if (item != null) {
				P.println(item.getTitle());
			}
		}
	}

	@Test
	public void testGetTaobaokeItemDetailList() throws TaobaoApiException,
			TaoBaoAccessLimitException {
		String v = "1452102315,6000006124,1451747061,6000004718,1452096045,6000005904,6000006602,1451836043,1451836037,6000006204";
		List<TaobaokeItemDetail> list = TaoBaoUtil
				.getTaobaokeItemDetailListByNum_iidsForTaobaoKe(v, null, false);
		for (TaobaokeItemDetail o : list) {
			P.println(o.getItem().getTitle());
		}
	}

	@Test
	public void testGetTaobaokeItemDetailList2() throws TaobaoApiException,
			TaoBaoAccessLimitException {
		String[] num_iid = { "1452102315", "6000006124" };
		List<TaobaokeItemDetail> list = TaoBaoUtil
				.getTaobaokeItemDetailListByNum_iidsForTaobaoKe(num_iid, null,
						false);
		for (TaobaokeItemDetail o : list) {
			P.println(o.getItem().getTitle());
		}
	}
}