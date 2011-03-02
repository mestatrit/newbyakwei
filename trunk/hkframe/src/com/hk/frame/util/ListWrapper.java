package com.hk.frame.util;

import java.util.ArrayList;
import java.util.List;

public class ListWrapper<T> {
	private int begin;

	private int size;

	private List<T> list;

	public ListWrapper(boolean initList) {
		if (initList) {
			list = new ArrayList<T>();
		}
	}

	public ListWrapper() {//
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}