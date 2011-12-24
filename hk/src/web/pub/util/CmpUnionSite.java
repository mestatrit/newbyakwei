package web.pub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmpUnionSite {
	private List<CmpUnionSiteOrder> cmpUnionSiteOrderList;

	private Map<Integer, CmpUnionSiteOrder> map = new HashMap<Integer, CmpUnionSiteOrder>();

	public CmpUnionSite(String data) {
		cmpUnionSiteOrderList = new ArrayList<CmpUnionSiteOrder>();
		String[] moddata = data.split(";");
		for (int i = 0; i < moddata.length; i++) {
			String[] t = moddata[i].split(":");
			CmpUnionSiteOrder order = new CmpUnionSiteOrder();
			order.setModule(Integer.parseInt(t[0]));
			if (t[1].equals("1")) {
				order.setHide(true);
			}
			cmpUnionSiteOrderList.add(order);
			map.put(order.getModule(), order);
		}
	}

	public boolean isHideModule(int module) {
		CmpUnionSiteOrder order = map.get(module);
		if (order == null) {
			return true;
		}
		return order.isHide();
	}

	public List<CmpUnionSiteOrder> getCmpUnionSiteOrderList() {
		return cmpUnionSiteOrderList;
	}
}