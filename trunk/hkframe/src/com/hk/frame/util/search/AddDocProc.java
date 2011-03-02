package com.hk.frame.util.search;

import org.apache.lucene.document.Document;

public interface AddDocProc<T> {

	Document buildDoc(T t);
}