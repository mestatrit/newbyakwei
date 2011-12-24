package com.hk.svr.pub;

import org.apache.lucene.document.Document;

public interface BatchIdx {
	public int getSize();
	public Document getDocument(int i);
}