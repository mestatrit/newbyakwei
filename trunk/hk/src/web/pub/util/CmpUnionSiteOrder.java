package web.pub.util;

import java.util.ArrayList;
import java.util.List;

public class CmpUnionSiteOrder {
	private int module;

	private boolean hide;

	public static List<CmpUnionSiteOrder> createList(String data) {
		List<CmpUnionSiteOrder> list = new ArrayList<CmpUnionSiteOrder>();
		String[] moddata = data.split(";");
		for (int i = 0; i < moddata.length; i++) {
			String[] t = moddata[i].split(":");
			CmpUnionSiteOrder order = new CmpUnionSiteOrder();
			order.setModule(Integer.parseInt(t[0]));
			if (t[1].equals("1")) {
				order.setHide(true);
			}
			list.add(order);
		}
		return list;
	}

	private int nextModule;

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public int getNextModule() {
		return nextModule;
	}

	public void setNextModule(int nextModule) {
		this.nextModule = nextModule;
	}
}