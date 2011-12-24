package com.hk.svr.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.hk.bean.Notice;

public class NoticeProcessor {
	private List<Notice> list = new CopyOnWriteArrayList<Notice>();

	private int size;

	public void setSize(int size) {
		this.size = size;
	}

	public void addNotice(List<Notice> olist) {
		list.addAll(olist);
	}

	public List<Notice> getNotictListAndRemove() {
		int localSize = size;
		if (localSize < 1) {
			localSize = 1;
		}
		if (localSize > list.size()) {
			localSize = list.size();
		}
		int sum = 0;
		List<Notice> tmplist = new ArrayList<Notice>();
		for (Notice o : list) {
			if (sum == localSize) {
				break;
			}
			tmplist.add(o);
		}
		list.removeAll(tmplist);
		return tmplist;
	}
}